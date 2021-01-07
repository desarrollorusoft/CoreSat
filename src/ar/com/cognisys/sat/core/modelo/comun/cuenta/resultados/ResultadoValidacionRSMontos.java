package ar.com.cognisys.sat.core.modelo.comun.cuenta.resultados;

import java.util.Map;

public class ResultadoValidacionRSMontos {

	private TipoResultado resultadoGeneral;
	private Map<Integer, TipoResultado> mapaResultados;
	
	public void agregarResultado(int cuenta, int codigo) {
		this.getMapaResultados().put(cuenta, TipoResultado.recuperar(codigo + 10));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mapaResultados == null) ? 0 : mapaResultados.hashCode());
		result = prime * result + ((resultadoGeneral == null) ? 0 : resultadoGeneral.hashCode());
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
		ResultadoValidacionRSMontos other = (ResultadoValidacionRSMontos) obj;
		if (mapaResultados == null) {
			if (other.mapaResultados != null)
				return false;
		} else if (!mapaResultados.equals(other.mapaResultados))
			return false;
		if (resultadoGeneral != other.resultadoGeneral)
			return false;
		return true;
	}

	public TipoResultado getResultadoGeneral() {
		return resultadoGeneral;
	}

	public void setResultadoGeneral(TipoResultado resultadoGeneral) {
		this.resultadoGeneral = resultadoGeneral;
	}

	public Map<Integer, TipoResultado> getMapaResultados() {
		return mapaResultados;
	}

	public void setMapaResultados(Map<Integer, TipoResultado> mapaResultados) {
		this.mapaResultados = mapaResultados;
	}
}