package ar.com.cognisys.sat.core.modelo.comun.consultas;

import java.io.Serializable;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.ObjetoPersistible;
import ar.com.cognisys.sat.core.modelo.comun.ArchivoConsulta;

/**
 * @author Landau
 *
 */
public class FormularioConsulta extends ObjetoPersistible implements Serializable {

	private static final long serialVersionUID = -4885489608582415686L;

	private String nombre;
	private String apellido;
	private String correo;
	private boolean telefonoSeleccionado;
	private String telefono;
	private String telefono2;
	private String cuit;
	private Long idTipoConsulta;
	private Long idCategoria;
	private Long idSubcategoria;
	private Long idCaracter;
	private Long idDato;
	private String descripcion;
	private List<ArchivoConsulta> archivos;
	private String dato;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( apellido == null ) ? 0 : apellido.hashCode() );
		result = prime * result + ( ( archivos == null ) ? 0 : archivos.hashCode() );
		result = prime * result + ( ( correo == null ) ? 0 : correo.hashCode() );
		result = prime * result + ( ( cuit == null ) ? 0 : cuit.hashCode() );
		result = prime * result + ( ( dato == null ) ? 0 : dato.hashCode() );
		result = prime * result + ( ( descripcion == null ) ? 0 : descripcion.hashCode() );
		result = prime * result + ( telefonoSeleccionado ? 1231 : 1237 );
		result = prime * result + ( ( idCaracter == null ) ? 0 : idCaracter.hashCode() );
		result = prime * result + ( ( idCategoria == null ) ? 0 : idCategoria.hashCode() );
		result = prime * result + ( ( idDato == null ) ? 0 : idDato.hashCode() );
		result = prime * result + ( ( idSubcategoria == null ) ? 0 : idSubcategoria.hashCode() );
		result = prime * result + ( ( idTipoConsulta == null ) ? 0 : idTipoConsulta.hashCode() );
		result = prime * result + ( ( nombre == null ) ? 0 : nombre.hashCode() );
		result = prime * result + ( ( telefono == null ) ? 0 : telefono.hashCode() );
		result = prime * result + ( ( telefono2 == null ) ? 0 : telefono2.hashCode() );
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
		FormularioConsulta other = ( FormularioConsulta ) obj;
		if ( apellido == null ) {
			if ( other.apellido != null )
				return false;
		} else if ( !apellido.equals( other.apellido ) )
			return false;
		if ( archivos == null ) {
			if ( other.archivos != null )
				return false;
		} else if ( !archivos.equals( other.archivos ) )
			return false;
		if ( correo == null ) {
			if ( other.correo != null )
				return false;
		} else if ( !correo.equals( other.correo ) )
			return false;
		if ( cuit == null ) {
			if ( other.cuit != null )
				return false;
		} else if ( !cuit.equals( other.cuit ) )
			return false;
		if ( dato == null ) {
			if ( other.dato != null )
				return false;
		} else if ( !dato.equals( other.dato ) )
			return false;
		if ( descripcion == null ) {
			if ( other.descripcion != null )
				return false;
		} else if ( !descripcion.equals( other.descripcion ) )
			return false;
		if ( telefonoSeleccionado != other.telefonoSeleccionado )
			return false;
		if ( idCaracter == null ) {
			if ( other.idCaracter != null )
				return false;
		} else if ( !idCaracter.equals( other.idCaracter ) )
			return false;
		if ( idCategoria == null ) {
			if ( other.idCategoria != null )
				return false;
		} else if ( !idCategoria.equals( other.idCategoria ) )
			return false;
		if ( idDato == null ) {
			if ( other.idDato != null )
				return false;
		} else if ( !idDato.equals( other.idDato ) )
			return false;
		if ( idSubcategoria == null ) {
			if ( other.idSubcategoria != null )
				return false;
		} else if ( !idSubcategoria.equals( other.idSubcategoria ) )
			return false;
		if ( idTipoConsulta == null ) {
			if ( other.idTipoConsulta != null )
				return false;
		} else if ( !idTipoConsulta.equals( other.idTipoConsulta ) )
			return false;
		if ( nombre == null ) {
			if ( other.nombre != null )
				return false;
		} else if ( !nombre.equals( other.nombre ) )
			return false;
		if ( telefono == null ) {
			if ( other.telefono != null )
				return false;
		} else if ( !telefono.equals( other.telefono ) )
			return false;
		if ( telefono2 == null ) {
			if ( other.telefono2 != null )
				return false;
		} else if ( !telefono2.equals( other.telefono2 ) )
			return false;
		return true;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public boolean isTelefonoSeleccionado() {
		return telefonoSeleccionado;
	}
	public void setTelefonoSeleccionado(boolean telefonoSeleccionado) {
		this.telefonoSeleccionado = telefonoSeleccionado;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public Long getIdTipoConsulta() {
		return idTipoConsulta;
	}
	public void setIdTipoConsulta(Long idTipoConsulta) {
		this.idTipoConsulta = idTipoConsulta;
	}
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Long getIdSubcategoria() {
		return idSubcategoria;
	}
	public void setIdSubcategoria(Long idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}
	public Long getIdCaracter() {
		return idCaracter;
	}
	public void setIdCaracter(Long idCaracter) {
		this.idCaracter = idCaracter;
	}
	public Long getIdDato() {
		return idDato;
	}
	public void setIdDato(Long idDato) {
		this.idDato = idDato;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<ArchivoConsulta> getArchivos() {
		return archivos;
	}
	public void setArchivos(List<ArchivoConsulta> archivos) {
		this.archivos = archivos;
	}
	public String getDato() {
		return dato;
	}
	public void setDato(String dato) {
		this.dato = dato;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
