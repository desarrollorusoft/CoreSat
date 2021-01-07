package ar.com.cognisys.sat.v2.persistencia.modelo.dto.consulta;

import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.IConsultaArchivoDTO;

public class ConsultaArchivoDTO implements IConsultaArchivoDTO{

	private Integer id;
	private Integer idConsultaFormulario;
	private String nombre;
	private String tipoContenido;
	private Integer archivoConsultante;
	private String ruta;

	public boolean isArchivoConsultante() {
		return this.archivoConsultante == null ? false : this.archivoConsultante.equals( 1 );
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdConsultaFormulario() {
		return idConsultaFormulario;
	}

	public void setIdConsultaFormulario(Integer idConsultaFormulario) {
		this.idConsultaFormulario = idConsultaFormulario;
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

	public Integer getArchivoConsultante() {
		return archivoConsultante;
	}

	public void setArchivoConsultante(Integer archivoConsultante) {
		this.archivoConsultante = archivoConsultante;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

}
