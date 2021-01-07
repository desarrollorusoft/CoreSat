package ar.com.cognisys.sat.core.modelo.comun.planDePago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.simulacion.SimulacionPlanDePago;
import ar.com.cognisys.sat.core.modelo.enums.EstadoCuotaAVencer;

public class PlanDePago {

	private List<CuotaAVencer> listaCuotasAVencer; 
	private TotalCuota totalCuotasAVencer;
	private SimulacionPlanDePago simulacionPlanDePago;
	private EstadoCuotaAVencer estadoCuotaAVencer;
	private Cuenta cuenta;
	
	public boolean tengoDatosConsulta() {
		
		if (this.getListaCuotasAVencer() != null || this.getTotalCuotasAVencer() != null) {
			
			return true;
		}
		
		return false;
	}
	
	public boolean tengoDatosResultado() {
		
		if (this.getSimulacionPlanDePago() != null) {
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuenta == null) ? 0 : cuenta.hashCode());
		result = prime
				* result
				+ ((estadoCuotaAVencer == null) ? 0 : estadoCuotaAVencer
						.hashCode());
		result = prime
				* result
				+ ((listaCuotasAVencer == null) ? 0 : listaCuotasAVencer
						.hashCode());
		result = prime
				* result
				+ ((simulacionPlanDePago == null) ? 0 : simulacionPlanDePago
						.hashCode());
		result = prime
				* result
				+ ((totalCuotasAVencer == null) ? 0 : totalCuotasAVencer
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanDePago other = (PlanDePago) obj;
		if (cuenta == null) {
			if (other.cuenta != null)
				return false;
		} else if (!cuenta.equals(other.cuenta))
			return false;
		if (estadoCuotaAVencer != other.estadoCuotaAVencer)
			return false;
		if (listaCuotasAVencer == null) {
			if (other.listaCuotasAVencer != null)
				return false;
		} else if (!listaCuotasAVencer.equals(other.listaCuotasAVencer))
			return false;
		if (simulacionPlanDePago == null) {
			if (other.simulacionPlanDePago != null)
				return false;
		} else if (!simulacionPlanDePago.equals(other.simulacionPlanDePago))
			return false;
		if (totalCuotasAVencer == null) {
			if (other.totalCuotasAVencer != null)
				return false;
		} else if (!totalCuotasAVencer.equals(other.totalCuotasAVencer))
			return false;
		return true;
	}

	public List<CuotaAVencer> getListaCuotasAVencer() {
		return listaCuotasAVencer;
	}
	
	public void setListaCuotasAVencer(List<CuotaAVencer> listaCuotasAVencer) {
		this.listaCuotasAVencer = listaCuotasAVencer;
	}
	
	public TotalCuota getTotalCuotasAVencer() {
		return totalCuotasAVencer;
	}
	
	public void setTotalCuotasAVencer(TotalCuota totalCuotasAVencer) {
		this.totalCuotasAVencer = totalCuotasAVencer;
	}
	
	public SimulacionPlanDePago getSimulacionPlanDePago() {
		return simulacionPlanDePago;
	}
	
	public void setSimulacionPlanDePago(SimulacionPlanDePago simulacionPlanDePago) {
		this.simulacionPlanDePago = simulacionPlanDePago;
	}

	public EstadoCuotaAVencer getEstadoCuotaAVencer() {
		return estadoCuotaAVencer;
	}

	public void setEstadoCuotaAVencer(EstadoCuotaAVencer estadoCuotaAVencer) {
		this.estadoCuotaAVencer = estadoCuotaAVencer;
	}
	
	public List<DuplaCuotaAVencer> getListaDuplaAVencer() {
		
		ArrayList<CuotaAVencer> auxList = new ArrayList<CuotaAVencer>();
		auxList.addAll(listaCuotasAVencer);
		
		Collections.sort(auxList);
		
		return generarDuplas(auxList);
	}
	
	private List<DuplaCuotaAVencer> generarDuplas(List<CuotaAVencer> input) {
		List<DuplaCuotaAVencer> output = new ArrayList<DuplaCuotaAVencer>();
		
		
		Iterator<CuotaAVencer> it = input.iterator();
		
		DuplaCuotaAVencer auxDupla = null;
		
		while (it.hasNext()) {
			CuotaAVencer c = it.next();
			
			if (auxDupla == null){
				auxDupla = new DuplaCuotaAVencer();
				auxDupla.setCuotaAbl(c);
			} else {
				if (auxDupla.getCuotaAbl().getPeriodo().equals(c.getPeriodo())){
					auxDupla.setCuotaProt(c);
					output.add(auxDupla);
					auxDupla = null;
				} else {
					output.add(auxDupla);
					auxDupla = new DuplaCuotaAVencer();
					auxDupla.setCuotaAbl(c);
				}
			}
		}
		return output;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}	
}