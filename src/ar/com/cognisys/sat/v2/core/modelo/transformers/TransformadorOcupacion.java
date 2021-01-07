package ar.com.cognisys.sat.v2.core.modelo.transformers;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJOEP;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoOEP;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.ddjj.FactoryDDJJOEP;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IClaveValorView;

public class TransformadorOcupacion {

	public static List<DDJJOEP> generar(IClaveValorView[] ocupaciones) {
		
		List<DDJJOEP> lista = new ArrayList<DDJJOEP>();
		
		if (ocupaciones != null) {
			for (IClaveValorView view : ocupaciones)
				lista.add( FactoryDDJJOEP.generar(TipoOEP.recuperar(view.getClave()), 
												  Float.parseFloat(view.getValor())) );
		}
		
		return lista;
	}
}