package ar.com.cognisys.sat.core.modelo.dao.ppc;

import java.sql.Connection;

import ar.com.cognisys.conexiones.recursos.AbstractDao;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.Parametros;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.ppc.CalculaCod;
import ar.com.cognisys.sat.core.modelo.ppc.PorcentajeRecMul;

public abstract class DaoCalculaCod extends AbstractDao {

	protected CalculaCod calculaCod;

	public DaoCalculaCod(Connection connection) {
		super( connection );
	}

	public Parametros calcular(long nroTransaccion, float liRecargo, float liTotMul, Integer nroPlan) throws ExcepcionControladaError {
		
		this.calculaCod = new CalculaCod( nroTransaccion, liRecargo, liTotMul, nroPlan );

		PorcentajeRecMul porcentaje = this.porcentaje();
		
		this.calculaCod.actualizar( porcentaje );

		Parametros pa = new Parametros();
		
		pa.setRecargo( this.calculaCod.getLiRecargo() );
		pa.setMulta( this.calculaCod.getLiTotMul() );
		
		return pa;
	}

	protected abstract PorcentajeRecMul porcentaje() throws ExcepcionControladaError;

}
