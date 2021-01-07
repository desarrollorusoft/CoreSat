package ar.com.cognisys.sat.core.modelo.dao.cuenta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;

public class DaoCuentaAutomotor extends DaoCuenta<CuentaVehiculos> {

	private static final String SQL_RECUPERACION_CUENTA_PAGINADA =
			"Select skip ? first ? 'VEHICULOS' Categoria, '' as Alias, V.C_Cuenta as Cuenta,\n"
			+ "Decode(TRIM(nvl(c_dominio_mercosur,'')),'',Decode(TRIM(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur) as Dominio,\n"
			+ "Decode(TRIM(nvl(c_dominio_mercosur,'')),'',Decode(TRIM(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur)||' '||N_Modelo as Descripcion, \n"
			+ "nvl((select 1 from boleta_electronica be where V.c_cuenta = be.c_cuenta and be.c_sistema = 31),0) as be \n"
			+ "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, recaudaciones:Vehiculos V, recaudaciones:Contrib_Cuentas Cc, recaudaciones:Contribuyentes Co\n"
			+ "Where C.Id_Usuario = ? \n"
			+ "And C.tipo_cuenta = 3 \n"
			+ "And V.c_baja = 0 \n"
			+ "And (c_dominio_mercosur = c.cuenta OR c_dominio_actual = c.cuenta OR c_dominio_original = c.cuenta)\n"
			+ "And Cc.C_Cuenta = V.C_Cuenta  \n"
			+ "And C_Sistema = 31 \n"
			+ "And Co.C_Documento = Cc.C_Documento \n"
			+ "And Co.N_Documento = Cc.N_Documento \n"
			+ "And V.C_baja = 0 \n"
			+ "Order By C.Cuenta";

	private static final String SQL_RECUPERACION_CUENTA = "Select 'VEHICULOS' Categoria, '' as Alias, V.C_Cuenta as Cuenta,\n"
														+ "Decode(TRIM(nvl(c_dominio_mercosur,'')),'',Decode(TRIM(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur) as Dominio,\n"
														+ "Decode(TRIM(nvl(c_dominio_mercosur,'')),'',Decode(TRIM(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur)||' '||N_Modelo as Descripcion, \n"
														+ "nvl((select 1 from boleta_electronica be where V.c_cuenta = be.c_cuenta and be.c_sistema = 31),0) as be \n"
														+ "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, recaudaciones:Vehiculos V, recaudaciones:Contrib_Cuentas Cc, recaudaciones:Contribuyentes Co\n"
														+ "Where C.Id_Usuario = ? \n"
														+ "And C.tipo_cuenta = 3 \n"
														+ "And V.c_baja = 0 \n"
														+ "And (c_dominio_mercosur = c.cuenta OR c_dominio_actual = c.cuenta OR c_dominio_original = c.cuenta)\n"
														+ "And Cc.C_Cuenta = V.C_Cuenta  \n"
														+ "And C_Sistema = 31 \n"
														+ "And Co.C_Documento = Cc.C_Documento \n"
														+ "And Co.N_Documento = Cc.N_Documento \n"
														+ "Order By C.Cuenta";
	
	private static final String SP_RECUPERO_TOTALES = "{call sp_veh_carga_mov_cogmvl( ? )}";
	
	private static final String SQL_RECUPERO_TOTALES = "Select (Nvl(A.I_Capital, 0) + Nvl(A.I_Recargo, 0) + Nvl(A.I_Multa, 0)) As Total_A_Vencer, \n "
												     + "       (Nvl(V.I_Capital, 0) + Nvl(V.I_Recargo, 0) + Nvl(V.I_Multa, 0)) As Total_Vencida \n "
												     + "From Tmp_Veh_Totales A, Tmp_Veh_Totalesv V ";

	//TODO FEDE HACER QUE SE ENCUENTRE EL ALIAS
	private static final String SQL_BUSQUEDA_CUENTA = "Select 'VEHICULOS' Categoria, '' as Alias, C_Cuenta as Cuenta, \n"
												    + "Decode(TRIM(nvl(c_dominio_mercosur,'')),'',Decode(TRIM(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur) as Dominio, \n"
												    + "Decode(TRIM(nvl(c_dominio_mercosur,'')),'',Decode(TRIM(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur)||' '||N_Modelo as descripcion, \n"
													+ "nvl((select 1 from boleta_electronica be where V.c_cuenta = be.c_cuenta and be.c_sistema = 31),0) as be \n"
												    + "From Vehiculos V \n"
												    + "Where (c_dominio_mercosur = ? OR c_dominio_actual = ? OR c_dominio_original = ?) \n"
												    + "And c_baja = 0 ";
	
	private static final String SP_RECUPERAR_DATOS = "{call sp_pad_veh_rod_cogmvl( ? , ? ) }";
	
	private static final String SQL_RECUPERAR_DATOS = "Select * from tmp_pad_vehiculos";

	private static final String SQL_BUSQUEDA_CUENTAS_FILTRO =
			"select skip ? first ?\n" +
					"'VEHICULOS' alias,\n" +
					"decode(trim(nvl(c_dominio_mercosur,'')),'',decode(trim(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur)||' '||n_modelo as descripcion, decode(trim(nvl(c_dominio_mercosur,'')),'',decode(trim(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur) as dominio,\n" +
					"cc.c_documento, cc.n_documento,\n" +
					"co.d_nombre, co.d_apellido, c.d_calle, co.n_nro, co.c_piso, co.c_dpto, co.c_postal, co.d_telefono, co.d_fax, v.c_cuenta as cuenta, \n" +
					"nvl((select 1 from boleta_electronica be where V.c_cuenta = be.c_cuenta and be.c_sistema = 31),0) as be \n" +
					"from vehiculos v, contrib_cuentas cc, contribuyentes co, calles c\n" +
					"where cc.c_sistema = 31\n" +
					"and v.c_baja = 0\n" +
					"and v.c_cuenta = cc.c_cuenta\n" +
					"and co.c_calle = c.c_calle\n" +
					"and co.c_documento = cc.c_documento\n" +
					"and co.n_documento = cc.n_documento\n" +
					"and v.c_cuenta in ( select c_cuenta\n" +
					"from vehiculos vh ,\n" +
					"web_recau:usuario_cuentas_rel_cogmvl uc\n" +
					"where vh.c_dominio_actual = uc.cuenta\n" +
					"and uc.tipo_cuenta = 3\n" +
					"union\n" +
					"select c_cuenta\n" +
					"from vehiculos vh ,\n" +
					"web_recau:usuario_cuentas_rel_cogmvl uc\n" +
					"where vh.c_dominio_original = uc.cuenta\n" +
					"and uc.tipo_cuenta = 3\n" +
					"union\n" +
					"select c_cuenta\n" +
					"from vehiculos vh ,\n" +
					"web_recau:usuario_cuentas_rel_cogmvl uc\n" +
					"where vh.c_dominio_mercosur = uc.cuenta\n" +
					"and uc.tipo_cuenta = 3)\n" +
					"and ('' = ?\n" +
					"or v.c_dominio_actual = ?\n" +
					"or v.c_dominio_mercosur = ?\n" +
					"or v.c_dominio_original = ?)\n";

	private static final String SQL_BUSQUEDA_CANTIDA_FILTRO =
			"Select count(*) as Cantidad\n" +
					"from vehiculos v, contrib_cuentas cc, contribuyentes co, calles c\n" +
					"where cc.c_sistema = 31\n" +
					"and v.c_baja = 0\n" +
					"and v.c_cuenta = cc.c_cuenta\n" +
					"and co.c_calle = c.c_calle\n" +
					"and co.c_documento = cc.c_documento\n" +
					"and co.n_documento = cc.n_documento\n" +
					"and v.c_cuenta in ( select c_cuenta\n" +
					"from vehiculos vh ,\n" +
					"web_recau:usuario_cuentas_rel_cogmvl uc\n" +
					"where vh.c_dominio_actual = uc.cuenta\n" +
					"and uc.tipo_cuenta = 3\n" +
					"union\n" +
					"select c_cuenta\n" +
					"from vehiculos vh ,\n" +
					"web_recau:usuario_cuentas_rel_cogmvl uc\n" +
					"where vh.c_dominio_original = uc.cuenta\n" +
					"and uc.tipo_cuenta = 3\n" +
					"union\n" +
					"select c_cuenta\n" +
					"from vehiculos vh ,\n" +
					"web_recau:usuario_cuentas_rel_cogmvl uc\n" +
					"where vh.c_dominio_mercosur = uc.cuenta\n" +
					"and uc.tipo_cuenta = 3)\n" +
					"and ('' = ?\n" +
					"or v.c_dominio_actual = ?\n" +
					"or v.c_dominio_mercosur = ?\n" +
					"or v.c_dominio_original = ?)\n";
	
	public DaoCuentaAutomotor(Connection connection) {
		super(connection);
	}
	
	@Override
	protected void cargarParametrosSQLBusquedaCuenta(String cuenta, PreparedStatement ps) throws SQLException {
		ps.setString(1, cuenta);
		ps.setString(2, cuenta);
		ps.setString(3, cuenta);
	}
	
	@Override
	protected CuentaVehiculos generarCuenta(ResultSet rs) throws SQLException {
		String dominio = rs.getString("Dominio");
		return FactoryCuenta.generarCuentaVehiculos(rs.getInt("Cuenta"), dominio == null ? null : dominio.trim(), rs.getString("Descripcion"), rs.getString("Alias"), rs.getInt("be") == 1);
	}

	@Override
	protected void cargarParamtrosSPTotales(CuentaVehiculos cuenta, CallableStatement cs) throws SQLException {
		cs.setString(1, cuenta.getDominio());
	}
	
	@Override
	public void cargarDatosCuenta(CuentaVehiculos cuenta) throws ExcepcionControladaError {
		super.cargarContribuyente(cuenta);
		this.ejecutarSPCargaDatos(cuenta);
		this.cargarDatos(cuenta);
	}

	@Override
	protected String getSQLRecuperacionCuentasPaginada() {
		return SQL_RECUPERACION_CUENTA_PAGINADA;
	}

	public void ejecutarSPCargaDatos(CuentaVehiculos cuenta) throws ErrorEnBaseException {
		
		CallableStatement cs = null;
		
		try {
			cs = this.getConnection().prepareCall( SP_RECUPERAR_DATOS );
			cs.setInt(1, cuenta.getSistema());
			cs.setString(2, cuenta.getDominio());
			cs.execute();
		} catch (SQLException e) {
			throw new ErrorEnBaseException("Error al recuperar los datos del vehículo", e);
		} finally {			
			super.cerrarRecursos(cs);
		}
	}
	
	public void cargarDatos(CuentaVehiculos cuenta) throws ErrorEnBaseException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_RECUPERAR_DATOS );
			rs = ps.executeQuery();
			
			if (rs.next()) {
				cuenta.setCatVehiculo(rs.getString("c_cat_veh"));
				cuenta.setCodigoMarca(rs.getString("c_cod_marca"));
				cuenta.setMarca(rs.getString("d_marca"));
				cuenta.setModelo(rs.getString("n_modelo"));
				cuenta.setModeloMarca(rs.getString("d_modelo"));
				cuenta.setTipoVehiculo(rs.getString("c_cod_marca"));
				cuenta.setValuacion(rs.getString("n_valuacion"));
			}
		} catch (SQLException e) {
			throw new ErrorEnBaseException("Error al recuperar los datos del vehículo", e);
		} finally {			
			super.cerrarRecursos(ps, rs);
		}
	}
	
	@Override
	protected void cargarFiltros(PreparedStatement ps, String filtro) throws SQLException {
		String filtroUpper = filtro == null ? "" : filtro.toUpperCase();
		ps.setString(3, filtroUpper);
		ps.setString(4, filtroUpper);
		ps.setString(5, filtroUpper);
		ps.setString(6, filtroUpper);
	}

	@Override
	protected String getSQLRecuperacionCuentasFiltradas() {
		return SQL_BUSQUEDA_CUENTAS_FILTRO;
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
		return TiposCuentas.VEHICULOS;
	}

	@Override
	protected String getSQLCantidadFiltrada() {
		return SQL_BUSQUEDA_CANTIDA_FILTRO;
	}
}