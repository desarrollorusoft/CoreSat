package ar.com.cognisys.sat.core.modelo.dao.cuenta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;

public class DaoCuentaMotovehiculo extends DaoCuenta<CuentaRodados> {

	private static final String SQL_RECUPERACION_CUENTA_PAGINADA =
			"Select skip ? first ? R.C_Cuenta as Cuenta, C.Descripcion as Alias, TRIM(DECODE(TRIM(nvl(r.c_dominio, r.c_mercosur)), '', r.c_mercosur, r.c_mercosur, r.c_mercosur, r.c_dominio)) as Dominio, Trim(D_Nombre) As Nombre, Trim(D_Apellido) As Apellido, \n"
			+ "       Trim(D_Marca) || ' ' || Trim(D_Modelo) || ' ' || N_Cilindrada || ' (' || N_Modelo || ')' As Descripcion, \n"
			+ "nvl((select 1 from boleta_electronica be where R.c_cuenta = be.c_cuenta and be.c_sistema = 4),0) as be \n"
			+ "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, recaudaciones:Rodados R, recaudaciones:Contrib_Cuentas Cc, recaudaciones:Contribuyentes Co \n"
			+ "Where C.Id_Usuario = ? \n"
			+ "And C.Tipo_cuenta = 4 \n"
			+ "And (c_dominio = C.cuenta or c_mercosur = C.cuenta) \n"
			+ "And R.C_Baja = 0 \n"
			+ "And Cc.C_Cuenta = R.C_Cuenta \n"
			+ "And C_Sistema = 4 \n"
			+ "And Co.C_Documento = Cc.C_Documento \n"
			+ "And Co.N_Documento = Cc.N_Documento \n"
			+ "And R.C_baja = 0 \n"
			+ "Order By C.Cuenta \n";

	private static final String SQL_RECUPERACION_CUENTA = "Select R.C_Cuenta as Cuenta, C.Descripcion as Alias, TRIM(DECODE(TRIM(nvl(r.c_dominio, r.c_mercosur)), '', r.c_mercosur, r.c_mercosur, r.c_mercosur, r.c_dominio)) as Dominio, Trim(D_Nombre) As Nombre, Trim(D_Apellido) As Apellido, \n"
														+ "       Trim(D_Marca) || ' ' || Trim(D_Modelo) || ' ' || N_Cilindrada || ' (' || N_Modelo || ')' As Descripcion, \n"
														+ "nvl((select 1 from boleta_electronica be where R.c_cuenta = be.c_cuenta and be.c_sistema = 4),0) as be \n"
														+ "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, recaudaciones:Rodados R, recaudaciones:Contrib_Cuentas Cc, recaudaciones:Contribuyentes Co \n"
														+ "Where C.Id_Usuario = ? \n"
														+ "And C.Tipo_cuenta = 4 \n"
														+ "And (c_dominio = C.cuenta or c_mercosur = C.cuenta) \n"
														+ "And R.C_Baja = 0 \n"
														+ "And Cc.C_Cuenta = R.C_Cuenta \n"
														+ "And C_Sistema = 4 \n"
														+ "And Co.C_Documento = Cc.C_Documento \n"
														+ "And Co.N_Documento = Cc.N_Documento \n"
														+ "Order By C.Cuenta \n";
	
	private static final String SP_RECUPERO_TOTALES = "{call sp_rod_carga_mov_cogmvl( ? )}";
	
	private static final String SQL_RECUPERO_TOTALES = "Select (Nvl(A.I_Capital, 0) + Nvl(A.I_Recargo, 0) + Nvl(A.I_Multa, 0)) As Total_A_Vencer, \n "
													 + "       (Nvl(V.I_Capital, 0) + Nvl(V.I_Recargo, 0) + Nvl(V.I_Multa, 0)) As Total_Vencida \n "
													 + "From Tmp_Rod_Totales A, Tmp_Rod_Totalesv V ";
	//TODO FEDE HACER QUE SE ENCUENTRE EL ALIAS
	private static final String SQL_BUSQUEDA_CUENTA = "Select 'RODADOS' Categoria, '' as Alias, C_Cuenta as Cuenta, Trim(D_Marca)||' '||N_Cilindrada as descripcion, \n"
													+ "Decode(TRIM(nvl(c_mercosur,'')),'',c_dominio,c_mercosur) as Dominio, \n"
													+ "nvl((select 1 from boleta_electronica be where R.c_cuenta = be.c_cuenta and be.c_sistema = 4),0) as be \n"
												    + "From Rodados R \n"
												    + "Where (c_dominio = ? or c_mercosur = ? ) \n"
												    + "And c_baja = 0 ";

	private static final String SP_RECUPERAR_DATOS = "{call sp_pad_veh_rod_cogmvl( ? , ? ) }";
	
	private static final String SQL_RECUPERAR_DATOS = "Select * from tmp_pad_rodados";

	private static final String SQL_BUSQUEDA_CUENTAS_FILTRO =
			"Select Skip ? First ? 'RODADOS' alias, R.C_Cuenta Numero_Cuenta, Trim(R.D_Marca)||' '||R.N_Cilindrada Descripcion, DECODE(TRIM(nvl(r.c_dominio, r.c_mercosur)), '', r.c_mercosur, r.c_mercosur, r.c_mercosur, r.c_dominio) as Dominio,\n" +
					"Cc.C_Documento, Cc.N_Documento,\n" +
					"Co.D_Nombre, Co.D_Apellido, C.D_Calle, Co.N_Nro, Co.C_Piso, Co.C_Dpto, Co.C_Postal, Co.D_Telefono, Co.D_Fax, r.c_cuenta as cuenta, \n" +
					"nvl((select 1 from boleta_electronica be where R.c_cuenta = be.c_cuenta and be.c_sistema = 4),0) as be \n" +
					"From Contrib_Cuentas Cc, Contribuyentes Co, Calles C, Rodados R\n" +
					"Where Cc.C_Sistema = 4\n" +
					"And Co.C_Calle = C.C_Calle\n" +
					"And Co.C_Documento = Cc.C_Documento\n" +
					"And Co.N_Documento = Cc.N_Documento\n" +
					"And r.C_Cuenta in (select c_cuenta\n" +
					"from recaudaciones:rodados r, Web_Recau:Usuario_Cuentas_Rel_Cogmvl cr\n" +
					"where r.c_dominio = cr.cuenta\n" +
					"and cr.tipo_cuenta = 4\n" +
					"union\n" +
					"select c_cuenta\n" +
					"from recaudaciones:rodados r, Web_Recau:Usuario_Cuentas_Rel_Cogmvl cr\n" +
					"where r.c_mercosur = cr.cuenta\n" +
					"and cr.tipo_cuenta = 4)\n" +
					"And r.c_cuenta = cc.c_cuenta\n" +
					"And ('' = ?\n" +
					"or R.c_dominio = ? \n" +
					"or r.c_mercosur = ? )\n";

	private static final String SQL_BUSQUEDA_CANTIDA_FILTRO =
			"Select count(*) as Cantidad\n" +
					"From Contrib_Cuentas Cc, Contribuyentes Co, Calles C, Rodados R\n" +
					"Where Cc.C_Sistema = 4\n" +
					"And Co.C_Calle = C.C_Calle\n" +
					"And Co.C_Documento = Cc.C_Documento\n" +
					"And Co.N_Documento = Cc.N_Documento\n" +
					"And r.C_Cuenta in (select c_cuenta\n" +
					"from recaudaciones:rodados r, Web_Recau:Usuario_Cuentas_Rel_Cogmvl cr\n" +
					"where r.c_dominio = cr.cuenta\n" +
					"and cr.tipo_cuenta = 4\n" +
					"union\n" +
					"select c_cuenta\n" +
					"from recaudaciones:rodados r, Web_Recau:Usuario_Cuentas_Rel_Cogmvl cr\n" +
					"where r.c_mercosur = cr.cuenta\n" +
					"and cr.tipo_cuenta = 4)\n" +
					"And r.c_cuenta = cc.c_cuenta\n" +
					"And ('' = ?\n" +
					"or R.c_dominio = ? \n" +
					"or r.c_mercosur = ? )\n";
	
	public DaoCuentaMotovehiculo(Connection connection) {
		super(connection);
	}
	
	@Override
	protected void cargarParametrosSQLBusquedaCuenta(String cuenta, PreparedStatement ps) throws SQLException {
		ps.setString(1, cuenta);
		ps.setString(2, cuenta);
	}

	@Override
	protected CuentaRodados generarCuenta(ResultSet rs) throws SQLException {
		String dominio = rs.getString("Dominio");
		return FactoryCuenta.generarCuentaMotovehiculos(rs.getInt("Cuenta"), dominio == null ? null : dominio.trim(), rs.getString("Descripcion"), rs.getString("Alias"), rs.getInt("be") == 1);
	}

	@Override
	protected void cargarParamtrosSPTotales(CuentaRodados cuenta, CallableStatement cs) throws SQLException {
		cs.setString(1, cuenta.getDominio());
	}
	
	@Override
	public void cargarDatosCuenta(CuentaRodados cuenta) throws ExcepcionControladaError {
		super.cargarContribuyente(cuenta);
		this.ejecutarSPCargaDatos(cuenta);
		this.cargarDatos(cuenta);
	}

	@Override
	protected String getSQLRecuperacionCuentasPaginada() {
		return SQL_RECUPERACION_CUENTA_PAGINADA;
	}

	private void ejecutarSPCargaDatos(CuentaRodados cuenta) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		
		try {
			cs = this.getConnection().prepareCall( SP_RECUPERAR_DATOS );
			cs.setInt(1, cuenta.getSistema());
			cs.setString(2, cuenta.getDominio());
			cs.execute();
		} catch (SQLException e) {
			throw new ErrorEnBaseException("Error al recuperar los datos del rodado", e);
		} finally {			
			super.cerrarRecursos(cs);
		}
	}

	private void cargarDatos(CuentaRodados cuenta) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareStatement( SQL_RECUPERAR_DATOS );
			rs = ps.executeQuery();
			
			if (rs.next()) {
				cuenta.setCilindrada(rs.getString("n_cilindrada"));
				cuenta.setMarca(rs.getString("d_marca"));
				cuenta.setModelo(rs.getString("n_modelo"));
			}
		} catch (SQLException e) {
			throw new ErrorEnBaseException("Error al recuperar los datos del rodado", e);
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
		return TiposCuentas.RODADOS;
	}

	@Override
	protected String getSQLCantidadFiltrada() {
		return SQL_BUSQUEDA_CANTIDA_FILTRO;
	}
}