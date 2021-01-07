package ar.com.cognisys.sat.v2.core.modelo.bo;

public interface IConsultaArchivo {

	Integer getNumeroConsulta();
	
	String getDirectorio();

	String getTipo();
		
	String getNombre();
	
	String getNombreGenerado();
	
	String getRuta();
	
	byte[] getData();
	
}
