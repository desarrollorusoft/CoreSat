package ar.com.cognisys.sat.core.modelo.factory.usuario;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Perfil;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Permiso;

public class FactoryPerfil {

	public static Perfil generarIntanciaVacia() {
		
		Perfil perfil = new Perfil();
		perfil.setListaPermisos(new ArrayList<Permiso>());
		
		return perfil;
	}
	
	public static Perfil generarIntanciaCompleta(String nombre, List<Permiso> listaPermisos) {
		
		Perfil perfil = generarIntanciaVacia();
		perfil.setListaPermisos(listaPermisos);
		perfil.setNombre(nombre);
		
		return perfil;
	}
}