package ar.com.cognisys.sat.v2.core.controlador.administrador;

import ar.com.cognisys.sat.core.administrador.AdministradorConexiones;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.persistencia.modelo.dao.notificacion.NotificacionDao;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.notificacion.NotificacionDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.notificacion.Notificacion;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AdministradorNotificaciones {


    public void crear(Notificacion notificacion) throws ExcepcionControladaError {
        Connection con = null;
        try {
            con = AdministradorConexiones.obtenerConexion();
            NotificacionDTO dto = new NotificacionDTO(null, notificacion.getIdAlerta(), notificacion.getIdUsuario(),
                    false, notificacion.getTitulo(), notificacion.getDescripcion(), null);
            new NotificacionDao(con).crear(dto);
        } finally {
            AdministradorConexiones.cerrarConnection(con);
        }


    }

    public List<Notificacion> obtenerPorUsuario(String cuit) throws ExcepcionControladaError {
        List<Notificacion> notificaciones;
        Connection con = null;
        try {
            con = AdministradorConexiones.obtenerConexion();
            notificaciones = this.notificacionDtoANotificacion(new NotificacionDao(con).recuperar(cuit));
        } finally {
            AdministradorConexiones.cerrarConnection(con);
        }
        return notificaciones;
    }

    public List<Notificacion> obtenerDelDia() throws ExcepcionControladaError {
        List<Notificacion> notificaciones;
        Connection con = null;
        try {
            con = AdministradorConexiones.obtenerConexion();
            notificaciones = this.notificacionDtoANotificacion(new NotificacionDao(con).recuperarDelDia());
        } finally {
            AdministradorConexiones.cerrarConnection(con);
        }
        return notificaciones;
    }

    public void visarNotificaciones(String cuit) throws ExcepcionControladaError {
        Connection con = null;

        try {
            con = AdministradorConexiones.obtenerConexion();
            new NotificacionDao(con).visarNotificacion(cuit);
        } finally {
            AdministradorConexiones.cerrarConnection(con);
        }
    }

    private List<Notificacion> notificacionDtoANotificacion(List<NotificacionDTO> recuperar) {
        List<Notificacion> notificaciones = new ArrayList<Notificacion>();
        for (NotificacionDTO dto : recuperar) {
            notificaciones.add(new Notificacion(dto.getId(), dto.getIdAlerta(), dto.getIdUsuario(), dto.isVisto(), dto.getTitulo(), dto.getDescripcion(), dto.getFecha()));
        }
        return notificaciones;
    }
}
