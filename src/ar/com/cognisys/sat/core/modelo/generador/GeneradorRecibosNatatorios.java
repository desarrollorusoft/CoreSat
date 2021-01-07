package ar.com.cognisys.sat.core.modelo.generador;

import java.util.Date;

import ar.com.cognisys.sat.core.modelo.comun.natatorios.CuentaPileta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public abstract class GeneradorRecibosNatatorios extends GeneradorRecibos {
	
	private CuentaPileta usuario;
	
	public GeneradorRecibosNatatorios(CuentaPileta usuario, String numeroComprobante, Date fechaVencimiento, Float monto) {
		super(numeroComprobante, fechaVencimiento, monto);
		this.setUsuario(usuario);
	}
	
	public abstract void generarRecibo() throws ExcepcionControladaError;
	
	@Override
	protected String generarCodigoBarras() {
		
		return CodigoDeBarras.generarCodigo48CaracteresCompletoNatatorio("110",
																		 this.getUsuario().getComprobante().toString().replace("-",""),
																		 this.getUsuario().getVencimiento(),
																		 String.valueOf(this.getUsuario().getDeuda() ),
																		 "00",
																		 "00000000",
																		 "910", //valor municipal
																		 new Date());
	}
	
	@Override
	protected String generarCodigoBarras(String tasa) {
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		GeneradorRecibosNatatorios other = (GeneradorRecibosNatatorios) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public CuentaPileta getUsuario() {
		return usuario;
	}

	public void setUsuario(CuentaPileta usuario) {
		this.usuario = usuario;
	}
}