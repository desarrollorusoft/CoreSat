package ar.com.cognisys.sat.v2.core.controlador.administrador;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.modelo.adapter.AlertaGeneralRecoveredAdapter;
import ar.com.cognisys.sat.v2.core.modelo.adapter.AlertaParticularRecoveredAdapter;
import ar.com.cognisys.sat.v2.core.modelo.bo.IAlerta;
import ar.com.cognisys.sat.v2.core.modelo.contenedor.ContenedorAlertas;
import ar.com.cognisys.sat.v2.persistencia.controlador.facade.FacadeAlertasDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.AlertaUsuarioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.creator.AlertaUsuarioDTOCreator;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.alerta.interfaz.IAlertaGeneralDTO;

public class AdministradorAlertas {

	public List<IAlerta> recuperarAlertasGenerales() throws ExcepcionControladaError {

		List<IAlerta> lista = new ArrayList<IAlerta>();

		List<IAlertaGeneralDTO> listaDTO = new FacadeAlertasDTO().recuperar();
		for ( IAlertaGeneralDTO alertaDTO : listaDTO )
			lista.add( new AlertaGeneralRecoveredAdapter( alertaDTO ) );

		return lista;
	}
	
	public List<IAlerta> recuperar() {
		return ContenedorAlertas.getInstancia().getLista();
	}

	public List<IAlerta> recuperar(String cuit) throws ExcepcionControladaError {
		
		List<IAlerta> lista = new ArrayList<IAlerta>();
		
		List<AlertaUsuarioDTO> listaAlertas = new FacadeAlertasDTO().recuperar( cuit );
		for (AlertaUsuarioDTO alertaDTO : listaAlertas)
			lista.add( new AlertaParticularRecoveredAdapter( alertaDTO ) );
			
		return lista;
	}

	public void crear(String cuit, IAlerta alerta) throws ExcepcionControladaError {
		AlertaUsuarioDTOCreator creator = new AlertaUsuarioDTOCreator();
		
		AlertaUsuarioDTO alertaDTO = creator
										.titulo( alerta.getTitulo() )
										.descripcion( alerta.getDescripcion() )
										.fecha( alerta.getFechaInicio() )
										.cuit( cuit )
										.create();
		
		new FacadeAlertasDTO().agregar( alertaDTO );
	}

	public void eliminar(String cuit, IAlerta alerta) throws ExcepcionControladaError {
		AlertaUsuarioDTO alertaDTO = this.crearAlertaUsuario( cuit, alerta );
		
		new FacadeAlertasDTO().borrar( alertaDTO );
	}

	private AlertaUsuarioDTO crearAlertaUsuario(String cuit, IAlerta alerta) {
		AlertaUsuarioDTOCreator creator = new AlertaUsuarioDTOCreator();
		
		return creator
				.titulo( alerta.getTitulo() )
				.descripcion( alerta.getDescripcion() )
				.fecha( alerta.getFechaInicio() )
				.cuit( cuit )
				.create();
	}

}
