package ar.com.cognisys.sat.core.modelo.dao.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.IRSActividad;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorActualizandoRegistracionException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class DaoRSActividad extends Dao {

	private static final String SQL_REGISTRO_DATO = "Insert into web_recau:regimen_simplificado_cabecera(cuit, correo, telefono, celular, id_regimen_simplificado_solicitante, ano)\n"
												  + "Values ( ? , ? , ? , ? , ? , ? )";
	
	private static final String UPDATE_ACTUALIZACION_DATOS = "Update web_recau:regimen_simplificado_cabecera\n"
														   + "Set correo = ?, telefono = ?, celular = ?\n"
														   + "Where cuit = ?";

	public DaoRSActividad(Connection connection) {
		super(connection);
	}

	public void registrarDatos(Integer idSolicitante, IRSActividad actividad) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareCall( SQL_REGISTRO_DATO );
			ps.setString(1, actividad.getCuit());
			ps.setString(2, actividad.getCorreo());
			ps.setString(3, actividad.getTelefono());
			ps.setString(4, actividad.getCelular());
			ps.setInt(5, idSolicitante);
			ps.setInt(6, Calendar.getInstance().get(Calendar.YEAR));
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorActualizandoRegistracionException(e);
		} finally {
			super.cerrarRecursos(ps);
		}
	}

	public void actualizar(IRSActividad actividad) throws ExcepcionControladaError {
		
		PreparedStatement ps = null;
		
		try {
			ps = this.getConnection().prepareCall( UPDATE_ACTUALIZACION_DATOS );
			ps.setString(1, actividad.getCorreo());
			ps.setString(2, actividad.getTelefono());
			ps.setString(3, actividad.getCelular());
			ps.setString(4, actividad.getCuit());
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorActualizandoRegistracionException(e);
		} finally {
			super.cerrarRecursos(ps);
		}
	}
}