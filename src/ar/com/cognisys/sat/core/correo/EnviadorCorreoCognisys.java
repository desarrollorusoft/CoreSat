package ar.com.cognisys.sat.core.correo;

public class EnviadorCorreoCognisys extends EnviadorCorreoAbstract {

	private static EnviadorCorreoCognisys instance;
	
	private EnviadorCorreoCognisys() {
		super( "correo_cognisys.properties" );
	}

	public static EnviadorCorreoCognisys getInstance() {
		if (EnviadorCorreoCognisys.instance == null)
			EnviadorCorreoCognisys.instance = new EnviadorCorreoCognisys();
		return instance;
	}

	public static void setInstance(EnviadorCorreoCognisys instance) {
		EnviadorCorreoCognisys.instance = instance;
	}
}