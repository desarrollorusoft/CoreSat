package ar.com.cognisys.sat.core.modelo.comun.cuenta.resultados;

public class ResultadoValidacionRSOEP {

	private TipoResultado tipo;

	public ResultadoValidacionRSOEP(Integer codigoMentros, Integer codigoUnidades) {
		int codigo = Integer.parseInt( codigoMentros.toString() + codigoUnidades.toString() );
		this.setTipo( TipoResultado.recuperar(codigo*100) );
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
		ResultadoValidacionRSOEP other = (ResultadoValidacionRSOEP) obj;
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