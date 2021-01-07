package ar.com.cognisys.sat.core.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.dao.cuenta.DaoCuentaCementerio;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuentasUsuario;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryContribuyente;

public class DaoCuentasUsuario extends Dao {

	public DaoCuentasUsuario(Connection connection) {
		super(connection);
	}
	
	public CuentasUsuario recuperarCuentasAsociadas(Integer idUsuario) throws ExcepcionControladaError {

		PreparedStatement ps = null;
		ResultSet rs = null;
		CuentasUsuario cuentas = FactoryCuentasUsuario.generar();


		String query ="Select \"ABL\" Categoria, C_Cuenta Numero_Cuenta, Trim(D_Calle) || \" \" || Nvl(N_Nro_Pro,\"\") || \" \" || Nvl(C_Piso_Pro,\"\") || \" \" || Nvl(C_Dpto_Pro,\"\") Descripcion \n "
				 + "From Calles C , Abl A \n "
				 + "Where A.C_Calle_Pro = C.C_Calle \n "
				 + "And A.C_Cuenta In (Select Cuenta \n "
				 + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
				 + "                   Where Id_Usuario = ? \n "
				 + "                   And tipo_cuenta = 1) \n "
				 + "Union \n "
				 + "Select \"COMERCIOS\" Categoria, C_Cuenta Numero_Cuenta, D_Razon_Social Descripcion \n "
				 + "From Comercios \n "
				 + "Where C_Cuenta In (Select Unique Cuenta \n "
				 + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
				 + "                   Where Id_Usuario = ? \n "
				 + "                   And tipo_cuenta = 2) \n "
				 + "Union \n "
				 + "Select \"VEHICULOS\" Categoria, C_Cuenta Numero_Cuenta, Decode(TRIM(nvl(c_dominio_mercosur,'')),'',Decode(TRIM(nvl(c_dominio_actual,'')),'',TRIM(c_dominio_original),TRIM(c_dominio_actual)),TRIM(c_dominio_mercosur))||' '||N_Modelo Descripcion \n "
				 + "From Vehiculos \n "
				 + "Where C_Cuenta In (Select Unique Cuenta \n "
				 + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
				 + "                   Where Id_Usuario = ? \n "
				 + "                   And c_baja = 0   \n "
			 	 + "                   And tipo_cuenta = 3) \n "
				 + "Union \n "
				 + "Select \"RODADOS\" Categoria, C_Cuenta Numero_Cuenta, Trim(D_Marca)||\" \"||N_Cilindrada Descripcion \n "
				 + "From Rodados \n "
				 + "Where C_Cuenta In (Select Unique Cuenta \n "
				 + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
				 + "                   Where Id_Usuario = ? \n  "
				 + "                   And c_baja = 0   \n "
				 + "                   And tipo_cuenta = 4) ";
					 
		try {
			ps = this.getConnection().prepareStatement(query);
			ps.setInt(1, idUsuario);
			ps.setInt(2, idUsuario);
			ps.setInt(3, idUsuario);
			ps.setInt(4, idUsuario);
			
			rs = ps.executeQuery();
			
            while (rs.next()) {
            	String categoria = rs.getString("Categoria").trim();
            	Integer numero = rs.getInt("Numero_Cuenta");
            	String descripcion = rs.getString("Descripcion").trim();
            	
				if (categoria.equals("ABL"))
					cuentas.agregarCuenta( FactoryCuenta.generarIntanciaCompletaABL(numero, descripcion) );
				
				else if (categoria.equals("COMERCIOS"))
				
					cuentas.agregarCuenta( FactoryCuenta.generarIntanciaCompletaComercios(numero, descripcion) );
				
				else if (categoria.equals("VEHICULOS"))
				
					cuentas.agregarCuenta( FactoryCuenta.generarIntanciaCompletaVehiculos(numero, descripcion,String.valueOf( numero ) ) );
				
				else if (categoria.equals("RODADOS"))
					cuentas.agregarCuenta( FactoryCuenta.generarIntanciaCompletaRodados(numero, descripcion,String.valueOf( numero ) ) );
			}
        } catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		cuentas.agregarCuentas( (new DaoCuentaCementerio(this.getConnection()).recuperarCuentas(idUsuario)) );
		
		return cuentas;
	}
	
	public Integer recuperarCuentaPorDominioVeh(String dominio) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer cuenta = null;
		
		String query = "select C_Cuenta "
				     + "from Vehiculos "
				     + "where (c_dominio_mercosur = ? or c_dominio_actual = ? or c_dominio_original = ?)";
		
		try {
			ps = this.getConnection().prepareStatement(query);
			ps.setString(1, dominio);
			ps.setString(2, dominio);
			ps.setString(3, dominio);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				cuenta = rs.getInt("C_Cuenta");
		} catch (SQLException e) {
			throw new ExcepcionControladaError("No se pudieron recuperar los datos del dominio", e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return cuenta;
	}
	
	public Integer recuperarCuentaPorDominioRod(String dominio) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer cuenta = null;
		
		String query = "select C_cuenta "
					 + "from Rodados "
					 + "where (c_mercosur = ? or c_dominio = ?) ";
		try {
			ps = this.getConnection().prepareStatement(query);
			ps.setString(1, dominio);
			ps.setString(2, dominio);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				cuenta = rs.getInt("C_cuenta");
			}
		} catch (SQLException e) {
			throw new ExcepcionControladaError("No se pudieron recuperar los datos del dominio", e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return cuenta;
	}
	
	/**
	 * Metodo que recupera un contribuyente (tipo + numero de documento) a partir de 'c_cuenta' de 'contrib_cuentas'
	 * 
	 * @param numeroCuenta
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public Contribuyente recuperarContribuyente(Integer numeroCuenta) throws ExcepcionControladaError {
		
		Contribuyente contribuyente = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "Select c_documento, n_documento \n"
					 + "From contrib_cuentas \n"
					 + "Where c_cuenta = ? ";
		try {
			ps = this.getConnection().prepareStatement(query);
			ps.setInt(1, numeroCuenta);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				contribuyente = FactoryContribuyente.generarInstanciaVacia();
				contribuyente.setNumeroDocumento(rs.getInt("n_documento"));
				contribuyente.setTipoDocumento(rs.getInt("c_documento"));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
		
		return contribuyente;
	}
}