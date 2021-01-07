package ar.com.cognisys.sat.core.contenedor;

import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorRegimenSimplificado;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoRechazoRS;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.ConflictosDDJJRS;

public class ContenedorTipoRechazoRS {

	public static final String ACTIVIDAD = "ACTIVIDAD_PRINCIPAL";
	public static final String PYP_OTROS = "CARTELERIA_OTROS";
	public static final String FACTURACION_PADRON = "MONTO_EXCESO";
	public static final String PYP_METROS_LETREROS = "CARTELERIA_LETREROS_EXCESO";
	public static final String PYP_METROS_AVISOS = "CARTELERIA_AVISOS_EXCESO";
	public static final String OEP_METROS_TOLDO = "OEP_TOLDOS_EXCESO";
	public static final String OEP_UNIDADES_POSTE = "OEP_POSTES_EXCESO";
	public static final String SV_UNIDADES = "SV_EXCESO";
	
	private static ContenedorTipoRechazoRS instancia;
	private List<TipoRechazoRS> listaTipos;
	
	public void cargar() throws ExcepcionControladaError {
		this.setListaTipos( AdministradorRegimenSimplificado.obtenerTiposRechazos() );
	}
	
	public TipoRechazoRS buscar(String nombre) {
		
		for (TipoRechazoRS t : this.getListaTipos())
			if (t.sos(nombre))
				return t;
		
		return null;
	}
	
	public TipoRechazoRS buscar(Integer codigo) {
		
		for (TipoRechazoRS t : this.getListaTipos())
			if (t.sos(codigo))
				return t;
		
		return null;
	}
	
	public Integer obtenerCodigoError(ConflictosDDJJRS[] errores) {
		int res = 1;

		for (ConflictosDDJJRS tr : errores)
			res *= tr.getCodigo();
		
		TipoRechazoRS tr = this.buscar(res);
		
		if (tr != null)
			return tr.getCodigo();
		else
			return -1;
	}
	
	public Integer obtenerCodigoError(List<TipoRechazoRS> lista) {
		int res = 1;
		
		for (TipoRechazoRS tr : lista)
			res *= tr.getCodigo();
		
		TipoRechazoRS tr = this.buscar(res);
		
		if (tr != null)
			return tr.getCodigo();
		else
			return -1;
	}
	
	public List<TipoRechazoRS> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<TipoRechazoRS> listaTipos) {
		this.listaTipos = listaTipos;
	}

	public static ContenedorTipoRechazoRS getInstancia() {
		
		if (instancia == null)
			instancia = new ContenedorTipoRechazoRS();
			
		return instancia;
	}
}