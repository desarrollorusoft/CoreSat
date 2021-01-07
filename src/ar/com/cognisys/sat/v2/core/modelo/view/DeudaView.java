package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaDeudaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IDeudaView;

public class DeudaView implements IDeudaView {

	private static final long serialVersionUID = -6421454863179257640L;
	private String aviso;
	private ICuotaDeudaView[] cuotasVencidas;
	private ICuotaDeudaView[] cuotasProximas;
	private String totalVencidas;
	private String totalProximas;

	// @formatter:off
	public DeudaView() { }
	// @formatter:on

	public DeudaView(String aviso, ICuotaDeudaView[] cuotasVencidas, ICuotaDeudaView[] cuotasProximas, String totalVencidas, String totalProximas) {
		this.aviso = aviso;
		this.cuotasVencidas = cuotasVencidas;
		this.cuotasProximas = cuotasProximas;
		this.totalVencidas = totalVencidas;
		this.totalProximas = totalProximas;
	}
	
	@Override
	public String getAviso() {
		return aviso;
	}

	@Override
	public ICuotaDeudaView[] getCuotasVencidas() {
		return cuotasVencidas;
	}

	@Override
	public ICuotaDeudaView[] getCuotasProximas() {
		return cuotasProximas;
	}

	@Override
	public String getTotalVencidas() {
		return totalVencidas;
	}

	@Override
	public String getTotalProximas() {
		return totalProximas;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public void setCuotasVencidas(ICuotaDeudaView[] cuotasVencidas) {
		this.cuotasVencidas = cuotasVencidas;
	}

	public void setCuotasProximas(ICuotaDeudaView[] cuotasProximas) {
		this.cuotasProximas = cuotasProximas;
	}

	public void setTotalVencidas(String totalVencidas) {
		this.totalVencidas = totalVencidas;
	}

	public void setTotalProximas(String totalProximas) {
		this.totalProximas = totalProximas;
	}
	
}
