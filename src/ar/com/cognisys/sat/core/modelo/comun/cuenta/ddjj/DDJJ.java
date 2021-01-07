package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import java.util.Date;
import java.util.List;

public abstract class DDJJ {

	private Float ganancia;
	private List<DDJJCarteleria> listaCarteleria;
	private List<DDJJOEP> ocupacion;
	private DDJJSV serviciosValios;
	private Date fechaHabilitacion;
	private Integer cantidadPersonas;
	private Actividades actividades;
	private Date fechaInicio;
	private Date fechaConfirmacion;
	private Integer cuentaABL;
	private Integer ano;
	private boolean enCurso;
	private Integer vez;

	public boolean isCompleta(){
		return !this.isEnCurso();
	}
	
	public float getCarteleria(int categoria) {
		
		float suma = 0f;
		
		for (DDJJCarteleria carteleria : this.getListaCarteleria())
			if (carteleria.sos(categoria))
				suma += carteleria.getMetros();
		
		return suma;
	}
	
	public float getOEPMetrosToldos() {
		float suma = 0f;
		
		for (DDJJOEP oep : ocupacion)
			if (oep.sos(TipoOEP.TOLDO))
				suma += oep.getValor();
		
		return suma;
	}

	public float getOEPUnidadesPostes() {
		float suma = 0f;
		
		for (DDJJOEP oep : ocupacion)
			if (oep.sos(TipoOEP.POSTE))
				suma += oep.getValor();
		
		return suma;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actividades == null) ? 0 : actividades.hashCode());
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((cantidadPersonas == null) ? 0 : cantidadPersonas.hashCode());
		result = prime * result + ((cuentaABL == null) ? 0 : cuentaABL.hashCode());
		result = prime * result + (enCurso ? 1231 : 1237);
		result = prime * result + ((fechaConfirmacion == null) ? 0 : fechaConfirmacion.hashCode());
		result = prime * result + ((fechaHabilitacion == null) ? 0 : fechaHabilitacion.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((ganancia == null) ? 0 : ganancia.hashCode());
		result = prime * result + ((listaCarteleria == null) ? 0 : listaCarteleria.hashCode());
		result = prime * result + ((ocupacion == null) ? 0 : ocupacion.hashCode());
		result = prime * result + ((serviciosValios == null) ? 0 : serviciosValios.hashCode());
		result = prime * result + ((vez == null) ? 0 : vez.hashCode());
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
		DDJJ other = (DDJJ) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (cantidadPersonas == null) {
			if (other.cantidadPersonas != null)
				return false;
		} else if (!cantidadPersonas.equals(other.cantidadPersonas))
			return false;
		if (cuentaABL == null) {
			if (other.cuentaABL != null)
				return false;
		} else if (!cuentaABL.equals(other.cuentaABL))
			return false;
		if (enCurso != other.enCurso)
			return false;
		if (fechaConfirmacion == null) {
			if (other.fechaConfirmacion != null)
				return false;
		} else if (!fechaConfirmacion.equals(other.fechaConfirmacion))
			return false;
		if (fechaHabilitacion == null) {
			if (other.fechaHabilitacion != null)
				return false;
		} else if (!fechaHabilitacion.equals(other.fechaHabilitacion))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (ganancia == null) {
			if (other.ganancia != null)
				return false;
		} else if (!ganancia.equals(other.ganancia))
			return false;
		if (vez == null) {
			if (other.vez != null)
				return false;
		} else if (!vez.equals(other.vez))
			return false;
		return true;
	}

	public Float getGanancia() {
		return ganancia;
	}

	public void setGanancia(Float ganancia) {
		this.ganancia = ganancia;
	}

	public List<DDJJCarteleria> getListaCarteleria() {
		return listaCarteleria;
	}

	public void setListaCarteleria(List<DDJJCarteleria> listaCarteleria) {
		this.listaCarteleria = listaCarteleria;
	}

	public DDJJSV getServiciosValios() {
		return serviciosValios;
	}

	public void setServiciosValios(DDJJSV serviciosValios) {
		this.serviciosValios = serviciosValios;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaConfirmacion() {
		return fechaConfirmacion;
	}

	public void setFechaConfirmacion(Date fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}

	public boolean isEnCurso() {
		return enCurso;
	}

	public void setEnCurso(boolean enCurso) {
		this.enCurso = enCurso;
	}

	public Date getFechaHabilitacion() {
		return fechaHabilitacion;
	}

	public void setFechaHabilitacion(Date fechaHabilitacion) {
		this.fechaHabilitacion = fechaHabilitacion;
	}

	public Integer getCantidadPersonas() {
		return cantidadPersonas;
	}

	public void setCantidadPersonas(Integer cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	public Actividades getActividades() {
		return actividades;
	}

	public void setActividades(Actividades actividades) {
		this.actividades = actividades;
	}

	public Integer getCuentaABL() {
		return cuentaABL;
	}

	public void setCuentaABL(Integer cuentaABL) {
		this.cuentaABL = cuentaABL;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public List<DDJJOEP> getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(List<DDJJOEP> ocupacion) {
		this.ocupacion = ocupacion;
	}

	public Integer getVez() {
		return vez;
	}

	public void setVez(Integer vez) {
		this.vez = vez;
	}
}