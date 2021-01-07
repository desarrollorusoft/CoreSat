package ar.com.cognisys.sat.core.modelo.comun.cuenta;

import org.apache.commons.lang.StringUtils;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.Catastro;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

public class CuentaABL extends Cuenta {

	private static final long serialVersionUID = -7409024696696556461L;
	public static String tipoCuenta = "ABL";
	
	private String verificador;
	private String numeroCirculacion;
	private String seccion;
	private String partida;
	private String valuacion;
	private String oficio;
	private String categoria;
	private String frente;
	private String superficie;
	private boolean eximido;
	private String calle;
	private String altura;
	private String piso;
	private String departamento;
	private String codigoPostal;
	private boolean baja;
	private String tipoDocumento;
	private String numeroDocumento;
	private String apellido;
	private String nombre;
	private String telefono;
	private String fax;
	private Catastro catastro;

	@Override
	public boolean esCorrecto(String validacion) {
		return this.formatearDV(this.verificador).equals( this.formatearDV(validacion) );
	}
	
	private String formatearDV(String dv) {
		return StringUtils.leftPad(dv, 2, "0");
	}
	
	public static void main(String[] args) {
		System.out.println();
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
	
	public String getDomicilio() {
		if ( this.calle == null )
			return null;
		
		String aux = this.calle;
		
		aux = this.altura == null ? aux : aux + " " + this.altura;
		aux = this.piso == null ? aux : aux + " " + this.piso;
		aux = this.departamento == null ? aux : aux + " " + this.departamento;
		
		return aux;
	}
	
	@Override
	public TiposCuentas getTipoCuenta() {
		return TiposCuentas.ABL;
	}

	@Override
	public Integer getSistema() {
		return 2;
	}
	
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
	public int compareTo(Cuenta o) {
		return this.getNumero().compareTo(o.getNumero());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((altura == null) ? 0 : altura.hashCode());
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + (baja ? 1231 : 1237);
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + (eximido ? 1231 : 1237);
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((frente == null) ? 0 : frente.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numeroCirculacion == null) ? 0 : numeroCirculacion.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((oficio == null) ? 0 : oficio.hashCode());
		result = prime * result + ((partida == null) ? 0 : partida.hashCode());
		result = prime * result + ((piso == null) ? 0 : piso.hashCode());
		result = prime * result + ((seccion == null) ? 0 : seccion.hashCode());
		result = prime * result + ((superficie == null) ? 0 : superficie.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		result = prime * result + ((valuacion == null) ? 0 : valuacion.hashCode());
		result = prime * result + ((verificador == null) ? 0 : verificador.hashCode());
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
		CuentaABL other = (CuentaABL) obj;
		if (altura == null) {
			if (other.altura != null)
				return false;
		} else if (!altura.equals(other.altura))
			return false;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (baja != other.baja)
			return false;
		if (calle == null) {
			if (other.calle != null)
				return false;
		} else if (!calle.equals(other.calle))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		if (eximido != other.eximido)
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (frente == null) {
			if (other.frente != null)
				return false;
		} else if (!frente.equals(other.frente))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeroCirculacion == null) {
			if (other.numeroCirculacion != null)
				return false;
		} else if (!numeroCirculacion.equals(other.numeroCirculacion))
			return false;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (oficio == null) {
			if (other.oficio != null)
				return false;
		} else if (!oficio.equals(other.oficio))
			return false;
		if (partida == null) {
			if (other.partida != null)
				return false;
		} else if (!partida.equals(other.partida))
			return false;
		if (piso == null) {
			if (other.piso != null)
				return false;
		} else if (!piso.equals(other.piso))
			return false;
		if (seccion == null) {
			if (other.seccion != null)
				return false;
		} else if (!seccion.equals(other.seccion))
			return false;
		if (superficie == null) {
			if (other.superficie != null)
				return false;
		} else if (!superficie.equals(other.superficie))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		if (valuacion == null) {
			if (other.valuacion != null)
				return false;
		} else if (!valuacion.equals(other.valuacion))
			return false;
		if (verificador == null) {
			if (other.verificador != null)
				return false;
		} else if (!verificador.equals(other.verificador))
			return false;
		return true;
	}

	public String getVerificador() {
		return verificador;
	}

	public void setVerificador(String verificador) {
		this.verificador = verificador;
	}

	public String getNumeroCirculacion() {
		return numeroCirculacion;
	}

	public void setNumeroCirculacion(String numeroCirculacion) {
		this.numeroCirculacion = numeroCirculacion;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getPartida() {
		return partida;
	}

	public void setPartida(String partida) {
		this.partida = partida;
	}

	public String getValuacion() {
		return valuacion;
	}

	public void setValuacion(String valuacion) {
		this.valuacion = valuacion;
	}

	public String getOficio() {
		return oficio;
	}

	public void setOficio(String oficio) {
		this.oficio = oficio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFrente() {
		return frente;
	}

	public void setFrente(String frente) {
		this.frente = frente;
	}

	public String getSuperficie() {
		return superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public boolean isEximido() {
		return eximido;
	}

	public void setEximido(boolean eximido) {
		this.eximido = eximido;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Catastro getCatastro() {
		return catastro;
	}

	public void setCatastro(Catastro catastro) {
		this.catastro = catastro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setTipoCuenta(String tipoCuenta) {
		CuentaABL.tipoCuenta = tipoCuenta;
	}

	public boolean tieneDV(String digitoVerificador) {
		if (digitoVerificador == null)
			return false;
		else if (StringUtils.isNumeric(digitoVerificador) )
			return (new Integer(digitoVerificador)).equals( new Integer(this.getVerificador()) );
		else
			return this.getVerificador().equals( digitoVerificador );
	}
}