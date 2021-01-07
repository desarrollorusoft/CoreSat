package ar.com.cognisys.sat.core.modelo.dao.cuenta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;

public class DaoCuentaComercio extends DaoCuenta<CuentaComercios> {

	private static final String SQL_RECUPERACION_CUENTA_PAGINADA =
			"Select skip ? first ? rs.C_Cuenta as Cuenta, m.d_razon_social as Descripcion, c.descripcion as Alias, rs.f_alta_da as fecha_habilitacion\n"
			+ "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, recaudaciones:Comercios m, recaudaciones:regimen_simplificado_cuentas rs\n"
			+ "Where C.Id_Usuario = ?\n"
			+ "And C.Tipo_Cuenta = 2\n"
			+ "And m.n_cuit = C.Cuenta\n"
			+ "and rs.c_cuenta = m.c_cuenta\n"
			+ "And m.C_baja = 0 \n"
			+ "Order By C.Cuenta ";

	private static final String SQL_RECUPERACION_CUENTA = "Select rs.C_Cuenta as Cuenta, m.d_razon_social as Descripcion, c.descripcion as Alias, rs.f_alta_da as fecha_habilitacion\n"
														+ "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, recaudaciones:Comercios m, recaudaciones:regimen_simplificado_cuentas rs\n"
														+ "Where C.Id_Usuario = ?\n" 
														+ "And C.Tipo_Cuenta = 2\n" 
														+ "And m.n_cuit = C.Cuenta\n"
														+ "and rs.c_cuenta = m.c_cuenta\n"
														+ "Order By C.Cuenta ";
	
	private static final String SP_RECUPERO_TOTALES = "{call recaudaciones:sp_ddjj_muestra_deuda_cog( ? )}";
	
	private static final String SQL_RECUPERO_TOTALES = "select nvl((select sum(i_capital+i_recargo+i_multa) from recaudaciones:ddjj_datos_deuda d where d.c_cuenta = c.c_cuenta and vencido = 'N'), 0) as Total_A_Vencer,\n" +
													   "  nvl((select sum(i_capital+i_recargo+i_multa) from recaudaciones:ddjj_datos_deuda d where d.c_cuenta = c.c_cuenta and vencido = 'S'), 0) as Total_Vencida\n" +
													   "from recaudaciones:comercios c\n" +
													   "where c.c_cuenta = ?  ";
	//TODO FEDE agregar el alias
	private static final String SQL_BUSQUEDA_CUENTA = "select 'COMERCIO' as Categoria, '' as Alias, c.c_cuenta as cuenta, c.d_razon_social as descripcion, (select f_habilitacion from recaudaciones:hab_com where c.c_cuenta between c_cuenta_d and c_cuenta_h) as fecha_habilitacion, \n"
													+ "nvl((select 1 from boleta_electronica be where c.c_cuenta = be.c_cuenta and be.c_sistema = 1),0) as be, c.n_cuit \n"
													+ "from recaudaciones:comercios c\n"
													+ "where c.c_cuenta = ?";

	private static final String SQL_MAS_DATOS_CUENTA = "Select (select f_habilitacion from recaudaciones:hab_com where c.c_cuenta between c_cuenta_d and c_cuenta_h) as fecha_habilitacion, c.d_razon_social as razon_social\n"
													 + "from recaudaciones:comercios c\n"
													 + "where c.c_cuenta = ?";

	private static final String SQL_RECUPERAR_CUENTAS_CUIT = "Select m.c_Cuenta as Cuenta, m.d_razon_social as Descripcion, '' as Alias, (select f_habilitacion from recaudaciones:hab_com where c.c_cuenta between c_cuenta_d and c_cuenta_h) as fecha_habilitacion, \n"
														   + "nvl((select 1 from boleta_electronica be where m.c_cuenta = be.c_cuenta and be.c_sistema = 1),0) as be \n"
														   + "From recaudaciones:Comercios m \n"
														   + "Where m.n_cuit = ? \n"
														   + "Order By m.c_Cuenta ";


	public DaoCuentaComercio(Connection connection) {
		super(connection);
	}

	@Override
	protected void cargarParametrosSQLBusquedaCuenta(String cuenta, PreparedStatement ps) throws SQLException {
		ps.setInt(1, new Integer(cuenta));
	}
	
	@Override
	public void cargarDatosCuenta(CuentaComercios cuenta) throws ExcepcionControladaError {
		super.cargarContribuyente(cuenta);
		this.cargarMasDatos(cuenta);
	}

	@Override
	protected String getSQLRecuperacionCuentasPaginada() {
		return SQL_RECUPERACION_CUENTA_PAGINADA;
	}

	private void cargarMasDatos(CuentaComercios cuenta) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_MAS_DATOS_CUENTA );
			ps.setInt(1, cuenta.getNumero());
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				cuenta.setFechaHabilitacion(rs.getDate("fecha_habilitacion"));
				cuenta.setRazonSocial(rs.getString("razon_social"));
			}
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}

	@Override
	protected CuentaComercios generarCuenta(ResultSet rs) throws SQLException {
		String nroCuit = rs.getString("n_cuit");
		return FactoryCuenta.generarIntanciaCompletaComercios(rs.getInt("Cuenta"), rs.getString("Descripcion"), rs.getString("Alias"), false, nroCuit == null? null:nroCuit.trim());
	}

	@Override
	protected void cargarParamtrosSPTotales(CuentaComercios cuenta, CallableStatement cs) throws SQLException {
		cs.setInt(1, cuenta.getNumero());
	}
	
	public List<CuentaComercios> recuperarCuentas(String cuit) throws ExcepcionControladaError {
		
		List<CuentaComercios> lista = new ArrayList<CuentaComercios>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_RECUPERAR_CUENTAS_CUIT );
			ps.setString(1, cuit);
			
			rs = ps.executeQuery();
			
			while (rs.next())
				lista.add( this.generarCuenta(rs) );
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return lista;
	}

	protected void cargarTotales(CuentaComercios cuenta) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( this.getSQLRecuperacionTotales() );
			ps.setInt( 1, cuenta.getNumero() );
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

	@Override
	protected String getSQLBusquedaCuenta() {
		return SQL_BUSQUEDA_CUENTA;
	}
	
	@Override
	protected String getSQLRecuperacionCuentas() {
		return SQL_RECUPERACION_CUENTA;
	}

	@Override
	protected String getSPTotales() {
		return SP_RECUPERO_TOTALES;
	}

	@Override
	protected String getSQLRecuperacionTotales() {
		return SQL_RECUPERO_TOTALES;
	}
	
	@Override
	protected TiposCuentas getTipoCuenta() {
		return TiposCuentas.COMERCIOS;
	}

	@Override
	protected void cargarFiltros(PreparedStatement ps, String filtro) throws SQLException {
		String filtroUpper = filtro == null ? "" : filtro.toUpperCase();
		ps.setString(3, filtroUpper);
		ps.setString(4, filtroUpper);
	}

	@Override
	protected String getSQLRecuperacionCuentasFiltradas() {
		return "   select skip ? first ? 'COMERCIO' as Categoria, '' as Alias, c.c_cuenta as cuenta, c.d_razon_social as descripcion, n_cuit " +
				" from comercios c " +
				"where ( '' = ? or ('' || n_cuit) = ? ) " +
				"and c_baja = 0";
	}

	@Override
	protected String getSQLCantidadFiltrada() {
		return "select count(*) as Cantidad " +
				"from comercios c " +
				"where c_baja = 0 " +
				"And ( '' = ? or ('' || n_cuit) = ? )";
	}
}
