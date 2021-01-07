package ar.com.cognisys.sat.core.administrador;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.comun.ArchivoConsulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Caracter;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Categoria;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Consulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.ConsultaAsociada;
import ar.com.cognisys.sat.core.modelo.comun.consultas.FormularioConsulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.LogNotificaciones;
import ar.com.cognisys.sat.core.modelo.comun.consultas.TipoConsulta;
import ar.com.cognisys.sat.core.modelo.dao.DaoFormularioConsultas;
import ar.com.cognisys.sat.core.modelo.enums.EstadoConsulta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class AdministradorFormularioConsultas extends Administrador {

	private static final long serialVersionUID = -2450796766184157655L;

	/**
	 * Se usa en HSAT
	 * @return
	 * @throws ExcepcionControladaError
	 */
	public static List<Categoria> obtenerCategorias() throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoFormularioConsultas( con )).obtenerCategorias();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static Categoria obtenerCategoria(String nombre) throws ExcepcionControladaError {

		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			return new DaoFormularioConsultas( con ).obtenerCategoria( nombre );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<TipoConsulta> obtenerTipoConsultas() throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoFormularioConsultas( con )).obtenerTipoConsultas();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	

	public static TipoConsulta obtenerTipoConsulta(String nombre) throws ExcepcionControladaError {

		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return new DaoFormularioConsultas( con ).obtenerTipoConsulta( nombre );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static void registrarConsulta(FormularioConsulta formulario) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoFormularioConsultas( con )).registrarConsulta( formulario );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}

	}

	public static List<Consulta> buscarTodas(Integer cantidad, Integer tamanoPagina, List<Long> categorias,
											 EstadoConsulta estado, String nombreOapellido, Date fechaDesde,
											 Date fechaHasta, boolean puedeCancelar, boolean habilitarFechas) throws ExcepcionControladaError {
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoFormularioConsultas(con)).buscarTodas(cantidad, tamanoPagina, categorias, estado, nombreOapellido, fechaDesde, fechaHasta, puedeCancelar, habilitarFechas);
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<Consulta> buscarTodas(Integer cantidad, Integer tamanoPagina, String numero) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoFormularioConsultas(con)).buscarTodas(cantidad, tamanoPagina, numero);
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static Integer buscarCantidadConsultas(List<Long> categorias, EstadoConsulta estado, String nombreOapellido, Date fechaDesde, Date fechaHasta, boolean puedeCancelar, boolean habilitarFecha) throws ExcepcionControladaError {
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoFormularioConsultas(con)).buscarCantidadConsultas(categorias, estado, nombreOapellido, fechaDesde, fechaHasta, puedeCancelar, habilitarFecha);
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static Integer buscarCantidadConsultas(String numero) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return (new DaoFormularioConsultas(con)).buscarCantidadConsultas(numero);
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static void guardarCorreo(Consulta consulta, String asunto, String correo, String usuario) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoFormularioConsultas( con )).guardarCorreo( consulta, asunto, correo, usuario );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static void actualizarConsulta(Consulta consulta) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoFormularioConsultas( con )).actualizarConsulta( consulta );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static void cerrarConsulta(String idConsulta) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoFormularioConsultas( con )).cerrarConsulta( idConsulta );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static void devolverConsulta(String idConsulta) throws ExcepcionControladaError {

		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			(new DaoFormularioConsultas( con )).devolverConsulta( idConsulta );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<LogNotificaciones> buscarLogs(Consulta consulta) throws ExcepcionControladaError {
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoFormularioConsultas( con )).buscarLogs( consulta );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static String getCorreoCategoria(Categoria categoria) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return ( new DaoFormularioConsultas( con ) ).getCategoriaCorreo( categoria );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al obtener el correo correspondiente a la categoria", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<Categoria> obtenerTributos() throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return ( new DaoFormularioConsultas( con ) ).obtenerCategorias();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al obtener las categorias de las consultas", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<ConsultaAsociada> buscarChats (Long id) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();

			return ( new DaoFormularioConsultas( con ) ).recuperarNuevasConsultas(id);
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al obtener el chat de consulta", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
    }
	public static void enviarNuevaConsulta(ConsultaAsociada nuevaConsulta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			( new DaoFormularioConsultas( con ) ).enviarNuevaConsulta( nuevaConsulta );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al enviar la nueva consulta", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	public static void enviarNuevaConsulta(ConsultaAsociada nuevaConsulta, String nombreUsuario) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			( new DaoFormularioConsultas( con ) ).enviarNuevaConsultaConUsuario( nuevaConsulta,nombreUsuario );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al enviar la nueva consulta", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static boolean averiguarYaCerrada(Integer consulta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return ( new DaoFormularioConsultas( con ) ).averiguarYaCerrada( consulta );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al averiguar si la consulta esta cerrada", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static Consulta buscarConsulta(Integer idConsulta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return ( new DaoFormularioConsultas( con ) ).obtenerConsultaPorId( idConsulta );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al buscar la consulta", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	
	public static List<Consulta> buscarConsultasPorCuit(String cuit) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return new DaoFormularioConsultas( con ).obtenerConsultasPorCuit( cuit );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	
	public static List<Consulta> buscarConsultasPorCorreo(String correo) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return new DaoFormularioConsultas( con ).obtenerConsultasPorCorreo( correo );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	
	public static List<Consulta> buscarConsultasPorCuitCuenta(String cuit, String cuenta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return new DaoFormularioConsultas( con ).obtenerConsultasPorCuitCuenta( cuit, cuenta );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<Consulta> buscarConsultasAsociadas(Consulta consulta) throws ExcepcionControladaError {
		
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return ( new DaoFormularioConsultas( con ) ).obtenerConsultasPorCorreo( consulta.getCorreo() );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al buscar las consultas asociadas", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}

	}

	public static void guardarArchivo(ArchivoConsulta archivo, Long idConsulta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			( new DaoFormularioConsultas( con ) ).guardarArchivos( idConsulta, archivo );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al guardar el Archivo", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<ArchivoConsulta> recuperarArchivos(Consulta consulta) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return ( new DaoFormularioConsultas( con ) ).obtenerArchivos( consulta.getId() );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al recuperar  los archivos asociados", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
	
	public static ArchivoConsulta recuperarArchivo(Long numeroConsulta, String nombreArchivo) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			return new DaoFormularioConsultas( con ).obtenerArchivo( numeroConsulta, nombreArchivo );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static void eliminarNuevaConsulta(ConsultaAsociada consultaAsociada) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			( new DaoFormularioConsultas( con ) ).eliminarConsultaAsociada( consultaAsociada.getId() );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al eliminar la consulta asociada", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static void eliminarArchivo(ArchivoConsulta a) throws ExcepcionControladaError {
		Connection con = null;
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			( new DaoFormularioConsultas( con ) ).eliminarArchivos( a );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( "Error al eliminar el Archivo", e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static Caracter obtenerCaracter(String nombreCaracter) throws ExcepcionControladaError {
		
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return new DaoFormularioConsultas( con ).obtenerCaracter( nombreCaracter );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static Consulta recuperarConsulta(Long id) throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return new DaoFormularioConsultas( con ).obtenerConsultaPorId( id.intValue() );
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}

	public static List<Caracter> obtenerCaracteres() throws ExcepcionControladaError {
		Connection con = null;

		try {
			con = AdministradorConexiones.obtenerConexion();

			return new DaoFormularioConsultas( con ).obtenerCaracteres();
		} catch ( Exception e ) {
			throw new ExcepcionControladaError( e.getMessage(), e );
		} finally {
			AdministradorConexiones.cerrarConnection( con );
		}
	}
}
