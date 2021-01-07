package ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta;

import java.io.Serializable;

import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;

public interface IConsultaDetalleView extends Serializable {
	
	String getNumero();
	
	String getNombre();
	
	String getApellido();
	
	String getCuit();
	
	String getTelefono();
	
	String getCorreo();

	IRutaNombreView[] getArchivos();
	
	void setArchivos(IRutaNombreView[] archivos);
	
	IMensajeView[] getMensajes();

	void setMensajes(IMensajeView[] mensajes);
	
}
