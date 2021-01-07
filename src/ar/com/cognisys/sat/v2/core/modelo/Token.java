package ar.com.cognisys.sat.v2.core.modelo;

import java.util.Date;

public class Token {

	private String nombreUsuario;
	private Date ultimoAcceso;

	public Token(String nombreUsuario, Date ultimoAcceso) {
		this.nombreUsuario = nombreUsuario;
		this.ultimoAcceso = ultimoAcceso;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}

}