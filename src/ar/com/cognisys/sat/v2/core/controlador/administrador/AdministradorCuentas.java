package ar.com.cognisys.sat.v2.core.controlador.administrador;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.administrador.AdministradorCuenta;
import ar.com.cognisys.sat.core.modelo.abstracto.Cuenta;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaCementerio;
import ar.com.cognisys.sat.core.modelo.enums.TiposCuentas;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaAlerta;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;
import ar.com.cognisys.sat.v2.core.modelo.exception.PersistenceSATException;
import ar.com.cognisys.sat.v2.persistencia.controlador.facade.FacadeCuentasDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaCementerioDTO;
import ar.com.cognisys.sat.v2.persistencia.modelo.dto.CuentaDTO;

public class AdministradorCuentas {

	public List<Cuenta> recuperarCuentasPorUsuario(Integer idUsuario) throws PersistenceSATException, ExcepcionControladaError {
		List<Cuenta> lista = new ArrayList<Cuenta>();

		FacadeCuentasDTO facadeDTO = new FacadeCuentasDTO();
		List<CuentaDTO> listaDTO = facadeDTO.recuperarCuentasPorUsuario( idUsuario );

		for ( CuentaDTO cuentaDTO : listaDTO )
			this.agregarCuenta( cuentaDTO, lista );

		return lista;
	}
	
	public Cuenta recuperarCuenta(TiposCuentas tipoCuenta, String nombreUsuario, String codigoCuenta) throws PersistenceSATException, ExcepcionControladaError {

		FacadeCuentasDTO facadeDTO = new FacadeCuentasDTO();
		CuentaDTO cuentaDTO = facadeDTO.recuperarCuenta( tipoCuenta.name(), nombreUsuario, codigoCuenta );

		Cuenta cuenta = this.transformarCuenta( cuentaDTO );

		return cuenta;
	}
	
	private void agregarCuenta(CuentaDTO cuentaDTO, List<Cuenta> lista) {
		Cuenta cuenta = this.transformarCuenta( cuentaDTO );
		
		if ( cuenta != null )
			lista.add( cuenta );
	}

	// TODO [RODRI] Mejorar esto!!
	private Cuenta transformarCuenta(CuentaDTO cuentaDTO) {
		if ( cuentaDTO == null )
			return null;
		
		TiposCuentas tc = TiposCuentas.recuperar( cuentaDTO.getTipo() );
		
		switch ( tc ) {
		case ABL:
			return FactoryCuenta.generarInstanciaABL( Integer.valueOf( cuentaDTO.getCodigo() ), cuentaDTO.getDescripcion(), cuentaDTO.getAlias(), cuentaDTO.isAceptaBE() );
		case COMERCIOS:
			return FactoryCuenta.generarInstanciaComercios( Integer.valueOf( cuentaDTO.getNumero() ), cuentaDTO.getDescripcion(), cuentaDTO.getAlias(), cuentaDTO.isAceptaBE() );
		case VEHICULOS:
			return FactoryCuenta.generarInstanciaVehiculos( cuentaDTO.getCodigo(), cuentaDTO.getDescripcion(), cuentaDTO.getAlias(), cuentaDTO.isAceptaBE(), cuentaDTO.getNumero() );
		case RODADOS:
			return FactoryCuenta.generarInstanciaRodados( cuentaDTO.getCodigo(), cuentaDTO.getDescripcion(), cuentaDTO.getAlias(), cuentaDTO.isAceptaBE(), cuentaDTO.getNumero() );
		case CEMENTERIO:
			try {
				return AdministradorCuenta.buscarCuenta(TiposCuentas.CEMENTERIO, cuentaDTO.getCodigo());
			} catch (ExcepcionControladaError e) { return null;
			} catch (ExcepcionControladaAlerta e) { return null; }
		case PILETAS:
			return FactoryCuenta.generarInstanciaPiletas( cuentaDTO.getCodigo(), cuentaDTO.getNumero().toString(), cuentaDTO.getAlias() );
		default:
			break;
		}
		
		return null;
	}	
}