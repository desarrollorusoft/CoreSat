package ar.com.cognisys.sat.core.modelo.generador.pagoOnline;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class Token {

	public static String obtenerToken(String url) throws ExcepcionControladaError {
		
		URLConnection php;
		
		try {
			// Envio los certificados
			doTrustToCertificates();
			
			php = (new URL(url)).openConnection();

			BufferedReader entrada = new BufferedReader(new InputStreamReader(php.getInputStream()));
			String linea = null;
			String respuesta = new String();
			
			while ((linea = entrada.readLine()) != null) {
				respuesta = respuesta + linea;
			}

			ArrayList<String> lista = new ArrayList<String>();
			Integer fromIndex = 0;
			String tag = "'";
			
			while (respuesta.indexOf("'", fromIndex) > 0) {
				Integer inicio = respuesta.indexOf(tag, fromIndex) + tag.length();
				Integer fin = respuesta.indexOf(tag, inicio);
				lista.add(respuesta.substring(inicio, fin));
				fromIndex = fin + tag.length();
			}

			return lista.get(1);
		} catch (Exception e) {
			return "";
			// TODO Mejorar el manejo de error!!
		}
	}
	
	public static void doTrustToCertificates() throws Exception {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
					return;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
					return;
				}
			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					
					if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
						System.out.println("Warning: URL host '" + urlHostName
										 + "' is different to SSLSession host '"
										 + session.getPeerHost() + "'.");
					}
					
					return true;
				}
			};
			
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			
		} catch (Exception e) {
			throw e;
		}
	}
}