package ar.com.cognisys.sat.v2.core.controlador;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ar.com.cognisys.sat.v2.core.modelo.Token;

public final class Authenticator {

	private static Authenticator instance;
	private Map<String, Token> mapa;

	public String generarToken(String nombreUsuario) {

		String authToken = UUID.randomUUID().toString();

		this.mapa.put( authToken, new Token( nombreUsuario, new Date() ) );

		return authToken;
	}

	public boolean esRequestValido(String authToken) {
		return this.mapa.containsKey( authToken );
	}

	public boolean esTokenDesactualizado(String authToken) {
		Token unToken = this.mapa.get( authToken );

		return this.obtenerDiferenciaMinutos( unToken ) > 30;
	}

	public String renovarToken(String authToken) {

		Token tokenDesactualizado = this.mapa.get( authToken );

		this.mapa.remove( authToken );

		return this.generarToken( tokenDesactualizado.getNombreUsuario() );
	}

	public void limpiarToken() {
		Iterator<Map.Entry<String, Token>> it = this.mapa.entrySet().iterator();
		while ( it.hasNext() ) {
			Map.Entry<String, Token> entry = it.next();

			if ( this.obtenerDiferenciaMinutos( entry.getValue() ) > 60 )
				it.remove();
		}
	}

	public void limpiarToken(String authToken) {
		if ( this.mapa.containsKey( authToken ) )
			this.mapa.remove( authToken );
	}

	private long obtenerDiferenciaMinutos(Token unToken) {
		long diffInMillies = new Date().getTime() - unToken.getUltimoAcceso().getTime();
		return TimeUnit.MINUTES.convert( diffInMillies, TimeUnit.MILLISECONDS );
	}
	
	public static Authenticator getInstance() {
		if ( instance == null )
			createInstance();

		return instance;
	}

	private static void createInstance() {
		synchronized ( Authenticator.class ) {
			if ( instance == null )
				instance = new Authenticator();
		}
	}

	private Authenticator() {
		this.mapa = new HashMap<String, Token>();
	}

}
