package ar.com.cognisys.sat.v2.vista.modelo.interfaz.consulta;

public interface IConsultaCompletaView extends IConsultaDetalleView, IConsultaCabeceraView {

	String getTelefono2();
	
	String getCaracter();
	
	void setNumero(String numero);
	
}
