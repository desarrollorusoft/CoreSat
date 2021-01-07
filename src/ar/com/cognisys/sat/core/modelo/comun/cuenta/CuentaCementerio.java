package ar.com.cognisys.sat.core.modelo.comun.cuenta;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public class CuentaCementerio extends Cuenta {

private static final long serialVersionUID = 1284428554199537894L;
	
	public static String tipoCuenta = "CEMENTERIO";
	private boolean baja;
	private Date fechaAlta;
	private Date fechaRenovacion;
	private Integer numeroSeccion;
	private String subSeccion;
	private String tablon;
	private String subTablon;
	private String letraTablon;
	private String lote;
	private String subLote;
	private String letraLote;
	private Integer numeroNomenclador;
	private boolean deudaEspecial;

	@Override
	public String getDatoCuenta() {
		return this.getNumero().toString();
	}
	
	@Override
	public boolean correspondePPC() {
		return true;
	}

	@Override
	public boolean correspondeDDJJ() {
		return false;
	}
	
	@Override
	public boolean esCorrecto(String validacion) {
		if ( validacion == null )
			return false;
		
		return this.numeroNomenclador.equals( this.convertirValidacion( validacion ) );
	}
	
	private Integer convertirValidacion(String validacion) {
		
		try {
			return Integer.valueOf( validacion );
		} catch ( Exception e ) {
			return null;
		}
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
	
	@Override
	public TiposCuentas getTipoCuenta() {
		return TiposCuentas.CEMENTERIO;
	}

	@Override
	public Integer getSistema() {
		return 9;
	}

	@Override
	public int compareTo(Cuenta o) {
		return this.getNumero().compareTo(o.getNumero());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (baja ? 1231 : 1237);
		result = prime * result + (deudaEspecial ? 1231 : 1237);
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaRenovacion == null) ? 0 : fechaRenovacion.hashCode());
		result = prime * result + ((letraLote == null) ? 0 : letraLote.hashCode());
		result = prime * result + ((letraTablon == null) ? 0 : letraTablon.hashCode());
		result = prime * result + ((lote == null) ? 0 : lote.hashCode());
		result = prime * result + ((numeroNomenclador == null) ? 0 : numeroNomenclador.hashCode());
		result = prime * result + ((numeroSeccion == null) ? 0 : numeroSeccion.hashCode());
		result = prime * result + ((subLote == null) ? 0 : subLote.hashCode());
		result = prime * result + ((subSeccion == null) ? 0 : subSeccion.hashCode());
		result = prime * result + ((subTablon == null) ? 0 : subTablon.hashCode());
		result = prime * result + ((tablon == null) ? 0 : tablon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaCementerio other = (CuentaCementerio) obj;
		if (baja != other.baja)
			return false;
		if (deudaEspecial != other.deudaEspecial)
			return false;
		if (fechaAlta == null) {
			if (other.fechaAlta != null)
				return false;
		} else if (!fechaAlta.equals(other.fechaAlta))
			return false;
		if (fechaRenovacion == null) {
			if (other.fechaRenovacion != null)
				return false;
		} else if (!fechaRenovacion.equals(other.fechaRenovacion))
			return false;
		if (letraLote == null) {
			if (other.letraLote != null)
				return false;
		} else if (!letraLote.equals(other.letraLote))
			return false;
		if (letraTablon == null) {
			if (other.letraTablon != null)
				return false;
		} else if (!letraTablon.equals(other.letraTablon))
			return false;
		if (lote == null) {
			if (other.lote != null)
				return false;
		} else if (!lote.equals(other.lote))
			return false;
		if (numeroNomenclador == null) {
			if (other.numeroNomenclador != null)
				return false;
		} else if (!numeroNomenclador.equals(other.numeroNomenclador))
			return false;
		if (numeroSeccion == null) {
			if (other.numeroSeccion != null)
				return false;
		} else if (!numeroSeccion.equals(other.numeroSeccion))
			return false;
		if (subLote == null) {
			if (other.subLote != null)
				return false;
		} else if (!subLote.equals(other.subLote))
			return false;
		if (subSeccion == null) {
			if (other.subSeccion != null)
				return false;
		} else if (!subSeccion.equals(other.subSeccion))
			return false;
		if (subTablon == null) {
			if (other.subTablon != null)
				return false;
		} else if (!subTablon.equals(other.subTablon))
			return false;
		if (tablon == null) {
			if (other.tablon != null)
				return false;
		} else if (!tablon.equals(other.tablon))
			return false;
		return true;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaRenovacion() {
		return fechaRenovacion;
	}

	public void setFechaRenovacion(Date fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}

	public Integer getNumeroSeccion() {
		return numeroSeccion;
	}

	public void setNumeroSeccion(Integer numeroSeccion) {
		this.numeroSeccion = numeroSeccion;
	}

	public String getSubSeccion() {
		return subSeccion;
	}

	public void setSubSeccion(String subSeccion) {
		this.subSeccion = subSeccion;
	}

	public String getTablon() {
		return tablon;
	}

	public void setTablon(String tablon) {
		this.tablon = tablon;
	}

	public String getSubTablon() {
		return subTablon;
	}

	public void setSubTablon(String subTablon) {
		this.subTablon = subTablon;
	}

	public String getLetraTablon() {
		return letraTablon;
	}

	public void setLetraTablon(String letraTablon) {
		this.letraTablon = letraTablon;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getSubLote() {
		return subLote;
	}

	public void setSubLote(String subLote) {
		this.subLote = subLote;
	}

	public String getLetraLote() {
		return letraLote;
	}

	public void setLetraLote(String letraLote) {
		this.letraLote = letraLote;
	}

	public Integer getNumeroNomenclador() {
		return numeroNomenclador;
	}

	public void setNumeroNomenclador(Integer numeroNomenclador) {
		this.numeroNomenclador = numeroNomenclador;
	}

	public boolean isDeudaEspecial() {
		return deudaEspecial;
	}

	public void setDeudaEspecial(boolean deudaEspecial) {
		this.deudaEspecial = deudaEspecial;
	}

}