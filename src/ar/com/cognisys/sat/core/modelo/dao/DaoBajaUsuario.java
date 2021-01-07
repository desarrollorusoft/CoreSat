package ar.com.cognisys.sat.core.modelo.dao;

import ar.com.cognisys.generics.persistencia.DAO;
import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.CuentaAutomotor;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.claveFiscal.UsuarioCF;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaRodados;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaVehiculos;
import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.BajaUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.MotivoBajaUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.enums.TiposDocumento;
import ar.com.cognisys.sat.core.modelo.excepcion.*;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryUsuario;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryContribuyente;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryDomicilio;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryPermisoUsuario;

import java.sql.*;
import java.util.*;

public class DaoBajaUsuario extends DAO {

	private static final String RECUPERAR_MOTIVOS = "SELECT id_motivo_baja as codigo, descripcion\n" +
													"FROM web_recau:motivo_baja\n" +
													"WHERE descripcion not in ('Por HSAT', 'Otro')";

	private static final String REGISTRAR_MOTIVO_BAJA = "INSERT INTO web_recau:usuario_motivo_baja(id_usuario, id_motivo_baja, aclaracion) VALUES ( ? , ? , ? )";

	private static final String RECUPERAR_BAJAS_USUARIO = "SELECT u.cuit, u.correo, u.telefono, u.fecha, u.flag_activacion, u.fecha_baja, m.id_motivo_baja as codigo, m.descripcion\n" +
														"FROM web_recau:usuario_cogmvl u, web_recau:motivo_baja m, web_recau:usuario_motivo_baja b\n" +
														"WHERE b.id_usuario = u.id_usuario\n" +
														"AND b.id_motivo_baja = m.id_motivo_baja\n" +
														"AND u.flag_baja = 1\n" +
														"ORDER BY u.fecha_baja desc, u.id_usuario, m.id_motivo_baja";

	public DaoBajaUsuario(Connection connection) {
		super( connection );
	}

	public List<MotivoBajaUsuario> recuperarTodos() throws ExcepcionControladaError {
		List<MotivoBajaUsuario> lista = new ArrayList<MotivoBajaUsuario>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( RECUPERAR_MOTIVOS );
			rs = ps.executeQuery();

			while (rs.next())
				lista.add( new MotivoBajaUsuario(rs.getInt("codigo"), rs.getString("descripcion")) );
		} catch ( Exception ex ) {
			throw new ErrorRecuperacionDatosException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return lista;
	}
	
	public void registrarBaja(Integer idUsuario, List<MotivoBajaUsuario> motivos) throws ErrorRecuperacionDatosException {
		for (MotivoBajaUsuario motivo: motivos)
			this.registrarMotivo(idUsuario, motivo);
	}

	private void registrarMotivo(Integer idUsuario, MotivoBajaUsuario motivo) throws ErrorRecuperacionDatosException {
		PreparedStatement ps = null;

		try {
			ps = this.prepareStatement( REGISTRAR_MOTIVO_BAJA );
			ps.setInt(1, idUsuario);
			ps.setInt(2, motivo.getCodigo());
			ps.setString(3, motivo.getAclaracion());

			ps.executeUpdate();
		} catch ( Exception ex ) {
			throw new ErrorRecuperacionDatosException( ex );
		} finally {
			super.cerrarRecursoST( ps );
		}
	}

	public List<BajaUsuario> recuperarBajas() throws ErrorRecuperacionDatosException {
		List<BajaUsuario> lista = new ArrayList<BajaUsuario>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.prepareStatement( RECUPERAR_BAJAS_USUARIO );
			rs = ps.executeQuery();

			BajaUsuario baja = null;

			while (rs.next()) {
				int idUsuario = rs.getInt("id_usuario");

				if (baja == null || !baja.tieneUsuario(idUsuario)) {
					baja = new BajaUsuario(FactoryUsuario.generar(idUsuario,
																rs.getString("cuit"),
																rs.getString("correo"),
																rs.getString("telefono"),
																"",
																false,
																rs.getInt("flag_activacion") == 1,
																rs.getDate("fecha_alta")),
																rs.getDate("fecha_baja"));
					lista.add( baja );
				}

				baja.agregarMotivo( new MotivoBajaUsuario(rs.getInt("codigo"), rs.getString("descripcion")) );
			}
		} catch ( Exception ex ) {
			throw new ErrorRecuperacionDatosException( ex );
		} finally {
			super.cerrarRecursoRS( rs );
			super.cerrarRecursoST( ps );
		}

		return lista;
	}
}