package ar.com.cognisys.sat.core.modelo.comun.cuenta.resultados;

public class ResultadoValidacionRSSV {

	private TipoResultado tipo;

	public ResultadoValidacionRSSV(Integer codigo) {
		this.setTipo( TipoResultado.recuperar(codigo*10000) );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		ResultadoValidacionRSSV other = (ResultadoValidacionRSSV) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	public TipoResultado getTipo() {
		return tipo;
	}

	private void setTipo(TipoResultado tipo) {
		this.tipo = tipo;
	}
}