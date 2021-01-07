package ar.com.cognisys.sat.core.modelo.comun.deudas;

import java.util.Date;

public interface ICuota {

	public boolean sos(Integer numeroTasa);
	
	public String getTasa();
	
	public String getPeriodo();
	
	public Float getCapital();
	
	public Float getRecargo();
	
	public Float getMulta();
	
	public Float getTotal();
	
	public Date getFechaVencimiento();
	
	public String getEstado();
	
	public boolean isPagar();
	
	public void setPagar(boolean pagar);
	
	public boolean isMora();

	public Integer getNumeroEstado();

	public Integer getNumeroOrigen();
}