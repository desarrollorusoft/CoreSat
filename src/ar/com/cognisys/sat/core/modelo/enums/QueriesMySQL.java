package ar.com.cognisys.sat.core.modelo.enums;

public enum QueriesMySQL {

	INGRESO("Insert Into web_recau:ingreso_usuario \n"
		  + "	(nombre_usuario, fecha_ingreso, id_aplicacion) \n "
		  + "values \n"
		  + "	(?, ?, ?)"),
	
	ABL_CONSULTA_DEUDA("insert into web_recau:registro_abl_consultar_deuda \n"
					 + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			  		 + "values \n"
			  		 + "	(?, ?, ?)"),
			  		 
	ABL_BOLETA_PAGO("insert into web_recau:registro_abl_boleta_pago \n"
				  + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			  	  + "values \n"
			  	  + "	(?, ?, ?)"),
			  	  
  	ABL_PLAN_PAGO("insert into web_recau:registro_abl_plan_plago \n"
				+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			  	+ "values \n"
			  	+ "	(?, ?, ?)"),
			  	 
  	ABL_SIMULACION("insert into web_recau:registro_abl_simulacion_ppc \n"
				 + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			  	 + "values \n"
			  	 + "	(?, ?, ?)"),
	
	ABL_RECIBO("insert into web_recau:registro_abl_generacion_recibo \n"
			 + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			 + "values \n"
			 + "	(?, ?, ?)"),
	
	ABL_PAGAR_INTERBANKING("insert into web_recau:registro_abl_pagar_interbanking \n"
						 + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
						 + "values \n"
						 + "	(?, ?, ?)"),
	
	ABL_PAGAR_PMC("insert into web_recau:registro_abl_pagar_PMC \n"
			    + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			    + "values \n"
			    + "	(?, ?, ?)"),
	
	ABL_PAGAR_LINKPAGOS("insert into web_recau:registro_abl_pagar_link_pagos \n"
				     + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
				     + "values \n"
				     + "	(?, ?, ?)"),
	
	ABL_PAGAR_CREDITO("insert into web_recau:registro_abl_pagar_credito \n"
				    + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
				    + "values \n"
				    + "	(?, ?, ?)"),
	
	AUTOMOTORES_CONSULTA_DEUDA("insert into web_recau:registro_automotores_consultar_deuda \n"
							 + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			  				 + "values \n"
			  				 + "	(?, ?, ?)"),
			  				 
	AUTOMOTORES_BOLETA_PAGO("insert into web_recau:registro_automotores_boleta_pago \n"
						  + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
						  + "values \n"
						  + "	(?, ?, ?)"),
						  
    AUTOMOTORES_PLAN_PAGO("insert into web_recau:registro_automotores_plan_plago \n"
						+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n"
			  			+ "values \n"
			  			+ "	(?, ?, ?)"),
	    
	AUTOMOTORES_SIMULACION("insert into web_recau:registro_automotores_simulacion_ppc \n"
						 + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			  			 + "values \n"
			  			 + "	(?, ?, ?)"),
	
	AUTOMOTORES_RECIBO("insert into web_recau:registro_automotores_generacion_recibo \n"
					 + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
		 			 + "values \n"
		 			 + "	(?, ?, ?)"),
	
	AUTOMOTORES_PAGAR_INTERBANKING("insert into web_recau:registro_automotores_pagar_interbanking \n"
								 + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
								 + "values \n"
								 + "	(?, ?, ?)"),
	
	AUTOMOTORES_PAGAR_PMC("insert into web_recau:registro_automotores_pagar_PMC \n"
					    + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
					    + "values \n"
					    + "	(?, ?, ?)"),
	
	AUTOMOTORES_PAGAR_LINKPAGOS("insert into web_recau:registro_automotores_pagar_link_pagos \n"
						      + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
						      + "values \n"
						      + "	(?, ?, ?)"),
	
	AUTOMOTORES_PAGAR_CREDITO("insert into web_recau:registro_automotores_pagar_credito \n"
						    + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
						    + "values \n"
						    + "	(?, ?, ?)"), 
						    
	ABL_SIMULACION_PPC_CALCULADO("insert into web_recau:registro_abl_simulacion_ppc_calculado \n"
						       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
						       + "values \n"
						       + "	(?, ?, ?)"), 
	
	AUTOMOTOR_SIMULACION_PPC_CALCULADO("insert into web_recau:registro_automotores_simulacion_ppc_calculado \n"
								      + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
								      + "values \n"
								      + "	(?, ?, ?)"),
	
	ABL_PLAN_INTERBANKING("insert into web_recau:registro_abl_plan_interbanking \n"
					    + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
					    + "values \n"
					    + "	(?, ?, ?)"), 
	
	ABL_PLAN_PMC("insert into web_recau:registro_abl_plan_PMC \n"
		       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
		       + "values \n"
		       + "	(?, ?, ?)"), 
	
	ABL_PLAN_CREDITO("insert into web_recau:registro_abl_plan_credito \n"
			       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			       + "values \n"
			       + "	(?, ?, ?)"), 
	
	AUTOMOTORES_PLAN_PMC("insert into web_recau:registro_automotores_plan_PMC \n"
				       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
				       + "values \n"
				       + "	(?, ?, ?)"), 
	
	AUTOMOTORES_PLAN_INTERBANKING("insert into web_recau:registro_automotores_plan_interbanking \n"
							    + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
							    + "values \n"
							    + "	(?, ?, ?)"), 
	
	AUTOMOTORES_PLAN_CREDITO("insert into web_recau:registro_automotores_plan_credito \n"
					       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
					       + "values \n"
					       + "	(?, ?, ?)"),
					       
	NATATORIOS_INGRESO("insert into web_recau:registro_natatorios_ingreso \n"
		        + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
		        + "values \n"
		        + "	(?, ?, ?)"), 

	NATATORIOS_GENERACION("insert into web_recau:registro_natatorios_generacion \n"
				       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
				       + "values \n"
				       + "	(?, ?, ?)"), 
	
	PAGO_MULTIPLE_INGRESO("insert into web_recau:registro_pago_multiple_ingreso \n"
				       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
				       + "values \n"
				       + "	(?, ?, ?)"), 
	
	PAGO_MULTIPLE_GENERACION("insert into web_recau:registro_pago_multiple_generacion \n"
				         + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
				         + "values \n"
				         + "	(?, ?, ?)"),
	
	INSERT_FORMULARIO_PM("Insert Into web_recau:tramite_pago_multiple \n"
			   + "    (TRIBUTO, CUENTA, PERIODO, CORREO) \n"
			   + "Values \n"
			   + "    ( ? , ? , ? , ? )"), 

	BUSCAR_FORMULARIO_PM("Select max(ID_TRAMITE_PAGO_MULTIPLE) as Id \n"
				   + "From web_recau:tramite_pago_multiple \n"
				   + "Where TRIBUTO = ? \n"
				   + "And CUENTA = ? \n"
				   + "And PERIODO = ? \n"
				   + "And CORREO = ? "), 
				   
	INSERT_PAGO_MULTIPLE("Insert Into web_recau:pago_multiple \n"
				   + "    (NUMERO_COMPROBANTE, IMPORTE, ID_LUGAR_PAGO) \n"
				   + "Values ( ? , ? , (Select ID_LUGAR_PAGO From lugar_pago Where NOMBRE = ?) ) "), 
				   
	BUSCAR_PAGO_MULTIPLE("Select max(ID_PAGO_MULTIPLE) as Id \n"
				   + "From web_recau:pago_multiple \n"
				   + "Where NUMERO_COMPROBANTE = ? \n"
				   + "And ID_LUGAR_PAGO = (Select ID_LUGAR_PAGO From lugar_pago Where NOMBRE = ?) "), 
				   
	INSERT_ARCHIVO("Insert Into web_recau:archivo \n"
			 + "    (NOMBRE, TIPO_CONTENIDO, DATA) \n"
			 + "Values  \n"
			 + "    ( ? , ? , ? ) "), 
			 
	BUSCAR_ARCHIVO("Select max(ID_ARCHIVO) as Id \n"
			 + "From web_recau:archivo \n"
			 + "Where NOMBRE = ? \n"
			 + "And TIPO_CONTENIDO = ? \n"
			 + "Order By Id Desc "),
	
	RELACIONAR_FORMUARIO_CON_PAGO("Insert Into web_recau:tramite_pago_multiple_rel \n"
							+ "    (ID_TRAMITE_PAGO_MULTIPLE, ID_PAGO_MULTIPLE) \n"
							+ "Values \n"
							+ "    ( ? , ? ) "),
							
	RELACIONAR_PAGO_CON_ARCHIVO("Insert Into web_recau:pagomultiple_archivo_rel \n"
						  + "    (ID_PAGO_MULTIPLE, ID_ARCHIVO) \n"
						  + "Values \n"
						  + "    ( ? , ? ) "),
	
	BUSCAR_LUGARES_PAGO("Select NOMBRE \n"
		          + "From web_recau:lugar_pago "),
		      	
  	CEMENTERIO_CONSULTA_DEUDA("insert into web_recau:registro_cementerio_consultar_deuda \n"
  						    + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
  						    + "values \n"
  						    + "	(?, ?, ?)"),
  	  		 
  	CEMENTERIO_BOLETA_PAGO("insert into web_recau:registro_cementerio_boleta_pago \n"
  					     + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
  					     + "values \n"
  					     + "	(?, ?, ?)"),
  		  	 
  	CEMENTERIO_SIMULACION("insert into web_recau:registro_cementerio_simulacion_ppc \n"
  					 	+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
  					 	+ "values \n"
  					 	+ "	(?, ?, ?)"),
  	
  	CEMENTERIO_RECIBO("insert into web_recau:registro_cementerio_generacion_recibo \n"
  				 	+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
  				 	+ "values \n"
  				 	+ "	(?, ?, ?)"),
  	
  	CEMENTERIO_PAGAR_INTERBANKING("insert into web_recau:registro_cementerio_pagar_interbanking \n"
  							 	+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
  							 	+ "values \n"
  							 	+ "	(?, ?, ?)"),
  	
  	CEMENTERIO_PAGAR_PMC("insert into web_recau:registro_cementerio_pagar_PMC \n"
  				       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
  				       + "values \n"
  				       + "	(?, ?, ?)"),
  	
  	CEMENTERIO_PAGAR_LINKPAGOS("insert into web_recau:registro_cementerio_pagar_link_pagos \n"
						     + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
						     + "values \n"
						     + "	(?, ?, ?)"),
  	
  	CEMENTERIO_PAGAR_CREDITO("insert into web_recau:registro_cementerio_pagar_credito \n"
  					       + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
  					       + "values \n"
  					       + "	(?, ?, ?)"),
  			    
  	CEMENTERIO_SIMULACION_PPC_CALCULADO("insert into web_recau:registro_cementerio_simulacion_ppc_calculado \n"
  							          + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
  							          + "values \n"
  							          + "	(?, ?, ?)"),
  	
  	RS_CONSULTAR_DEUDA("insert into web_recau:rs_consultar_deuda \n"
	          + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
	          + "values \n"
	          + "	(?, ?, ?)"), 
  	
  	RS_BOLETA_PAGO("insert into web_recau:rs_boleta_de_pago \n"
	          + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
	          + "values \n"
	          + "	(?, ?, ?)"), 
  	
  	RS_GENERACION_RECIBO("insert into web_recau:rs_generacion_recibo \n"
	          + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
	          + "values \n"
	          + "	(?, ?, ?)"), 
  	
  	RS_PAGO_INTERBANKING("insert into web_recau:rs_pagar_interbanking \n"
	          + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
	          + "values \n"
	          + "	(?, ?, ?)"), 
  	
  	RS_PAGO_CREDITO("insert into web_recau:rs_pagar_credito \n"
	          + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
	          + "values \n"
	          + "	(?, ?, ?)"), 
  	
  	RS_PAGO_PMC("insert into web_recau:rs_pagar_PMC \n"
	          + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
	          + "values \n"
	          + "	(?, ?, ?)"), 
  	
  	RS_LINK_PAGOS("insert into web_recau:rs_pagar_link_pagos \n"
	          + "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
	          + "values \n"
	          + "	(?, ?, ?)"),

	ABL_PAGAR_MP("insert into web_recau:registro_abl_pagar_mp \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	AUTOMOTORES_PAGAR_MP("insert into web_recau:registro_automotores_pagar_mp \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	CEMENTERIO_PAGAR_MP("insert into web_recau:registro_cementerio_pagar_mp \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	RS_PAGAR_MP("insert into web_recau:registro_rs_pagar_mp \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	ABL_PLAN_MP("insert into web_recau:registro_abl_plan_mp \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	AUTOMOTORES_PLAN_MP("insert into web_recau:registro_automotores_plan_mp \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	CEMENTERIO_PLAN_MP("insert into web_recau:registro_cementerio_plan_mp \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	RS_PLAN_MP("insert into web_recau:registro_rs_plan_mp \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	RS_PLAN_INTERBANKING("insert into web_recau:registro_rs_plan_interbanking \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	RS_PLAN_PMC("insert into web_recau:registro_rs_plan_PMC \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	RS_PLAN_CREDITO("insert into web_recau:registro_rs_plan_credito \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	RS_PLAN_LINK("insert into web_recau:registro_rs_plan_link_pagos \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	CEMENTERIO_PLAN_INTERBANKING("insert into web_recau:registro_cementerio_plan_interbanking \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	CEMENTERIO_PLAN_PMC("insert into web_recau:registro_cementerio_plan_PMC \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	CEMENTERIO_PLAN_CREDITO("insert into web_recau:registro_cementerio_plan_credito \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	CEMENTERIO_PLAN_LINK("insert into web_recau:registro_cementerio_plan_link_pagos \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	RS_SIMULACION("insert into web_recau:registro_rs_simulacion_ppc \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),

	RS_SIMULACION_PPC_CALCULADO("insert into web_recau:registro_rs_simulacion_ppc_calculado \n"
			+ "	(nombre_usuario, fecha_registro, id_aplicacion) \n "
			+ "values \n"
			+ "	(?, ?, ?)"),
	;

	
	private String sql;

	private QueriesMySQL(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}