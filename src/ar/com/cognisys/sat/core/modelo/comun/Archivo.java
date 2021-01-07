package ar.com.cognisys.sat.core.modelo.comun;

import java.io.File;
import java.util.Arrays;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;

public class Archivo extends ObjetoPersistible {
	
	private String nombre;
	private String tipoContenido;
	private String tempUrl;
	private String tempNombreUrl;
	private String tempExtensionUrl;
	private byte[] data;
	private File temp;
	private String filePath;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((temp == null) ? 0 : temp.hashCode());
		result = prime * result + ((tempExtensionUrl == null) ? 0 : tempExtensionUrl.hashCode());
		result = prime * result + ((tempNombreUrl == null) ? 0 : tempNombreUrl.hashCode());
		result = prime * result + ((tempUrl == null) ? 0 : tempUrl.hashCode());
		result = prime * result + ((tipoContenido == null) ? 0 : tipoContenido.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Archivo other = (Archivo) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (temp == null) {
			if (other.temp != null)
				return false;
		} else if (!temp.equals(other.temp))
			return false;
		if (tempExtensionUrl == null) {
			if (other.tempExtensionUrl != null)
				return false;
		} else if (!tempExtensionUrl.equals(other.tempExtensionUrl))
			return false;
		if (tempNombreUrl == null) {
			if (other.tempNombreUrl != null)
				return false;
		} else if (!tempNombreUrl.equals(other.tempNombreUrl))
			return false;
		if (tempUrl == null) {
			if (other.tempUrl != null)
				return false;
		} else if (!tempUrl.equals(other.tempUrl))
			return false;
		if (tipoContenido == null) {
			if (other.tipoContenido != null)
				return false;
		} else if (!tipoContenido.equals(other.tipoContenido))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public String getTempUrl() {
		return tempUrl;
	}

	public void setTempUrl(String tempUrl) {
		this.tempUrl = tempUrl;
	}

	public String getTempNombreUrl() {
		return tempNombreUrl;
	}

	public void setTempNombreUrl(String tempNombreUrl) {
		this.tempNombreUrl = tempNombreUrl;
	}

	public String getTempExtensionUrl() {
		return tempExtensionUrl;
	}

	public void setTempExtensionUrl(String tempExtensionUrl) {
		this.tempExtensionUrl = tempExtensionUrl;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public File getTemp() {
		return temp;
	}

	public void setTemp(File temp) {
		this.temp = temp;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}