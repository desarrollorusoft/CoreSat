package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaApagar;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class DeudasCuentaAblAPagar {
	
	private List<CuotaApagar> cuotasAPagar;
	private List<DuplaCuotaAPagar> duplaCuotasAPagar;
	private TotalCuota totalApagar;
	private TotalCuota totalApagarContado;
	
	public List<DuplaCuotaAPagar> getDuplaCuotasAPagar() {
		
		ArrayList<DuplaCuotaAPagar> auxList = new ArrayList<DuplaCuotaAPagar>();
		auxList.addAll(duplaCuotasAPagar);
		
		Collections.sort(auxList);
		
		return auxList;
	}

	public void setDuplaCuotasAPagar(List<DuplaCuotaAPagar> duplaCuotasAPagar) {
		this.duplaCuotasAPagar = duplaCuotasAPagar;
	}
	
	public List<CuotaApagar> getCuotasAPagar() {
		
		ArrayList<CuotaApagar> auxList = new ArrayList<CuotaApagar>();
		auxList.addAll(cuotasAPagar);
		
		Collections.sort(auxList);
		
		return auxList;
	}
	
	public void setCuotasAPagar(List<CuotaApagar> cuotasAPagar) {
		this.cuotasAPagar = cuotasAPagar;
		
		List<DuplaCuotaAPagar> listaDuplas = new ArrayList<DuplaCuotaAPagar>();
		
		ArrayList<CuotaApagar> auxList = new ArrayList<CuotaApagar>();
		auxList.addAll(cuotasAPagar);
		
		Iterator<CuotaApagar> it = auxList.iterator();
		
		DuplaCuotaAPagar auxDupla = null;
		
		while (it.hasNext()) {
			CuotaApagar c = it.next();
			
			if (auxDupla == null){
				auxDupla = new DuplaCuotaAPagar();
				
				this.setearDupla(auxDupla, c);
			} else {
				if (auxDupla.getPeriodo().equals(c.getFechaVencimiento())) {
					this.setearDupla(auxDupla, c);
					listaDuplas.add(auxDupla);
					auxDupla = null;
				} else {
					listaDuplas.add(auxDupla);
					auxDupla = new DuplaCuotaAPagar();
					this.setearDupla(auxDupla, c);
				}
			}
		}
		
		if (auxDupla != null) {
			listaDuplas.add(auxDupla);
		}
		
		Collections.sort(listaDuplas);
		setDuplaCuotasAPagar(listaDuplas);
	}
	
	public void setearDupla(DuplaCuotaAPagar dupla, CuotaApagar cuota) {
		
		if (esABL(cuota.getTasa())) {
			dupla.setCuotaAbl(cuota);
		} else {
			dupla.setCuotaProt(cuota);
		}
		
		dupla.setPeriodo(cuota.getFechaVencimiento());
	}
	
	public boolean esABL(String tasa) {
		
		if (tasa.trim().equalsIgnoreCase("ABL") || tasa.trim().equalsIgnoreCase("A.L C.V.P y S.V")) {
			return true;
		}
		
		return false;
	}
	
	public TotalCuota getTotalApagar() {
		return totalApagar;
	}
	
	public void setTotalApagar(TotalCuota totalApagar) {
		this.totalApagar = totalApagar;
	}

	public TotalCuota getTotalApagarContado() {
		return totalApagarContado;
	}

	public void setTotalApagarContado(TotalCuota totalApagarContado) {
		this.totalApagarContado = totalApagarContado;
	}
}