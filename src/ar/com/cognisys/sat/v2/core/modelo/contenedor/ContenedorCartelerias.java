package ar.com.cognisys.sat.v2.core.modelo.contenedor;

import java.util.List;
import java.util.Map;

import ar.com.cognisys.sat.core.logger.CoreSATLogger;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.ddjj.TipoCartel;
import ar.com.cognisys.sat.v2.core.modelo.contenedor.loader.ContenedorCarteleriasLoader;

public class ContenedorCartelerias {

	private static ContenedorCartelerias instancia;
	private Thread t;
	private Map<Integer, List<TipoCartel>> mapa;
	
	// TODO MODIFICAR
	public TipoCartel get(Integer ano, Integer i) {
		for ( TipoCartel tipoCartel : this.getMapa().get( ano ) )
			if( tipoCartel.getCodigo().equals( i ) )
				return tipoCartel;
		
		return null;
	}
	
	public List<TipoCartel> getLista(int ano) {
		return this.getMapa().get( ano );
	}
	
	public static void iniciarInstancia() {
		if ( instancia == null )
			generarInstancia();
	}
	
	public static void reiniciarInstancia() {
		instancia = null;
		iniciarInstancia();
	}

	private static void generarInstancia() {
		synchronized ( ContenedorCartelerias.class ) {
			if ( instancia == null )
				instancia = new ContenedorCartelerias();
		}
	}

	private ContenedorCartelerias()  {
		t = new Thread( new ContenedorCarteleriasLoader( this ) );
		t.start();
	}

	public static ContenedorCartelerias getInstancia() {
		esperarHilo();
		return instancia;
	}

	private static void esperarHilo() {
		if ( instancia.t.isAlive() ) {
			try {
				instancia.t.join();
			} catch ( InterruptedException e ) {
				CoreSATLogger.getLogger().error( "Hubo una interrupción en la inicialización del contenedor de cartelerías", e );
				
				instancia.t.interrupt();
			}
		}
	}

	public Map<Integer, List<TipoCartel>> getMapa() {
		return mapa;
	}

	public void setMapa(Map<Integer, List<TipoCartel>> mapa) {
		this.mapa = mapa;
	}
}