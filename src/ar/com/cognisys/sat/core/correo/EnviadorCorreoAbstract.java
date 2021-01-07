package ar.com.cognisys.sat.core.correo;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import org.apache.log4j.Level;;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import ar.com.cognisys.sat.core.adds.MailLogger;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeProblemasConBase;
import ar.com.cognisys.sat.core.correo.mensaje.MensajeRecibo;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnvioEmailException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;

public abstract class EnviadorCorreoAbstract {

	private Properties prop;
	private Properties propertiesFile;
	private Session sesion;
	private String host;
	private String port;
	private String auth;
	private String tls;
	private String user;
	private String pass;

	protected EnviadorCorreoAbstract(String properties) {
		setProp(System.getProperties());
		InputStream in = EnviadorCorreoAbstract.class.getResourceAsStream( properties );
		
		try {
			setPropertiesFile(new Properties());
			getPropertiesFile().load(in);
			in.close();

			this.setPort( (String) getPropertiesFile().get("mail.smtp.port") );
			this.setHost( (String) getPropertiesFile().get("mail.smtp.host") );
			this.setAuth( (String) getPropertiesFile().get("mail.smtp.auth") );
			this.setTls( (String) getPropertiesFile().get("mail.smtp.starttls.enable") );

			getProp().put("mail.smtp.port", this.getPort());
			getProp().put("mail.smtp.host", this.getHost());
			getProp().put("mail.smtp.auth", this.getAuth());
			getProp().put("mail.smtp.starttls.enable", this.getTls());

			this.setUser( (String) getPropertiesFile().get("mail.smtp.user") );
			this.setPass( (String) getPropertiesFile().get("mail.smtp.password") );

			setSesion(Session.getInstance(getProp(), new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(getUser(), getPass());
				}
			}));
			//this.getSesion().setDebugOut(new PrintStream( new LoggingOutputStream( MailLogger.getLogger(), Level.INFO ) ));
		} catch (IOException e) {
			MailLogger.getLogger().error("Fallo la carga de la configuracion", e);
		}
	}

	public void debbug(boolean debbugMode) {
		this.getSesion().setDebug( debbugMode );
	}

	public boolean debbugMode() {
		return this.getSesion() != null && this.getSesion().getDebug();
	}

	public void enviarMensajeReporte(MensajeProblemasConBase mensajeProblemasConBase) throws ExcepcionControladaAlerta {
		String sender = "";
		try {
			MimeMessage mail = new MimeMessage(getSesion());
			sender = (String) getPropertiesFile().get("mail.smtp.mail.sender");
			mail.setFrom(new InternetAddress( sender ));
			for (String destinatario : mensajeProblemasConBase.getDestinatariosReportes()) {
				mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			}
			mail.setSubject(mensajeProblemasConBase.getEncabezado(), "UTF-8");
			mail.setContent(mensajeProblemasConBase.getMensaje(),"text/html; charset=utf-8");

			Transport.send(mail);
		} catch (MessagingException e) {
			this.logError(e, sender);
			throw new ErrorEnvioEmailException(e);
		}
	}

	public void enviarMensajePublico(MensajeCorreo mensaje) throws ExcepcionControladaAlerta {
		String sender = "";
		try {
			MimeMessage mail = new MimeMessage(getSesion());
			sender = (String) getPropertiesFile().get("mail.smtp.mail.sender");
			mail.setFrom(new InternetAddress( sender ));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(mensaje.getDestinatario()));

			mail.setSubject(mensaje.getEncabezado(), "UTF-8");
			mail.setContent(mensaje.getMensaje(),"text/html; charset=utf-8");

			Transport.send(mail);
		} catch (MessagingException e) {
			this.logError(e, sender);
			throw new ErrorEnvioEmailException(e);
		}
	}
	
	public void enviarMensajeProveedorAdjunto(MensajeRecibo mensaje) throws Exception {
		String sender = "";
		try {
			MimeMessage mail = new MimeMessage(getSesion());
			sender = (String) getPropertiesFile().get("mail.smtp.mail.sender");
			mail.setFrom(new InternetAddress(sender));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(mensaje.getDestinatario()));
			mail.setSubject(mensaje.getEncabezado(), "UTF-8");

	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent( mensaje.getMensaje(), "text/html; charset=utf-8" );

	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	        messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(mensaje.getPathArchivo())));
	        messageBodyPart.setFileName(mensaje.getNombreArchivo());
	        multipart.addBodyPart(messageBodyPart);
	        mail.setContent(multipart);
			
			Transport.send(mail);
		} catch (MessagingException e) {
			this.logError(e, sender);
			throw new Exception(e.getMessage(), e);
		}
	}

	private void logError(MessagingException e, String sender) {
		MailLogger.getLogger().error("Fallo el envio de mail - Sender ["+sender+"] "+
				"Host ["+this.getSesion().getProperty("mail.smtp.host")+
				"] Port ["+this.getSesion().getProperty("mail.smtp.port")+
				"] Auth ["+this.getSesion().getProperty("mail.smtp.auth")+
				"] TLS ["+this.getSesion().getProperty("mail.smtp.starttls.enable")+
				"] User ["+this.getSesion().getProperty("mail.smtp.user")+
				"] Pass ["+this.getSesion().getProperty("mail.smtp.password")+"] Session ["+
				this.getSesion().toString()
				+"]", e);
	}

	private Properties getProp() {
		return prop;
	}

	private void setProp(Properties prop) {
		this.prop = prop;
	}

	private Session getSesion() {
		return sesion;
	}

	private void setSesion(Session sesion) {
		this.sesion = sesion;
	}

	private Properties getPropertiesFile() {
		return propertiesFile;
	}

	private void setPropertiesFile(Properties propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getTls() {
		return tls;
	}

	public void setTls(String tls) {
		this.tls = tls;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
