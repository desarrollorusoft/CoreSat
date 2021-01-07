package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IDeudaRSView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegimenDeudasView;

public class RegimenDeudasView implements IRegimenDeudasView {
	
	private static final long serialVersionUID = -5108113808493793979L;
	private IDeudaRSView[] deudas;
	
	public RegimenDeudasView() {}
	
	public RegimenDeudasView(IDeudaRSView[] deudas) {
		this.deudas = deudas;
	}
	
	@Override
	public IDeudaRSView[] getDeudas() {
		return deudas;
	}
}