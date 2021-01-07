package ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj;

import java.util.Date;

public class RSPeriodoDeuda {

	private Integer ano;
	private Integer cuota;
	private Date vencimiento;
	private Float monto;
	
	public RSPeriodoDeuda(Integer cuota, Date vencimiento, Integer ano) {
		this.setCuota(cuota);
		this.setVencimiento(vencimiento);
		this.setAno(ano);
	}
	
	public String getPeriodo() {
		return ano.toString() + "-" + String.format("%02d", cuota); 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuota == null) ? 0 : cuota.hashCode());
		result = prime * result + ((monto == null) ? 0 : monto.hashCode());
		result = prime * result + ((vencimiento == null) ? 0 : vencimiento.hashCode());
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
		RSPeriodoDeuda other = (RSPeriodoDeuda) obj;
		if (cuota == null) {
			if (other.cuota != null)
				return false;
		} else if (!cuota.equals(other.cuota))
			return false;
		if (monto == null) {
			if (other.monto != null)
				return false;
		} else if (!monto.equals(other.monto))
			return false;
		if (vencimiento == null) {
			if (other.vencimiento != null)
				return false;
		} else if (!vencimiento.equals(other.vencimiento))
			return false;
		return true;
	}

	public Integer getCuota() {
		return cuota;
	}

	public void setCuota(Integer cuota) {
		this.cuota = cuota;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
}