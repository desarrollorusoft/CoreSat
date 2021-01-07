package ar.com.cognisys.sat.v2.core.modelo.transformers;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.contenedor.ContenedorTipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoRechazoRS;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.ICodigoErrorValidacionRSView;

public class TransformadorTiposRechazosRS {

	public static List<TipoRechazoRS> generar(ICodigoErrorValidacionRSView[] errores) {
		
		List<TipoRechazoRS> lista = new ArrayList<TipoRechazoRS>();
		
		for (ICodigoErrorValidacionRSView ce : errores)
			lista.add( ContenedorTipoRechazoRS.getInstancia().buscar(ce.getCodigo()) );
		
		return lista;
	}
}