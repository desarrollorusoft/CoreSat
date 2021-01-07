package ar.com.cognisys.sat.core.modelo.comun.consultas;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;

public class ConsultaAsociada extends ObjetoPersistible{

	private Integer idConsultaAsociada;
	private String nuevaConsulta;
	private boolean delConsultante;
	private Date fechaConsulta;
	private String usuario;
	
	public boolean isInterno(){
		return !this.isDelConsultante();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( delConsultante ? 1231 : 1237 );
		result = prime * result + ( ( fechaConsulta == null ) ? 0 : fechaConsulta.hashCode() );
		result = prime * result + ( ( idConsultaAsociada == null ) ? 0 : idConsultaAsociada.hashCode() );
		result = prime * result + ( ( nuevaConsulta == null ) ? 0 : nuevaConsulta.hashCode() );
		result = prime * result + ( ( usuario == null ) ? 0 : usuario.hashCode() );
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if ( this == obj )
			return true;
		if ( !super.equals( obj ) )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		ConsultaAsociada other = ( ConsultaAsociada ) obj;
		if ( delConsultante != other.delConsultante )
			return false;
		if ( fechaConsulta == null ) {
			if ( other.fechaConsulta != null )
				return false;
		} else if ( !fechaConsulta.equals( other.fechaConsulta ) )
			return false;
		if ( idConsultaAsociada == null ) {
			if ( other.idConsultaAsociada != null )
				return false;
		} else if ( !idConsultaAsociada.equals( other.idConsultaAsociada ) )
			return false;
		if ( nuevaConsulta == null ) {
			if ( other.nuevaConsulta != null )
				return false;
		} else if ( !nuevaConsulta.equals( other.nuevaConsulta ) )
			return false;
		if ( usuario == null ) {
			if ( other.usuario != null )
				return false;
		} else if ( !usuario.equals( other.usuario ) )
			return false;
		return true;
	}

	public String getUsuarioToUpperCase() {
		return this.usuario.toUpperCase();
	}

	public Integer getIdConsultaAsociada() {
		return idConsultaAsociada;
	}
	public void setIdConsultaAsociada(Integer idConsultaAsociada) {
		this.idConsultaAsociada = idConsultaAsociada;
	}
	public String getNuevaConsulta() {
		return nuevaConsulta;
	}
	public void setNuevaConsulta(String nuevaConsulta) {
		this.nuevaConsulta = nuevaConsulta;
	}
	public boolean isDelConsultante() {
		return delConsultante;
	}
	public void setDelConsultante(boolean delConsultante) {
		this.delConsultante = delConsultante;
	}
	public Date getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
