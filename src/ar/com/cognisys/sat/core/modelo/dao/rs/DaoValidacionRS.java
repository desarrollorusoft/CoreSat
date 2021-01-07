package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaComercios;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJ;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoCartel;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.resultados.ResultadoValidacionRSOEP;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.resultados.ResultadoValidacionRSSV;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class DaoValidacionRS extends DAO {

	private static final String SP_VALIDA_RECTIFICATIVA = "{call recaudaciones:sp_rs_valido_ddjj_cog( ? , ? )}";

	private static final String SP_VALIDO_CARTELES = "{call sp_rs_valido_carteles_cog( 0 , ? , 0 , ? , 0 , ? , 0 , ? , 0 , ? , 0 , ? , 0 , ? , 0 , ? , 0 , ? , 0 , ? , ? )}";

	private static final String SP_VALIDO_OEP = "{call sp_rs_valido_oep_cog( 0 , ? , 0 , ? , ? )}";

	private static final String SP_VALIDO_SERVICIOS_VARIOS = "{call sp_rs_valido_sv_cog( 0 , ? , 0 , ? , ? )}";
	
	public DaoValidacionRS(Connection connection) {
		super(connection);
	}

	public Integer obtenerCantidadDDJJRealizadas(Integer padron, Integer ano) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			cs = this.prepareCall( SP_VALIDA_RECTIFICATIVA );
			cs.setInt( 1, padron );
			cs.setInt( 2, ano );
			cs.execute();

			rs = cs.getResultSet();

			if (rs.next())
				return rs.getInt(1);
			else
				return 100;
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}
	}
	
	/**
	 * Revisar si sirve eso, porque recibe una lista de cuentas y se maneja un listado de PadronRS
	 * 
	 * Si es 0: OK
	 * Si es 1: si está excedido en metros, tanto en LETREROS como en AVISOS
	 * Si es 2: si se excedió únicamente en LETREROS
	 * Si es 3: si el excedente fue de AVISOS
	 * 
	 * Si no se manda nada, va 0 en todos los datos de las categorias
	 * 
	 * @param listado
	 * @param carteles
	 * @param ano
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public Integer validarCarteleria(List<CuentaComercios> listado, List<TipoCartel> carteles, Integer ano) throws ExcepcionControladaError {

		Map<TipoCartel, Float> mapa = this.peraparDatos( listado, carteles );
		return this.ejecutarValidacionCarteleria( mapa, ano );
	}

	private Map<TipoCartel, Float> peraparDatos(List<CuentaComercios> listado, List<TipoCartel> carteles) {

		Map<TipoCartel, Float> mapa = new HashMap<TipoCartel, Float>();

		for ( TipoCartel tc : carteles )
			mapa.put( tc, 0f );

		for ( CuentaComercios cc : listado )
			for ( DDJJ ddjj : cc.getListaDeclaraciones() )
				for ( DDJJCarteleria cartel : ddjj.getListaCarteleria() )
					mapa.put( cartel.getTipo(), mapa.get( cartel.getTipo() ) + cartel.getMetros() );

		return mapa;
	}
	
	private Integer ejecutarValidacionCarteleria(Map<TipoCartel, Float> mapa, Integer ano) throws ExcepcionControladaError {

		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			cs = this.prepareCall( SP_VALIDO_CARTELES );

			for ( Entry<TipoCartel, Float> entry : mapa.entrySet() )
				cs.setFloat( entry.getKey().getCodigo(), entry.getValue() );

			cs.setInt( 11, ano );
			cs.execute();
			rs = cs.getResultSet();

			if (rs.next())
				return rs.getInt( 1 );
			else
				return -1;
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}
	}
	
	public ResultadoValidacionRSOEP validarOEP(List<CuentaComercios> listado, Integer ano) throws ExcepcionControladaError {

		float metros = 0f;
		int unidades = 0;

		for ( CuentaComercios cc : listado )
			for ( DDJJ ddjj : cc.getListaDeclaraciones() ) {
				metros += ddjj.getOEPMetrosToldos();
				unidades += ddjj.getOEPUnidadesPostes();
			}

		return this.ejecutarValidacionOEP( metros, unidades, ano );
	}

	private ResultadoValidacionRSOEP ejecutarValidacionOEP(float metros, int unidades, int ano) throws ExcepcionControladaError {

		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			cs = this.prepareCall( SP_VALIDO_OEP );

			cs.setFloat( 1, metros );
			cs.setInt( 2, unidades );
			cs.setInt( 3, ano );
			cs.execute();

			rs = cs.getResultSet();

			if ( rs.next() )
				return new ResultadoValidacionRSOEP( rs.getInt( 1 ), rs.getInt( 2 ) );

		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}

		return null;
	}
	
	public ResultadoValidacionRSSV validarSV(List<CuentaComercios> listado, Integer ano) throws ExcepcionControladaError {

		int motores = 0;
		int calderas = 0;

		for ( CuentaComercios cc : listado ) {
			for ( DDJJ ddjj : cc.getListaDeclaraciones() ) {
				motores += ddjj.getServiciosValios().getMotores();
				calderas += ddjj.getServiciosValios().getCalderas();
			}
		}

		return this.ejecutarValidacionSV( motores, calderas, ano );
	}

	private ResultadoValidacionRSSV ejecutarValidacionSV(int motores, int calderas, int ano) throws ExcepcionControladaError {

		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			cs = this.prepareCall( SP_VALIDO_SERVICIOS_VARIOS );
			cs.setFloat( 1, motores );
			cs.setInt( 2, calderas );
			cs.setInt( 3, ano );
			cs.execute();

			rs = cs.getResultSet();

			if ( rs.next() )
				return new ResultadoValidacionRSSV( rs.getInt( 1 ) );
		} catch ( Exception e ) {
			throw new ErrorRecuperacionDatosException( e );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( cs );
		}

		return null;
	}
}