package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorCuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.v2.core.controlador.director.DirectorCuotaDeudaView;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.core.modelo.view.CuotaDeudaView;
import ar.com.cognisys.sat.v2.core.modelo.view.DeudaView;
import ar.com.cognisys.sat.v2.core.modelo.view.TipoCuentaView;
import ar.com.cognisys.sat.v2.core.modelo.view.builder.CuotaDeudaViewBuilder;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaDeudaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IDeudaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaView;

public class FacadeDeudas {

	/**
	 * Método utilizado para recuperar las cuotas vencidas,
	 * las cuotas por vencer y el aviso.
	 * 
	 * @param cuenta
	 * @return deuda
	 * @throws PersistenceSATException 
	 */
	public IDeudaView recuperarEstadoDeDeudas(ICuentaView cuentaView) throws PersistenceSATException {
		
		IDeudaView deudaView = null;
		
		try {
			TipoCuentaView tcv = TipoCuentaView.obtener( cuentaView.getTipo() );
			TiposCuentas tipoCuenta = tcv.getBo();
			
			Deuda deuda = AdministradorCuenta.recuperarDeudas( tipoCuenta, cuentaView.getCodigo() );
			
			deudaView = this.crearDeudaView( deuda );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible recuperar el estado de las deudas", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new PersistenceSATException( "No es posible recuperar el estado de las deudas", e );
		}
		
		return deudaView;
	}

	private IDeudaView crearDeudaView(Deuda deuda) {
		
		DeudaView deudaView = new DeudaView();
		
		deudaView.setAviso( deuda.generarAviso() );
		
		List<ICuotaDeudaView> listaVencidas = this.generarCuotasDeuda( deuda.getListaCuotasVencidas() );
		deudaView.setCuotasVencidas( listaVencidas.toArray( new ICuotaDeudaView[] {} ) );
		
		List<ICuotaDeudaView> listaProximas = this.generarCuotasDeuda( deuda.getListaCuotasAVencer() );
		deudaView.setCuotasProximas( listaProximas.toArray( new ICuotaDeudaView[] {} ) );
		
		deudaView.setTotalProximas( String.valueOf( deuda.getTotalCoutasAVencer().getTotal() ) );
		deudaView.setTotalVencidas( String.valueOf( deuda.getTotalCoutasVencidas().getTotal() ) );
		
		return deudaView;
	}

	private List<ICuotaDeudaView> generarCuotasDeuda(List<Cuota> listaCuotas) {
		List<ICuotaDeudaView> lista = new ArrayList<ICuotaDeudaView>();
		for (Cuota cuota : listaCuotas)
			if ( !( cuota.sos( 102 ) && cuota.tenesCuotaAsociada() ) )
			this.agregarCuota( cuota, lista );
		
		return lista;
	}

	private void agregarCuota(Cuota cuota, List<ICuotaDeudaView> lista) {
		CuotaDeudaView cuotaDeuda = null;
		
		cuotaDeuda = cuota.getCuotaAsociada() == null ? this.crearCuotaDeuda( cuota ) : this.crearCuotaDeuda( cuota, this.crearCuotaDeuda( cuota.getCuotaAsociada() ) );
		lista.add( cuotaDeuda );
	}

	private CuotaDeudaView crearCuotaDeuda(Cuota cuota) {
		
		
		CuotaDeudaViewBuilder builder = new CuotaDeudaViewBuilder( cuota );
		DirectorCuotaDeudaView director = new DirectorCuotaDeudaView( builder );
		
		director.construir();
		
		return director.getCuotaDeuda();
	}
	
	private CuotaDeudaView crearCuotaDeuda(Cuota cuota, CuotaDeudaView cuotaAsociada) {
		CuotaDeudaViewBuilder builder = new CuotaDeudaViewBuilder( cuota, cuotaAsociada );
		DirectorCuotaDeudaView director = new DirectorCuotaDeudaView( builder );
		
		director.construir();
		
		return director.getCuotaDeuda();
	}


	public boolean tieneDeuda(ICuentaView cuentaView) throws PersistenceSATException {

		boolean tieneDeuda;
		try {
			TipoCuentaView tipoCuentaView = TipoCuentaView.obtener( cuentaView.getTipo() );
			TiposCuentas tipoCuenta = tipoCuentaView.getBo();
			
			Cuenta cuenta = AdministradorCuenta.buscarCuenta(tipoCuenta, cuentaView.getCodigo());
			
//			List<CuotaApagar> lista = AdministradorCuenta.recuperarCuotasVencidas( cuenta, new Date() );
			
//			tieneDeuda = lista != null && !lista.isEmpty();
			tieneDeuda = false;
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible saber si el usuario tiene deudas", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new PersistenceSATException( "No es posible saber si el usuario tiene deudas", e );
		}
		
		return tieneDeuda;
	}

}
