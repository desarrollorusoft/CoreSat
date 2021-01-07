package ar.com.cognisys.sat.v2.core.modelo.view.builder;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.v2.core.modelo.view.CuotaDeudaView;

public class CuotaDeudaViewBuilder {

	private Cuota cuota;
	private CuotaDeudaView cuotaDeudaAsociada;
	protected CuotaDeudaView cuotaDeuda;
	
	public CuotaDeudaViewBuilder(Cuota cuota) {
		this.cuota = cuota;
	}
	
	public CuotaDeudaViewBuilder(Cuota cuota, CuotaDeudaView cuotaDeudaAsociada) {
		this.cuota = cuota;
		this.cuotaDeudaAsociada = cuotaDeudaAsociada;
	}

	public void inicializar() {
		this.cuotaDeuda = new CuotaDeudaView();
	}

	public void cargarDatos() {
		this.cuotaDeuda.setCapital( String.valueOf( this.cuota.getCapital() ) );
		this.cuotaDeuda.setRecargo( String.valueOf( this.cuota.getRecargo() ) );
		this.cuotaDeuda.setMulta( String.valueOf( this.cuota.getMulta() ) );
		this.cuotaDeuda.setTotal( String.valueOf( this.cuota.getTotal() ) );
		this.cuotaDeuda.setVencimiento( this.cuota.getFechaVencimiento() );
		this.cuotaDeuda.setEnMora( this.cuota.isMora() );
		this.cuotaDeuda.setTasa( this.cuota.getTasa() );
		this.cuotaDeuda.setPeriodo( this.cuota.getPeriodo() );
	}

	public void cargarCuotaAsociada() {
		this.cuotaDeuda.setCuotaAsociada( this.cuotaDeudaAsociada );
	}

	public CuotaDeudaView getCuotaDeuda() {
		return this.cuotaDeuda;
	}

}
