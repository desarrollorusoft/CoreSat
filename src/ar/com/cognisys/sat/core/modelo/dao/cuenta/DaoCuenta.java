package ar.com.cognisys.sat.core.modelo.dao.cuenta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.dao.DaoCuentasContribuyente;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.InvalidaDatoCuentaException;

public abstract class DaoCuenta<CTA extends Cuenta> extends Dao {

	public DaoCuenta(Connection connection) {
		super(connection);
	}
	
	public CTA buscarCuenta(String cuenta) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( this.getSQLBusquedaCuenta() );
			this.cargarParametrosSQLBusquedaCuenta(cuenta, ps);

			rs = ps.executeQuery();

			if (rs.next()) {
				CTA cta = this.generarCuenta(rs);
				this.cargarDatosCuenta(cta);
				return cta;
			} else
				throw new InvalidaDatoCuentaException( cuenta );
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}
	
	protected abstract void cargarParametrosSQLBusquedaCuenta(String cuenta, PreparedStatement ps) throws SQLException;

	public abstract void cargarDatosCuenta(CTA cuenta) throws ExcepcionControladaError; 
	
	public List<Cuenta> recuperarCuentas(Integer idUsuario) throws ExcepcionControladaError {
		
		List<CTA> lista = this.obtenerLista(idUsuario);
		
		for (CTA cuenta : lista) {
			this.cargarTotalesDeuda(cuenta);
			this.cargarContribuyente(cuenta);
		}
		
		return castear(lista);
	}

	public List<Cuenta> recuperarCuentas(Integer idUsuario, int page) throws ExcepcionControladaError {

		List<CTA> lista = this.obtenerLista(idUsuario, page);

		for (CTA cuenta : lista) {
			this.cargarTotalesDeuda(cuenta);
			this.cargarContribuyente(cuenta);
		}

		return castear(lista);
	}

	public void cargarTotalesDeuda(CTA cuenta) throws ExcepcionControladaError {
		this.ejecutarSPTotales(cuenta);
		this.cargarTotales(cuenta);
	}
	
	protected void cargarContribuyente(CTA cuenta) throws ErrorRecuperacionDatosException {
		Contribuyente c = (new DaoCuentasContribuyente(this.getConnection())).recuperarContribuyente(this.getTipoCuenta(), cuenta.getNumero());
		cuenta.setContribuyente(c);
	}

	private List<CTA> obtenerLista(Integer idUsuario) throws ExcepcionControladaError {

		List<CTA> lista = new ArrayList<CTA>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( this.getSQLRecuperacionCuentas() );
			ps.setInt(1, idUsuario);
			rs = ps.executeQuery();

			while (rs.next())
				lista.add( this.generarCuenta(rs) );
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return lista;
	}

	/**
	 * Metodo que obtiene una lista paginada cada 10 registros
	 * @param idUsuario
	 * @param page
	 * @return Lista de Cuentas
	 * @throws ExcepcionControladaError
	 */
	private List<CTA> obtenerLista(Integer idUsuario, int page) throws ExcepcionControladaError {

		List<CTA> lista = new ArrayList<CTA>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( this.getSQLRecuperacionCuentasPaginada() );
			ps.setInt(1, page*10);
			ps.setInt(2, 10);
			ps.setInt(3, idUsuario);

			rs = ps.executeQuery();

			while (rs.next())
				lista.add( this.generarCuenta(rs) );
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return lista;
	}

	protected abstract String getSQLRecuperacionCuentasPaginada();

	public List<CTA> obtenerLista(Integer inicio, String filtro, Integer cantidad) throws ExcepcionControladaError {

		List<CTA> lista = new ArrayList<CTA>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( this.getSQLRecuperacionCuentasFiltradas() );
			ps.setInt(1, inicio);
			ps.setInt(2, cantidad);
			this.cargarFiltros(ps, filtro);
			rs = ps.executeQuery();

			while (rs.next()) {
				CTA cuenta = this.generarCuenta( rs );
				this.cargarContribuyente( cuenta );
				lista.add( cuenta );
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return lista;
	}
	
	protected abstract void cargarFiltros(PreparedStatement ps, String filtro) throws SQLException;

	protected abstract CTA generarCuenta(ResultSet rs) throws SQLException, ExcepcionControladaError, ExcepcionControladaAlerta;

	private void ejecutarSPTotales(CTA cuenta) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		
		try {
			cs = this.getConnection().prepareCall( this.getSPTotales() );
			this.cargarParamtrosSPTotales(cuenta, cs);
			
			cs.execute();
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(cs);
		}
	}

	protected abstract void cargarParamtrosSPTotales(CTA cuenta, CallableStatement cs) throws SQLException;

	protected void cargarTotales(CTA cuenta) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( this.getSQLRecuperacionTotales() );
			rs = ps.executeQuery();

			if (rs.next()) {
				cuenta.setaVencer(rs.getFloat("Total_A_Vencer"));
				cuenta.setDeuda(rs.getFloat("Total_Vencida"));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}
	
	private List<Cuenta> castear(List<CTA> lista) {
		
		List<Cuenta> listaCast = new ArrayList<Cuenta>();
		listaCast.addAll( lista );
		
		return listaCast;
	}
	
	public Integer recuperarCantidadCuentas(String filtro, Integer cantidadFiltros) throws ExcepcionControladaError {
		
		Integer cantidad = 0;
		String filtroUpper = filtro == null ? "" : filtro.toUpperCase();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( this.getSQLCantidadFiltrada() );

			for (int i = 0; i < cantidadFiltros; i++)
				ps.setString(i + 1, filtroUpper);
			
			rs = ps.executeQuery();

			if (rs.next()) 
				cantidad = rs.getInt("Cantidad");
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return cantidad;
	}
	
	protected abstract String getSQLBusquedaCuenta();
	
	protected abstract String getSQLRecuperacionCuentas();
	
	protected abstract String getSPTotales();
	
	protected abstract String getSQLRecuperacionTotales();
	
	protected abstract String getSQLRecuperacionCuentasFiltradas();
	
	protected abstract TiposCuentas getTipoCuenta();
	
	protected abstract String getSQLCantidadFiltrada();
}