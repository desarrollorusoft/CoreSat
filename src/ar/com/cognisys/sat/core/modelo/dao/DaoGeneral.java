package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.estadisticas.general.EstadisticasGeneralCompleto;
import ar.com.cognisys.sat.core.modelo.comun.estadisticas.general.EstadisticasGeneralOrigen;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.estadisticas.FactoryEstadisticaGeneralCompleta;
import ar.com.cognisys.sat.core.modelo.factory.estadisticas.FactoryEstadisticaGeneralOrigen;

public class DaoGeneral extends Dao {

	public DaoGeneral(Connection connection) {
		super(connection);
	}
	
	public List<EstadisticasGeneralOrigen> recuperarWeb(String escala, Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		if ( escala.toLowerCase().equals("Dias".toLowerCase())  ) 
			return recuperarWebDias(fechaDesde, fechaHasta);
		else if ( escala.toLowerCase().equals("Semanas".toLowerCase()) )
			return recuperarWebSemana(fechaDesde, fechaHasta);
		else if ( escala.toLowerCase().equals("Meses".toLowerCase()) )
			return recuperarWebMes(fechaDesde, fechaHasta);
		
		return new ArrayList<EstadisticasGeneralOrigen>();
	}
	
	private List<EstadisticasGeneralOrigen> recuperarWebDias(Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralOrigen> lista = new ArrayList<EstadisticasGeneralOrigen>();
		try {
			
			long interval = 1000 * 60 * 60 * 24; //un dia en milisegundos
			long curTime = fechaDesde.getTime();
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			while (curTime <= endTime) {				
				lista.add(FactoryEstadisticaGeneralOrigen.
						generarInstancia(new Long(0), 
										formatter.format(new Date(curTime)), 
										new Long(0), 
										new Long(0), 
										new Long(0)));
				curTime += interval;
			}
			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_DIAS.getSql());
			ps.setString(1, new java.sql.Date(initTime).toString());
			ps.setString(2, new java.sql.Date(endTime).toString());
			ps.setInt(3, 1);
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				String fecha = rs.getString("Fecha");
				for ( EstadisticasGeneralOrigen e : lista  ) {
					if ( e.getFecha().equals(fecha) ) {
						e.setInterbanking(rs.getLong("Interbanking"));
						e.setPmc(rs.getLong("PMC"));
						e.setRecibo(rs.getLong("Recibos"));
						e.setTarjetaCredito(rs.getLong("Credito"));
						break;
					}
				}
			}
			Collections.reverse(lista);
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos web", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}
	
	private List<EstadisticasGeneralOrigen> recuperarWebSemana(Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralOrigen> lista = new ArrayList<EstadisticasGeneralOrigen>();
		try {
			
			long interval = 1000 * 60 * 60 * 24 * 7; //una semana en milisegundos
			long curTime = fechaDesde.getTime();
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");			
			Calendar calendar = Calendar.getInstance();	
			
			while (curTime <= endTime) {				
				calendar.setTime(new Date(curTime));
				while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
					calendar.add(Calendar.DATE, -1); 
					curTime = calendar.getTimeInMillis();
				}
				lista.add(FactoryEstadisticaGeneralOrigen.
						generarInstancia(new Long(0),
										formatter.format(calendar.getTime()), 
										new Long(0), 
										new Long(0), 
										new Long(0)));
				curTime += (interval);
			}
			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_SEMANAS.getSql());
			ps.setString(1, new java.sql.Date(initTime).toString());
			ps.setString(2, new java.sql.Date(endTime).toString());
			ps.setInt(3, 1);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String fecha = rs.getString("Fecha");
				String[] partes = fecha.split("/");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(partes[0]));        
				cal.set(Calendar.YEAR, Integer.parseInt(partes[1]));
				while (cal.get(Calendar.DAY_OF_WEEK) > cal.getFirstDayOfWeek()) {
					cal.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
				}		
				fecha = formatter.format(cal.getTime());
				// buscar el valor en el array, y actualizar el valor del long
				for (EstadisticasGeneralOrigen e : lista) {
					if (e.getFecha().equals(fecha)) {
						e.setInterbanking(rs.getLong("Interbanking"));
						e.setPmc(rs.getLong("PMC"));
						e.setRecibo(rs.getLong("Recibos"));
						e.setTarjetaCredito(rs.getLong("Credito"));
						break;
					}
				}
			}
			Collections.reverse(lista);
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos web", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}

	private List<EstadisticasGeneralOrigen> recuperarWebMes(Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralOrigen> lista = new ArrayList<EstadisticasGeneralOrigen>();
		try {
			
			long interval = 1000 * 60 * 60 * 24;
			long curTime = fechaDesde.getTime();
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			Calendar calendar = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();
			int multiplicadorMes = 0;	
			
			while (curTime <= endTime) {
				calendar.setTime(new Date(curTime));
				int diaDelMes = calendar.get(Calendar.DAY_OF_MONTH);
				if (diaDelMes > 1) {
					calendar.add(Calendar.DATE, -(diaDelMes - 1)); 
					curTime = calendar.getTimeInMillis();
				}				
				multiplicadorMes = this.getDiasDelMes(calendar);				
				lista.add(FactoryEstadisticaGeneralOrigen.
						generarInstancia(new Long(0),
								formatter.format(calendar.getTime()), 
								new Long(0), 
								new Long(0), 
								new Long(0)));
				curTime += (interval * multiplicadorMes);
			}			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_MESES.getSql());
			ps.setString(1, new java.sql.Date(initTime).toString());
			ps.setString(2, new java.sql.Date(endTime).toString());
			ps.setInt(3, 1);
			rs = ps.executeQuery();	
			
			while (rs.next()) {
				String fecha = rs.getString("Fecha");				
				String[] partes = fecha.split("/");				
				cal.set(Calendar.YEAR, Integer.parseInt(partes[1]));
				cal.set(Calendar.MONTH, Integer.parseInt(partes[0]) - 1);
				int diaDelMes = cal.get(Calendar.DAY_OF_MONTH);
				if (diaDelMes > 1) {
					cal.add(Calendar.DATE, -(diaDelMes - 1)); 
				}
				fecha = formatter.format(cal.getTime());

				// buscar el valor en el array, y actualizar el valor del long
				for (EstadisticasGeneralOrigen e : lista) {
					if (e.getFecha().equals(fecha)) {
						e.setInterbanking(rs.getLong("Interbanking"));
						e.setPmc(rs.getLong("PMC"));
						e.setRecibo(rs.getLong("Recibos"));
						e.setTarjetaCredito(rs.getLong("Credito"));
						break;
					}
				}
			}
			Collections.reverse(lista);
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos web", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}

	public List<EstadisticasGeneralOrigen> recuperarMobile(String escala, Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		if ( escala.toLowerCase().equals("Dias".toLowerCase())  ) 
			return recuperarMobileDias(fechaDesde, fechaHasta);
		else if ( escala.toLowerCase().equals("Semanas".toLowerCase()) )
			return recuperarMobileSemana(fechaDesde, fechaHasta);
		else if ( escala.toLowerCase().equals("Meses".toLowerCase()) )
			return recuperarMobileMes(fechaDesde, fechaHasta);
		return new ArrayList<EstadisticasGeneralOrigen>();
	}
	
	private List<EstadisticasGeneralOrigen> recuperarMobileDias(Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralOrigen> lista = new ArrayList<EstadisticasGeneralOrigen>();
		try {
			
			long interval = 1000 * 60 * 60 * 24; //un dia en milisegundos
			long curTime = fechaDesde.getTime();
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			while (curTime <= endTime) {				
				lista.add(FactoryEstadisticaGeneralOrigen.
						generarInstancia(new Long(0), 
										formatter.format(new Date(curTime)), 
										new Long(0), 
										new Long(0), 
										new Long(0)));
				curTime += interval;
			}
			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_DIAS.getSql());
			ps.setString(1, new java.sql.Date(initTime).toString());
			ps.setString(2, new java.sql.Date(endTime).toString());
			ps.setInt(3, 2);
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				String fecha = rs.getString("Fecha");
				for ( EstadisticasGeneralOrigen e : lista  ) {
					if ( e.getFecha().equals(fecha) ) {
						e.setInterbanking(rs.getLong("Interbanking"));
						e.setPmc(rs.getLong("PMC"));
						e.setRecibo(rs.getLong("Recibos"));
						e.setTarjetaCredito(rs.getLong("Credito"));
						break;
					}
				}
			}
			Collections.reverse(lista);
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos web", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}

	private List<EstadisticasGeneralOrigen> recuperarMobileSemana(Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralOrigen> lista = new ArrayList<EstadisticasGeneralOrigen>();
		try {
			
			long interval = 1000 * 60 * 60 * 24 * 7; //una semana en milisegundos
			long curTime = fechaDesde.getTime();
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");			
			Calendar calendar = Calendar.getInstance();	
			
			while (curTime <= endTime) {				
				calendar.setTime(new Date(curTime));
				while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
					calendar.add(Calendar.DATE, -1); 
					curTime = calendar.getTimeInMillis();
				}
				lista.add(FactoryEstadisticaGeneralOrigen.
						generarInstancia(new Long(0),
										formatter.format(calendar.getTime()), 
										new Long(0), 
										new Long(0), 
										new Long(0)));
				curTime += (interval);
			}
			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_SEMANAS.getSql());
			ps.setDate(1, new java.sql.Date(initTime));
			ps.setDate(2, new java.sql.Date(endTime));
			ps.setInt(3, 2);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String fecha = rs.getString("Fecha");
				String[] partes = fecha.split("/");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(partes[0]));        
				cal.set(Calendar.YEAR, Integer.parseInt(partes[1]));
				while (cal.get(Calendar.DAY_OF_WEEK) > cal.getFirstDayOfWeek()) {
					cal.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
				}		
				fecha = formatter.format(cal.getTime());
				// buscar el valor en el array, y actualizar el valor del long
				for (EstadisticasGeneralOrigen e : lista) {
					if (e.getFecha().equals(fecha)) {
						e.setInterbanking(rs.getLong("Interbanking"));
						e.setPmc(rs.getLong("PMC"));
						e.setRecibo(rs.getLong("Recibos"));
						e.setTarjetaCredito(rs.getLong("Credito"));
						break;
					}
				}
			}
			Collections.reverse(lista);
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos web", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}

	private List<EstadisticasGeneralOrigen> recuperarMobileMes(Date fechaDesde, Date fechaHasta)  throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralOrigen> lista = new ArrayList<EstadisticasGeneralOrigen>();
		try {
			
			long interval = 1000 * 60 * 60 * 24;
			long curTime = fechaDesde.getTime();
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			Calendar calendar = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();
			int multiplicadorMes = 0;	
			
			while (curTime <= endTime) {
				calendar.setTime(new Date(curTime));
				int diaDelMes = calendar.get(Calendar.DAY_OF_MONTH);
				if (diaDelMes > 1) {
					calendar.add(Calendar.DATE, -(diaDelMes - 1)); 
					curTime = calendar.getTimeInMillis();
				}				
				multiplicadorMes = this.getDiasDelMes(calendar);				
				lista.add(FactoryEstadisticaGeneralOrigen.
						generarInstancia(new Long(0),
								formatter.format(calendar.getTime()), 
								new Long(0), 
								new Long(0), 
								new Long(0)));
				curTime += (interval * multiplicadorMes);
			}			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_MESES.getSql());
			ps.setDate(1, new java.sql.Date(initTime));
			ps.setDate(2, new java.sql.Date(endTime));
			ps.setInt(3, 2);
			rs = ps.executeQuery();	
			
			while (rs.next()) {
				String fecha = rs.getString("Fecha");				
				String[] partes = fecha.split("/");				
				cal.set(Calendar.YEAR, Integer.parseInt(partes[1]));
				cal.set(Calendar.MONTH, Integer.parseInt(partes[0]) - 1);
				int diaDelMes = cal.get(Calendar.DAY_OF_MONTH);
				if (diaDelMes > 1) {
					cal.add(Calendar.DATE, -(diaDelMes - 1)); 
				}
				fecha = formatter.format(cal.getTime());

				// buscar el valor en el array, y actualizar el valor del long
				for (EstadisticasGeneralOrigen e : lista) {
					if (e.getFecha().equals(fecha)) {
						e.setInterbanking(rs.getLong("Interbanking"));
						e.setPmc(rs.getLong("PMC"));
						e.setRecibo(rs.getLong("Recibos"));
						e.setTarjetaCredito(rs.getLong("Credito"));
						break;
					}
				}
			}
			Collections.reverse(lista);
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos web", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}

	public List<EstadisticasGeneralCompleto> recuperarListaCompleta(String escala, Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		if ( escala.toLowerCase().equals("Dias".toLowerCase())  ) 
			return recuperarCompletoDias(fechaDesde, fechaHasta);
		else if ( escala.toLowerCase().equals("Semanas".toLowerCase()) )
			return recuperarCompletoSemana(fechaDesde, fechaHasta);
		else if ( escala.toLowerCase().equals("Meses".toLowerCase()) )
			return recuperarCompletoMes(fechaDesde, fechaHasta);
		return new ArrayList<EstadisticasGeneralCompleto>();
	}

	private List<EstadisticasGeneralCompleto> recuperarCompletoDias(Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralCompleto> lista = new ArrayList<EstadisticasGeneralCompleto>();
		try {
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			SimpleDateFormat formatBD = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat formatView = new SimpleDateFormat("dd/MM/yyyy");

			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAL_COMPLETA_DIAS.getSql());
			ps.setString(1, new java.sql.Date(initTime).toString());
			ps.setString(2, new java.sql.Date(endTime).toString());
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				String fecha = rs.getString("Dia");
				fecha = formatView.format(formatBD.parse(fecha));
				EstadisticasGeneralCompleto e = FactoryEstadisticaGeneralCompleta.
						generarInstancia(rs.getString("Medio"), 
										 fecha, 
										 rs.getString("Tributo"), 
										 rs.getLong("Web"), 
										 rs.getLong("Mobile"),
										 rs.getLong("Total"));
				lista.add(e);
			}
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos del listado completo", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}

	private List<EstadisticasGeneralCompleto> recuperarCompletoSemana(Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralCompleto> lista = new ArrayList<EstadisticasGeneralCompleto>();
		try {
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAL_COMPLETA_SEMANAS.getSql());
			ps.setString(1, new java.sql.Date(initTime).toString());
			ps.setString(2, new java.sql.Date(endTime).toString());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String fecha = rs.getString("Semana");
				String[] partes = fecha.split("/");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(partes[1]));        
				cal.set(Calendar.YEAR, Integer.parseInt(partes[0]));
				while (cal.get(Calendar.DAY_OF_WEEK) > cal.getFirstDayOfWeek()) {
					cal.add(Calendar.DATE, -1);
				}		
				fecha = formatter.format(cal.getTime());
				EstadisticasGeneralCompleto e = FactoryEstadisticaGeneralCompleta.
						generarInstancia(rs.getString("Medio"),
										 fecha, 
										 rs.getString("Tributo"), 
										 rs.getLong("Web"), 
										 rs.getLong("Mobile"),
										 rs.getLong("Total"));
				lista.add(e);
			}
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos del listado completo", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}

	private List<EstadisticasGeneralCompleto> recuperarCompletoMes(Date fechaDesde, Date fechaHasta) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EstadisticasGeneralCompleto> lista = new ArrayList<EstadisticasGeneralCompleto>();
		try {
			long initTime = fechaDesde.getTime();
			long endTime = fechaHasta.getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			
			ps = this.getConnection().prepareStatement(Queries.RECUPERAR_ESTADISTICAS_GENERAL_COMPLETA_MESES.getSql());
			ps.setString(1, new java.sql.Date(initTime).toString());
			ps.setString(2, new java.sql.Date(endTime).toString());
			rs = ps.executeQuery();	
			
			while (rs.next()) {
				String fecha = rs.getString("Mes");				
				String[] partes = fecha.split("/");				
				cal.set(Calendar.YEAR, Integer.parseInt(partes[0]));
				cal.set(Calendar.MONTH, Integer.parseInt(partes[1]) - 1);
				int diaDelMes = cal.get(Calendar.DAY_OF_MONTH);
				if (diaDelMes > 1) {
					cal.add(Calendar.DATE, -(diaDelMes - 1)); 
				}
				fecha = formatter.format(cal.getTime());
				EstadisticasGeneralCompleto e = FactoryEstadisticaGeneralCompleta.
						generarInstancia(rs.getString("Medio"),
										 fecha, 
										 rs.getString("Tributo"), 
										 rs.getLong("Web"), 
										 rs.getLong("Mobile"),
										 rs.getLong("Total"));
				lista.add(e);
			}
			return lista;
		} catch (Exception e) {
			throw new ExcepcionControladaError("Error al conseguir los datos del listado completo", e);
		} finally {
			if ( rs != null ) { try { rs.close(); }catch (Exception e) {} }
			if ( ps != null ) { try { ps.close(); }catch (Exception e) {} }
		}
	}
	
	private int getDiasDelMes(Calendar calendar) throws ExcepcionControladaError {

		int mes = calendar.get(Calendar.MONTH);
		int anio = calendar.get(Calendar.YEAR);
		
		switch (mes) {
		case 0: // Enero
		case 2: // Marzo
		case 4: // Mayo
		case 6: // Julio
		case 7: // Agosto
		case 9: // Octubre
		case 11: // Diciembre
			return 31;
		case 3: // Abril
		case 5: // Junio
		case 8: // Septiembre
		case 10: // Noviembre
			return 30;
		case 1: // Febrero
			if (((anio % 100 == 0) && (anio % 400 == 0)) || ((anio % 100 != 0) && (anio % 4 == 0)))
				return 29; // Anio Bisiesto
			else
				return 28;
		default:
			throw new ExcepcionControladaError("Mes inválido", null);
		}
	}
}