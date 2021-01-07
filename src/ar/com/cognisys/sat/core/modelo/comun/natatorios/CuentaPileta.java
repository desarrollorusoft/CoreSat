package ar.com.cognisys.sat.core.modelo.comun.natatorios;

import java.math.BigDecimal;
import java.util.Date;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.enums.TiposDocumento;

public class CuentaPileta extends Cuenta {

	private static final long serialVersionUID = -5870861874224977153L;
	private TiposDocumento tipoDocumento;
	private BigDecimal numeroDocumento;
	private String nombre;
	private String apellido;
	private String sede;
	private Integer nroSocio;         
	private Long comprobante;
	private Date vencimiento;
	private Integer id;

	@Override
	public TiposCuentas getTipoCuenta() { return TiposCuentas.PILETAS; }

	@Override
	public Integer getSistema() {
		return null;
	}

	@Override
	public String getDatoCuenta() {
		return this.getTipoDocumento().getNombre() + " " + this.getNumeroDocumento();
	}

	@Override
	public boolean correspondePPC() {
		return false;
	}

	@Override
	public boolean correspondeDDJJ() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((comprobante == null) ? 0 : comprobante.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nroSocio == null) ? 0 : nroSocio.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((sede == null) ? 0 : sede.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		result = prime * result + ((vencimiento == null) ? 0 : vencimiento.hashCode());
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
		CuentaPileta other = (CuentaPileta) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (comprobante == null) {
			if (other.comprobante != null)
				return false;
		} else if (!comprobante.equals(other.comprobante))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nroSocio == null) {
			if (other.nroSocio != null)
				return false;
		} else if (!nroSocio.equals(other.nroSocio))
			return false;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (sede == null) {
			if (other.sede != null)
				return false;
		} else if (!sede.equals(other.sede))
			return false;
		if (tipoDocumento != other.tipoDocumento)
			return false;
		if (vencimiento == null) {
			if (other.vencimiento != null)
				return false;
		} else if (!vencimiento.equals(other.vencimiento))
			return false;
		return true;
	}

	public TiposDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	
	public void setTipoDocumento(TiposDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public BigDecimal getNumeroDocumento() {
		return numeroDocumento;
	}
	
	public void setNumeroDocumento(BigDecimal numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getSede() {
		return sede;
	}
	
	public void setSede(String sede) {
		this.sede = sede;
	}

	public Integer getNroSocio() {
		return nroSocio;
	}

	public void setNroSocio(Integer nroSocio) {
		this.nroSocio = nroSocio;
	}

	public Long getComprobante() {
		return comprobante;
	}

	public void setComprobante(Long comprobante) {
		this.comprobante = comprobante;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String obtenerCodigo() {
		return null;
	}

	@Override
	public String obtenerTipo() {
		return this.getTipoCuenta().getNombre();
	}

	@Override
	public boolean esCorrecto(String validacion) {
		return false;
	}

	@Override
	public int compareTo(Cuenta o) {
		return 0;
	}
}