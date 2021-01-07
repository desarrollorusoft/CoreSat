package ar.com.cognisys.sat.core.adds;

import ar.com.cognisys.generics.logger.LoggerAdministrator;
import ar.com.cognisys.generics.logger.excepcion.FileAppenderException;
import org.apache.log4j.Logger;

public class MailLogger {

    private static final String KEY_LOGGER = "SAT_MAIL";

    // @formatter:off
    private MailLogger() { }
    // @formatter:on

    public static Logger getLogger() {
        return new LoggerAdministrator().getLogger( KEY_LOGGER );
    }

    public static void initLogger(String ruta) throws FileAppenderException {
        new LoggerAdministrator().createFileLogger( KEY_LOGGER, ruta );
    }
}