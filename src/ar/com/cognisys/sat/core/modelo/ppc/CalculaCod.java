package ar.com.cognisys.sat.core.modelo.ppc;

public class CalculaCod {

	private long nroTransaccion;
	private float liRecargo;
	private float liTotMul;
	private Integer nroPlan;

	public CalculaCod(long nroTransaccion, float liRecargo, float liTotMul, Integer nroPlan) {
		this.nroTransaccion = nroTransaccion;
		this.liRecargo = liRecargo;
		this.liTotMul = liTotMul;
		this.nroPlan = nroPlan;
	}

	public long getNroTransaccion() {
		return nroTransaccion;
	}

	public float getLiRecargo() {
		return liRecargo;
	}

	public float getLiTotMul() {
		return liTotMul;
	}

	public Integer getNroPlan() {
		return nroPlan;
	}

	public void actualizar(PorcentajeRecMul porcentaje) {
		
		if ( porcentaje == null )
			return;
		
		this.actualizarRecargo( porcentaje );
		this.actualizarMulta( porcentaje );
	}

	private void actualizarRecargo(PorcentajeRecMul porcentaje) {

		if ( porcentaje.isLiPorcRec( 0 ) )
			return;
		
		if ( porcentaje.isLiPorcRec( 100 ) ) {
			this.liRecargo = 0;
			return;
		}
		
		float aux = this.liRecargo * porcentaje.getLiPorcRec() / 100;
		
		this.liRecargo -= aux;
	}

	private void actualizarMulta(PorcentajeRecMul porcentaje) {
		
		if ( porcentaje.isLiPorcMul( 0 ) )
			return;
		
		if ( porcentaje.isLiPorcMul( 100 ) ) {
			this.liTotMul = 0;
			return;
		}
		
		float aux = this.liTotMul * porcentaje.getLiPorcMul() / 100;
		
		this.liTotMul -= aux;
		
	}


}
