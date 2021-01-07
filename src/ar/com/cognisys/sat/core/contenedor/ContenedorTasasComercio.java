package ar.com.cognisys.sat.core.contenedor;

import ar.com.cognisys.sat.core.administrador.AdministradorRegimenSimplificado;
import ar.com.cognisys.sat.core.administrador.AdministradorTasasComercio;
import ar.com.cognisys.sat.core.modelo.comun.Tasa;
import ar.com.cognisys.sat.core.modelo.comun.rs.TopeRS;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

import java.util.Map;

public class ContenedorTasasComercio {

	private static ContenedorTasasComercio instancia;
	private Map<Integer, Tasa> mapa;
	
	public void cargar() throws ExcepcionControladaError {
		this.setMapa( AdministradorTasasComercio.buscar() );
	}
	
	public Tasa recuperar(Integer codigo) {
		return this.getMapa().get( codigo );
	}
	
	private Map<Integer, Tasa> getMapa() {
		return mapa;
	}

	private void setMapa(Map<Integer, Tasa> mapa) {
		this.mapa = mapa;
	}

	public static ContenedorTasasComercio getInstancia() {
		if (instancia == null)
			instancia = new ContenedorTasasComercio();
		return instancia;
	}
}