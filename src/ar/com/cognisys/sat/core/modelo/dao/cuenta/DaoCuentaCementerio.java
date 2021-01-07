package ar.com.cognisys.sat.core.modelo.dao.cuenta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.dao.DaoCementerio;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.CuentaCementerioVacia;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuentaCementerio;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryContribuyente;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryDomicilio;

public class DaoCuentaCementerio extends DaoCuenta<CuentaCementerio> {

	private static final String SQL_RECUPERACION_CUENTA_PAGINADA =
			"Select skip ? first ? C.Cuenta, C.Descripcion as Alias \n"
			+ "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C, cementerio:arrendatarios a \n"
			+ "Where C.Id_Usuario = ? \n"
			+ "And C.Tipo_Cuenta = 5 \n"
			+ "And C.cuenta = a.c_cuenta||'' \n"
			+ "And a.c_padron = 1 \n"
			+ "Order By C.Cuenta ";

	private static final String SQL_RECUPERACION_CUENTA = "Select C.Cuenta, C.Descripcion as Alias \n "
													    + "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C \n "
													    + "Where C.Id_Usuario = ? \n "
													    + "And C.Tipo_Cuenta = 5 \n "
													    + "Order By C.Cuenta ";
	
	private static final String SP_BUSCAR_DATOS_CUENTA = "{call spl_top_obt_cementerio_cogmvl( ? , 9 )}";

	private static final String SQL_BUSQUEDA_CUENTAS_FILTRO = "Select skip ? first ? 'CEMENTERIO' alias, cuenta as Cuenta \n"
										 		            + "From web_recau:usuario_cuentas_rel_cogmvl c, cementerio:arrendatarios a \n"
										 		            + "Where c.tipo_cuenta = 5 \n"
										 		            + "and Upper( '' || c.cuenta) Like UPPER(?) \n"
															+ "And C.cuenta = a.c_cuenta||'' \n"
															+ "And a.c_padron = 1 \n"
										 		            + "GROUP by cuenta";
	
	private static final String SQL_BUSQUEDA_CANTIDA_FILTRO = "Select count(*) as Cantidad \n"
											                + "From web_recau:usuario_cuentas_rel_cogmvl c, cementerio:arrendatarios a \n"
											                + "Where c.tipo_cuenta = 5 \n"
															+ "And C.cuenta = a.c_cuenta||'' \n"
															+ "And a.c_padron = 1 \n"
											                + "and Upper( '' || c.cuenta) Like UPPER(?)";
	
	public DaoCuentaCementerio(Connection connection) {
		super(connection);
	}

	@Override
	protected CuentaCementerio generarCuenta(ResultSet rs) throws SQLException, ExcepcionControladaError, ExcepcionControladaAlerta {
		
		CuentaCementerio cuenta = this.recuperarCuenta( rs.getInt("Cuenta") );
		cuenta.setAlias( rs.getString("Alias") );
		
		return cuenta;
	}
	
	@Override
	public CuentaCementerio buscarCuenta(String cuenta) throws ExcepcionControladaAlerta, ExcepcionControladaError {
		return recuperarCuenta(new Integer(cuenta));
	}

	public CuentaCementerio recuperarCuenta(Integer numeroCuenta) throws ExcepcionControladaError, ExcepcionControladaAlerta {
		
		CuentaCementerio cuenta = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			cs = this.getConnection().prepareCall( SP_BUSCAR_DATOS_CUENTA );
			cs.setInt(1, numeroCuenta);
			cs.execute();
			
			rs = cs.getResultSet();
			
			if (rs.next()) {
				if (rs.getString("c_cuenta_nom") == null)
					throw new CuentaCementerioVacia();
				else if ( rs.getString("c_padron") == null || rs.getInt("c_padron") != 1)
					throw new CuentaCementerioVacia();
				
				cuenta = FactoryCuentaCementerio.generarInstancia(FactoryContribuyente.generarInstanciaCompleta(rs.getString("d_nombre"), 
																											    rs.getString("d_apellido"), 
																											    rs.getInt("n_documento"), // numeroDocumento
																											    rs.getString("d_mail"), 
																											    FactoryDomicilio.generarInstanciaCompleta(rs.getString("d_calle"), 
																						    														   	  rs.getInt("n_nro"), 
																						    														   	  rs.getString("c_piso"), 
																						    														   	  rs.getString("c_dpto"), 
																						    														   	  rs.getInt("c_postal")), 
																											    rs.getString("d_telefono"), 
																											    rs.getString("d_mail"), 
																											    5), // tipoDocumento 
																  rs.getString("d_nomenclador"), 
																  rs.getDate("f_alta"), 
																  rs.getDate("f_renovacion"), 
																  rs.getString("d_rep_lote"), 
																  rs.getString("d_rep_tablon"), 
																  rs.getString("c_lote"), 
																  numeroCuenta, 
																  rs.getInt("n_seccion"), 
																  rs.getString("c_sub_lote"), 
																  rs.getString("c_sub_seccion"), 
																  rs.getString("c_sub_tablon"), 
																  rs.getString("c_tablon"),
																  rs.getInt("c_cuenta_nom"),
																  (rs.getInt("c_baja") == 1),
																  (rs.getString("s_deuda_leg").equals("S")));
//																  (rs.getString("s_deuda_esp").equals("S"))); //  TODO Descomentar esto cuando pase a PROD
			}
		} catch (CuentaCementerioVacia e) {
			throw e;
		} catch (SQLException ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos( cs, rs );
		}
		
		return cuenta;
	}
	
	@Override
	public void cargarTotalesDeuda(CuentaCementerio cuenta) throws ExcepcionControladaError {
		
		Deuda deuda = (new DaoCementerio(this.getConnection())).recuperarDeudaNormal(cuenta.getNumero(), cuenta.getNumeroNomenclador(), new Date());
		
		cuenta.setaVencer(deuda.getTotalCoutasAVencer().getTotal());
		cuenta.setDeuda(deuda.getTotalCoutasVencidas().getTotal());
		cuenta.setDeudas(deuda);
	}
	
	
	
	@Override
	protected void cargarContribuyente(CuentaCementerio cuenta) {}

	@Override
	protected String getSQLRecuperacionCuentasPaginada() {
		return SQL_RECUPERACION_CUENTA_PAGINADA;
	}

	@Override
	protected void cargarParamtrosSPTotales(CuentaCementerio cuenta, CallableStatement cs) throws SQLException {}
	
	@Override
	protected void cargarFiltros(PreparedStatement ps, String filtro) throws SQLException {
		ps.setString(3, "%"+filtro+"%");
	}

	@Override
	protected String getSQLRecuperacionCuentasFiltradas() {
		return SQL_BUSQUEDA_CUENTAS_FILTRO;
	}

	@Override
	protected String getSQLCantidadFiltrada() {
		return SQL_BUSQUEDA_CANTIDA_FILTRO;
	}
	
	@Override
	protected String getSQLRecuperacionCuentas() {
		return SQL_RECUPERACION_CUENTA;
	}

	@Override
	protected String getSPTotales() {
		return null;
	}

	@Override
	protected String getSQLRecuperacionTotales() {
		return null;
	}

	@Override
	protected void cargarParametrosSQLBusquedaCuenta(String cuenta, PreparedStatement ps) throws SQLException {}

	@Override
	protected String getSQLBusquedaCuenta() {
		return null;
	}

	@Override
	public void cargarDatosCuenta(CuentaCementerio cuenta) throws ExcepcionControladaError {}
	
	@Override
	protected TiposCuentas getTipoCuenta() {
		return TiposCuentas.CEMENTERIO;
	}
}