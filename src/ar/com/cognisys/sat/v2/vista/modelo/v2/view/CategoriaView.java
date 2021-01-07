package ar.com.cognisys.sat.v2.vista.modelo.v2.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICategoriaView;

public class CategoriaView implements ICategoriaView {

	private static final long serialVersionUID = 3037914874498846732L;
	private String categoria;
	private String[] caracteres;
	private String expresionRegular;
	private String nombreDato;

	@Override
	public String getCategoria() {
		return categoria;
	}

	@Override
	public String[] getCaracteres() {
		return caracteres;
	}

	@Override
	public String getExpresionRegular() {
		return expresionRegular;
	}

	@Override
	public String getNombreDato() {
		return nombreDato;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setCaracteres(String[] caracteres) {
		this.caracteres = caracteres;
	}

	public void setExpresionRegular(String expresionRegular) {
		this.expresionRegular = expresionRegular;
	}

	public void setNombreDato(String nombreDato) {
		this.nombreDato = nombreDato;
	}

	
}
