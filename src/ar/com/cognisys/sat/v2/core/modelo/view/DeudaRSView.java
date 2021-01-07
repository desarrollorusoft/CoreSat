package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IDeudaRSView;

public class DeudaRSView implements IDeudaRSView {
	
	private static final long serialVersionUID = 2565276146260884032L;
	private String padron;
	private ICuotaView[] cuotas;
	
	public DeudaRSView() {}
	
	public DeudaRSView(String padron, ICuotaView[] cuotas) {
		this.padron = padron;
		this.cuotas = cuotas;
	}
	
	@Override
	public String getPadron() {
		return padron;
	}

	@Override
	public ICuotaView[] getCuotas() {
		return cuotas;
	}
}