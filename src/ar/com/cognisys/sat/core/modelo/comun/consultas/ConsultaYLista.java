package ar.com.cognisys.sat.core.modelo.comun.consultas;

import java.io.Serializable;
import java.util.List;

public class ConsultaYLista implements Serializable	{

	private static final long serialVersionUID = 8051258462905325217L;
	
	private Consulta consulta;
	private List<Consulta> listaConsultas;
	
	public ConsultaYLista(Consulta c, List<Consulta> lc) {
		this.consulta = c;
		this.listaConsultas = lc;
	}
	public Consulta getConsulta() {
		return consulta;
	}
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	public List<Consulta> getListaConsultas() {
		return listaConsultas;
	}
	public void setListaConsultas(List<Consulta> listaConsultas) {
		this.listaConsultas = listaConsultas;
	}
	
	
}
