package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.pago.DatosPago;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.factory.pago.FactoryDatosPago;

public class DaoPagosOnline extends Dao {

	public DaoPagosOnline(Connection con) {
		super(con);
	}
	
	public Integer recuperarCantidadDatosPago(String medioPago, Float importeDesde, Float importeHasta,
			Date fechaDesde, Date fechaHasta, String filtro) throws ErrorRecuperacionDatosException {
		
		Integer cantidad = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String queryBase = "Select Count(*) As Cantidad \n " +
						 "From Web_Recau:Pago_Online_Cogmvl \n " +
						 "Where 1 = 1 \n " ;

		String queryCompleta = this.armarQueryCompleta(queryBase, medioPago, importeDesde, importeHasta, fechaDesde, fechaHasta, filtro);
		try {
			ps = this.getConnection().prepareStatement(queryCompleta);
			
			rs = ps.executeQuery();
			
            if (rs.next()) {
            
            	cantidad = rs.getInt("Cantidad");
				}
            
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {try {rs.close();} catch (SQLException e) {}}
			if (ps != null) {try {ps.close();} catch (SQLException e) {}}
		}
		
		
		return cantidad;
	}

	public List<DatosPago> recuperarListaDatosPago(Integer inicio, Integer cantidad, String medioPago, 
			Float importeDesde, Float importeHasta, Date fechaDesde, Date fechaHasta, String filtro) throws ErrorRecuperacionDatosException {
		
		List<DatosPago> lista = new ArrayList<DatosPago>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		String queryBase = "Select Skip ? First ? \n " +
						 "    Id, Tipo, Datos, Tasa, Cuenta, Razon, Recibo, Fecha_Pago, Valor, Fecha \n " +
						 "From Web_Recau:Pago_Online_Cogmvl \n " +
						 "Where 1 = 1 \n ";
	
		String queryCompleta = this.armarQueryCompleta(queryBase, medioPago, importeDesde, importeHasta, fechaDesde, fechaHasta, filtro);
		
		try {
			ps = this.getConnection().prepareStatement(queryCompleta);
			ps.setInt(1, inicio);
			ps.setInt(2, cantidad);
			
			rs = ps.executeQuery();
			
            while (rs.next()) {
            
            	lista.add(FactoryDatosPago.generarInstanciaCompleta(rs.getLong("id"), 
            														rs.getString("tipo"),
																	rs.getString("datos"), 
																	rs.getString("tasa"), 
																	rs.getString("cuenta"), 
																	rs.getString("razon"), 
																	rs.getString("recibo"), 
																	rs.getDate("fecha_pago"), 
																	rs.getFloat("valor"), 
																	rs.getDate("fecha")));
				}
            
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {try {rs.close();} catch (SQLException e) {}}
			if (ps != null) {try {ps.close();} catch (SQLException e) {}}
		}
		
		return lista;
	}
	

	public Float recuperarMaximoImporte(String medioPago, Date fechaDesde, Date fechaHasta, String filtro) throws ErrorRecuperacionDatosException {

		Float maximo = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String queryBase = "Select Max(Valor) As Maximo \n " +
						 "From Web_Recau:Pago_Online_Cogmvl \n " +
						 "Where 1 = 1 \n " ;

		String queryCompleta = this.armarQueryMaximo(queryBase, medioPago, fechaDesde, fechaHasta, filtro);
		try {
			ps = this.getConnection().prepareStatement(queryCompleta);
			
			rs = ps.executeQuery();
			
            if (rs.next()) {
            	maximo = rs.getFloat("Maximo");
				}
            
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {try {rs.close();} catch (SQLException e) {}}
			if (ps != null) {try {ps.close();} catch (SQLException e) {}}
		}
		
		
		return maximo;
	}

	private String armarQueryCompleta(String queryBase, String medioPago, Float importeDesde, Float importeHasta, 
			Date fechaDesde, Date fechaHasta, String filtro) {
		
		String query = "";
		
		if (this.hayMedioPago(medioPago)) {
			query = query + "And Upper(Tipo) = '" + medioPago.trim().toUpperCase() + "' \n";
		}
		if (this.hayImportes(importeDesde, importeHasta)) {
			query = query + "And (Valor >= " + importeDesde + " And Valor <= " + importeHasta + ") \n";
		}
		if (this.hayFechas(fechaDesde, fechaHasta)){
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
			@SuppressWarnings("deprecation")
			Date fechaHastaCorrecta = new GregorianCalendar(fechaHasta.getYear() + 1900, fechaHasta.getMonth(), fechaHasta.getDate() + 1).getTime();
			
			query = query + "And (Fecha Between To_Date('" + formatoFecha.format(fechaDesde) + "', '%d-%m-%Y') And To_Date('" + formatoFecha.format(fechaHastaCorrecta) + "', '%d-%m-%Y')) \n";
		}
		
		if (this.hayFiltro(filtro)){
			query = query + "And (Upper(Tasa) Like '%" + filtro.trim().toUpperCase() + "%' \n" +
							"Or Upper(Cuenta) Like '%" + filtro.trim().toUpperCase() + "%' \n" + 
							"Or Upper(Razon) Like '%" + filtro.trim().toUpperCase() + "%' \n" + 
							"Or Upper(Recibo) Like '%" + filtro.trim().toUpperCase() + "%' ) \n";
		}
		
		query = query + "Order By 1 Desc \n";
		
		return queryBase + query;
	}
	
	private String armarQueryMaximo(String queryBase, String medioPago, Date fechaDesde, Date fechaHasta, String filtro) {
		
		String query = "";
		
		if (this.hayMedioPago(medioPago)) {
			query = query + "And Upper(Tipo) = '" + medioPago.trim().toUpperCase() + "' \n";
		}
		if (this.hayFechas(fechaDesde, fechaHasta)){
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
			@SuppressWarnings("deprecation")
			Date fechaHastaCorrecta = new GregorianCalendar(fechaHasta.getYear() + 1900, fechaHasta.getMonth(), fechaHasta.getDay() + 1).getTime();
			
			query = query + "And (Fecha Between To_Date('" + formatoFecha.format(fechaDesde) + "', '%d-%m-%Y') And To_Date('" + formatoFecha.format(fechaHastaCorrecta) + "', '%d-%m-%Y')) \n";
		}
		if (this.hayFiltro(filtro)){
			query = query + "And (Upper(Tasa) Like '%" + filtro.trim().toUpperCase() + "%' \n" +
							"Or Upper(Cuenta) Like '%" + filtro.trim().toUpperCase() + "%' \n" + 
							"Or Upper(Razon) Like '%" + filtro.trim().toUpperCase() + "%' \n" + 
							"Or Upper(Recibo) Like '%" + filtro.trim().toUpperCase() + "%' ) \n";
		}
		
		query = query + "Order By 1 Desc \n";
		
		return queryBase + query;
	}

	private boolean hayFiltro(String filtro) {
		return filtro!=null && !filtro.trim().isEmpty();
	}

	private boolean hayFechas(Date fechaDesde, Date fechaHasta) {
		return fechaDesde!=null && fechaHasta!=null;
	}

	private boolean hayMedioPago(String medioPago) {
		return medioPago!=null && !medioPago.trim().isEmpty();
	}

	private boolean hayImportes(Float importeDesde, Float importeHasta) {
		return importeDesde!=null && importeDesde >= 0 && importeHasta!=null && importeHasta >= 0;
	}

}
