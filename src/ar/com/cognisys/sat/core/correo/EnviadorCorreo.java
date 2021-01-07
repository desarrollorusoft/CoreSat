package ar.com.cognisys.sat.core.correo;

public class EnviadorCorreo extends EnviadorCorreoAbstract {

	private static EnviadorCorreo instance;

	private EnviadorCorreo() {
		super( "correo.properties" );
// 		super.debbug(true);
	}
	
	public static EnviadorCorreo getInstance() {
		if (EnviadorCorreo.instance == null)
			EnviadorCorreo.instance = new EnviadorCorreo();
		return instance;
	}
}