package ar.com.cognisys.sat.core.modelo.factory.rs;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoRechazoRS;

public class FactoryTipoRechazoRS {

	public static TipoRechazoRS generar() {
		
		TipoRechazoRS tr = new TipoRechazoRS();
		
		return tr;
	}
	
	public static TipoRechazoRS generar(Integer codigo, String descripcion, String clave) {
		
		TipoRechazoRS tr = generar();
		tr.setCodigo(codigo);
		tr.setDescripcion(descripcion);
		tr.setClave(clave);
		
		return tr;
	}
}