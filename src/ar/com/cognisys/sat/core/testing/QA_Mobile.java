package ar.com.cognisys.sat.core.testing;

public class QA_Mobile {

//	@Test
//	public void qa1_login() throws Exception {
//		
//		Usuario u = AdministradorContribuyente.buscarUsuarioPorCorreo("fgarnero@cognisys.com.ar");
//		
//		Assert.assertNotNull("El usuario esta en NULL", u);
//		Assert.assertEquals("Recupero cualquier cosa", u.getIdUsuario(), new Integer(1));
//	}
//	
//	@Test
//	public void qa2_recuperarTotalesCuentas() throws Exception {
//		
//		HashMap<TiposCuentas, Integer> tabla = AdministradorUsuario.recuperarCantidadesCuentas(1);
//		
//		Assert.assertNotNull("La tabla esta en NULL", tabla);
//		
//		for (TiposCuentas t : tabla.keySet()) {
//			if (t == TiposCuentas.ABL) {
//				Assert.assertEquals(tabla.get(t), new Integer(90));
//			} else if (t == TiposCuentas.VEHICULOS) {
//				Assert.assertEquals(tabla.get(t), new Integer(15));
//			} else if (t == TiposCuentas.RODADOS) {
//				Assert.assertEquals(tabla.get(t), new Integer(9));
//			} else if (t == TiposCuentas.CEMENTERIO) {
//				Assert.assertEquals(tabla.get(t), new Integer(4));
//			}
//		}
//	}
//	
//	@Test
//	public void qa3_recuperarCuentasABL() throws Exception {
//		
//		List<Cuenta> listaCuentas = AdministradorCuenta.recuperarCuentasPorTipo(1, 1);
//		
//		Assert.assertNotNull("La tabla esta en NULL", listaCuentas);
//		Assert.assertEquals("La lista esta incompleta", listaCuentas.size(), 86);
//		Assert.assertEquals("La cuenta no es la misma", listaCuentas.get(0).getNumero().intValue(), 110002);
//	}
//	
//	@Test
//	public void qa4_recuperarCuentasVehiculos() throws Exception {
//		
//		List<Cuenta> listaCuentas = AdministradorCuenta.recuperarCuentasPorTipo(1, 3);
//		
//		Assert.assertNotNull("La tabla esta en NULL", listaCuentas);
//		Assert.assertEquals("La lista esta incompleta", listaCuentas.size(), 41);
//	}
//	
//	@Test
//	public void qa5_recuperarCuentasRodados() throws Exception {
//		
//		List<Cuenta> listaCuentas = AdministradorCuenta.recuperarCuentasPorTipo(1, 4);
//		
//		Assert.assertNotNull("La tabla esta en NULL", listaCuentas);
//		Assert.assertEquals("La lista esta incompleta", listaCuentas.size(), 14);
//	}
//	
//	@Test
//	public void qa6_buscarCuenta() throws Exception {
//		
//		String dominio = "257CLL";
//		
//		Integer numero = AdministradorCuenta.recuperarNumeroCuentaMVL(dominio, TiposCuentas.RODADOS);
//		
//		CuentasUsuario cu = AdministradorCuenta.buscarCuentas(TiposCuentas.RODADOS.getNombre(), dominio.toString());
//		
//	}
//	
//	@Test
//	public void qa6_recuperarCuentasCementerio() throws Exception {
//		
//		List<Cuenta> listaCuentas = AdministradorCuenta.recuperarCuentasPorTipo(1, 5);
//		
//		Assert.assertNotNull("La tabla esta en NULL", listaCuentas);
//		Assert.assertEquals("La lista esta incompleta", listaCuentas.size(), 4);
//		
//		Assert.assertNotNull("Las deudas estan en NULL", listaCuentas.get(0).getDeudas());
//		
//		for (Cuenta cuenta : listaCuentas) {
//			Assert.assertNotNull("El NumeroNomenclador esta en NULL", ((CuentaCementerio) cuenta).getNumeroNomenclador());
//			
//		}
//	}
//	
//	@Test
//	public void qa7_recuperarCuentas() throws Exception {
//		
//		CuentasUsuario cu = AdministradorCuenta.recuperarCuentas(1);
//		
//		Assert.assertNotNull("Las cuentas no puede estar en NULL", cu);
//		Assert.assertNotNull("Las listas de cuentas no puede estar en NULL", cu.getListaCuentas());
//		Assert.assertEquals("Las listas de cuentas no coinciden", cu.getListaCuentas().size(), 118);
//	}
//	
//	@Test
//	public void qa8_recuperarPeriodosAbl() throws Exception {
//		
//		DeudasCuentaAbl deuda = AdministradorCuenta.recuperarDeudaAbl(110002);
//		
//		Assert.assertNotNull("Las deudas no pueden ser nulas", deuda);
//		Assert.assertNotNull("Las deudas debe tener total", deuda.getTotalCoutasGeneral());
//		Assert.assertTrue("Debe tener deudas", deuda.getListaCuotasVencidas().size() > 0);
//		Assert.assertTrue("Debe tener deuda en Legales", deuda.isTieneDeudaLegales());
//		
//		for (Cuota c : deuda.getListaCuotas()) {
//			Assert.assertNotNull("La cuota no puede tener transaccion nula", c.getNumeroTransaccion());
//		}
//	}
//
//	@Test
//	public void qa9_recuperarPeriodosAutomotores() throws Exception {
//		
//		Deuda deuda = AdministradorCuenta.recuperarDeudaVehiculos("AHZ164");
//		
//		Assert.assertNotNull("Las deudas no pueden ser nulas", deuda);
//		Assert.assertNotNull("Las deudas debe tener total", deuda.getTotalCoutasGeneral());
//		Assert.assertTrue("Debe tener deudas", deuda.getListaCuotasVencidas().size() > 0);
//		
//		for (Cuota c : deuda.getListaCuotas()) {
//			Assert.assertNotNull("La cuota no puede tener transaccion nula", c.getNumeroTransaccion());
//		}
//	}
//
//	@Test
//	public void qa10_recuperarPeriodosRodados() throws Exception {
//		
//		Deuda deuda = AdministradorCuenta.recuperarDeudaRodados("014CTB");
//		
//		Assert.assertNotNull("Las deudas no pueden ser nulas", deuda);
//		Assert.assertNotNull("Las deudas debe tener total", deuda.getTotalCoutasGeneral());
//		Assert.assertTrue("Debe tener deudas", deuda.getListaCuotasVencidas().size() > 0);
//		
//		for (Cuota c : deuda.getListaCuotas()) {
//			Assert.assertNotNull("La cuota no puede tener transaccion nula", c.getNumeroTransaccion());
//		}
//	}
//	
//	@Test
//	public void qa11_recalcularDeuda_Rodados() throws ExcepcionControladaError {
//		
//		String cuentaDominio = "260DIR";
//		Integer cuentaNomCementerio = 0;
//		
//		List<Cuota> listaCuotas = new ArrayList<Cuota>();
//		
//		Cuota c1 = new Cuota();
//		c1.setTotal(238.87f);
//		c1.setPeriodo("2014-1");
//		try {
//			c1.setFechaVencimiento(new SimpleDateFormat("dd-MM-yyyy").parse("28-08-2014"));
//		} catch (Exception e) {}
//		c1.setNumeroTransaccion(71272404);
//		
//		Cuota c2 = new Cuota();
//		c2.setTotal(258.45f);
//		c2.setPeriodo("2015-1");
//		try {
//			c2.setFechaVencimiento(new SimpleDateFormat("dd-MM-yyyy").parse("10-07-2015"));
//		} catch (Exception e) {}
//		c2.setNumeroTransaccion(77356786);
//		
//		listaCuotas.add(c1);
//		listaCuotas.add(c2);
//		
//		Deuda deuda = AdministradorCuenta.recalcularDeuda(cuentaDominio, cuentaNomCementerio, TiposCuentas.RODADOS, new Date(), listaCuotas);
//		
//		Assert.assertNotNull("La deuda no debe venir nula", deuda);
//		for (Cuota cuota : deuda.getListaCuotas()) {
//			Assert.assertNotNull("La fecha no debe ser nula", cuota.getFechaVencimiento());
//		}
//	}
//	
//	@Test
//	public void qa11_recalcularDeuda_abl() throws ExcepcionControladaError {
//		
//		String cuenta = "110002";
//		
//		DeudasCuentaAbl deuda = AdministradorCuenta.recuperarDeudaAbl(new Integer(cuenta));
//		
//		Assert.assertNotNull("Las deudas no pueden ser nulas", deuda);
//		Assert.assertNotNull("Las deudas debe tener total", deuda.getTotalCoutasGeneral());
//		Assert.assertTrue("Debe tener deudas", deuda.getListaCuotasVencidas().size() > 0);
//		
//		for (Cuota c : deuda.getListaCuotas()) {
//			Assert.assertNotNull("La cuota no puede tener transaccion nula", c.getNumeroTransaccion());
//		}
//		
//		Deuda deudaRecalculada = AdministradorCuenta.recalcularDeuda(cuenta, null, TiposCuentas.ABL, new Date(), deuda.getListaCuotas(), false);
//		
//		Assert.assertNotNull("La deuda no debe venir nula", deudaRecalculada);
////		for (Cuota cuota : deudaRecalculada.getListaCuotas()) {
////			Assert.assertNotNull("La fecha no debe ser nula", cuota.getFechaVencimiento());
////		}
//		
//		for (Cuota c : deudaRecalculada.getListaCuotas()) {
//			if (c.getNumeroTasa() != 102) { // Descartamos la cuota asociada de ABL (CPC)
//				System.out.println("Cuota: Periodo["+c.getPeriodo()+"] - Importe ["+c.getTotal().toString()+"] - Tasa["+c.getTasa()+"]");
//				
//				if (c.getCuotaAsociada() != null && c.getNumeroTasa() == 100) {
//					System.out.println("\t|---> Cuota: Periodo["+c.getCuotaAsociada().getPeriodo()+"] - Importe ["+c.getCuotaAsociada().getTotal().toString()+"] - Tasa["+c.getCuotaAsociada().getTasa()+"]");
//				}
//			}
//		}
//	}
//	
//	@Test
//	public void qa20_desasignarCuenta() throws ExcepcionControladaError, ExcepcionControladaAlerta {
//		
//		HashMap<String, TiposCuentas> tabla = new HashMap<String, TiposCuentas>();
//		tabla.put("110020", TiposCuentas.ABL);
//		tabla.put("110002", TiposCuentas.ABL);
//		tabla.put("AHZ164", TiposCuentas.VEHICULOS);
//		tabla.put("AHZ199", TiposCuentas.VEHICULOS);
//		tabla.put("132BUP", TiposCuentas.RODADOS);
//		
//		Integer idUsuario = 1;
//		AdministradorCuenta.desasociarMultiplesCuentas(idUsuario, tabla);
//	}
//	
//	@Test
//	public void qa21_cuentaAutomotor() throws ExcepcionControladaError, ExcepcionControladaAlerta {
//		
//		AdministradorCuenta.recuperarCuentasPorTipo(1, TiposCuentas.RODADOS.getCodigo_usuario());
//	}
}