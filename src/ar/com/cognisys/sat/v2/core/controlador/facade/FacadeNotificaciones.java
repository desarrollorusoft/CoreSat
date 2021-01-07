package ar.com.cognisys.sat.v2.core.controlador.facade;


import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.controlador.administrador.AdministradorNotificaciones;
import ar.com.cognisys.sat.v2.persistencia.modelo.notificacion.Notificacion;

import java.util.List;

public class FacadeNotificaciones {
    public void crear(Notificacion notificacion) throws ExcepcionControladaError {
        new AdministradorNotificaciones().crear(notificacion);
    }

    public List<Notificacion> obtenerNotificacionesPorUsuario(String cuit) throws ExcepcionControladaError {
        return new AdministradorNotificaciones().obtenerPorUsuario(cuit);
    }

    public List<Notificacion> obtenerNotificacionesDelDia() throws ExcepcionControladaError {
        return new AdministradorNotificaciones().obtenerDelDia();
    }

    public void actualizarNotificaciones(String cuit) throws ExcepcionControladaError {
        new AdministradorNotificaciones().visarNotificaciones(cuit);
    }
}
