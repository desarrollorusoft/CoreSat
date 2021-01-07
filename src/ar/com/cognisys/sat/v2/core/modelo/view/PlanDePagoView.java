package ar.com.cognisys.sat.v2.core.modelo.view;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IPlanDePagoView;

public class PlanDePagoView implements IPlanDePagoView {

	private static final long serialVersionUID = -8329201577421763805L;
	private String nombre;
	private String anticipo;
	private String porcentaje;
	private String importeTotal;
	private String importeAnticipo;
	private String importePlan;
	private ICuotaView[] cuotas;

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getAnticipo() {
		return anticipo;
	}

	@Override
	public String getPorcentaje() {
		return porcentaje;
	}

	@Override
	public String getImporteTotal() {
		return importeTotal;
	}

	@Override
	public String getImporteAnticipo() {
		return importeAnticipo;
	}

	@Override
	public String getImportePlan() {
		return importePlan;
	}

	@Override
	public ICuotaView[] getCuotas() {
		return cuotas;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setAnticipo(String anticipo) {
		this.anticipo = anticipo;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}

	public void setImporteAnticipo(String importeAnticipo) {
		this.importeAnticipo = importeAnticipo;
	}

	public void setImportePlan(String importePlan) {
		this.importePlan = importePlan;
	}

	public void setCuotas(ICuotaView[] cuotas) {
		this.cuotas = cuotas;
	}

}
