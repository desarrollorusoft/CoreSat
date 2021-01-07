package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;

@Deprecated
public class DeudasCuentaAutomotores extends Deuda {

	public List<DuplaCuota> getListaDuplaAVencer() {
		
		ArrayList<Cuota> auxList = new ArrayList<Cuota>();
		auxList.addAll(this.getListaCuotasAVencer());
		
		Collections.sort(auxList);
		
		return generarDuplas(auxList);
	}	
	
	public List<DuplaCuota> getListaDuplasVencidas() {
		ArrayList<Cuota> auxList = new ArrayList<Cuota>();
		auxList.addAll(this.getListaCuotasVencidas());
		
		Collections.sort(auxList);
		
		return generarDuplas(auxList);
	}

	public List<DuplaCuota> getListaDuplaAPagar() {
		
		ArrayList<Cuota> auxList = new ArrayList<Cuota>();
		auxList.addAll(this.getListaCuotasAVencer());
		auxList.addAll(this.getListaCuotasVencidas());
		
		Collections.sort(auxList);
		
		return generarDuplas(auxList);
	}	
	
	private List<DuplaCuota> generarDuplas(List<Cuota> input) {
		
		List<DuplaCuota> output = new ArrayList<DuplaCuota>();
		Iterator<Cuota> it = input.iterator();
		
		DuplaCuota auxDupla = null;
		
		while (it.hasNext()) {
			Cuota c = it.next();
			
			if (auxDupla == null){
				auxDupla = new DuplaCuota();
				auxDupla.setCuotaAbl(c);
			} else {
				if (auxDupla.getCuotaAbl().getPeriodo().equals(c.getPeriodo())){
					auxDupla.setCuotaProt(c);
					output.add(auxDupla);
					auxDupla = null;
				} else {
					output.add(auxDupla);
					auxDupla = new DuplaCuota();
					auxDupla.setCuotaAbl(c);
				}
			}
		}
		
		return output;
	}
}