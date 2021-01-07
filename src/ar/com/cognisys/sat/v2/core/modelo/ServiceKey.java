package ar.com.cognisys.sat.v2.core.modelo;

public class ServiceKey {
	
	private static final String SERIAL = "f80ebc87-ad5c-4b29-9366-5359768df5a1";

	public static boolean sos(String serviceKey) {
		return SERIAL.equals( serviceKey );
	}

}