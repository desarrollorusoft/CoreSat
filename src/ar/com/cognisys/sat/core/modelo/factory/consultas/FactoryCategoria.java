package ar.com.cognisys.sat.core.modelo.factory.consultas;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.consultas.Caracter;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Categoria;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Dato;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Permiso;

public class FactoryCategoria {

	public static Categoria generarInstancia(){
		Categoria c = new Categoria();
		return c;
	}
	
	public static Categoria generarInstancia(Long id, String nombre) {
		Categoria c = generarInstancia();
		c.setId(id);
		c.setNombre(nombre);
		return c;
	}
	public static Categoria generarInstancia(Long id, String nombre, List<Caracter> caracteresPosibles) {
		Categoria c = generarInstancia();
		c.setId(id);
		c.setNombre(nombre);
		c.setCaracteresPosibles(caracteresPosibles);
		return c;
	}
	public static Categoria generarInstancia(Long id, String nombre, Dato dato, List<Caracter> caracteresPosibles, Permiso permisoAsociado) {
		Categoria c = generarInstancia(id, nombre);
		c.setDato( dato );
		c.setCaracteresPosibles( caracteresPosibles );
		c.setPermisoAsociado( permisoAsociado );
		
		return c;
	}
}
