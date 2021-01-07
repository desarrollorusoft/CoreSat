package ar.com.cognisys.sat.core.modelo.comun.usuarioSat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;

// TODO Se puede optimizar usando un Map
public class CuentasUsuario implements Serializable {

	private static final long serialVersionUID = 8924380503278583299L;
	
	private List<Cuenta> listaCuentas;
	
	public void agregarCuenta(Cuenta cuenta) {
		this.getListaCuentas().add(cuenta);
	}
	
	public void agregarCuentaOrdenada(Cuenta cuenta) {
		this.agregarCuenta(cuenta);
		this.ordernar();
	}

	public void agregarCuentas(List<Cuenta> listaNueva) {
		this.getListaCuentas().addAll(listaNueva);
	}

	public void quitar(Cuenta cuentaADesvincular) {
		this.getListaCuentas().remove(cuentaADesvincular);
	}
	
	public void ordernar() {
		Collections.sort(this.getListaCuentas());
	}
	
	public boolean hay(Cuenta cuenta) {
		for (Cuenta c : this.getListaCuentas())
			if (c.sos(cuenta))
				return true;
		
		return false;
	}
	
	public List<Cuenta> obtenerCuentas(TiposCuentas tipo) {
		
		List<Cuenta> lista = new ArrayList<Cuenta>();
		
		for (Cuenta cuenta : this.getListaCuentas())
			if ( cuenta.sos(tipo) )
				lista.add( cuenta );
			
		return lista;
	}

	public Cuenta obtenerCuenta(String codigo) {
		for (Cuenta cuenta : this.getListaCuentas())
			if ( cuenta.obtenerCodigo().equals( codigo ) )
				return cuenta;
			
		return null;
	}
	
	public List<Cuenta> obtenerCuentasBE() {
		
		List<Cuenta> lista = new ArrayList<Cuenta>();
		
		for (Cuenta cuenta : this.getListaCuentas())
			if ( cuenta.isAceptaBE() )
				lista.add( cuenta );
			
		return lista;
	}
	
	public TiposCuentas[] obtenerTiposCuenta() {
		
		Set<TiposCuentas> conjunto = new HashSet<TiposCuentas>();
		
		for (Cuenta cuenta : this.getListaCuentas())
			conjunto.add(cuenta.getTipoCuenta());
		
		return conjunto.toArray( new TiposCuentas[] {} );
	}
	
	public boolean hayCuentas(TiposCuentas tipoCuenta) {
		
		for (Cuenta cuenta : this.getListaCuentas())
			if (cuenta.sos( tipoCuenta ))
				return true;
		
		return false;
	}
	
	public int getCantidad() {
		return (this.getListaCuentas() == null ? 0 : this.getListaCuentas().size());
	}
	
	public List<Cuenta> getListaCuentas() {
		return listaCuentas;
	}
	
	public void setListaCuentas(List<Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}
}