package ar.com.cognisys.sat.core.modelo.abstracto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryTotalCuota;
import ar.com.cognisys.sat.v2.core.modelo.bo.enumerator.TipoAviso;

public abstract class Deuda {

	private List<Cuota> listaCuotasAVencer;
	private TotalCuota totalCoutasAVencer;
	private List<Cuota> listaCuotasVencidas;
	private TotalCuota totalCoutasVencidas;
	private TotalCuota totalCoutasGeneral;
	private boolean tieneDeudaLegales;
	/* Para la generacion de recibos y pagos */
	private String numeroComprobante;
	private String idCarrito;
	
	public String generarAviso() {
		if ( this.listaCuotasVencidas == null || this.listaCuotasVencidas.isEmpty() )
			return null;
		
		return this.tieneDeudaLegales ? TipoAviso.LEGALES.name() : TipoAviso.DESCUENTO.name();
	}
	
	public void agregarCouta(Cuota cuota) {
		if (cuota.getFechaVencimiento().before(new Date()))
			this.getListaCuotasVencidas().add(cuota);
		else
			this.getListaCuotasAVencer().add(cuota);
	}
	
	public void agregarCouta(Cuota cuota, boolean esVencida) {
		if (esVencida)
			this.getListaCuotasVencidas().add(cuota);
		else
			this.getListaCuotasAVencer().add(cuota);
	}
	
	public void agregarLista(List<Cuota> listaCuotas) {
		for (Cuota cuota : listaCuotas)
			this.agregarCouta(cuota);

		Collections.sort( this.getListaCuotasAVencer() );
		Collections.sort( this.getListaCuotasVencidas() );
	}
	
	public void caulcularTotales() {
		this.setTotalCoutasAVencer( this.calcularTotal(this.getListaCuotasAVencer()) );
		this.setTotalCoutasVencidas( this.calcularTotal(this.getListaCuotasVencidas()) );
		this.setTotalCoutasGeneral( FactoryTotalCuota.generarIntanciaCompleta(this.getTotalCoutasAVencer().getCapital() + this.getTotalCoutasVencidas().getCapital(), 
																			  this.getTotalCoutasAVencer().getRecargo() + this.getTotalCoutasVencidas().getRecargo(), 
																			  this.getTotalCoutasAVencer().getMulta() + this.getTotalCoutasVencidas().getMulta(), 
																			  this.getTotalCoutasAVencer().getTotal() + this.getTotalCoutasVencidas().getTotal()) );
	}
	
	private TotalCuota calcularTotal(List<Cuota> listaCuotas) {
		TotalCuota total = FactoryTotalCuota.generarIntanciaVacia();
		
		for (Cuota cuota : listaCuotas) {
			
			total.setCapital(total.getCapital() + cuota.getCapital() );
			total.setRecargo(total.getRecargo() + cuota.getRecargo() );
			total.setMulta(total.getMulta() + cuota.getMulta() );
			total.setTotal(total.getTotal() + cuota.getTotal() );
		}
		
		return total;
	}
	
	public List<Cuota> getListaCuotas() {
		
		List<Cuota> lista = new ArrayList<Cuota>();
		
		lista.addAll(getListaCuotasAVencer());
		lista.addAll(getListaCuotasVencidas());
		
		return lista;
	}
	
	public boolean tieneCoutasVencidas() {
		return this.getListaCuotasVencidas() != null && !this.getListaCuotasVencidas().isEmpty();
	}
	
	public boolean tieneCuotasAVencer() {
		return this.getListaCuotasAVencer() != null && !this.getListaCuotasAVencer().isEmpty();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCarrito == null) ? 0 : idCarrito.hashCode());
		result = prime * result + ((listaCuotasAVencer == null) ? 0 : listaCuotasAVencer.hashCode());
		result = prime * result + ((listaCuotasVencidas == null) ? 0 : listaCuotasVencidas.hashCode());
		result = prime * result + ((numeroComprobante == null) ? 0 : numeroComprobante.hashCode());
		result = prime * result + (tieneDeudaLegales ? 1231 : 1237);
		result = prime * result + ((totalCoutasAVencer == null) ? 0 : totalCoutasAVencer.hashCode());
		result = prime * result + ((totalCoutasGeneral == null) ? 0 : totalCoutasGeneral.hashCode());
		result = prime * result + ((totalCoutasVencidas == null) ? 0 : totalCoutasVencidas.hashCode());
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
		Deuda other = (Deuda) obj;
		if (idCarrito == null) {
			if (other.idCarrito != null)
				return false;
		} else if (!idCarrito.equals(other.idCarrito))
			return false;
		if (listaCuotasAVencer == null) {
			if (other.listaCuotasAVencer != null)
				return false;
		} else if (!listaCuotasAVencer.equals(other.listaCuotasAVencer))
			return false;
		if (listaCuotasVencidas == null) {
			if (other.listaCuotasVencidas != null)
				return false;
		} else if (!listaCuotasVencidas.equals(other.listaCuotasVencidas))
			return false;
		if (numeroComprobante == null) {
			if (other.numeroComprobante != null)
				return false;
		} else if (!numeroComprobante.equals(other.numeroComprobante))
			return false;
		if (tieneDeudaLegales != other.tieneDeudaLegales)
			return false;
		if (totalCoutasAVencer == null) {
			if (other.totalCoutasAVencer != null)
				return false;
		} else if (!totalCoutasAVencer.equals(other.totalCoutasAVencer))
			return false;
		if (totalCoutasGeneral == null) {
			if (other.totalCoutasGeneral != null)
				return false;
		} else if (!totalCoutasGeneral.equals(other.totalCoutasGeneral))
			return false;
		if (totalCoutasVencidas == null) {
			if (other.totalCoutasVencidas != null)
				return false;
		} else if (!totalCoutasVencidas.equals(other.totalCoutasVencidas))
			return false;
		return true;
	}

	public List<Cuota> getListaCuotasAVencer() {
		return listaCuotasAVencer;
	}

	public void setListaCuotasAVencer(List<Cuota> listaCuotasAVencer) {
		this.listaCuotasAVencer = listaCuotasAVencer;
	}

	public TotalCuota getTotalCoutasAVencer() {
		return totalCoutasAVencer;
	}

	public void setTotalCoutasAVencer(TotalCuota totalCoutasAVencer) {
		this.totalCoutasAVencer = totalCoutasAVencer;
	}

	public List<Cuota> getListaCuotasVencidas() {
		return listaCuotasVencidas;
	}

	public void setListaCuotasVencidas(List<Cuota> listaCuotasVencidas) {
		this.listaCuotasVencidas = listaCuotasVencidas;
	}

	public TotalCuota getTotalCoutasVencidas() {
		return totalCoutasVencidas;
	}

	public void setTotalCoutasVencidas(TotalCuota totalCoutasVencidas) {
		this.totalCoutasVencidas = totalCoutasVencidas;
	}

	public TotalCuota getTotalCoutasGeneral() {
		return totalCoutasGeneral;
	}

	public void setTotalCoutasGeneral(TotalCuota totalCoutasGeneral) {
		this.totalCoutasGeneral = totalCoutasGeneral;
	}

	public boolean isTieneDeudaLegales() {
		return tieneDeudaLegales;
	}

	public void setTieneDeudaLegales(boolean tieneDeudaLegales) {
		this.tieneDeudaLegales = tieneDeudaLegales;
	}

	public String getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(String numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public String getIdCarrito() {
		return idCarrito;
	}

	public void setIdCarrito(String idCarrito) {
		this.idCarrito = idCarrito;
	}

}