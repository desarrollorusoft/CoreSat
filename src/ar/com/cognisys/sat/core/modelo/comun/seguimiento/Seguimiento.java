package ar.com.cognisys.sat.core.modelo.comun.seguimiento;

import java.io.Serializable;
import java.util.ArrayList;

import ar.com.cognisys.sat.core.modelo.comun.NavegacionSAT;
import ar.com.cognisys.sat.core.modelo.factory.seguimiento.FactoryAcceso;

public class Seguimiento implements Serializable {

	private static final long serialVersionUID = -6238434232664337011L;
	private ArrayList<Acceso> listaAccesos;
	
	public void agregar(NavegacionSAT acceso) {
		
		this.getListaAccesos().add(FactoryAcceso.generarInstancia(acceso));
	}
	
	public Acceso recuperarPrimero() {
		
		return this.getListaAccesos().get(0);
	}
	
	public Acceso recuperarUltimo() {
		
		return this.getListaAccesos().get(this.getListaAccesos().size() - 1);
	}
	
	public ArrayList<Acceso> getListaAccesos() {
		return listaAccesos;
	}
	
	public void setListaAccesos(ArrayList<Acceso> listaAccesos) {
		this.listaAccesos = listaAccesos;
	}
}