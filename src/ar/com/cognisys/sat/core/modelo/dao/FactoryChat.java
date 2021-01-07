package ar.com.cognisys.sat.core.modelo.dao;


import ar.com.cognisys.sat.core.modelo.comun.consultas.ConsultaAsociada;

import java.util.Date;

public class FactoryChat {

    public static ConsultaAsociada generarInstancia(int id_consulta_asociada, long id, boolean delConsultante, Date fechaConsulta, String consulta, String usuario) {
        ConsultaAsociada consultaAsociada = new ConsultaAsociada();
        consultaAsociada.setIdConsultaAsociada(id_consulta_asociada);
        consultaAsociada.setId(id);
        consultaAsociada.setDelConsultante(delConsultante);
        consultaAsociada.setFechaConsulta(fechaConsulta);
        consultaAsociada.setNuevaConsulta(consulta);
        consultaAsociada.setUsuario(usuario);
        return consultaAsociada;
    }
}
