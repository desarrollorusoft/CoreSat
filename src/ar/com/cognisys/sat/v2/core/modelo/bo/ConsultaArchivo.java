package ar.com.cognisys.sat.v2.core.modelo.bo;

import java.text.Normalizer;
import java.util.Date;
import java.util.regex.Pattern;

public class ConsultaArchivo implements IConsultaArchivo {

	private Integer numeroConsulta;
	private String directorio;
	private String tipo;
	private String nombre;
	private String nombreGenerado;
	private byte[] data;

	public void generarNombre() {
		if ( this.nombre == null || this.nombreGenerado != null )
			return;

		String extension = this.obtenerExtension();
		String nombreSinExtension = this.obtenerNombreSinExtension();
		
		String aux = nombreSinExtension + "-" + new Date().getTime();
				
		this.nombreGenerado = extension == null ? aux : aux + extension;
	}

	private String obtenerNombreSinExtension() {
		if ( !this.nombre.contains( "." ) )
			return this.nombre;

		return this.nombre.substring( 0, this.nombre.lastIndexOf( "." ) );
	}

	private String obtenerExtension() {
		if ( !this.nombre.contains( "." ) )
			return null;

		return this.nombre.substring( this.nombre.lastIndexOf( "." ), this.nombre.length() );
	}
	
	public void normalizarNombre() {
		this.setNombre((Pattern.compile("\\p{InCombiningDiacriticalMarks}+"))
	    		.matcher( Normalizer.normalize(this.getNombre(), Normalizer.Form.NFD) )
	    		.replaceAll("").replaceAll("[^\\dA-Za-z .-]", "").replaceAll("\\s+", "_"));
	}

	public String getRuta() {
		return directorio + this.nombreGenerado;
	}

	public String getDirectorio() {
		return directorio;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	public Integer getNumeroConsulta() {
		return numeroConsulta;
	}

	public void setNumeroConsulta(Integer numeroConsulta) {
		this.numeroConsulta = numeroConsulta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreGenerado() {
		return nombreGenerado;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}	
}