package ar.com.cognisys.sat.core.testing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Deuda;
import ar.com.cognisys.sat.core.modelo.comun.Catastro;
import ar.com.cognisys.sat.core.modelo.comun.Contribuyente;
import ar.com.cognisys.sat.core.modelo.comun.Domicilio;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.TotalCuota;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.cuenta.FactoryCuenta;
import ar.com.cognisys.sat.core.modelo.factory.deudas.FactoryDeudasCuentaAbl;
import ar.com.cognisys.sat.core.modelo.generador.recibos.GeneradorReciboABL;

public class AblTest {
	
	public static void main(String[] args) throws ExcepcionControladaError {
		
		Domicilio domicilio = new Domicilio();
		domicilio.setAltura(1234);
		domicilio.setCalle("Calle");
		domicilio.setCodigoPostal(1324);
		domicilio.setDepartamento("Departamento");
		domicilio.setDescripcionCompleta("Descripcion completa");
		domicilio.setPiso("Piso");
		
		Contribuyente contribuyente = new Contribuyente();
		contribuyente.setApellido("Apellido");
		contribuyente.setCorreo("Correo");
		contribuyente.setDomicilio(domicilio);
		contribuyente.setFax("FAX");
		contribuyente.setNombre("Nombre");
		contribuyente.setNumeroDocumento(35243578);
		contribuyente.setTelefono("TELEFONO");
		contribuyente.setTipoDocumento(1);
		
		Cuota cuota = new Cuota();
		cuota.setCapital(10f);
		cuota.setEstado("Estado");
		cuota.setFechaVencimiento(new Date());
		cuota.setMora(true);
		cuota.setMulta(100f);
		cuota.setNumeroTasa(10);
		cuota.setNumeroTransaccion(132);
		cuota.setPagar(true);
		cuota.setPeriodo("2017-03-22");
		cuota.setRecargo(100f);
		cuota.setTasa("1");
		cuota.setTotal(1000f);
		List<Cuota> lista = new ArrayList<Cuota>();
		lista.add(cuota);
		
		TotalCuota totalCoutasAVencer = new TotalCuota();
		totalCoutasAVencer.setCapital(100f);
		totalCoutasAVencer.setMulta(1000f);
		totalCoutasAVencer.setRecargo(100F);
		totalCoutasAVencer.setTotal(15115F);
		
		Deuda deudas = FactoryDeudasCuentaAbl.
				generarIntanciaCompleta(lista, 
										lista, 
										totalCoutasAVencer , 
										totalCoutasAVencer, 
										totalCoutasAVencer);
		
		Catastro c = new Catastro();
		c.setC("c");
		c.setF("f");
		c.setLm("lm");
		c.setLp("lp");
		c.setM("m");
		c.setP("p");
		c.setP2("p2");
		c.setS("s");
		c.setUc("uc");
		c.setUf("uf");
		
		CuentaABL cuenta = FactoryCuenta.generarIntanciaCompletaABL(1, "Descripcion");
		cuenta.setaVencer(10f);
		cuenta.setContribuyente(contribuyente);
		cuenta.setDeuda(100f);
		cuenta.setDeudaLegales(true);
		cuenta.setDeudas(deudas);
		cuenta.setSelected(true);
		cuenta.setCatastro(c);
		
		GeneradorReciboABL g = new GeneradorReciboABL(
				cuenta, 
				"123456789", 
				new Date(), 
				1500F, 
				lista);
		
		g.generarRecibo();
	}
}