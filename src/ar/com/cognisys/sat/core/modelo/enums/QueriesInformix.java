package ar.com.cognisys.sat.core.modelo.enums;

public enum QueriesInformix {

	/*
	 * 1 - Numero de cuenta
	 * */
	CONSULTAR_DEUDA_SP("{call sp_abl_carga_deuda_cogmvl( 2 , 3 , ? )}"),
	
	/*
	 * 1 - Numero de cuenta
	 * 2 - Fecha
	 * */
	BOLETA_PAGO_SP_CONTADO("{call sp_abl_deuda_pago_cogmvl(?,1,1,?)}"),
	
	BOLETA_PAGO_TOTAL("Select Sum(I_Capital) Capital,  \n "
					+ "       Sum(I_Recargo) Recargo,  \n "
					+ "       Sum(I_Multa) Multa,  \n "
					+ "       Sum(I_Capital + I_Recargo + I_Multa) Total,  \n "
					+ "       Round(Sum(I_Capital + I_Recargo),2),  \n "
					+ "       Round(Sum(I_Recargo),2) as recargo_redondeado  \n "
					+ "From Tmp_Deuda_Pago "),
	
	/*
	 * 1 - Numero de cuenta
	 * 2 - Fecha
	 * */
	BOLETA_PAGO_SP_COMUN("{call sp_abl_deuda_pago_cogmvl(?,1,0,?)}"),

	BOLETA_PAGO_CUOTAS("Select *, (I_Capital + I_Recargo + I_Multa) \n "
					 + "From Tmp_Deuda_Pago \n "
					 + "Order By D_Periodo, C_Tasa "),
	
	RECALCULAR_DEUDA("{call sp_recalcula_deuda_cogmvl(?,?,?,?,0,?,?)}"),
	
	BUSQUEDA_CUENTAS_NATATORIOS("Select tipo_documento, nro_documento, descripcion \n"
							  + "From web_recau:usuario_cuentas_piletas_cogmvl \n"
							  + "Where id_usuario = ? "),

	BUSQUEDA_CUENTAS_NATATORIOS_PAGINADA("Select skip ? first ? tipo_documento, nro_documento, descripcion \n"
			+ "From web_recau:usuario_cuentas_piletas_cogmvl \n"
			+ "Where id_usuario = ? "),
	
	BUSQUEDA_BOLETA_NATATORIOS("{call sp_carga_socio_pileta( ? , ? )}"),
	
	RECUPERACION_DATOS_NATATORIOS("Select * From tmp_socios_pileta_web \n"
								+ "Where c_tipo_doc = ? \n"
								+ "And n_nro_doc = ? "),
								
	BUSQUEDA_RECIBOS_1("{call sp_busca_recibos_1_cogmvl( ? )}"),
	BUSQUEDA_RECIBOS_2("{call sp_busca_recibos_2_cogmvl( ?, ? )}"),
	BUSQUEDA_RECIBOS_3("{call sp_busca_recibos_3_cogmvl( ?, ?, ?, ? )}"),
	BUSQUEDA_RECIBOS_4("{call sp_busca_recibos_4_cogmvl( ?, ?, ? )}"),
	BUSQUEDA_RECIBOS_5("{call sp_busca_recibos_5_cogmvl( ? )}"),
	
	RECUPERACION_CUENTA_ABL("Select 1 \n"
						  + "From Web_Recau:Usuario_Cogmvl u, Web_Recau:Usuario_Cuentas_Rel_Cogmvl c \n"
						  + "Where u.correo = ? \n"
						  + "And c.id_usuario = u.id_usuario "
						  + "And c.tipo_cuenta = ? "
						  + "And c.cuenta = ? "),
	
	RECUPERACION_TIPO_VEHICULO("Select 1 \n"
							+  "From Web_Recau:Usuario_Cogmvl u, Web_Recau:Usuario_Cuentas_Rel_Cogmvl c \n"
							+  "Where u.correo = ? \n"
							+  "And c.id_usuario = u.id_usuario \n"
							+  "And c.cuenta = (Select c.c_cuenta \n"
											+  "from recaudaciones:vehiculos v, recaudaciones:contrib_cuentas c \n"
											+  "where (v.c_dominio_actual = ? or v.c_dominio_original = ? or v.c_dominio_mercosur = ?) \n"
											+  "and c.c_sistema = 31 \n"
											+  "and v.c_baja = 0 \n"
											+  "and c.c_cuenta = v.c_cuenta) \n"
							+  "Union \n"
							+  "Select 1 \n"
							+  "From Web_Recau:Usuario_Cogmvl u, Web_Recau:Usuario_Cuentas_Rel_Cogmvl c \n"
							+  "Where u.correo = ? \n"
							+  "And c.id_usuario = u.id_usuario \n"
							+  "And c.cuenta = (Select c.c_cuenta \n"
											+  "from recaudaciones:rodados r, recaudaciones:contrib_cuentas c \n"
											+  "where (r.c_dominio = ? or r.c_mercosur = ?) \n"
											+  "and c.c_sistema = 4 \n"
											+  "and c.c_cuenta = r.c_cuenta)"),
	
	SPP_ELIMINAR_TABLA("Drop Table Tmp_Deuda_Pago"),
	
	SPP_CREAR_TABLA("Create Temp Table Tmp_Deuda_Pago ( \n"
				  + "    C_Tasa SMALLINT, \n"
				  + "    D_Tasa_Abr Char (15), \n"
				  + "    D_Periodo Char (9), \n"
				  + "    I_Capital Dec (16,2), \n"
				  + "    I_Recargo Dec (16,2), \n"
				  + "    I_Multa Dec (16,2), \n"
				  + "    N_Transac Integer) With No Log "),
				  
	SPP_INSERT_TRANSACCION("Insert Into Tmp_Deuda_Pago \n"
						 + "    (C_tasa, D_Tasa_Abr, D_Periodo, I_Capital, I_Recargo, I_Multa, N_Transac) \n"
						 + "Values  \n"
						 + "    ( ? , ? , ? , ? , ? , ? , ? ) "), 
	
	SPP_CALCULAR("{call spl_web_ins_solicitud( TODAY , 100 , ? , ? , ? , ? )}"),
	
	SPP_BUSQUEDA_PLANES("Select n_cuota, i_cuota \n"
			          + "From recaudaciones:ppc_web_cuotas \n"
			          + "Where n_solicitud = ? "), 
			          
	BUSCAR_CUENTAS_CEMENTERIO("Select cuenta \n"
							+ "From web_recau:usuario_cuentas_rel_cogmvl \n"
							+ "Where id_usuario = ? \n"
							+ "And tipo_cuenta = 5 "),
	
	BUSCAR_DATOS_CUENTA_CEMENTERIO("{call spl_top_obt_cementerio_cogmvl( ? , 9 )}"),
							
	BUSCAR_DEUDAS_CUENTA_CEMENTERIO("{call spl_top_cem_obt_deuda_cogmvl( ? , ? , ? , 0 )}"),
	
	BUSCAR_DEUDAS_CUENTA_CEMENTERIO_CONTADO("{call spl_top_cem_obt_deuda_cogmvl( ? , ? , ? , 1 )}"),
	
	RECALCULAR_DEUDA_CEMENTERIO("{call sp_recalcula_deuda_cogmvl( ? , ? , ? , ? , ? , ? , ? )}"),
	
	GENERAR_DATOS_ABL("{call sp_abl_carga_pad_cogmvl( ? , 2 )}"), 
	
	RECUPERAR_DATOS_ABL("Select * From tmp_abl_cons_pad "),
	
	COMPROB_PPC("select Max(n_comprob) as n_comprob \n"
			  + "from recibo_plan \n"
			  + "where n_plan = ? \n"
			  + "and n_cuota = ? "), 
	
	/*
	 * 1 - ID_USUARIO
	 * */
	RECUPERAR_CANTIDADES_CUENTAS("Select Tipo_Cuenta, Count(*) Cantidad \n"
							   + "From Web_Recau:Usuario_Cuentas_Rel_Cogmvl C \n"
							   + "Where C.Id_Usuario = ? \n"
							   + "And ((c.tipo_cuenta = 1 And exists (Select 1 From recaudaciones:abl a Where a.C_cuenta = C.Cuenta And a.C_baja = 0)) Or \n"
							   + "    (c.tipo_cuenta = 3 And exists (Select 1 From recaudaciones:vehiculos v Where (v.c_dominio_mercosur = C.Cuenta Or v.c_dominio_actual = C.Cuenta Or v.c_dominio_original = C.Cuenta) And v.C_baja = 0)) Or \n"
							   + "    (c.tipo_cuenta = 4 And exists (Select 1 From recaudaciones:rodados r Where (r.C_dominio = C.Cuenta Or r.C_mercosur = c.Cuenta) And r.C_baja = 0)) Or \n"
							   + "    (c.tipo_cuenta = 5 And exists (Select 1 From cementerio:arrendatarios a Where c.cuenta = a.c_cuenta||'' And a.c_padron = 1)) Or \n"
							   + "    (c.tipo_cuenta = 2 And exists (Select 1 From recaudaciones:comercios com, recaudaciones:regimen_simplificado_cuentas rsc Where com.n_cuit = C.Cuenta And com.C_baja = 0 And rsc.c_cuenta = com.c_cuenta))) \n"
							   + "Group By Tipo_Cuenta \n"
							   + "Union \n"
							   + "Select 6 as Tipo_Cuenta, Count(*) Cantidad \n"
							   + "From web_recau:usuario_cuentas_piletas_cogmvl C \n"
							   + "Where C.Id_Usuario = ? \n"
							   + "Group By Tipo_Cuenta \n"
							   + "Order By Tipo_Cuenta "), 
	/*
	 * 1 - ID_USUARIO
	 * 2 - CODIGO
	 * */
	GUARDAR_CODIGO_ACTIVACION("Insert Into activacion_mobile(id_usuario, codigo) \n"
							+ "Values ( ? , ? )"), 
	/*
	 * 1 - ID_USUARIO
	 * */
	BUSCAR_CODIGO_ACTIVACION("Select codigo \n"
						   + "From recaudaciones:activacion_mobile \n"
						   + "Where id_usuario = ? \n"
						   + "order by fecha desc"),
    /*
	 * 1 - SISTEMA
	 * 2 - DOMINIO
	 * */
	SPP_RECUPERAR_DATOS_AUTOMOTOR("{call sp_pad_veh_rod_cogmvl( ? , ? ) }"),
	

	/**
	 * 1:TIPO 2:DATOS
	 */
	TRACE_PAGO("Insert Into web_recau:pago_online_cogmvl(tipo, datos)\n"
			 + "Values ( ? , ? )"),
			 
	/**
	 * 1:TIPO 2:DATOS 3:TASA 4:CUENTA 5:RAZON 6:RECIBO 7:FECHA PAGO 8:IMPORTE
	 */ 
	TRACE_PAGO_DATOS("Insert Into web_recau:pago_online_cogmvl(tipo, datos, tasa, cuenta, razon, recibo, fecha_pago, valor)\n"
			 + "Values ( ? , ?, ?, ?, ?, ?, ?, ? )"),
	
	/**
	 * 1: CUIT<br/>
	 * 2: CLAVE<br/>
	 * 3: CORREO<br/>
	 */
	ACTUALIZAR_DATOS_MIGRACION("Update web_recau:usuario_cogmvl \n"
							 + "Set cuit = ?, clave = ?, flag_activacion = 1, nombre_usuario = 'carga_cuit_'||current \n"
							 + "Where id_usuario = ? "),
	;					   
	
	private String query;

	private QueriesInformix(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

}