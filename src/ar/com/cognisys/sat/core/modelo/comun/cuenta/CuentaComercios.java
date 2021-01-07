package ar.com.cognisys.sat.core.modelo.comun.cuenta;

import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJ;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.RSDeudaPadron;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public class CuentaComercios extends Cuenta {

	private static final long serialVersionUID = -7409024696696556461L;
	public static String tipoCuenta = "COMERCIOS";
	private String razonSocial;
	private Date fechaHabilitacion;
	private List<DDJJ> listaDeclaraciones;
	private RSDeudaPadron RSdeuda;
	private String cuit;

	@Override
	public boolean esCorrecto(String validacion) {
		return true;
	}
	
	@Override
	public String obtenerCodigo() {
		Integer numero = super.getNumero();
		return numero == null ? null : String.valueOf( numero );
	}

	@Override
	public String obtenerTipo() {
		return this.getTipoCuenta().getNombre();
	}
	
	// TODO Negrada. Arreglar cuando hayan declaraciones
	public DDJJ getDDJJEnCurso() {
		
		if ( this.getListaDeclaraciones() == null || this.getListaDeclaraciones().isEmpty() )
			return null;
		
		return this.getListaDeclaraciones().get( this.getListaDeclaraciones().size() - 1 );
	}
	
	@Override
	public TiposCuentas getTipoCuenta() {
		return TiposCuentas.COMERCIOS;
	}
	
	@Override
	public Integer getSistema() {
		return 1;
	}
	
	@Override
	public String getDatoCuenta() {
		return this.getNumero().toString();
	}
	
	@Override
	public boolean correspondePPC() {
		return false;
	}

	@Override
	public boolean correspondeDDJJ() {
		return true;
	}

	public boolean sos(String padron) {
		return this.getNumero().toString().equals(padron);
	}
	
	@Override
	public int compareTo(Cuenta o) {
		return this.getNumero().compareTo(o.getNumero());
	}
	
	public DDJJ getDeclaracionOriginal() {
		if (this.getListaDeclaraciones() != null)
			return this.getListaDeclaraciones().get(0);
		else
			return null;
	}

	public DDJJ getDeclaracionRectificativa() {
		if (this.getListaDeclaraciones() != null && this.getListaDeclaraciones().size() == 2)
			return this.getListaDeclaraciones().get(1);
		else
			return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( fechaHabilitacion == null ) ? 0 : fechaHabilitacion.hashCode() );
		result = prime * result + ( ( listaDeclaraciones == null ) ? 0 : listaDeclaraciones.hashCode() );
		result = prime * result + ( ( razonSocial == null ) ? 0 : razonSocial.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj )
			return true;
		if ( !super.equals( obj ) )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		CuentaComercios other = ( CuentaComercios ) obj;
		if ( fechaHabilitacion == null ) {
			if ( other.fechaHabilitacion != null )
				return false;
		} else if ( !fechaHabilitacion.equals( other.fechaHabilitacion ) )
			return false;
		if ( listaDeclaraciones == null ) {
			if ( other.listaDeclaraciones != null )
				return false;
		} else if ( !listaDeclaraciones.equals( other.listaDeclaraciones ) )
			return false;
		if ( razonSocial == null ) {
			if ( other.razonSocial != null )
				return false;
		} else if ( !razonSocial.equals( other.razonSocial ) )
			return false;
		return true;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public Date getFechaHabilitacion() {
		return fechaHabilitacion;
	}

	public void setFechaHabilitacion(Date fechaHabilitacion) {
		this.fechaHabilitacion = fechaHabilitacion;
	}

	public List<DDJJ> getListaDeclaraciones() {
		return listaDeclaraciones;
	}

	public void setListaDeclaraciones(List<DDJJ> listaDeclaraciones) {
		this.listaDeclaraciones = listaDeclaraciones;
	}

	public RSDeudaPadron getRSdeuda() {
		return RSdeuda;
	}

	public void setRSdeuda(RSDeudaPadron rSdeuda) {
		RSdeuda = rSdeuda;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
}
