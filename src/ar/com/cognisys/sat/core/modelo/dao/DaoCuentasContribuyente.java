package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.CuentaAutomotor;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryContribuyente;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryDomicilio;

public class DaoCuentasContribuyente extends DAO {

	private static final String SQL_RECUPERAR_CONTRIBUYENTE = "select d_nombre as nombre, d_apellido as apellido, d_calle as calle, n_nro as numero, c_piso as piso,\n"
															+ " 	  c_dpto as depto, c_postal as cp, d_telefono as telefono1, d_fax as telefono2, d_mail as correo,\n"
															+ "		  co.n_documento as numero_documento, co.c_documento as tipo_documento\n" 
															+ "from recaudaciones:contrib_cuentas cc, recaudaciones:contribuyentes co, recaudaciones:calles ca\n" 
															+ "where co.c_documento = cc.c_documento\n"
															+ "and co.n_documento = cc.n_documento\n"
															+ "and co.c_calle = ca.c_calle\n"
															+ "and cc.c_cuenta = ?\n"
															+ "and cc.c_sistema = ?";
	private static final String SQL_BUSCAR_CUENTAS = "SELECT c_cuenta, c_sistema \n" +
													"FROM contrib_cuentas cc, contribuyentes c\n" +
													"WHERE c.n_cuit = ?\n" +
													"AND cc.n_documento = c.n_documento\n" +
													"AND cc.c_documento = c.c_documento\n" +
													"AND c_sistema in (1,2,31,4,9)";
	private static final String SQL_BUSCAR_DOMINIO_AUTO = "SELECT Decode(TRIM(nvl(c_dominio_mercosur,'')),'',Decode(TRIM(nvl(c_dominio_actual,'')),'',c_dominio_original,c_dominio_actual),c_dominio_mercosur) as DOMINIO\n" +
														"FROM recaudaciones:vehiculos\n" +
														"WHERE c_cuenta = ?";
	private static final String SQL_BUSCAR_DOMINIO_MOTO = "SELECT TRIM(DECODE(TRIM(nvl(c_dominio, c_mercosur)), '', c_mercosur, c_mercosur, c_mercosur, c_dominio)) as DOMINIO\n" +
														"FROM recaudaciones:rodados\n" +
														"WHERE c_cuenta = ?";

	public DaoCuentasContribuyente(Connection connection) {
		super( connection );
	}
	
	public Contribuyente recuperarContribuyente(TiposCuentas tipo, Integer numero) throws ErrorRecuperacionDatosException {

		Contribuyente contribuyente = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( SQL_RECUPERAR_CONTRIBUYENTE );
			ps.setInt( 1, numero );
			ps.setInt( 2, tipo.getCodigo() );
			rs = ps.executeQuery();

			if (rs.next()) {
				contribuyente = FactoryContribuyente.generarInstanciaCompleta(rs.getString("nombre"), 
																			  rs.getString("apellido"), 
																			  rs.getInt("numero_documento"), 
																	 		  rs.getString("correo"), 
																			  FactoryDomicilio.generarInstanciaCompleta(rs.getString("calle"), 
																					  									rs.getInt("numero"), 
																					  									rs.getString("piso"),
																					  									rs.getString("depto"), 
																					  									rs.getInt("cp")), 
																			  rs.getString("telefono1"), 
																			  rs.getString("telefono2"), 
																			  rs.getInt("tipo_documento"));
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
		
		return contribuyente;
	}
	
	/**
	 * 
	 * Utilizar {@link #recuperarContribuyente(TiposCuentas, Integer)}
	 * 
	 * @deprecated
	 * @param tipoCuenta
	 * @param numeroCuenta
	 * @return contribuyente
	 * @throws ExcepcionControladaError
	 */
	@Deprecated public Contribuyente recuperarDatosContribuyente(Integer tipoCuenta, String numeroCuenta) throws ExcepcionControladaError {

		Integer cuenta = Integer.parseInt(numeroCuenta);
		Integer sistema;
		
		switch (tipoCuenta) {
			case 1: sistema = 2;  break;
			case 2: sistema = 1;  break;
			case 3: sistema = 31; break;
			case 4: sistema = 4;  break;
			case 5: sistema = 9; break;
			default: throw new ExcepcionControladaError("No es valido el tipo de cuenta", null);
		}
		
		Integer tipoDocumento = 0;
		Integer numeroDocumento = 0;
		
		String sql = "SELECT C_documento, N_documento \n"
					+ "FROM	 Contrib_cuentas \n"
					+ "WHERE  C_cuenta = ? AND C_sistema = ?; \n";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {

			ps = this.prepareStatement( sql );
			ps.setInt( 1, cuenta );
			ps.setInt( 2, sistema );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				tipoDocumento = rs.getInt( 1 );
				numeroDocumento = rs.getInt( 2 );
			}
		} catch (Exception e) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		String nombre = "";
		String apellido = "";
		String calle = "";
		Integer altura = 0;
		String piso = "";
		String departamento = "";
		Integer codigoPostal = 0;
		String telefono = "";
		String fax = "";
		String correo = "";
		
		ps = null;
		rs = null;
		
		sql = "SELECT D_nombre, \n"
			+ "		  D_apellido, \n"
			+ "		  D_calle, \n"
			+ "		  N_nro, \n"
			+ "		  C_piso, \n"
			+ "		  C_dpto, \n"
			+ "		  C_postal, \n"
			+ "		  D_telefono, \n"
			+ "		  D_fax \n"
			+ "FROM	 Contribuyentes Co, Calles Ca \n"
			+ "WHERE  Co.C_documento = ? AND Co.N_documento = ? AND Co.C_calle = Ca.C_calle \n ";
		
		try {

			ps = prepareStatement( sql );
			ps.setInt( 1, tipoDocumento );
			ps.setInt( 2, numeroDocumento );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				nombre = rs.getString( 1 );
				apellido = rs.getString( 2 );
				calle = rs.getString( 3 );
				altura = rs.getInt( 4 );
				piso = rs.getString( 5 );
				departamento = rs.getString( 6 );
				codigoPostal = rs.getInt( 7 );
				telefono = rs.getString( 8 );
				fax = rs.getString( 9 );
			}		

			return FactoryContribuyente.generarInstanciaCompleta(
																nombre, 
																apellido, 
																numeroDocumento, 
																correo, 
																FactoryDomicilio.generarInstanciaCompleta(
																		calle, 
																		altura, 
																		piso,
																		departamento, 
																		codigoPostal ), 
																telefono, 
																fax, 
																tipoDocumento );
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

	}

    public List<Cuenta> buscarPorCuit(String cuit) throws ErrorRecuperacionDatosException {
    	List<Cuenta> lista = new ArrayList<Cuenta>();
		PreparedStatement ps = null;
    	ResultSet rs = null;

    	try {
    		ps = this.prepareStatement( SQL_BUSCAR_CUENTAS );
    		ps.setString(1, cuit);
    		rs = ps.executeQuery();

    		while (rs.next()) {
    			Cuenta c = FactoryCuenta.generarBasica( rs.getInt("c_cuenta"), rs.getInt("c_sistema") );

    			if (c.getTipoCuenta() == TiposCuentas.VEHICULOS)
    				this.cargarDominio((CuentaAutomotor) c, SQL_BUSCAR_DOMINIO_AUTO);
    			else if (c.getTipoCuenta() == TiposCuentas.RODADOS)
					this.cargarDominio((CuentaAutomotor) c, SQL_BUSCAR_DOMINIO_MOTO);

    			lista.add(c);
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
    		super.cerrarRecursoRS(rs);
    		super.cerrarRecursoST(ps);
		}

    	return lista;
	}

	private void cargarDominio(CuentaAutomotor cuenta, String sql) throws ErrorRecuperacionDatosException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( sql );
			ps.setInt(1, cuenta.getNumero());
			rs = ps.executeQuery();

			if (rs.next())
				cuenta.setDominio( rs.getString("DOMINIO") );
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursoRS(rs);
			super.cerrarRecursoST(ps);
		}
	}
}