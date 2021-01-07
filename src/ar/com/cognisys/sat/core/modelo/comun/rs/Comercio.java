package ar.com.cognisys.sat.core.modelo.comun.rs;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;

public class Comercio {

	private String cuit;
	private String razonSocial;
	private List<DDJJRS> listaDeclaraciones;
	private SolicitanteRS solicitanteRS;
	private Usuario titular;
	
	public boolean tieneSolicitante() {
		return this.getSolicitanteRS() != null;
	}
	
	public PadronRS obtenerPadronDeclaracion(String ano, String numeroCuenta) {
		DDJJRS ddjj = this.obtenerDeclaracion( Integer.parseInt(ano) );
		
		if (ddjj != null)
			return ddjj.recuperar( numeroCuenta );
		else
			return null;
	}
	
	public DDJJRS obtenerDeclaracion(int ano) {
		for (DDJJRS ddjj : this.getListaDeclaraciones())
			if (ddjj.sos( ano ))
				return ddjj;
		
		return null;
	}
	
	public DDJJRS obtenerDeclaracion(PadronRS padron) {
		
		Integer ano = padron.obtenerAnoDeclaracion();
		
		if (ano != null) {			
			for (DDJJRS ddjj : this.getListaDeclaraciones())
				if (ddjj.sos( ano ))
					return ddjj;

			return null;
		} else {
			for (DDJJRS ddjj : this.getListaDeclaraciones())
				if (ddjj.tiene( padron ) && !padron.tieneVersiones())
					return ddjj;

			return null;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuit == null) ? 0 : cuit.hashCode());
		result = prime * result + ((listaDeclaraciones == null) ? 0 : listaDeclaraciones.hashCode());
		result = prime * result + ((razonSocial == null) ? 0 : razonSocial.hashCode());
		result = prime * result + ((solicitanteRS == null) ? 0 : solicitanteRS.hashCode());
		result = prime * result + ((titular == null) ? 0 : titular.hashCode());
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
		Comercio other = (Comercio) obj;
		if (cuit == null) {
			if (other.cuit != null)
				return false;
		} else if (!cuit.equals(other.cuit))
			return false;
		if (listaDeclaraciones == null) {
			if (other.listaDeclaraciones != null)
				return false;
		} else if (!listaDeclaraciones.equals(other.listaDeclaraciones))
			return false;
		if (razonSocial == null) {
			if (other.razonSocial != null)
				return false;
		} else if (!razonSocial.equals(other.razonSocial))
			return false;
		if (solicitanteRS == null) {
			if (other.solicitanteRS != null)
				return false;
		} else if (!solicitanteRS.equals(other.solicitanteRS))
			return false;
		if (titular == null) {
			if (other.titular != null)
				return false;
		} else if (!titular.equals(other.titular))
			return false;
		return true;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Usuario getTitular() {
		return titular;
	}

	public void setTitular(Usuario titular) {
		this.titular = titular;
	}

	public List<DDJJRS> getListaDeclaraciones() {
		return listaDeclaraciones;
	}

	public void setListaDeclaraciones(List<DDJJRS> listaDeclaraciones) {
		this.listaDeclaraciones = listaDeclaraciones;
	}

	public SolicitanteRS getSolicitanteRS() {
		return solicitanteRS;
	}

	public void setSolicitanteRS(SolicitanteRS solicitanteRS) {
		this.solicitanteRS = solicitanteRS;
	}
}