package ar.com.cognisys.sat.core.modelo.comun;

public class GeneradorClaves {
		 
	private static String NUMEROS = "0123456789";
 
	private static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 
	private static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

	public static String getPinNumber() {
		return getPassword(NUMEROS, 4);
	}
 
	public static String getClave(int length) {
		return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}
 
	private static String getPassword(String key, int length) {
		
		String pswd = "";
 
		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int)(Math.random() * key.length())));
		}
 
		return pswd;
	}
}