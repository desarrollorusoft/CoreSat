package ar.com.cognisys.sat.core.modelo.generador.pagoOnline;

import java.util.HashMap;

public class PaquetePago {

	private String url;
	private HashMap<String, String> parametros;
	
	public PaquetePago(String url, HashMap<String, String> parametros) {
		this.setUrl(url);
		this.setParametros(parametros);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parametros == null) ? 0 : parametros.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		PaquetePago other = (PaquetePago) obj;
		if (parametros == null) {
			if (other.parametros != null)
				return false;
		} else if (!parametros.equals(other.parametros))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public HashMap<String, String> getParametros() {
		return parametros;
	}
	
	public void setParametros(HashMap<String, String> parametros) {
		this.parametros = parametros;
	}
}