package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryCuotaAdapter;

public class DeudaAdapter extends Deuda {

	private List<CuotaAdapter> cuotasVencidas;
	private List<CuotaAdapter> cuotasAVencer;
	
	public void generarAdapters() {
		this.setCuotasAVencer(new ArrayList<CuotaAdapter>());
		if (hayCuotas( this.getListaCuotasAVencer() ))
			this.generarAdaptersAVencer();
		
		this.setCuotasVencidas(new ArrayList<CuotaAdapter>());
		if (hayCuotas( this.getListaCuotasVencidas() ))
			this.generarAdaptersVencidos();
	}
	
	private void generarAdaptersAVencer() {
		this.generarListaAdapter(this.getListaCuotasAVencer(), this.getCuotasAVencer());
	}

	private void generarAdaptersVencidos() {
		this.generarListaAdapter(this.getListaCuotasVencidas(), this.getCuotasVencidas());
	}
	
	private void generarListaAdapter(List<Cuota> listaCuotas, List<CuotaAdapter> listaAdapters) {
		
		CuotaAdapter ca = this.crearCuotaAdapter(listaAdapters);
		for (Cuota c : listaCuotas) {
			if (aplica(ca, c))
				ca.agregar(c);
			else
				ca = this.crearCuotaAdapter(listaAdapters, c);
		}
	}
	
	private boolean aplica(CuotaAdapter ca, Cuota c) {
		return ca.getPeriodo() == null ||
				((c.getNumeroTasa() == 100 || c.getNumeroTasa() == 102) && ca.getPeriodo().equals(c.getPeriodo()));
	}

	private CuotaAdapter crearCuotaAdapter(List<CuotaAdapter> listaAdapters, Cuota cuota) {
		CuotaAdapter ca = this.crearCuotaAdapter(listaAdapters);
		ca.agregar(cuota);
		return ca;
	}
	
	private CuotaAdapter crearCuotaAdapter(List<CuotaAdapter> listaAdapters) {
		CuotaAdapter ca = FactoryCuotaAdapter.generar();
		listaAdapters.add(ca);
		return ca;
	}

	private boolean hayCuotas(List<Cuota> listaCuotas) {
		return listaCuotas != null && !listaCuotas.isEmpty();
	}

	public List<CuotaAdapter> getCuotasVencidas() {
		return cuotasVencidas;
	}

	public void setCuotasVencidas(List<CuotaAdapter> cuotasVencidas) {
		this.cuotasVencidas = cuotasVencidas;
	}

	public List<CuotaAdapter> getCuotasAVencer() {
		return cuotasAVencer;
	}

	public void setCuotasAVencer(List<CuotaAdapter> cuotasAVencer) {
		this.cuotasAVencer = cuotasAVencer;
	}
}