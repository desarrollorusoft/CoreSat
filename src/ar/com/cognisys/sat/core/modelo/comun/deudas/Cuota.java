package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.util.Date;

public class Cuota implements Comparable<Cuota>, ICuota {

	private Integer numeroTasa;
	private String tasa;
	private String periodo;
	private Float capital;
	private Float recargo;
	private Float multa;
	private Float total;
	private Date fechaVencimiento;
	private String estado;
	private boolean pagar;
	private Integer numeroTransaccion;
	private boolean mora;
	private Cuota cuotaAsociada;
	private Integer numeroEstado;
	private Integer numeroOrigen;
	
	public static Float calcularTotalPeriodo(double capital, double recargo, double multa) {
		return (new Float(capital + recargo + multa));
	}

	public boolean sos(Integer numeroTasa) {
		return this.numeroTasa.equals( numeroTasa );
	}

	public boolean tenesCuotaAsociada() {
		return this.cuotaAsociada != null;
	}
	
	public boolean tienePeriodo(String periodo) {
		return this.getPeriodo().equals( periodo );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaVencimiento == null) ? 0 : fechaVencimiento.hashCode());
		result = prime * result + (mora ? 1231 : 1237);
		result = prime * result + ((multa == null) ? 0 : multa.hashCode());
		result = prime * result + ((numeroTasa == null) ? 0 : numeroTasa.hashCode());
		result = prime * result + ((numeroTransaccion == null) ? 0 : numeroTransaccion.hashCode());
		result = prime * result + (pagar ? 1231 : 1237);
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result + ((recargo == null) ? 0 : recargo.hashCode());
		result = prime * result + ((tasa == null) ? 0 : tasa.hashCode());
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
		Cuota other = (Cuota) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaVencimiento == null) {
			if (other.fechaVencimiento != null)
				return false;
		} else if (!fechaVencimiento.equals(other.fechaVencimiento))
			return false;
		if (mora != other.mora)
			return false;
		if (multa == null) {
			if (other.multa != null)
				return false;
		} else if (!multa.equals(other.multa))
			return false;
		if (numeroTasa == null) {
			if (other.numeroTasa != null)
				return false;
		} else if (!numeroTasa.equals(other.numeroTasa))
			return false;
		if (numeroTransaccion == null) {
			if (other.numeroTransaccion != null)
				return false;
		} else if (!numeroTransaccion.equals(other.numeroTransaccion))
			return false;
		if (pagar != other.pagar)
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		if (recargo == null) {
			if (other.recargo != null)
				return false;
		} else if (!recargo.equals(other.recargo))
			return false;
		if (tasa == null) {
			if (other.tasa != null)
				return false;
		} else if (!tasa.equals(other.tasa))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cuota{" +
				"numeroTasa=" + numeroTasa +
				", tasa='" + tasa + '\'' +
				", periodo='" + periodo + '\'' +
				", capital=" + capital +
				", recargo=" + recargo +
				", multa=" + multa +
				", total=" + total +
				", fechaVencimiento=" + fechaVencimiento +
				", estado='" + estado + '\'' +
				", pagar=" + pagar +
				", numeroTransaccion=" + numeroTransaccion +
				", mora=" + mora +
				'}';
	}

	@Override
	public int compareTo(Cuota o) {
		if (o == null) {
			return 1;
		}
		Integer out = null;
		if (this.getFechaVencimiento() != null && o.getFechaVencimiento() != null) {
			out = this.getFechaVencimiento().compareTo(o.getFechaVencimiento());
		} else if (this.getPeriodo() != null) {
			out = this.getPeriodo().trim().compareToIgnoreCase((o.getPeriodo() != null) ? o.getPeriodo().trim() : o.getPeriodo());
		}
		
		if (out != 0)
			return out;
		return this.getNumeroTasa().compareTo(o.getNumeroTasa());
	}

	public String getTasa() {
		if (tasa.trim().equals("ABL"))
			return "A.L C.V.P y S.V";
		
		if (tasa.trim().equals("PROT.CIUDADANA"))
			return "y C.P.C";
		
		if (tasa.trim().equals("ROD"))
			return "RODADOS";
		
		if (tasa.trim().equals("VEH"))
			return "AUTOMOTOR";
		
		if (tasa.trim().equals("CEM"))
			return "CEMENTERIO";
		
		return tasa;
	}
	
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public Float getCapital() {
		return capital;
	}
	
	public void setCapital(Float capital) {
		this.capital = capital;
	}
	
	public Float getRecargo() {
		return recargo;
	}
	
	public void setRecargo(Float recargo) {
		this.recargo = recargo;
	}
	
	public Float getMulta() {
		return multa;
	}
	
	public void setMulta(Float multa) {
		this.multa = multa;
	}
	
	public Float getTotal() {
		return total;
	}
	
	public void setTotal(Float total) {
		this.total = total;
	}
	
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isPagar() {
		return pagar;
	}

	public void setPagar(boolean pagar) {
		this.pagar = pagar;
	}

	public Integer getNumeroTransaccion() {
		return numeroTransaccion;
	}

	public void setNumeroTransaccion(Integer numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}
	
	public Integer getNumeroTasa() {
		return numeroTasa;
	}

	public void setNumeroTasa(Integer numeroTasa) {
		this.numeroTasa = numeroTasa;
	}
	
	public boolean isMora() {
		return mora;
	}

	public void setMora(boolean mora) {
		this.mora = mora;
	}

	public Cuota getCuotaAsociada() {
		return cuotaAsociada;
	}

	public void setCuotaAsociada(Cuota cuotaAsociada) {
		this.cuotaAsociada = cuotaAsociada;
	}

	public Integer getNumeroEstado() {
		return numeroEstado;
	}

	public void setNumeroEstado(Integer numeroEstado) {
		this.numeroEstado = numeroEstado;
	}

	public Integer getNumeroOrigen() {
		return numeroOrigen;
	}

	public void setNumeroOrigen(Integer numeroOrigen) {
		this.numeroOrigen = numeroOrigen;
	}
}