package ar.com.cognisys.sat.core.modelo.factory.consultas;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.consultas.ConsultaAsociada;

public class FactoryNuevaConsulta {

	public static ConsultaAsociada crear() {
		return new ConsultaAsociada();
	}

	public static ConsultaAsociada generar(Integer idConsultaAsociada) {
		
		ConsultaAsociada n = crear();
		
		n.setIdConsultaAsociada( idConsultaAsociada );
		
		return n;	
	}

	public static ConsultaAsociada generar(int idConsultaAsociada, String nuevaConsulta, boolean delConsultante ) {
		ConsultaAsociada n = generar(idConsultaAsociada);
		n.setNuevaConsulta( nuevaConsulta );
		n.setDelConsultante( delConsultante );
		
		return n;
	}
	
	public static ConsultaAsociada generar(int idConsultaAsociada, String nuevaConsulta, boolean delConsultante, Date fechaConsulta, String usuario ) {
		
		ConsultaAsociada c = generar(idConsultaAsociada,nuevaConsulta,delConsultante);
		c.setFechaConsulta( fechaConsulta );
		c.setUsuario( usuario );
		
		return c;
	}
	
	public static ConsultaAsociada generar(int idConsultaAsociada, String nuevaConsulta, boolean delConsultante, Date fechaConsulta ) {
		
		ConsultaAsociada c = generar(idConsultaAsociada,nuevaConsulta,delConsultante);
		c.setFechaConsulta( fechaConsulta );
		
		return c;
	}
}
