package ar.com.cognisys.sat.core.modelo.comun.pago;

import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;

public class BoletaPago {

	private Deuda deudaNormal;
	private Deuda deudaContado;
	private Deuda deudaFinal;
	
	public TotalCuota getTotalPagoContado() {
		
		return (this.getDeudaContado() != null) ? this.getDeudaContado().getTotalCoutasGeneral() : null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deudaContado == null) ? 0 : deudaContado.hashCode());
		result = prime * result + ((deudaFinal == null) ? 0 : deudaFinal.hashCode());
		result = prime * result + ((deudaNormal == null) ? 0 : deudaNormal.hashCode());
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
		BoletaPago other = (BoletaPago) obj;
		if (deudaContado == null) {
			if (other.deudaContado != null)
				return false;
		} else if (!deudaContado.equals(other.deudaContado))
			return false;
		if (deudaFinal == null) {
			if (other.deudaFinal != null)
				return false;
		} else if (!deudaFinal.equals(other.deudaFinal))
			return false;
		if (deudaNormal == null) {
			if (other.deudaNormal != null)
				return false;
		} else if (!deudaNormal.equals(other.deudaNormal))
			return false;
		return true;
	}

	public Deuda getDeudaNormal() {
		return deudaNormal;
	}

	public void setDeudaNormal(Deuda deudaNormal) {
		this.deudaNormal = deudaNormal;
	}

	public Deuda getDeudaContado() {
		return deudaContado;
	}

	public void setDeudaContado(Deuda deudaContado) {
		this.deudaContado = deudaContado;
	}

	public Deuda getDeudaFinal() {
		return deudaFinal;
	}

	public void setDeudaFinal(Deuda deudaFinal) {
		this.deudaFinal = deudaFinal;
	}
}