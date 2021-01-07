package ar.com.cognisys.sat.core.contenedor;

import java.util.Map;

import ar.com.cognisys.sat.core.administrador.AdministradorRegimenSimplificado;
import ar.com.cognisys.sat.core.modelo.comun.rs.TopeRS;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class ContenedorTopesRS {

	private static ContenedorTopesRS instancia;
	private Map<Integer, TopeRS> mapa;
	
	public void cargar() throws ExcepcionControladaError {
		this.setMapa( AdministradorRegimenSimplificado.obtenerTopes() );
	}
	
	public TopeRS recuperar(Integer ano) {
		return this.getMapa().get( ano );
	}
	
	private Map<Integer, TopeRS> getMapa() {
		return mapa;
	}

	private void setMapa(Map<Integer, TopeRS> mapa) {
		this.mapa = mapa;
	}

	public static ContenedorTopesRS getInstancia() {
		
		if (instancia == null)
			instancia = new ContenedorTopesRS();
			
		return instancia;
	}
}