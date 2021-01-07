package ar.com.cognisys.sat.v2.core.modelo.adapters;

import ar.com.cognisys.sat.core.modelo.comun.Archivo;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IArchivoView;

public class ArchivoViewAdapter implements IArchivoView {

	private static final long serialVersionUID = -6600089920438461989L;
	private Archivo archivo;

	public ArchivoViewAdapter(Archivo archivo) {
		this.archivo = archivo;
	}

	@Override
	public String getData() {
		return String.valueOf( archivo.getData() );
	}

	@Override
	public String getTipo() {
		return archivo.getTipoContenido();
	}

	@Override
	public String getNombre() {
		return archivo.getNombre();
	}

}
