package ar.com.cognisys.sat.v2.core.controlador.facade;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorCuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanSimulacion;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.ResultadoSimulacion;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.SimulacionIncorrectaException;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.core.modelo.exception.WarningSATException;
import ar.com.cognisys.sat.v2.core.modelo.view.CuotaView;
import ar.com.cognisys.sat.v2.core.modelo.view.PlanDePagoView;
import ar.com.cognisys.sat.v2.core.modelo.view.TipoCuentaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ICuotaView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IPlanDePagoView;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.ISimulacionView;

public class FacadePlanesDePago {

	/**
	 * Método utilizado para simular un plan de pago.
	 * 
	 * @param simulacion
	 * @return planDePago
	 * @throws PersistenceSATException 
	 * @throws WarningSATException 
	 */
	public IPlanDePagoView simular(ISimulacionView simulacion) throws PersistenceSATException, WarningSATException {
		
		IPlanDePagoView plan = null;
		try {
			TipoCuentaView tcv = TipoCuentaView.obtener( simulacion.getCuenta().getTipo() );
			TiposCuentas tipoCuenta = tcv.getBo();
			
			Cuenta cuenta = AdministradorCuenta.buscarCuenta(tipoCuenta, simulacion.getCuenta().getCodigo());
			
//			List<CuotaApagar> listaTransacciones = AdministradorCuenta.recuperarCuotasVencidas( cuenta, new Date() );
//
//			ResultadoSimulacion result = AdministradorPlanPago.calcularPlan(cuenta, Integer.valueOf( simulacion.getAnticipo() ), listaTransacciones);
			
//			plan = this.crearPlanDePagoView( result, simulacion.getAnticipo() );
			
			plan = this.crearPlanDePagoView( null, simulacion.getAnticipo() );
				
		} catch ( SimulacionIncorrectaException e ) {
			throw new WarningSATException( e.getMessage() );
		} catch ( ExcepcionControladaError e ) {
			throw new PersistenceSATException( "No es posible simular el plan de pago", e );
		} catch ( ExcepcionControladaAlerta e ) {
			throw new PersistenceSATException( "No es posible simular el plan de pago", e );
		}
		
		return plan;
	}

	private IPlanDePagoView crearPlanDePagoView(ResultadoSimulacion result, String anticipo) {
		PlanDePagoView planDePago = new PlanDePagoView();
		
		planDePago.setAnticipo( anticipo );
		planDePago.setImporteAnticipo( String.valueOf( result.getImporteAnticipo() ) );
		planDePago.setImportePlan( String.valueOf( result.getImportePlan() ) );
		planDePago.setImporteTotal( String.valueOf( result.getImporteAnticipo() + result.getImportePlan() ) );
		planDePago.setPorcentaje( String.valueOf( result.getPorcentajeCalculado() ) );
		planDePago.setNombre( "Simulación Plan de Pago" );
		
		List<ICuotaView> lista = new ArrayList<ICuotaView>();
		for (PlanSimulacion plan : result.getListaPlanes())
			lista.add( this.crearCuota( plan ) );
			
		
		planDePago.setCuotas( lista.toArray( new ICuotaView[] { } ) );
		
		return planDePago;
	}

	private ICuotaView crearCuota(PlanSimulacion plan) {
		String numero = String.valueOf( plan.getNumeroCuotas() );
		String importe = String.valueOf( plan.getImporteCuota() );
		return new CuotaView( numero, importe );
	}
}
