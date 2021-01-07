package ar.com.cognisys.sat.v2.core.modelo.view.builder;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.v2.core.modelo.view.TipoCuentaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.CuentaAsociadaView;

public class CuentaAsociadaViewBuilder {

	protected CuentaAsociadaView cuentaAsociada;
	private Cuenta cuenta;

	public CuentaAsociadaViewBuilder(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public CuentaAsociadaView getCuentaAsociada() {
		return cuentaAsociada;
	}

	public void inicializar() {
		this.cuentaAsociada = new CuentaAsociadaView();
	}

	public void cargarDatos() {
		this.cuentaAsociada.setAlias( this.cuenta.getAlias() );
		this.cuentaAsociada.setDescripcion( this.cuenta.getDescripcion() );
		this.cuentaAsociada.setCodigo( this.cuenta.obtenerCodigo() );
		
		String clave = this.cuenta.obtenerTipo();
		TiposCuentas tc = TiposCuentas.recuperar( clave );
		
		TipoCuentaView tcv = TipoCuentaView.obtener( tc );
		
		this.cuentaAsociada.setTipo( tcv.name() );
	}

	public void cargarImporte() {
		this.cuentaAsociada.setImporte( this.obtenerImporte() );

	}

	private String obtenerImporte() {
		if ( this.cuenta.getDeuda() == null && this.cuenta.getaVencer() == null )
			return null;
		
		if ( this.cuenta.getDeuda() == null )
			return String.valueOf( this.cuenta.getaVencer() );
		
		if ( this.cuenta.getaVencer() == null )
			return String.valueOf( this.cuenta.getDeuda() );
		
		return String.valueOf( this.cuenta.getDeuda() + this.cuenta.getaVencer() );
	}

}
