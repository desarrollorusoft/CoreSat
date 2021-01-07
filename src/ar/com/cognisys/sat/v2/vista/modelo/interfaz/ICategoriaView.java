package ar.com.cognisys.sat.v2.vista.modelo.interfaz;

import java.io.Serializable;

public interface ICategoriaView extends Serializable {

	String getCategoria();
	
	String[] getCaracteres();
	
	String getNombreDato();
	
	String getExpresionRegular();
	
}
