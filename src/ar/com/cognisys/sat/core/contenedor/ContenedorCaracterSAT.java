package ar.com.cognisys.sat.core.contenedor;

import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorCaracterSAT;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CaracterSAT;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class ContenedorCaracterSAT {

	private static ContenedorCaracterSAT instancia;
	private List<CaracterSAT> listaCaracteres;
	
	public void cargar() throws ExcepcionControladaError {
		this.setListaCaracteres( AdministradorCaracterSAT.obtener() );
	}
	
	public CaracterSAT buscar(String nombre) {
		
		for (CaracterSAT c : this.getListaCaracteres())
			if (c.sos(nombre))
				return c;
		
		return null;
	}
	
	public List<CaracterSAT> getListaCaracteres() {
		return listaCaracteres;
	}

	public void setListaCaracteres(List<CaracterSAT> listaCaracteres) {
		this.listaCaracteres = listaCaracteres;
	}

	public static ContenedorCaracterSAT getInstancia() {
		
		if (instancia == null)
			instancia = new ContenedorCaracterSAT();
			
		return instancia;
	}
}