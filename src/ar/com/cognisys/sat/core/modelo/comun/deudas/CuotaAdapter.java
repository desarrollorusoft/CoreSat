package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CuotaAdapter implements ICuota {

	private List<Cuota> cuotas;
	
	public CuotaAdapter() {
		this.setCuotas(new ArrayList<Cuota>());
	}
	
	public CuotaAdapter(Cuota cuota) {
		this.setCuotas(new ArrayList<Cuota>());
		this.agregar(cuota);
	}
	
	public CuotaAdapter(List<Cuota> cuotas) {
		this.setCuotas(cuotas);
	}
	
	public void agregar(Cuota cuota) {
		this.getCuotas().add( cuota );
	}
	
	@Override
	public boolean sos(Integer numeroTasa) {
		for (Cuota cuota : this.getCuotas())
			if (cuota.sos(numeroTasa))
				return true;
		
		return false;
	}

	@Override
	public String getTasa() {
		String tasa = "";
		for (Cuota cuota : this.getCuotas())
			tasa += (tasa.isEmpty() ? "":" | ") + cuota.getTasa();
		
		return tasa;
	}

	@Override
	public String getPeriodo() {
		if (this.getCuotas().isEmpty())			
			return null;
		else
			return this.getCuotas().get(0).getPeriodo();
	}

	@Override
	public Float getCapital() {
		float monto = 0f;
		for (Cuota cuota : this.getCuotas())
			monto += cuota.getCapital();
			
		return monto;
	}

	@Override
	public Float getRecargo() {
		float monto = 0f;
		for (Cuota cuota : this.getCuotas())
			monto += cuota.getRecargo();
			
		return monto;
	}

	@Override
	public Float getMulta() {
		float monto = 0f;
		for (Cuota cuota : this.getCuotas())
			monto += cuota.getMulta();
			
		return monto;
	}

	@Override
	public Float getTotal() {
		float monto = 0f;
		for (Cuota cuota : this.getCuotas())
			monto += cuota.getTotal();
			
		return monto;
	}

	@Override
	public Date getFechaVencimiento() {
		if (this.getCuotas().isEmpty())			
			return null;
		else
			return this.getCuotas().get(0).getFechaVencimiento();
	}

	@Override
	public String getEstado() {
		if (this.getCuotas().isEmpty())			
			return null;
		else
			return this.getCuotas().get(0).getEstado();
	}

	@Override
	public boolean isPagar() {
		if (this.getCuotas().isEmpty())			
			return false;
		else
			return this.getCuotas().get(0).isPagar();
	}

	@Override
	public void setPagar(boolean pagar) {
		for (Cuota cuota : this.getCuotas())
			cuota.setPagar( pagar );
	}

	@Override
	public boolean isMora() {
		for (Cuota cuota : this.getCuotas())
			if (cuota.isMora())
				return true;
				
		return false;
	}

	@Override
	public Integer getNumeroEstado() {
		if (this.getCuotas().isEmpty())
			return null;
		else
			return this.getCuotas().get(0).getNumeroEstado();
	}

	@Override
	public Integer getNumeroOrigen() {
		if (this.getCuotas().isEmpty())
			return null;
		else
			return this.getCuotas().get(0).getNumeroOrigen();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuotas == null) ? 0 : cuotas.hashCode());
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
		CuotaAdapter other = (CuotaAdapter) obj;
		if (cuotas == null) {
			if (other.cuotas != null)
				return false;
		} else if (!cuotas.equals(other.cuotas))
			return false;
		return true;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	protected void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public boolean tienePeriodo(String periodo) {
		return this.getPeriodo().equals(periodo);
	}
}