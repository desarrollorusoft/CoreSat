package ar.com.cognisys.sat.core.modelo.comun.rs.validador;

import java.util.HashMap;
import java.util.Map;

import ar.com.cognisys.sat.core.contenedor.ContenedorTipoRechazoRS;
import ar.com.cognisys.sat.core.contenedor.ContenedorTopesRS;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.TopeRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.ConflictosDDJJRS;

public abstract class ValidadorDDJJRS {

	private VersionPadronRS version;
	private Map<Integer, ConflictosDDJJRS> mapa;
	private TopeRS tope;
	
	public ValidadorDDJJRS(VersionPadronRS version) {
		this.setVersion(version);
		mapa = new HashMap<Integer, ConflictosDDJJRS>();
	}
	
	public void validar() {
		this.setTope( ContenedorTopesRS.getInstancia().recuperar( this.getVersion().getAno() ) );
		this.validacionGeneral();
		this.validacionParticular();
	}
	
	private void validacionGeneral() {
		/* Se valida que la facturacion del padron no esteexcedida */
		if ( this.getTope().excedeFacturacionPadron( this.getVersion().getFacturacion() ) )
			this.agregarRechazo( ContenedorTipoRechazoRS.FACTURACION_PADRON );
	}

	protected void agregarRechazo(String claveRechazo) {
		TipoRechazoRS tr = ContenedorTipoRechazoRS.getInstancia().buscar( claveRechazo );
		this.getMapa().put(tr.getCodigo(), new ConflictosDDJJRS(tr.getCodigo(), tr.getDescripcion()));
	}

	protected abstract void validacionParticular();

	public ConflictosDDJJRS[] obtenerConflictos() {
		return this.getMapa().values().toArray( new ConflictosDDJJRS[] {} );
	}
	
	protected VersionPadronRS getVersion() {
		return version;
	}

	protected void setVersion(VersionPadronRS version) {
		this.version = version;
	}

	protected Map<Integer, ConflictosDDJJRS> getMapa() {
		return mapa;
	}

	protected void setMapa(Map<Integer, ConflictosDDJJRS> mapa) {
		this.mapa = mapa;
	}

	protected TopeRS getTope() {
		return tope;
	}

	protected void setTope(TopeRS tope) {
		this.tope = tope;
	}
}