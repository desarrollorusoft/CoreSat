package ar.com.cognisys.sat.core.contenedor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.com.cognisys.sat.core.administrador.AdministradorRegimenSimplificado;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class ContenedorActividadComercial {

	private static ContenedorActividadComercial instancia;
	private Map<Integer, List<ActividadComercial>> mapaActividades;
	
	public void cargar() throws ExcepcionControladaError {
		this.setMapaActividades( AdministradorRegimenSimplificado.recuperarActividades() );
	}
	
	public ActividadComercial recuperarActividad(Integer codigo, Integer ano) {

		for (ActividadComercial ac : this.getMapaActividades().get( ano ))
			if (ac.sos(codigo))
				return ac;
		
		return null;
	}

	public List<ActividadComercial> getListaActividades(Integer ano) {
		return this.getMapaActividades().get( ano );
	}

	public Map<Integer, List<ActividadComercial>> getMapaActividades() {
		return mapaActividades;
	}

	public void setMapaActividades(Map<Integer, List<ActividadComercial>> mapaActividades) {
		this.mapaActividades = mapaActividades;
	}

	public static ContenedorActividadComercial getInstancia() {
		
		if (instancia == null)
			instancia = new ContenedorActividadComercial();
			
		return instancia;
	}
}