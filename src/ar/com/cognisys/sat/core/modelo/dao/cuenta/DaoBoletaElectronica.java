package ar.com.cognisys.sat.core.modelo.dao.cuenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.EventoBE;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class DaoBoletaElectronica extends DAO {

	public DaoBoletaElectronica(Connection connection) {
		super(connection);
	}

	public void activar(Cuenta cuenta, Integer idUsuario) throws ErrorActualizandoRegistracionException {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( Queries.ACTIVAR_BOLETA_ELECTRONICA.getSql() );
			ps.setInt( 1, cuenta.getNumero() );
			ps.setInt( 2, cuenta.getTipoCuenta().getCodigo() );

			ps.executeUpdate();
			
		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			this.registrarEvento(cuenta, idUsuario, EventoBE.ACTIVACION);
			super.cerrarRecursoST( ps );
		}
	}

	public void desactivar(Cuenta cuenta, Integer idUsuario) throws ErrorActualizandoRegistracionException {
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( Queries.DESACTIVAR_BOLETA_ELECTRONICA.getSql() );
			ps.setInt( 1, cuenta.getNumero() );
			ps.setInt( 2, cuenta.getTipoCuenta().getCodigo() );

			ps.executeUpdate();
		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			this.registrarEvento(cuenta, idUsuario, EventoBE.DESACTIVACION);
			super.cerrarRecursoST( ps );
		}
	}

	private void registrarEvento(Cuenta cuenta, Integer idUsuario, EventoBE evento) throws ErrorActualizandoRegistracionException {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.prepareStatement( Queries.REGISTRAR_EVENTO_BOLETA_ELECTRONICA.getSql() );
			ps.setInt( 1, idUsuario );
			ps.setInt( 2, cuenta.getTipoCuenta().getCodigo_usuario() );
			ps.setString( 3, cuenta.getDatoCuenta() );
			ps.setString( 4, evento.name() );

			ps.executeUpdate();
		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public boolean existe(Cuenta cuenta) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.prepareStatement( Queries.EXISTE_BOLETA_ELECTRONICA.getSql() );
			ps.setInt( 1, cuenta.getTipoCuenta().getCodigo() );
			ps.setInt( 2, cuenta.getNumero() );

			rs = ps.executeQuery();
			
			return ( rs.next() );
		} catch ( Exception ex ) {
			throw new ErrorActualizandoRegistracionException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}
	}
}