package ar.com.cognisys.sat.core.modelo.dao.cuenta;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.comun.Catastro;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryContribuyente;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryDomicilio;

public class DaoCuentaAbl extends DaoCuenta<CuentaABL> {

	private static final String SQL_RECUPERACION_CUENTA_PAGINADA = "Select skip ? first ? \n"
																 + "		C.Cuenta, C.Descripcion as Alias, Trim(D_Calle) || \" \" || Nvl(N_Nro_Pro,\"\") || \" \" || Nvl(C_Piso_Pro,\"\") || \" \" || Nvl(C_Dpto_Pro,\"\") Descripcion, Trim(D_Nombre) as Nombre, Trim(D_Apellido) as Apellido, \n"
																 + " nvl((select 1 from boleta_electronica be where a.c_cuenta = be.c_cuenta and be.c_sistema = 2),0) as be \n"
																 + "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, Calles L, Abl A, Contrib_Cuentas Cc, Contribuyentes Co \n "
																 + "Where C.Id_Usuario = ? \n "
																 + "And C.Tipo_Cuenta = 1 \n "
																 + "And A.C_Cuenta = C.Cuenta \n "
																 + "And L.C_Calle = A.C_Calle_Pro \n "
																 + "And Cc.C_Cuenta = C.Cuenta  \n "
																 + "And C_Sistema = 2 \n "
																 + "And Co.C_Documento = Cc.C_Documento  \n "
																 + "And Co.N_Documento = Cc.N_Documento \n "
																 + "And A.C_baja = 0 \n"
																 + "Order By C.Cuenta ";

	private static final String SQL_RECUPERACION_CUENTA = "Select C.Cuenta, C.Descripcion as Alias, Trim(D_Calle) || \" \" || Nvl(N_Nro_Pro,\"\") || \" \" || Nvl(C_Piso_Pro,\"\") || \" \" || Nvl(C_Dpto_Pro,\"\") Descripcion, Trim(D_Nombre) as Nombre, Trim(D_Apellido) as Apellido, \n"
														+ "       nvl((select 1 from boleta_electronica be where a.c_cuenta = be.c_cuenta and be.c_sistema = 2),0) as be \n"
													    + "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, Calles L, Abl A, Contrib_Cuentas Cc, Contribuyentes Co \n "
													    + "Where C.Id_Usuario = ? \n "
													    + "And C.Tipo_Cuenta = 1 \n "
													    + "And A.C_Cuenta = C.Cuenta \n "
													    + "And L.C_Calle = A.C_Calle_Pro \n "
													    + "And Cc.C_Cuenta = C.Cuenta  \n "
													    + "And C_Sistema = 2 \n "
													    + "And Co.C_Documento = Cc.C_Documento  \n "
													    + "And Co.N_Documento = Cc.N_Documento \n "
													    + "Order By C.Cuenta ";
	
	private static final String SP_RECUPERO_TOTALES = "{call sp_abl_carga_deuda_cogmvl( 2 , 3 , ? )}";
	
	private static final String SQL_RECUPERO_TOTALES = "Select (Nvl(A.I_Capital, 0) + Nvl(A.I_Recargo, 0) + Nvl(A.I_Multa, 0)) As Total_A_Vencer, \n "
												     + "       (Nvl(V.I_Capital, 0) + Nvl(V.I_Recargo, 0) + Nvl(V.I_Multa, 0)) As Total_Vencida \n "
												     + "From Tmp_Abl_Totales A, Tmp_Abl_Totalesv V ";
	
	private static final String SQL_BUSQUEDA_CUENTA = "Select \"ABL\" Categoria, '' as Alias, C_Cuenta As Cuenta, Trim(D_Calle) || \" \" || Nvl(N_Nro_Pro,\"\") || \" \" || Nvl(C_Piso_Pro,\"\") || \" \" || Nvl(C_Dpto_Pro,\"\") descripcion, \n"
													+ "nvl((select 1 from boleta_electronica be where a.c_cuenta = be.c_cuenta and be.c_sistema = 2),0) as be \n"
												    + "From Calles C , Abl A \n "
												    + "Where A.C_Calle_Pro = C.C_Calle \n "
												    + "And A.C_Baja = 0 \n"
												    + "And A.C_Cuenta In (Select Unique C_Cuenta \n "
												    + "                   From Contrib_Cuentas \n "
												    + "                   Where C_cuenta = ? \n "
												    + "                   And C_sistema = ?) \n ";
	
	private static final String SP_CARGA_DATOS = "{call sp_abl_carga_pad_cogmvl( ? , 2 )}";
	
	private static final String SQL_CARGA_DATOS = "Select * From tmp_abl_cons_pad ";

	private static final String SQL_BUSQUEDA_CUENTAS_FILTRO = "Select Skip ? First ?\n" +
															  "'ABL' alias, A.C_Cuenta cuenta, Trim(D_Calle) || ' ' || Nvl(N_Nro_Pro,'') || ' ' || Nvl(C_Piso_Pro,'') || ' ' || Nvl(C_Dpto_Pro,'') Descripcion, \n" +
															  "nvl((select 1 from boleta_electronica be where a.c_cuenta = be.c_cuenta and be.c_sistema = 2),0) as be \n" +
															  "From Calles C , Abl A, Contrib_Cuentas Cc, Contribuyentes Co\n" +
															  "Where A.C_Calle_Pro = C.C_Calle\n" +
															  "And A.C_Cuenta In (Select Unique Cuenta\n" +
															  "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl\n" +
															  "Where Tipo_Cuenta = 1)\n" +
															  "And Cc.C_Cuenta = A.C_Cuenta\n" +
															  "And Cc.C_Sistema = 2\n" +
															  "And A.C_Calle_Pro = C.C_Calle\n" +
															  "And Co.C_Documento = Cc.C_Documento\n" +
															  "And Co.N_Documento = Cc.N_Documento\n" +
															  "And ( '' = ? or ('' || A.C_Cuenta) = ? )\n";

	private static final String SQL_BUSQUEDA_CANTIDA_FILTRO = "Select --+ORDERED \n"
															+ "		  Count(*) as Cantidad \n"
															+ "From Abl A, Contrib_Cuentas Cc \n"
															+ "Where A.C_Cuenta In ( \n"
															+ "		Select Unique Cuenta \n"
															+ "		From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n"
															+ "		Where Tipo_Cuenta = 1 \n"
															+ ") \n"
															+ "And Cc.C_Cuenta = A.C_Cuenta \n"
															+ "And Cc.C_Sistema = 2 \n"
															+ "And ( '' = ? or ('' || A.C_Cuenta) = ? )";
	
	public DaoCuentaAbl(Connection connection) {
		super(connection);
	}

	@Override
	protected void cargarParametrosSQLBusquedaCuenta(String cuenta, PreparedStatement ps) throws SQLException {
		ps.setBigDecimal(1, new BigDecimal(cuenta));
		ps.setInt(2, TiposCuentas.ABL.getCodigo());
	}
	
	@Override
	protected CuentaABL generarCuenta(ResultSet rs) throws SQLException {
		return FactoryCuenta.generarIntanciaCompletaABL(rs.getInt("Cuenta"), rs.getString("Descripcion"), rs.getString("Alias"), rs.getInt("be") == 1);
	}

	@Override
	protected void cargarParamtrosSPTotales(CuentaABL cuenta, CallableStatement cs) throws SQLException {
		cs.setInt(1, cuenta.getNumero());
	}
	
	@Override
	public void cargarDatosCuenta(CuentaABL cuenta) throws ExcepcionControladaError {
		
		this.ejecutarSPCargaDatosCuenta(cuenta);
		this.recuperarDatos(cuenta);
	}

	@Override
	protected String getSQLRecuperacionCuentasPaginada() {
		return SQL_RECUPERACION_CUENTA_PAGINADA;
	}

	private void ejecutarSPCargaDatosCuenta(CuentaABL cuenta) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		
		try {
			cs = this.getConnection().prepareCall( SP_CARGA_DATOS );
			cs.setInt(1, cuenta.getNumero());
			cs.execute();
			
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(cs);
		}
	}

	private void recuperarDatos(CuentaABL cuenta) throws ExcepcionControladaError {
		
		Contribuyente contribuyente = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.getConnection().prepareCall( SQL_CARGA_DATOS );
			rs = ps.executeQuery();
			
			if (rs.next()) {
				contribuyente = FactoryContribuyente.generarInstanciaCompleta(rs.getString("d_nombre"), 
																			  rs.getString("d_apellido"), 
																			  rs.getInt("n_documento"), 
																			  "", 
																			  FactoryDomicilio.generarInstanciaCompleta(rs.getString("d_calle_pro"), 
																					  									rs.getInt("n_nro_pro"), 
																					  									(rs.getString("c_piso_pro") == null ? "" : rs.getString("c_piso_pro")),
																					  									rs.getString("c_dpto_pro"), 
																					  									rs.getInt("c_postal_pro")), 
										  									  rs.getString("d_telefono"), 
																			  "", 
																			  0);
				cuenta.setDescripcion(contribuyente.getDomicilio().toString());
				cuenta.setContribuyente(contribuyente);
				cuenta.setBaja("1".equals(rs.getString("c_baja")));
				cuenta.setCategoria(rs.getString("n_cate"));
				cuenta.setEximido("1".equals(rs.getString("c_eximido")));
				cuenta.setFrente(rs.getString("n_frente"));
				cuenta.setOficio(rs.getString("n_oficio"));
				cuenta.setPartida(rs.getString("n_partida"));
				cuenta.setSuperficie(rs.getString("n_superf"));
				cuenta.setValuacion(rs.getString("n_valua"));
				cuenta.setVerificador(rs.getString("n_verificador"));
				cuenta.setCatastro(new Catastro());
				cuenta.getCatastro().setC(rs.getString("n_circ"));
				cuenta.getCatastro().setS(rs.getString("c_seccion"));
				cuenta.getCatastro().setF(rs.getString("n_fraccion"));
				cuenta.getCatastro().setM(rs.getString("n_manz"));
				cuenta.getCatastro().setLm(rs.getString("d_lmanz"));
				cuenta.getCatastro().setP(rs.getString("n_parc"));
				cuenta.getCatastro().setLp(rs.getString("d_lparc"));
				cuenta.getCatastro().setUc(rs.getString("d_lufun"));
				cuenta.getCatastro().setUf(rs.getString("n_ufun"));
				cuenta.getCatastro().setP2(rs.getString("n_poli"));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}
	
	@Override
	protected void cargarFiltros(PreparedStatement ps, String filtro) throws SQLException {
		String filtroUpper = filtro == null ? "" : filtro.toUpperCase();
		ps.setString(3, filtroUpper);
		ps.setString(4, filtroUpper);
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
		return TiposCuentas.ABL;
	}
	
	@Override
	protected String getSQLCantidadFiltrada() {
		return SQL_BUSQUEDA_CANTIDA_FILTRO;
	}
}