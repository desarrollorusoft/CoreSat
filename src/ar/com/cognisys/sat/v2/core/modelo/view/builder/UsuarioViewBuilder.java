package ar.com.cognisys.sat.v2.core.modelo.view.builder;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.RSDatos;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.IRSActividad;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.interfaz.IRSSolicitante;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.v2.core.controlador.director.DirectorCuentaAsociadaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.CuentaAsociadaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.UsuarioView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.ActividadView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.RegimenPadronesView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.RegimenView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.SolicitanteView;

public class UsuarioViewBuilder {

	protected UsuarioView usuarioView;
	private Usuario usuario;
	private List<Cuenta> listaCuentas;
	private RSDatos rsDatos;
	private boolean puedeRS;
	
	public UsuarioViewBuilder(Usuario usuario, List<Cuenta> listaCuentas, RSDatos rsDatos, boolean puedeRS) {
		this.usuario = usuario;
		this.listaCuentas = listaCuentas;
		this.rsDatos = rsDatos;
		this.puedeRS = puedeRS;
	}

	public void inicializar() {
		this.usuarioView = new UsuarioView();
	}

	public void cargarDatos() {
		this.usuarioView.setCorreo( this.usuario.getCorreo() );
		this.usuarioView.setCuit( this.usuario.getCuit() );
		this.usuarioView.setNewsletter(this.usuario.isNewsLetter());
		this.usuarioView.setTelefono(this.usuario.getTelefono1());
		this.usuarioView.setTelefono2(this.usuario.getTelefono2());
		this.usuarioView.setMuestroRS(puedeRS);
		this.usuarioView.setMigrado(this.usuario.isMigrado());
	}

	public void cargarCuentasAsociadas() {
		List<CuentaAsociadaView> lista = new ArrayList<CuentaAsociadaView>();
		for ( Cuenta cuenta : this.listaCuentas )
			if ( cuenta != null )
				lista.add( this.crearCuentaAsociadaView( cuenta ) );
		
		this.usuarioView.setCuentasAsociadas( lista.toArray( new CuentaAsociadaView[]{ } ) );
	}
	
	public void cargarRSDatos() {
		
		if (puedeRS) {
			RegimenView regimen = null;
			
			if (rsDatos == null)
				regimen = this.crearDatosVacios();
			else
				regimen = this.cargarDatosReales();
			
			this.usuarioView.setRegimenPadrones(new RegimenPadronesView(regimen));
		}
	}
	
	private RegimenView crearDatosVacios() {
		
		ActividadView actividad = this.crearActividadViewVacia();
		SolicitanteView solicitante = this.crearSolicitanteViewVacia();
		
		return new RegimenView(actividad, solicitante, false, false);
	}

	private ActividadView crearActividadViewVacia() {
		return new ActividadView(usuario.getCuit(), usuario.getCorreo(), usuario.getTelefono1());
	}

	private SolicitanteView crearSolicitanteViewVacia() {
		return new SolicitanteView(usuario.getCorreo(), usuario.getTelefono1());
	}

	private RegimenView cargarDatosReales() {
		
		ActividadView actividad = this.crearActividadView( rsDatos.getActividad() );
		SolicitanteView solicitante = this.crearSolicitanteView( rsDatos.getSolicitante() );
		
		return new RegimenView(actividad, solicitante, rsDatos.tieneDatosCompletos(), rsDatos.isRechazo());
	}

	private ActividadView crearActividadView(IRSActividad actividad) {
		return new ActividadView(actividad.getCuit(), actividad.getCorreo(), actividad.getTelefono(), actividad.getCelular());
	}

	private SolicitanteView crearSolicitanteView(IRSSolicitante solicitante) {
		return new SolicitanteView(solicitante.getNombre(), 
								   solicitante.getApellido(), 
								   solicitante.getCaracter().getNombre(), 
								   solicitante.getCorreo(), 
								   solicitante.getTelefono(), 
								   solicitante.getCelular());
	}

	private CuentaAsociadaView crearCuentaAsociadaView(Cuenta cuenta) {
		
		CuentaAsociadaViewBuilder builder = new CuentaAsociadaViewBuilder( cuenta );
		DirectorCuentaAsociadaView director = new DirectorCuentaAsociadaView( builder );
		
		director.construir();
		
		return director.getCuentaAsociada();
	}
	
	public UsuarioView getUsuarioView() {
		return this.usuarioView;
	}
}