package ar.com.cognisys.sat.core.modelo.comun;

public class ArchivoConsulta {

	private boolean delConsultante;

	private Archivo archivo;

	public boolean isDelHsat(){
		return !this.isDelConsultante();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((archivo == null) ? 0 : archivo.hashCode());
		result = prime * result + (delConsultante ? 1231 : 1237);
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
		ArchivoConsulta other = (ArchivoConsulta) obj;
		if (archivo == null) {
			if (other.archivo != null)
				return false;
		} else if (!archivo.equals(other.archivo))
			return false;
		if (delConsultante != other.delConsultante)
			return false;
		return true;
	}

	public boolean isDelConsultante() {
		return delConsultante;
	}

	public void setDelConsultante(boolean delConsultante) {
		this.delConsultante = delConsultante;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

}
