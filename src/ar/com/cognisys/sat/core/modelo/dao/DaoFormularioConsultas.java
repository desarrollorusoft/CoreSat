package ar.com.cognisys.sat.core.modelo.dao;

import ar.com.cognisys.sat.core.modelo.Queries;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.ArchivoConsulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.*;
import ar.com.cognisys.sat.core.modelo.enums.EstadoConsulta;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.FactoryArchivo;
import ar.com.cognisys.sat.core.modelo.factory.FactoryArchivoConsulta;
import ar.com.cognisys.sat.core.modelo.factory.consultas.*;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryPermiso;
import ar.com.cognisys.sat.v2.core.controlador.administrador.AdministradorConsultaArchivo;
import ar.com.cognisys.sat.v2.core.controlador.administrador.properties.AdministradorProperties;
import ar.com.cognisys.sat.v2.core.modelo.bo.ConsultaArchivo;
import ar.com.cognisys.sat.v2.core.modelo.exception.ErrorLecturaPropertiesException;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaoFormularioConsultas extends Dao {

    public DaoFormularioConsultas(Connection connection) {
        super(connection);
    }

    public List<Categoria> obtenerCategorias() throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Categoria> lista = new ArrayList<Categoria>();

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CATEGORIAS_CONSULTAS.getSql());
            rs = ps.executeQuery();
            while (rs.next()) {
                Long l = rs.getLong("id_consulta_categoria");
                lista.add(FactoryCategoria.generarInstancia(l, rs.getString("nombre"),
                        FactoryDato.generarInstancia(rs.getLong("id_consulta_categoria_dato"), rs.getString("dato"), rs.getInt("requerido"), rs.getString("expresion_regular"), rs.getString("mensaje_error")),
                        obtenerCaracteres(rs.getInt("id_consulta_categoria")), FactoryPermiso.generarIntanciaCompleta(rs.getString("permiso"))));
            }

            return lista;
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

    }

    private List<Caracter> obtenerCaracteres(Integer idCategoria) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Caracter> lista = new ArrayList<Caracter>();

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CARACTERES.getSql());
            ps.setInt(1, idCategoria);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(FactoryCaracter.generarInstancia(rs.getLong("id_consulta_caracter"), rs.getString("nombre")));
            }

            return lista;
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

    }

    public List<Caracter> obtenerCaracteres() throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Caracter> lista = new ArrayList<Caracter>();
        String query = Queries.OBTENER_CARACTERES_GENERALES.getSql();

            try {
                ps = this.getConnection().prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    lista.add(FactoryCaracter.generarInstancia(rs.getLong("id_consulta_caracter"), rs.getString("nombre")));
                }

                return lista;
            } catch (SQLException e) {
                throw new ErrorEnBaseException(e.getMessage(), e);
            } finally {
                try {
                    if (rs != null)
                        rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (ps != null)
                        ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

    }

    public List<TipoConsulta> obtenerTipoConsultas() throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TipoConsulta> lista = new ArrayList<TipoConsulta>();

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_TIPO_CONSULTA.getSql());
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(FactoryTipoConsulta.generarInstancia(rs.getLong("id_tipo"), rs.getString("nombre")));
            }

            return lista;

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

    }

    public TipoConsulta obtenerTipoConsulta(String nombre) throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        TipoConsulta tipoConsulta = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_TIPO_CONSULTA_NOMBRE.getSql());
            ps.setString(1, nombre);

            rs = ps.executeQuery();
            if (rs.next()) {
                tipoConsulta = FactoryTipoConsulta.generarInstancia(
                        rs.getLong("id_tipo"),
                        rs.getString("nombre"));
            }

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }

        return tipoConsulta;
    }

    public void registrarConsulta(FormularioConsulta formulario) throws ExcepcionControladaError {

        try {
            this.getConnection().setAutoCommit(false);
            formulario.setCuit(cuitSinMascara(formulario.getCuit()));
            guardarDatosformulario(formulario);


            if (formulario.getArchivos() != null) {
                for (ArchivoConsulta a : formulario.getArchivos()) {
                    guardarArchivos(formulario.getId(), a);
                }
            }

            this.getConnection().commit();
        } catch (SQLException e) {
            try {
                this.getConnection().rollback();
            } catch (SQLException e1) {
            }
            throw new ErrorEnBaseException(e.getMessage(), e);
        }

    }


    public void guardarArchivos(Long l, ArchivoConsulta a) throws ExcepcionControladaError {

        //TODO CAMBIAR ESTO CUANDO HAYA TIEMPO
        try {
            a.getArchivo().setFilePath(AdministradorProperties.getInstancia().getPropiedad("DIRECTORIO_CONSULTA_FTP") + "/" + l.toString() + "/");
            ConsultaArchivo c = new ConsultaArchivo();
            c.setData(a.getArchivo().getData());
            c.setNumeroConsulta(Integer.valueOf(l.toString()));
            c.setDirectorio(a.getArchivo().getFilePath());
            c.setNombre(a.getArchivo().getNombre());
            c.normalizarNombre();
            c.setTipo(a.getArchivo().getTipoContenido());
            c.generarNombre();

            new AdministradorConsultaArchivo().crear(c);

            a.getArchivo().setFilePath(AdministradorProperties.getInstancia().getPropiedad("ENCABEZADO_FTP_PUBLICO_DESCARGAS")
                    + c.getDirectorio()
                    + c.getNombreGenerado());
        } catch (ErrorLecturaPropertiesException e) {
            throw new ExcepcionControladaError(e.getMessage(), e);
        }
    }

    private void guardarDatosformulario(FormularioConsulta formulario) throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.INSERTAR_FORMULARIO.getSql(), PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, formulario.getNombre().toUpperCase());
            ps.setString(2, formulario.getApellido().toUpperCase());
            ps.setString(3, formulario.getCorreo().toUpperCase());
            ps.setInt(4, formulario.isTelefonoSeleccionado() ? 1 : 0);
            ps.setString(5, formulario.getTelefono());
            ps.setString(6, formulario.getTelefono2());
            ps.setString(7, formulario.getCuit());
            ps.setLong(8, formulario.getIdTipoConsulta());
            ps.setLong(9, formulario.getIdCategoria());
            ps.setLong(10,6);                             // Se inserta "No informado" para no tener que sacar totalmente caracter del modelo
            ps.setLong(11, formulario.getIdDato());
            ps.setString(12, formulario.getDescripcion() == null ? formulario.getDescripcion() : formulario.getDescripcion().toUpperCase());
            ps.setString(13, formulario.getDato() != null ? formulario.getDato().toUpperCase() : "");
            ps.setTimestamp(14, new Timestamp(new Date().getTime()));
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next())
                formulario.setId(rs.getLong(1));

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

    }

    private String cuitSinMascara(String cuit) {
        return cuit.replaceAll("-", "").trim();
    }

    public List<Consulta> buscarTodas(Integer cantidad, Integer tamanoPagina, List<Long> categorias, EstadoConsulta estado, String nombreOApellido, Date fechaDesde, Date fechaHasta, boolean puedeCancelar, boolean habilitarFechas) throws ErrorEnBaseException {
        if( habilitarFechas )
            return this.buscarTodasConFecha(cantidad, tamanoPagina, categorias, estado, nombreOApellido, fechaDesde, fechaHasta, puedeCancelar);
        else
            return this.buscarTodasSinFecha(cantidad, tamanoPagina, categorias, estado, nombreOApellido, puedeCancelar);
    }

    private List<Consulta> buscarTodasConFecha(Integer cantidad, Integer tamanoPagina, List<Long> categorias, EstadoConsulta estado, String nombreOApellido, Date fechaDesde, Date fechaHasta, boolean puedeCancelar)
            throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String query = reemplazarValores(Queries.OBTENER_CONSULTA.getSql(), categorias);
            String finalQuery = query.replace("__ESTADO__", this.reemplazarEstado(estado));

            ps = this.getConnection().prepareStatement(finalQuery);


            ps.setInt(1, cantidad);
            ps.setInt(2, tamanoPagina);

            ps.setInt(3, puedeCancelar ? 1 : 0);
            ps.setInt(4, EstadoConsulta.CANCELADA.getId());

            ps.setString(5, new java.sql.Date(fechaDesde.getTime()).toString());
            ps.setString(6, new java.sql.Date(fechaHasta.getTime()).toString());

            ps.setString(7, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(8, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(9, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(10, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(11, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");


            rs = ps.executeQuery();

            return getLista( rs );
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }
    }

    private List<Consulta> buscarTodasSinFecha(Integer cantidad, Integer tamanoPagina, List<Long> categorias, EstadoConsulta estado, String nombreOApellido, boolean puedeCancelar)
            throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String query = reemplazarValores(Queries.OBTENER_CONSULTA_SIN_FECHA.getSql(), categorias);
            String finalQuery = query.replace("__ESTADO__", this.reemplazarEstado(estado));

            ps = this.getConnection().prepareStatement(finalQuery);

            ps.setInt(1, cantidad);
            ps.setInt(2, tamanoPagina);

            ps.setInt(3, puedeCancelar ? 1 : 0);
            ps.setInt(4, EstadoConsulta.CANCELADA.getId());

            ps.setString(5, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(6, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(7, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(8, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(9, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");

            rs = ps.executeQuery();

            return getLista( rs );
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<Consulta> buscarTodas(Integer cantidad, Integer tamanoPagina, String numero)
            throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CONSULTAS_POR_ID.getSql());


            ps.setInt(1, cantidad);
            ps.setInt(2, tamanoPagina);
            ps.setString(3, numero);

            rs = ps.executeQuery();

            return getLista( rs );

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }
    }

    private List<Consulta> getLista( ResultSet rs ) throws ErrorEnBaseException, SQLException {
        List<Consulta> lista = new ArrayList<Consulta>();

        while (rs.next()) {
            Long l = rs.getLong("id_consulta_formulario");
            lista.add(FactoryConsulta.generarInstancia(l,
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dato"),
                    EstadoConsulta.obtenerPorId(rs.getInt("estado")),
                    rs.getString("tipo"),
                    FactoryCategoria.generarInstancia(rs.getLong("idCategoria"), rs.getString("categoria")),
                    rs.getTimestamp("fecha_sistema"),
                    rs.getTimestamp("fecha_actualizacion")));
        }

        return lista;
    }

    public Integer buscarCantidadConsultas(List<Long> categorias, EstadoConsulta estado, String nombreOApellido, Date fechaDesde, Date fechaHasta, boolean puedeCancelar, boolean habilitarFechas) throws ErrorEnBaseException {
        if( habilitarFechas )
            return buscarCantidadConsultasConFecha(categorias, estado, nombreOApellido, fechaDesde, fechaHasta, puedeCancelar);
        else
            return buscarCantidadConsultasSinFecha(categorias, estado, nombreOApellido, puedeCancelar);
    }

    public Integer buscarCantidadConsultasConFecha(List<Long> categorias, EstadoConsulta estado, String nombreOApellido, Date fechaDesde, Date fechaHasta, boolean puedeCancelar) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String query = reemplazarValores(Queries.OBTENER_CANTIDAD_CONSULTAS.getSql(), categorias);
            String finalQuery = query.replace("__ESTADO__", this.reemplazarEstado(estado));

            ps = this.getConnection().prepareStatement(finalQuery);

            ps.setInt(1, puedeCancelar ? 1 : 0);
            ps.setInt(2, EstadoConsulta.CANCELADA.getId());

            ps.setString(3, new java.sql.Date(fechaDesde.getTime()).toString());
            ps.setString(4, new java.sql.Date(fechaHasta.getTime()).toString());

            ps.setString(5, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(6, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(7, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(8, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(9, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");

            rs = ps.executeQuery();

            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

        return null;
    }

    public Integer buscarCantidadConsultasSinFecha(List<Long> categorias, EstadoConsulta estado, String nombreOApellido, boolean puedeCancelar) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String query = reemplazarValores(Queries.OBTENER_CANTIDAD_CONSULTAS_SIN_FECHA.getSql(), categorias);
            String finalQuery = query.replace("__ESTADO__", this.reemplazarEstado(estado));

            ps = this.getConnection().prepareStatement(finalQuery);

            ps.setInt(1, puedeCancelar ? 1 : 0);
            ps.setInt(2, EstadoConsulta.CANCELADA.getId());

            ps.setString(3, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(4, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(5, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(6, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");
            ps.setString(7, (nombreOApellido == null || nombreOApellido.isEmpty()) ? "%%" : "%" + nombreOApellido.toUpperCase() + "%");

            rs = ps.executeQuery();

            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

        return null;
    }

    public Integer buscarCantidadConsultas(String numero) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CANTIDAD_CONSULTAS_POR_ID.getSql());
            ps.setString(1, numero);

            rs = ps.executeQuery();

            while (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

        return null;
    }

    private CharSequence reemplazarEstado(EstadoConsulta estado) {

        if (estado != null)
            return String.valueOf(estado.getId());

        return "SELECT UNIQUE estado FROM web_recau:consulta_formulario";
    }

    private String reemplazarValores(String query, List<Long> categorias) {

        String reemplazo = "";

        for (int i = 0; i < categorias.size(); i++) {
            reemplazo += String.valueOf(categorias.get(i));
            reemplazo += ",";
        }
        if (reemplazo.length() > 0)
            reemplazo = reemplazo.substring(0, reemplazo.length() - 1);

        else
            reemplazo = "SELECT id_consulta_categoria FROM web_recau:consulta_categoria";

        return query.replace("__VALORES__", reemplazo);
    }

    public List<ArchivoConsulta> obtenerArchivos(Long l) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ArchivoConsulta> lista = new ArrayList<ArchivoConsulta>();
        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_ARCHIVOS_CONSULTA.getSql());
            ps.setLong(1, l);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(FactoryArchivoConsulta.generarInstancia(FactoryArchivo.generarInstancia(rs.getLong("id_consulta_archivo"), rs.getString("nombre"), rs.getString("tipo_contenido"), rs.getString("RUTA")), rs.getInt("archivo_consultante") == 1));
            }
            return lista;

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public ArchivoConsulta obtenerArchivo(Long numeroConsulta, String nombreArchivo) throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_ARCHIVO_CONSULTA.getSql());

            ps.setLong(1, numeroConsulta);
            ps.setString(2, nombreArchivo);

            rs = ps.executeQuery();

            if (rs.next())
                return FactoryArchivoConsulta.generarInstancia(FactoryArchivo.generarInstancia(rs.getLong("id_consulta_archivo"), rs.getString("nombre"), rs.getString("tipo_contenido"), rs.getString("ruta")), rs.getInt("archivo_consultante") == 1);

            return null;

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }
    }

    public void guardarCorreo(Consulta consulta, String asunto, String correo, String usuario) throws ErrorEnBaseException {

        PreparedStatement ps = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.GUARDAR_CORREO.getSql());
            ps.setLong(1, consulta.getId());
            ps.setString(2, asunto);
            ps.setString(3, correo);
            ps.setString(4, usuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

    }

    @Deprecated
    public void actualizarConsulta(Consulta consulta) throws ErrorEnBaseException {

        PreparedStatement ps = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.ACTUALIZAR_CONSULTA.getSql());
            ps.setTimestamp(1, new Timestamp((new Date().getTime())));
            ps.setInt(2, consulta.getEstado().getId());
            ps.setLong(3, consulta.getCategoria().getId());
            ps.setLong(4, consulta.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public void cerrarConsulta(String idConsulta) throws ErrorEnBaseException {

        PreparedStatement ps = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.ACTUALIZAR_CONSULTA.getSql());
            ps.setTimestamp(1, new Timestamp((new Date().getTime())));
            ps.setInt(2, EstadoConsulta.CERRADO.getId());
            ps.setLong(3, Long.valueOf(idConsulta));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public void devolverConsulta(String idConsulta) throws ErrorEnBaseException {

        PreparedStatement ps = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.ACTUALIZAR_CONSULTA.getSql());
            ps.setTimestamp(1, new Timestamp((new Date().getTime())));
            ps.setInt(2, EstadoConsulta.DEVUELTA.getId());
            ps.setLong(3, Long.valueOf(idConsulta));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<LogNotificaciones> buscarLogs(Consulta consulta) throws ExcepcionControladaError {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LogNotificaciones> lista = new ArrayList<LogNotificaciones>();

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_LOG_CONSULTA.getSql());
            ps.setLong(1, consulta.getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(FactoryLogNotificacones.generarInstancia(rs.getString("asunto"), rs.getTimestamp("fecha_sistema"), rs.getString("correo"), rs.getString("usuario")));
            }

            return lista;
        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
            }
        }

    }

    public String getCategoriaCorreo(Categoria categoria) throws ExcepcionControladaError {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String correo = null;
        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CATEGORIA_CORREO.getSql());
            ps.setLong(1, categoria.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                correo = rs.getString("Correo");
            }
            return correo;
        } catch (Exception e) {
            throw new ExcepcionControladaError("Error al obtener el correo de la categoria", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public void enviarNuevaConsulta(ConsultaAsociada nuevaConsulta) throws ExcepcionControladaError {
        PreparedStatement ps = null;
        try {
            this.getConnection().setAutoCommit(false);

            ps = this.getConnection().prepareStatement(Queries.AGREGAR_NUEVA_CONSULTA.getSql());
            ps.setInt(1, nuevaConsulta.getIdConsultaAsociada());
            ps.setString(2, nuevaConsulta.getNuevaConsulta());
            ps.setInt(3, nuevaConsulta.isDelConsultante() ? 1 : 0);
            ps.setString(4, nuevaConsulta.getUsuario());

            ps.executeUpdate();

            nuevaConsulta.setId(obtenerNuevoIdConsultaAsociada());

            this.getConnection().commit();
        } catch (SQLException e) {

            try {
                this.getConnection().rollback();
            } catch (SQLException e1) {
            }
            throw new ExcepcionControladaError("Error al enviar la nueva consulta", e);

        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public void enviarNuevaConsultaConUsuario(ConsultaAsociada nuevaConsulta, String nombreUsuario) throws ExcepcionControladaError {
        PreparedStatement ps = null;
        try {
            this.getConnection().setAutoCommit(false);

            ps = this.getConnection().prepareStatement(Queries.AGREGAR_NUEVA_CONSULTA.getSql());
            ps.setInt(1, nuevaConsulta.getIdConsultaAsociada());
            ps.setString(2, nuevaConsulta.getNuevaConsulta());
            ps.setInt(3, nuevaConsulta.isDelConsultante() ? 1 : 0);
            ps.setString(4, nombreUsuario);

            ps.executeUpdate();

            nuevaConsulta.setId(obtenerNuevoIdConsultaAsociada());

            this.getConnection().commit();
        } catch (SQLException e) {

            try {
                this.getConnection().rollback();
            } catch (SQLException e1) {
            }
            throw new ExcepcionControladaError("Error al enviar la nueva consulta", e);

        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }
    }


    private Long obtenerNuevoIdConsultaAsociada() throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_ULTIMO_ID_CONSULTA_ASOCIADA.getSql());
            rs = ps.executeQuery();
            if (rs.next())
                return rs.getLong(1);

            return null;
        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrio un error al recuparar las consultas asociadas", e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<ConsultaAsociada> recuperarNuevasConsultas(Long l) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<ConsultaAsociada> lista = new ArrayList<ConsultaAsociada>();

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_NUEVAS_CONSULTAS.getSql());
            ps.setInt(1, l.intValue());
            rs = ps.executeQuery();
            while (rs.next())
                lista.add(FactoryNuevaConsulta.generar(l.intValue(),
                        rs.getString("nueva_consulta"),
                        rs.getInt("consulta_usuario") == 1,
                        new Date(rs.getTimestamp("fecha_sistema").getTime()), rs.getString("usuario_consulta")));
        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrio un error al recuparar las consultas asociadas", e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }

        return lista;
    }

    public boolean averiguarYaCerrada(Integer consulta) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.CONSULTA_YA_CERRADA.getSql());

            ps.setInt(1, consulta);
            rs = ps.executeQuery();

            if (rs.next())
                return true;

            return false;

        } catch (SQLException e) {
            throw new ErrorEnBaseException("No se pudo comprobar el estado de la consulta", e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }

    }

    public Consulta obtenerConsultaPorId(Integer idConsulta) throws ExcepcionControladaError {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CONSULTA_POR_ID.getSql());
            ps.setInt(1, idConsulta);

            rs = ps.executeQuery();
            if (rs.next())
                return FactoryConsulta.generarInstancia(rs.getLong("id_consulta_formulario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        (rs.getInt("telefono_seleccionado") == 1),
                        rs.getString("telefono"), rs.getString("telefono_2"),
                        rs.getString("cuit"), rs.getString("descripcion"),
                        rs.getString("dato"),
                        EstadoConsulta.obtenerPorId(rs.getInt("estado")),
                        rs.getString("tipo"),
                        this.obtenerCategoria(rs.getString("categoria")),
                        rs.getString("caracter"),
                        rs.getString("tipo_dato"),
                        rs.getTimestamp("fecha_sistema"),
                        rs.getTimestamp("fecha_actualizacion"),
                        recuperarNuevasConsultas(rs.getLong("id_consulta_formulario")),
                        recuperarArchivosAsociados(rs.getLong("id_consulta_formulario")));

            return null;
        } catch (ErrorEnBaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrio un problema al recuperar la consulta", e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }

    }


    private List<IRutaNombreView> recuperarArchivosAsociados(long numeroConsulta) throws ExcepcionControladaError {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IRutaNombreView> lista = new ArrayList<IRutaNombreView>();


        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_ARCHIVO_CONSULTA.getSql());

            ps.setLong(1, numeroConsulta);

            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(FactoryArchivoRutaNombre.generar(rs.getString("nombre"), AdministradorProperties.getInstancia().getPropiedad("ENCABEZADO_FTP_PUBLICO_DESCARGAS") + rs.getString("ruta")));
            }

            return lista;
        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrió un error al recuperar los archivos de la consulta", e);
        } catch (ErrorLecturaPropertiesException e) {
            throw new ExcepcionControladaError(e.getMessage(), e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }
    }

    public List<Consulta> obtenerConsultasPorCuit(String cuit) throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Consulta> lista = new ArrayList<Consulta>();

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CONSULTAS_POR_CUIT.getSql());
            ps.setString(1, cuit);

            rs = ps.executeQuery();

            while (rs.next())
                lista.add(
                        FactoryConsulta.generarInstancia(
                                rs.getLong("id_consulta_formulario"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getString("correo"),
                                rs.getInt("telefono_seleccionado") == 1,
                                rs.getString("telefono"),
                                rs.getString("telefono_2"),
                                rs.getString("cuit"),
                                rs.getString("descripcion"),
                                rs.getString("dato"),
                                EstadoConsulta.obtenerPorId(rs.getInt("estado")),
                                rs.getString("tipo"),
                                FactoryCategoria.generarInstancia(
                                        rs.getLong("idCategoria"),
                                        rs.getString("categoria")),
                                rs.getString("caracter"),
                                rs.getString("tipo_dato"),
                                rs.getTimestamp("fecha_sistema"),
                                rs.getTimestamp("fecha_actualizacion"),
                                recuperarNuevasConsultas(rs.getLong("id_consulta_formulario"))));

        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrio un problema al recuperar la consulta", e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }

        return lista;
    }

    public List<Consulta> obtenerConsultasPorCuitCuenta(String cuit, String cuenta) throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Consulta> lista = new ArrayList<Consulta>();

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CONSULTAS_POR_CUIT_CUENTA.getSql());
            ps.setString(1, cuit);
            ps.setString(2, cuenta);

            rs = ps.executeQuery();

            while (rs.next())
                lista.add(
                        FactoryConsulta.generarInstancia(
                                rs.getLong("id_consulta_formulario"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getString("correo"),
                                rs.getInt("telefono_seleccionado") == 1,
                                rs.getString("telefono"),
                                rs.getString("telefono_2"),
                                rs.getString("cuit"),
                                rs.getString("descripcion"),
                                rs.getString("dato"),
                                EstadoConsulta.obtenerPorId(rs.getInt("estado")),
                                rs.getString("tipo"),
                                FactoryCategoria.generarInstancia(
                                        rs.getLong("idCategoria"),
                                        rs.getString("categoria")),
                                rs.getString("caracter"),
                                rs.getString("tipo_dato"),
                                rs.getTimestamp("fecha_sistema"),
                                rs.getTimestamp("fecha_actualizacion"),
                                recuperarNuevasConsultas(rs.getLong("id_consulta_formulario"))));

        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrio un problema al recuperar la consulta", e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }

        return lista;
    }

    public List<Consulta> obtenerConsultasPorCorreo(String correo) throws ErrorEnBaseException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Consulta> lista = new ArrayList<Consulta>();

        try {
            ps = this.getConnection().prepareStatement(Queries.OBTENER_CONSULTAS_POR_CORREO.getSql());
            ps.setString(1, correo == null ? "" : correo.trim().toUpperCase());

            rs = ps.executeQuery();
            while (rs.next())
                lista.add(FactoryConsulta.generarInstancia(rs.getLong("id_consulta_formulario"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("correo"), (rs.getInt("telefono_seleccionado") == 1),
                        rs.getString("telefono"), rs.getString("telefono_2"), rs.getString("cuit"), rs.getString("descripcion"), rs.getString("dato"),
                        EstadoConsulta.obtenerPorId(rs.getInt("estado")), rs.getString("tipo"), FactoryCategoria.generarInstancia(rs.getLong("idCategoria"), rs.getString("categoria")),
                        rs.getString("caracter"), rs.getString("tipo_dato"), rs.getTimestamp("fecha_sistema"), rs.getTimestamp("fecha_actualizacion"), recuperarNuevasConsultas(rs.getLong("id_consulta_formulario"))));

            return lista;
        } catch (ErrorEnBaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrio un problema al recuperar las consultas Asociadas", e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
            }
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public void eliminarConsultaAsociada(Long idAsociada) throws ErrorEnBaseException {
        PreparedStatement ps = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.ELIMINAR_CONSULTA_ASOCIADA.getSql());
            ps.setLong(1, idAsociada);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrio un problema al eliminar consultas Asociadas", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }

    }

    public void eliminarArchivos(ArchivoConsulta a) throws ErrorEnBaseException {

        PreparedStatement ps = null;

        try {
            ps = this.getConnection().prepareStatement(Queries.ELIMINAR_ARCHIVO_CONSULTA.getSql());
            ps.setLong(1, a.getArchivo().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ErrorEnBaseException("Ocurrio un problema al eliminar el archivo", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public Caracter obtenerCaracter(String nombre) throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Caracter caracter = null;

        try {

            ps = this.getConnection().prepareStatement(Queries.OBTENER_CARACTER_POR_NOMBRE.getSql());
            ps.setString(1, nombre);

            rs = ps.executeQuery();

            if (rs.next()) {
                Long l = rs.getLong("id_consulta_caracter");
                caracter = FactoryCaracter.generarInstancia(l, rs.getString("nombre"));
            }

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }

        return caracter;
    }

    public Categoria obtenerCategoria(String nombre) throws ErrorEnBaseException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Categoria categoria = null;

        try {

            ps = this.getConnection().prepareStatement(Queries.OBTENER_CATEGORIA_POR_NOMBRE.getSql());
            ps.setString(1, nombre);

            rs = ps.executeQuery();

            if (rs.next()) {
                Long l = rs.getLong("id_consulta_categoria");
                categoria = FactoryCategoria.generarInstancia(
                        l,
                        rs.getString("nombre"),
                        FactoryDato.generarInstancia(
                                rs.getLong("id_consulta_categoria_dato"),
                                rs.getString("dato"),
                                rs.getInt("requerido"),
                                rs.getString("expresion_regular"),
                                rs.getString("mensaje_error")),
                        this.obtenerCaracteres(rs.getInt("id_consulta_categoria")),
                        FactoryPermiso.generarIntanciaCompleta(rs.getString("permiso")));
            }

        } catch (SQLException e) {
            throw new ErrorEnBaseException(e.getMessage(), e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }

        return categoria;
    }
}
