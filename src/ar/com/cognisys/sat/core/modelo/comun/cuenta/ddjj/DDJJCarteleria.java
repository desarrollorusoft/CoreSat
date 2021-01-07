package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import java.math.BigDecimal;

import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJCarteleria;

public class DDJJCarteleria {

	private TipoCartel tipo;
	private Float metros;
	private String descripcion;
	private String urlImagen;
	
	public boolean sos(int categoria) {
		return this.getTipo().sos(categoria);
	}
	
	public DDJJCarteleria generarClon() {
		return FactoryDDJJCarteleria.generar(this.getTipo().generarClon(), this.getMetros(), this.getDescripcion(), this.getUrlImagen());
	}
	
	public boolean esOtros() {
		return this.getTipo().sosOtros();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((metros == null) ? 0 : metros.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((urlImagen == null) ? 0 : urlImagen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DDJJCarteleria other = (DDJJCarteleria) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (metros == null) {
			if (other.metros != null)
				return false;
		} else if (!metros.equals(other.metros))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (urlImagen == null) {
			if (other.urlImagen != null)
				return false;
		} else if (!urlImagen.equals(other.urlImagen))
			return false;
		return true;
	}

	public TipoCartel getTipo() {
		return tipo;
	}

	public void setTipo(TipoCartel tipo) {
		this.tipo = tipo;
	}

	public Float getMetros() {
		return metros;
	}

	public void setMetros(Float metros) {
		this.metros = metros;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public static Float calcularDimension(float area) {
		if (area < 1f)
			return 1f;
		else {
			BigDecimal bd = new BigDecimal(Float.toString( area ));
		    bd = bd.setScale(0, BigDecimal.ROUND_HALF_DOWN);
		    return bd.floatValue();	
		}
	}
}