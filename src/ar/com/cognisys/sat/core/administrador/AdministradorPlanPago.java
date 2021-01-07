package ar.com.cognisys.sat.core.administrador;

import java.sql.Connection;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.ResultadoSimulacion;
import ar.com.cognisys.sat.core.modelo.dao.DaoPlanPago;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;

public class AdministradorPlanPago extends Administrador {

	private static final long serialVersionUID = -4603885591090315836L;
	
	public static ResultadoSimulacion calcularPlan(Cuenta cuenta, Integer anticipo, List<Cuota> listaCuotas) throws ExcepcionControladaError {
		
		Connection con = null;
		
		try {
			con = AdministradorConexiones.obtenerConexion();
			
			return (new DaoPlanPago(con)).calcularPlanPago(listaCuotas, cuenta, anticipo);
		} finally {
			AdministradorConexiones.cerrarConnection(con);
		}
	}
}