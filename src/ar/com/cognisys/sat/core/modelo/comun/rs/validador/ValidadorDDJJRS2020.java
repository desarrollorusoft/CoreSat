package ar.com.cognisys.sat.core.modelo.comun.rs.validador;

import ar.com.cognisys.sat.core.contenedor.ContenedorTipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJCarteleria;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.DDJJOEP;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoOEP;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS;
import ar.com.cognisys.sat.core.modelo.comun.rs.versiones.VersionPadronRS2020;

public class ValidadorDDJJRS2020 extends ValidadorDDJJRS {

	public ValidadorDDJJRS2020(VersionPadronRS version) {
		super(version);
	}

	@Override
	protected void validacionParticular() {
		VersionPadronRS2020 v = (VersionPadronRS2020) this.getVersion();
		
		/* Validar si cumple con las actividades permitidas */
		if (!v.getActividades().getActividadPrincipal().isPermitido())
			super.agregarRechazo( ContenedorTipoRechazoRS.ACTIVIDAD );
		
		for (ActividadComercial a : v.getActividades().getOtrasActividades())
			if (!a.isPermitido())
				super.agregarRechazo( ContenedorTipoRechazoRS.ACTIVIDAD );
		
		/* Validar si cumple con los tipos de carteles permitidos */
		for (DDJJCarteleria c : v.getListaCarteleria())
			if ( c.esOtros() )
				super.agregarRechazo( ContenedorTipoRechazoRS.PYP_OTROS );
		
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