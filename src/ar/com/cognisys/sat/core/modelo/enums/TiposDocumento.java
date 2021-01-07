package ar.com.cognisys.sat.core.modelo.enums;

public enum TiposDocumento {

	DNI("DNI", "Documento Nacional de Identidad", 1, "D.N.I."),
	CI("CI", "Cédula de Identidad", 2, "C.I."),
	LE("LE", "Libreta de Enrolamiento", 3, "L.E."),
	LC("LC", "Libreta Cívica", 4, "L.C."),
	PASAPORTE("PASAPORTE", "Pasaporte", 5, "PASAPORTE");
	
	private String nombre;
	private String descripcion;
	private Integer codigo;
	private String nombrePiletas;
	
	private TiposDocumento(String nombre, String descripcion, Integer codigo, String nombrePiletas) {
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setCodigo(codigo);
		this.setNombrePiletas(nombrePiletas);
	}

	public static Integer getCodigoPorNombre(String nombre) {
		return getTipoDocumento(nombre).getCodigo();
	}

	public static TiposDocumento getTipoDocumentoPorNombrePiletas(String nombrePiletas) {
		TiposDocumento codigo = null;

		if (nombrePiletas.equals(TiposDocumento.DNI.getNombrePiletas())) {
			codigo = TiposDocumento.DNI;

		} else if (nombrePiletas.equals(TiposDocumento.CI.getNombrePiletas())) {
			codigo = TiposDocumento.CI;

		} else if (nombrePiletas.equals(TiposDocumento.LE.getNombrePiletas())) {
			codigo = TiposDocumento.LE;

		} else if (nombrePiletas.equals(TiposDocumento.LC.getNombrePiletas())) {
			codigo = TiposDocumento.LC;

		} else if (nombrePiletas.equals(TiposDocumento.PASAPORTE.getNombrePiletas())) {
			codigo = TiposDocumento.PASAPORTE;
		}

		return codigo;
	}
	
	public static TiposDocumento getTipoDocumento(String nombre) {
		
		TiposDocumento codigo = null;
		
		if (nombre.equals(TiposDocumento.DNI.getNombre())) {
			codigo = TiposDocumento.DNI;
			
		} else if (nombre.equals(TiposDocumento.CI.getNombre())) {
			codigo = TiposDocumento.CI;
			
		} else if (nombre.equals(TiposDocumento.LE.getNombre())) {
			codigo = TiposDocumento.LE;
			
		} else if (nombre.equals(TiposDocumento.LC.getNombre())) {
			codigo = TiposDocumento.LC;
			
		} else if (nombre.equals(TiposDocumento.PASAPORTE.getNombre())) {
			codigo = TiposDocumento.PASAPORTE;
		}
		
		return codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombrePiletas() { return nombrePiletas; }

	public void setNombrePiletas(String nombrePiletas) { this.nombrePiletas = nombrePiletas; }
}