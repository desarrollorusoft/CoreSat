package ar.com.cognisys.sat.core.modelo.comun;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String format(Date date, String template) {
        return new SimpleDateFormat( template ).format( date );
    }

    public static String formatInformix(Date fechaPago) {
        return "TO_DATE('"+new SimpleDateFormat("yyyy-MM-dd").format(fechaPago) +"','%Y-%m-%d')";
    }
}