package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import java.util.List;

public class RSDeudaPadron {

	private Float total;
	private Float monto200;
	private Float monto201;
	private Float monto202;
	private Float monto203;
	private List<RSPeriodoDeuda> vencimientos;
	private String cuenta;
	
	public void cargarVencimientos() {
		for (RSPeriodoDeuda periodo : vencimientos)
			periodo.setMonto(this.getTotal());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((monto200 == null) ? 0 : monto200.hashCode());
		result = prime * result + ((monto201 == null) ? 0 : monto201.hashCode());
		result = prime * result + ((monto202 == null) ? 0 : monto202.hashCode());
		result = prime * result + ((monto203 == null) ? 0 : monto203.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		RSDeudaPadron other = (RSDeudaPadron) obj;
		if (monto200 == null) {
			if (other.monto200 != null)
				return false;
		} else if (!monto200.equals(other.monto200))
			return false;
		if (monto201 == null) {
			if (other.monto201 != null)
				return false;
		} else if (!monto201.equals(other.monto201))
			return false;
		if (monto202 == null) {
			if (other.monto202 != null)
				return false;
		} else if (!monto202.equals(other.monto202))
			return false;
		if (monto203 == null) {
			if (other.monto203 != null)
				return false;
		} else if (!monto203.equals(other.monto203))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	protected Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	protected Float getMonto200() {
		return monto200;
	}

	public void setMonto200(Float monto200) {
		this.monto200 = monto200;
	}

	protected Float getMonto201() {
		return monto201;
	}

	public void setMonto201(Float monto201) {
		this.monto201 = monto201;
	}

	protected Float getMonto202() {
		return monto202;
	}

	public void setMonto202(Float monto202) {
		this.monto202 = monto202;
	}

	protected Float getMonto203() {
		return monto203;
	}

	public void setMonto203(Float monto203) {
		this.monto203 = monto203;
	}

	public List<RSPeriodoDeuda> getVencimientos() {
		return vencimientos;
	}

	public void setVencimientos(List<RSPeriodoDeuda> vencimientos) {
		this.vencimientos = vencimientos;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
}