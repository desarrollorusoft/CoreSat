package ar.com.cognisys.sat.core.logger;

import org.apache.log4j.Logger;

import ar.com.cognisys.generics.logger.LoggerAdministrator;
import ar.com.cognisys.generics.logger.excepcion.FileAppenderException;

public class CoreSATLogger {

	private static final String KEY_LOGGER = "CoreSAT";

	// @formatter:off
	private CoreSATLogger() { }
	// @formatter:on

	public static Logger getLogger() {
		return new LoggerAdministrator().getLogger( KEY_LOGGER );
	}

	public static void initLogger(String ruta) throws FileAppenderException {
		new LoggerAdministrator().createFileLogger( KEY_LOGGER, ruta );
	}
}
