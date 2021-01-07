package ar.com.cognisys.sat.core.modelo.comun;

import java.security.MessageDigest;

public class SegurizadorClaves {

	public static String codificar(String clave) {
		try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(clave.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	public static void main(String[] args) {
		System.out.println( SegurizadorClaves.codificar("mvl") );
	}
}