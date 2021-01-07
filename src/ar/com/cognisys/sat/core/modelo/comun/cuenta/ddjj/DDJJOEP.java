package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJOEP;

/**
 * DDJJ de Ocupacion de Espacios Publicos
 * @author fgarnero
 */
public class DDJJOEP {
	
	private TipoOEP tipo;
	private float valor;
	
	public DDJJOEP generarClon() {
		return FactoryDDJJOEP.generar(this.getTipo(), this.getValor());
	}
	
	public boolean sos(TipoOEP tipo) {
		return (this.getTipo() == tipo);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + Float.floatToIntBits(valor);
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
		DDJJOEP other = (DDJJOEP) obj;
		if (tipo != other.tipo)
			return false;
		if (Float.floatToIntBits(valor) != Float.floatToIntBits(other.valor))
			return false;
		return true;
	}

	public TipoOEP getTipo() {
		return tipo;
	}

	public void setTipo(TipoOEP tipo) {
		this.tipo = tipo;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
}