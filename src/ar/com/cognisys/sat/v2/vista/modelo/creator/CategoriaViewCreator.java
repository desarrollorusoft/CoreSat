package ar.com.cognisys.sat.v2.vista.modelo.creator;

import ar.com.cognisys.sat.v2.vista.modelo.v2.view.CategoriaView;

public class CategoriaViewCreator {

	private CategoriaView categoriaView;

	public CategoriaViewCreator() {
		this.categoriaView = new CategoriaView();
	}
	
	public CategoriaViewCreator nombreDato(String nombreDato) {
		this.categoriaView.setNombreDato( nombreDato );
		
		return this;
	}
	
	public CategoriaViewCreator caracteres(String[] caracteres) {
		this.categoriaView.setCaracteres( caracteres );
		
		return this;
	}
	
	public CategoriaViewCreator categoria(String categoria) {
		this.categoriaView.setCategoria( categoria );
		
		return this;
	}
	
	public CategoriaViewCreator expresionRegular(String expresionRegular) {
		this.categoriaView.setExpresionRegular( expresionRegular );
		
		return this;
	}
	
	public CategoriaView create() {
		return this.categoriaView;
	}

}
