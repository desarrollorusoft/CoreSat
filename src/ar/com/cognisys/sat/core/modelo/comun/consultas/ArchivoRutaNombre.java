package ar.com.cognisys.sat.core.modelo.comun.consultas;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;

public class ArchivoRutaNombre implements IRutaNombreView {
	
	private static final long serialVersionUID = 4803300802338888977L;

	private String nombre;
	private String ruta;
	
	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public String getRuta() {
		// TODO Auto-generated method stub
		return ruta;
	}

	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	
	
}
