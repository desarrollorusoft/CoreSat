package ar.com.cognisys.sat.core.contenedor;

import ar.com.cognisys.sat.core.administrador.AdministradorBajaUsuario;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.ActividadComercial;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.MotivoBajaUsuario;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContenedorMotivosBaja {

	private static ContenedorMotivosBaja instancia;
	private List<MotivoBajaUsuario> motivos;
	
	public void cargar() throws ExcepcionControladaError {
		this.setMotivos( AdministradorBajaUsuario.recuperarTodos() );
	}

	public List<MotivoBajaUsuario> getMotivos() {
		return motivos;
	}

	private void setMotivos(List<MotivoBajaUsuario> motivos) {
		this.motivos = motivos;
	}

	public static ContenedorMotivosBaja getInstancia() {
		if (instancia == null)
			instancia = new ContenedorMotivosBaja();
		return instancia;
	}
}