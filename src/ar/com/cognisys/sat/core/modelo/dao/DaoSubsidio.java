package ar.com.cognisys.sat.core.modelo.dao;

import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.AdjuntoTramite;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.DatosComercio;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.EstadoTramite;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.TramiteSubsidio;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.validador.CUIT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public class DaoSubsidio extends Dao {

	private static final String QUERY_GET_TRAMITE = "select id_pae, trim(d_apellido) as ape_titular, trim(d_nombre) as nom_titular, n_cuit, \n" +
													"trim(d_calle) d_calle, n_nro, nvl(c_piso,'') c_piso, nvl(c_dpto,'') c_dpto, c_postal, url_pdf, telefono_dec, celular_dec, dfe_dec, \n" +
													"trim(d_ape_nom_dec) as nombre, nvl(n_dni_dec,'') as dni, trim(n_cuit_dec) as cuil, trim(d_correo_dec) as correo, trim(n_cbu_dec) as cbu, confirmado, aceptado, trim(d_banco_dec) as d_banco_dec\n" +
													"from ddjj_pae2020_cab \n" +
													"WHERE n_cuit = ?";

	private static final String QUERY_GET_DATOS = "select c_cuenta, trim(d_rubro) rubro, trim(d_razon_social) razon_social,  \n" +
													"trim(d_calle) d_calle, n_nro, nvl(c_piso,'') c_piso, nvl(c_dpto,'') c_dpto, c_postal,\n" +
													"trim(d_fantasia_dec) nombre_fantasia, trim(exp_hab_dec) expendiente, propietario_dec propietario, vig_cont_des_dec, vig_cont_has_dec, cta_abl_dec, resp_abl_dec\n" +
													"from ddjj_pae2020_det\n" +
													"where id_pae = ?";

	private static final String QUERY_GET_ADJUNTOS = "select nombre, url \n" +
													"from ddjj_pae2020_adj \n" +
													"where id_pae = ?";

	private static final String UPDATE_TRAMITE = "update ddjj_pae2020_cab\n" +
												"set d_ape_nom_dec = ?, n_dni_dec = ?, n_cuit_dec = ?, d_correo_dec = ?, n_cbu_dec = ?, d_banco_dec = ?, telefono_dec = ?, celular_dec = ?, dfe_dec = ?\n" +
												"where id_pae = ?";

	private static final String UPDATE_DATOS = "update ddjj_pae2020_det\n" +
												"set d_fantasia_dec = ?, exp_hab_dec = ?, propietario_dec = ?, vig_cont_des_dec = ?, vig_cont_has_dec = ?, cta_abl_dec = ?, resp_abl_dec = ?\n" +
												"where id_pae = ?\n" +
												"AND c_cuenta = ?";

	private static final String UPDATE_DATOS_sin_fechas = "update ddjj_pae2020_det\n" +
															"set d_fantasia_dec = ?, exp_hab_dec = ?, propietario_dec = ?, cta_abl_dec = ?, resp_abl_dec = ?\n" +
															"where id_pae = ?\n" +
															"AND c_cuenta = ?";


	private static final String DELETE_ADJUNTOS = "DELETE FROM ddjj_pae2020_adj WHERE id_pae = ? AND c_cuenta = ?";

	private static final String INSERT_ADJUNTO = "INSERT INTO ddjj_pae2020_adj(id_pae, c_cuenta, url, nombre) values ( ? , ? , ? , ? )";

	private static final String UPDATE_EMVIAR_TRAMITE = "update ddjj_pae2020_cab set confirmado = 'S', url_pdf = ?, f_envio = current WHERE id_pae = ?";

	private static final String QUERY_LISTA_BASE = "select skip ? first ? id_pae, trim(d_apellido) as ape_titular, trim(d_nombre) as nom_titular, n_cuit, n_cuit_dec as cuil, n_dni_dec as dni, \n" +
													"telefono_dec, celular_dec, dfe_dec, trim(n_cuit_dec) as cuil, trim(d_correo_dec) as correo, c.d_ape_nom_dec as nombre \n" +
													"from ddjj_pae2020_cab c \n" +
													"WHERE confirmado = 'S' \n" +
													"AND aceptado = ? ";

	private static final String QUERY_LISTA_BASE_CANTIDAD = "select count(*) as cantidad \n" +
															"from ddjj_pae2020_cab c \n" +
															"WHERE confirmado = 'S' \n" +
															"AND aceptado = ? ";

	private static final String UPDATE_CERRAR_TRAMITE = "update ddjj_pae2020_cab set aceptado = 'S', f_cierre = current WHERE id_pae = ?";

	public DaoSubsidio(Connection connection) {
		super(connection);
	}

	public TramiteSubsidio recuperarTramite(String cuit) throws ExcepcionControladaError {
		TramiteSubsidio t = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( QUERY_GET_TRAMITE );
			ps.setString( 1, cuit );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				t = new TramiteSubsidio(rs.getInt("id_pae"));
				t.setTitular(rs.getString("ape_titular"), rs.getString("nom_titular"));
				t.setCbu(rs.getString("cbu"));
				t.setCorreo(rs.getString("correo"));
				t.setCuit(rs.getString("cuil"));
				t.setDni(rs.getString("dni"));
				t.setEstado(rs.getString("confirmado").equals("S") ? EstadoTramite.ENVIADO : EstadoTramite.INICIADO );
				t.setCuitComercio(rs.getString("n_cuit"));
				t.setDomicilio(rs.getString("d_calle"), rs.getString("n_nro"), rs.getString("c_piso"), rs.getString("c_dpto"), rs.getString("c_postal"));
				t.setRepresentante(rs.getString("nombre"));
				t.setBanco(rs.getString("d_banco_dec"));
				t.setUrlPDF(rs.getString("url_pdf"));
				t.setTelefono(rs.getString("telefono_dec"));
				t.setCelular(rs.getString("celular_dec"));
				t.setDfe(rs.getString("dfe_dec").equals("S"));
				t.setEstado( rs.getString("aceptado").equals("S") ? EstadoTramite.CERRADO : t.getEstado() );

				t.setDatos( this.recuperarCuentas( t.getId() ) );
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return t;
	}

	private List<DatosComercio> recuperarCuentas(Integer id) throws ExcepcionControladaError {
		List<DatosComercio> list = new ArrayList<DatosComercio>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( QUERY_GET_DATOS );
			ps.setInt( 1, id );
			rs = ps.executeQuery();

			while ( rs.next() ) {
				DatosComercio d = new DatosComercio();
				d.setCuenta(rs.getInt("c_cuenta"));
				d.setCuentaAbl(rs.getInt("cta_abl_dec"));
				d.setDomicilio(rs.getString("d_calle"), rs.getString("n_nro"), rs.getString("c_piso"), rs.getString("c_dpto"), rs.getString("c_postal"));
				d.setExpediente(rs.getString("expendiente"));
				d.setInicioContrato(rs.getDate("vig_cont_des_dec"));
				d.setFinContrato(rs.getDate("vig_cont_has_dec"));
				d.setNombreFantasia(rs.getString("nombre_fantasia"));
				d.setRazonSocial(rs.getString("razon_social"));
				if (rs.getString("propietario") == null)
					d.setPropietario(false);
				else d.setPropietario(true);
				if (rs.getString("resp_abl_dec") == null)
					d.setResponsableAbl(false);
				else d.setResponsableAbl(true);
				d.setRubro(rs.getString("rubro"));
				d.setAdjuntos( this.recuperarAdjuntos(id) );
				list.add( d );
			}
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return list;
	}

	public List<AdjuntoTramite> recuperarAdjuntos(Integer id) throws ExcepcionControladaError {
		List<AdjuntoTramite> lista = new ArrayList<AdjuntoTramite>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( QUERY_GET_ADJUNTOS );
			ps.setInt( 1, id );
			rs = ps.executeQuery();

			while ( rs.next() )
				lista.add(new AdjuntoTramite(rs.getString("url"), rs.getString("nombre")));
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return lista;
	}

	public void actualizar(TramiteSubsidio tramite) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( UPDATE_TRAMITE );
			ps.setString(1, tramite.getRepresentante().isEmpty() ? "" : tramite.getRepresentante().toUpperCase());
			ps.setInt(2, tramite.getDni().isEmpty()? 0 : new Integer(tramite.getDni()));
			ps.setString(3, CUIT.quitarMascara(tramite.getCuit()));
			ps.setString(4, tramite.getCorreo().isEmpty() ? "" : tramite.getCorreo().toLowerCase());
			ps.setString(5, tramite.getCbu());
			ps.setString(6, tramite.getBanco().isEmpty() ? "" : tramite.getBanco().toUpperCase());
			ps.setString(7, tramite.getTelefono());
			ps.setString(8, tramite.getCelular());
			ps.setString(9, tramite.isDfe() ? "S":"N");
			ps.setInt( 10, tramite.getId() );

			ps.executeUpdate();

			this.actualizarCuentas( tramite );
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}

	private void actualizarCuentas(TramiteSubsidio tramite) throws ExcepcionControladaError {
		for (DatosComercio d: tramite.getDatos())
			this.actualizarCuenta( tramite.getId(), d );
	}

	private void actualizarCuenta(Integer id, DatosComercio d) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( UPDATE_DATOS );
			ps.setString(1, d.getNombreFantasia().isEmpty() ? "" : d.getNombreFantasia().toUpperCase());
			ps.setString(2, d.getExpediente().isEmpty() ? "" : d.getExpediente().toUpperCase());
			ps.setString(3, d.isPropietario()? "S" : "N");
			ps.setDate(4, d.getInicioContrato() == null ? null : new java.sql.Date(d.getInicioContrato().getTime()));
			ps.setDate(5, d.getFinContrato() == null ? null : new java.sql.Date(d.getFinContrato().getTime()));
			ps.setInt(6, d.getCuentaAbl());
			ps.setString(7, d.isResponsableAbl()? "S" : "N");
			ps.setInt( 8, id );
			ps.setInt( 9, d.getCuenta() );

			ps.executeUpdate();

			this.actualizarAdjuntos( id, d );
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}

	public void actualizarAdjuntos(Integer id, DatosComercio d) throws ExcepcionControladaError {
		this.borrarAdjuntos( id, d.getCuenta() );
		for (AdjuntoTramite adjunto: d.getAdjuntos())
			this.crearAdjunto( id, d.getCuenta(), adjunto );
	}

	public void borrarAdjuntos(Integer id, Integer cuenta) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( DELETE_ADJUNTOS );
			ps.setInt( 1, id );
			ps.setInt( 2, cuenta );
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}

	public void crearAdjunto(Integer id, Integer cuenta, AdjuntoTramite a) throws ExcepcionControladaError {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = this.getConnection().prepareStatement( INSERT_ADJUNTO );
			ps.setInt( 1, id );
			ps.setInt( 2, cuenta );
			ps.setString( 3, a.getUrl() );
			ps.setString( 4, a.getNombre() );
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps, rs);
		}
	}

	public void enviar(TramiteSubsidio tramite) throws ExcepcionControladaError {
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement(UPDATE_EMVIAR_TRAMITE);
			ps.setString(1, tramite.getUrlPDF());
			ps.setInt( 2, tramite.getId() );
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps);
		}
	}

	public List<TramiteSubsidio> recuperarLista(Integer inicio, Integer cantidad, Integer cuenta, Date fechaDesde, Date fechaHasta, EstadoTramite estado) throws ErrorRecuperacionDatosException {
		List<TramiteSubsidio> lista = new ArrayList<TramiteSubsidio>();
		ResultSet rs = null;
		PreparedStatement ps = null;

		String queryCompleta = this.armarQueryCompleta(QUERY_LISTA_BASE, cuenta, fechaDesde, fechaHasta);

		try {
			ps = this.getConnection().prepareStatement(queryCompleta);
			ps.setInt(1, inicio);
			ps.setInt(2, cantidad);
			ps.setString(3, estado == EstadoTramite.CERRADO ? "S" : "N");

			rs = ps.executeQuery();

			while (rs.next()) {
				TramiteSubsidio t = new TramiteSubsidio(rs.getInt("id_pae"));
				t.setTitular(rs.getString("ape_titular"), rs.getString("nom_titular"));
				t.setCorreo(rs.getString("correo"));
				t.setCuit(rs.getString("cuil"));
				t.setDni(rs.getString("dni"));
				t.setCuitComercio(rs.getString("n_cuit"));
				t.setRepresentante(rs.getString("nombre"));
				t.setCelular(rs.getString("celular_dec"));
				t.setDfe(rs.getString("dfe_dec").equals("S"));
				lista.add(t);
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return lista;
	}

	public int recuperarCantidadLista(Integer padron, Date fechaDesde, Date fechaHasta, EstadoTramite estado) throws ErrorRecuperacionDatosException {
		ResultSet rs = null;
		PreparedStatement ps = null;

		String queryCompleta = this.armarQueryCompleta(QUERY_LISTA_BASE_CANTIDAD, padron, fechaDesde, fechaHasta);

		try {
			ps = this.getConnection().prepareStatement(queryCompleta);
			ps.setString(1, estado == EstadoTramite.CERRADO ? "S" : "N");

			rs = ps.executeQuery();

			if (rs.next())
				return rs.getInt("cantidad");
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			super.cerrarRecursos(ps, rs);
		}

		return 0;
	}

	private String armarQueryCompleta(String queryBase, Integer cuenta, Date fechaDesde, Date fechaHasta) {
		String query = "";

		if (cuenta != null)
			query = query + "And exists (select 1 from ddjj_pae2020_det d where d.id_pae = c.id_pae And d.c_cuenta = "+cuenta+")\n";

		if (fechaDesde != null && fechaHasta != null) {
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
			@SuppressWarnings("deprecation")
			Date fechaHastaCorrecta = new GregorianCalendar(fechaHasta.getYear() + 1900, fechaHasta.getMonth(), fechaHasta.getDate() + 1).getTime();

			query = query + "And (f_envio Between To_Date('" + formatoFecha.format(fechaDesde) + "', '%d-%m-%Y') And To_Date('" + formatoFecha.format(fechaHastaCorrecta) + "', '%d-%m-%Y')) \n";
		}
		return queryBase + query + "Order By 1 Desc \n";
	}

    public void cerrar(TramiteSubsidio tramite) throws ErrorRecuperacionDatosException {
		PreparedStatement ps = null;

		try {
			ps = this.getConnection().prepareStatement(UPDATE_CERRAR_TRAMITE);
			ps.setInt( 1, tramite.getId() );
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ErrorRecuperacionDatosException(e);
		} finally {
			super.cerrarRecursos(ps);
		}
	}
}