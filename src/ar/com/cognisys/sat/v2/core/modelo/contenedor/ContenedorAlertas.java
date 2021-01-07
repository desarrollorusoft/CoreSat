package ar.com.cognisys.sat.v2.core.modelo.contenedor;

import java.util.List;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.modelo.bo.IAlerta;
import ar.com.cognisys.sat.v2.core.modelo.contenedor.loader.ContenedorAlertasLoader;

public class ContenedorAlertas {

	private static ContenedorAlertas instancia;
	private Thread t;
	private List<IAlerta> lista;
	
	public static void iniciarInstancia() throws ExcepcionControladaError {
		if ( instancia == null )
			generarInstancia();
	}
	
	public static void reiniciarInstancia() throws ExcepcionControladaError {
		instancia = null;
		
		iniciarInstancia();
	}

	private static void generarInstancia() throws ExcepcionControladaError {
		synchronized ( ContenedorAlertas.class ) {
			if ( instancia == null )
				instancia = new ContenedorAlertas();
		}
	}

	private ContenedorAlertas() throws ExcepcionControladaError {
		t = new Thread( new ContenedorAlertasLoader( this ) );
		t.start();
	}

	public static ContenedorAlertas getInstancia() {
		esperarHilo();
		
		return instancia;
	}

	private static void esperarHilo() {
		if ( instancia.t.isAlive() ) {
			try {
				instancia.t.join();
			} catch ( InterruptedException e ) {
				// TODO [RODRI] Loguear
			}
		}
	}

	public List<IAlerta> getLista() {
		return lista;
	}

	public void setLista(List<IAlerta> lista) {
		this.lista = lista;
	}
	
}
