package ar.com.cognisys.sat.core.modelo.comun.rs.validador;

import ar.com.cognisys.sat.core.contenedor.ContenedorTipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJOEP;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoOEP;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2019;

public class ValidadorDDJJRS2019 extends ValidadorDDJJRS {

	public ValidadorDDJJRS2019(VersionPadronRS version) {
		super(version);
	}

	@Override
	protected void validacionParticular() {
		VersionPadronRS2019 v = (VersionPadronRS2019) this.getVersion();
		
		/* Validar si cumple con las actividades permitidas */
		if (!v.getActividades().getActividadPrincipal().isPermitido())
			super.agregarRechazo( ContenedorTipoRechazoRS.ACTIVIDAD );
		
		for (ActividadComercial a : v.getActividades().getOtrasActividades())
			if (!a.isPermitido())
				super.agregarRechazo( ContenedorTipoRechazoRS.ACTIVIDAD );
		
		/* Validar si cumple con los tipos de carteles permitidos y su metraje*/
		float metrosLetreros = 0f;
		float metrosAvisos = 0f;
		for (DDJJCarteleria c : v.getListaCarteleria()) {
			if ( c.esOtros() )
				super.agregarRechazo( ContenedorTipoRechazoRS.PYP_OTROS );
			
			if (c.getTipo().getCodigo() <= 5)
				metrosLetreros += c.getMetros();
			else
				metrosAvisos += c.getMetros();
		}
		
		if (metrosLetreros > 0f && this.getTope().excedeMetrosPyPLetreros( metrosLetreros ))
			super.agregarRechazo( ContenedorTipoRechazoRS.PYP_METROS_LETREROS );
		
		if (metrosAvisos > 0f && this.getTope().excedeMetrosPyPAvisos( metrosAvisos ))
			super.agregarRechazo( ContenedorTipoRechazoRS.PYP_METROS_AVISOS );
		
		/* Validar Excesos de OEP */
		float metrosToldo = 0f;
		float unidadesPoste = 0f; 
		for (DDJJOEP oep : v.getListaOEP()) {
			if (oep.sos( TipoOEP.TOLDO ))
				metrosToldo += oep.getValor();
			
			else if (oep.sos( TipoOEP.POSTE ))
				unidadesPoste += oep.getValor();
		}
		
		if (metrosToldo > 0f && this.getTope().excedeMetrosToldo( metrosToldo ))
			super.agregarRechazo( ContenedorTipoRechazoRS.OEP_METROS_TOLDO );
		
		if (unidadesPoste > 0f && this.getTope().excedeUnidadesPoste( unidadesPoste ))
			super.agregarRechazo( ContenedorTipoRechazoRS.OEP_UNIDADES_POSTE );
		
		/* Se valida Servicios Varios */
		int unidadesSV = v.getServiciosVarios().getUnidadesTotales();
		if (unidadesSV > 0 && this.getTope().excedeUnidadesSV( unidadesSV ))
			super.agregarRechazo( ContenedorTipoRechazoRS.SV_UNIDADES );
	}	
}