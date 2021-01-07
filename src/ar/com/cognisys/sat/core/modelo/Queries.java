package ar.com.cognisys.sat.core.modelo;

public enum Queries {

	CUENTA_TODAS("Select 'ABL' Categoria, C_Cuenta Numero_Cuenta, Trim(D_Calle) || ' ' || Nvl(N_Nro_Pro,'') || ' ' || Nvl(C_Piso_Pro,'') || ' ' || Nvl(C_Dpto_Pro,'') Descripcion \n "
			   + "From Calles C , Abl A \n "
			   + "Where A.C_Calle_Pro = C.C_Calle \n "
			   + "And A.C_Cuenta In (Select Unique Cuenta \n "
			   + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
			   + "                   Where Tipo_Cuenta = 1) \n "
			   + "Union \n "
			   + "Select 'COMERCIOS' Categoria, C_Cuenta Numero_Cuenta, D_Razon_Social Descripcion \n "
			   + "From Comercios \n "
			   + "Where C_Cuenta In (Select Unique Cuenta \n "
			   + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
			   + "                   Where Tipo_Cuenta = 2) \n "
			   + "Union \n "
			   + "Select 'VEHICULOS' Categoria, C_Cuenta Numero_Cuenta, C_Dominio_Actual||' '||N_Modelo Descripcion \n "
			   + "From Vehiculos \n "
			   + "Where C_Cuenta In (Select Unique Cuenta \n "
			   + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
			   + "                   Where Tipo_Cuenta = 3) \n "
			   + "Union \n "
			   + "Select 'RODADOS' Categoria, C_Cuenta Numero_Cuenta, Trim(D_Marca)||' '||N_Cilindrada Descripcion \n "
			   + "From Rodados \n "
			   + "Where C_Cuenta In (Select Unique Cuenta \n "
			   + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
			   + "                   Where Tipo_Cuenta = 4) "),
	
	CUENTAS_ABL("Select Skip ? First ? \n "
			  + "       'ABL' Categoria, A.C_Cuenta Numero_Cuenta, Trim(D_Calle) || ' ' || Nvl(N_Nro_Pro,'') || ' ' || Nvl(C_Piso_Pro,'') || ' ' || Nvl(C_Dpto_Pro,'') Descripcion \n "
			  + "From Calles C , Abl A, Contrib_Cuentas Cc, Contribuyentes Co \n "
			  + "Where A.C_Calle_Pro = C.C_Calle \n "
			  + "And A.C_Cuenta In (Select Unique Cuenta \n "
			  + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
			  + "                   Where Tipo_Cuenta = 1) \n "
			  + "And Cc.C_Cuenta = A.C_Cuenta \n "
			  + "And Cc.C_Sistema = 2 \n "
			  + "And A.C_Calle_Pro = C.C_Calle \n "
			  + "And Co.C_Documento = Cc.C_Documento \n "
			  + "And Co.N_Documento = Cc.N_Documento \n "
			  + "And (Upper('' || A.C_Cuenta) Like UPPER(?) \n "
			  + "	  Or Upper(Trim(D_Calle) || ' ' || Nvl(N_Nro_Pro,'') || ' ' || Nvl(C_Piso_Pro,'') || ' ' || Nvl(C_Dpto_Pro,'')) Like UPPER(?) \n "
			  + "	  Or Upper(Co.D_Nombre) Like UPPER(?) \n "
			  + "	  Or Upper(Co.D_Apellido) Like UPPER(?)) "),
			  
	CUENTAS_CANTIDAD_ABL("Select Count(*) as Cantidad\n "
					   + "From Calles C , Abl A, Contrib_Cuentas Cc, Contribuyentes Co \n "
					   + "Where A.C_Calle_Pro = C.C_Calle \n "
					   + "And A.C_Cuenta In (Select Unique Cuenta \n "
					   + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
					   + "                   Where Tipo_Cuenta = 1) \n "
					   + "And Cc.C_Cuenta = A.C_Cuenta \n "
					   + "And Cc.C_Sistema = 2 \n "
					   + "And A.C_Calle_Pro = C.C_Calle \n "
					   + "And Co.C_Documento = Cc.C_Documento \n "
					   + "And Co.N_Documento = Cc.N_Documento \n "
					   + "And (Upper('' || A.C_Cuenta) Like UPPER(?) \n "
					   + "	  Or Upper(Trim(D_Calle) || ' ' || Nvl(N_Nro_Pro,'') || ' ' || Nvl(C_Piso_Pro,'') || ' ' || Nvl(C_Dpto_Pro,'')) Like UPPER(?) \n "
					   + "	  Or Upper(Co.D_Nombre) Like UPPER(?) \n "
					   + "	  Or Upper(Co.D_Apellido) Like UPPER(?)) "),
	
	CUENTAS_VEHICULOS("Select Skip ? First ? \n "
					+ "       'VEHICULOS' Categoria, V.C_Cuenta Numero_Cuenta, V.C_Dominio_Actual||' '||V.N_Modelo Descripcion, V.C_Dominio_Actual Dominio, \n "
					+ "       Cc.C_Documento, Cc.N_Documento, \n "
					+ "       Co.D_Nombre, Co.D_Apellido, C.D_Calle, Co.N_Nro, Co.C_Piso, Co.C_Dpto, Co.C_Postal, Co.D_Telefono, Co.D_Fax  \n "
					+ "From Vehiculos V, Contrib_Cuentas Cc, Contribuyentes Co, Calles C  \n "
					+ "Where V.C_Cuenta In (Select Unique Cuenta \n "
					+ "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
					+ "                   Where Tipo_Cuenta = 3) \n "
					+ "And Cc.C_Cuenta = V.C_Cuenta \n "
					+ "And Cc.C_Sistema = 31 \n "
					+ "And Co.C_Calle = C.C_Calle \n "
					+ "And Co.C_Documento = Cc.C_Documento \n "
					+ "And Co.N_Documento = Cc.N_Documento \n "
					+ "And (Upper('' || V.C_Cuenta) Like UPPER(?) \n "
					+ "		Or Upper(V.C_Dominio_Actual||' '||V.N_Modelo) Like UPPER(?) \n "
					+ "		Or Upper(Co.D_Nombre) Like UPPER(?) \n "
					+ "		Or Upper(Co.D_Apellido) Like UPPER(?)) "),
	
	CUENTAS_CANTIDAD_VEHICULOS("Select Count(*) as Cantidad \n "
							 + "From Vehiculos V, Contrib_Cuentas Cc, Contribuyentes Co, Calles C  \n "
							 + "Where V.C_Cuenta In (Select Unique Cuenta \n "
							 + "                   From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
							 + "                   Where Tipo_Cuenta = 3) \n "
							 + "And Cc.C_Cuenta = V.C_Cuenta \n "
							 + "And Cc.C_Sistema = 31 \n "
							 + "And Co.C_Calle = C.C_Calle \n "
							 + "And Co.C_Documento = Cc.C_Documento \n "
							 + "And Co.N_Documento = Cc.N_Documento \n "
							 + "And (Upper('' || V.C_Cuenta) Like UPPER(?) \n "
							 + "		Or Upper(V.C_Dominio_Actual||' '||V.N_Modelo) Like UPPER(?) \n "
							 + "		Or Upper(Co.D_Nombre) Like UPPER(?) \n "
							 + "		Or Upper(Co.D_Apellido) Like UPPER(?)) "),
	
	CUENTAS_RODADOS("Select Skip ? First ? \n "
				  + "       'RODADOS' Categoria, R.C_Cuenta Numero_Cuenta, Trim(R.D_Marca)||' '||R.N_Cilindrada Descripcion, DECODE(TRIM(nvl(r.c_dominio, r.c_mercosur)), \"\", r.c_mercosur, r.c_mercosur, r.c_mercosur, r.c_dominio) as Dominio,\n "
				  + "       Cc.C_Documento, Cc.N_Documento, \n "
				  + "       Co.D_Nombre, Co.D_Apellido, C.D_Calle, Co.N_Nro, Co.C_Piso, Co.C_Dpto, Co.C_Postal, Co.D_Telefono, Co.D_Fax  \n "
				  + "From Rodados R, Contrib_Cuentas Cc, Contribuyentes Co, Calles C  \n "
				  + "Where R.C_Cuenta In (Select Unique Cuenta \n "
				  + "                     From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
				  + "                     Where Tipo_Cuenta = 4) \n "
				  + "And Cc.C_Cuenta = R.C_Cuenta \n "
				  + "And Cc.C_Sistema = 4 \n "
				  + "And Co.C_Calle = C.C_Calle \n "
				  + "And Co.C_Documento = Cc.C_Documento \n "
				  + "And Co.N_Documento = Cc.N_Documento \n "
				  + "And (Upper('' || R.C_Cuenta) Like UPPER(?) \n "
				  + "	  Or Upper(Trim(R.D_Marca)||' '||R.N_Cilindrada) Like UPPER(?) \n "
				  + "	  Or Upper(Co.D_Nombre) Like UPPER(?) \n "
				  + "	  Or Upper(Trim(R.c_dominio)) Like UPPER(?) \n "
				  + "	  Or Upper(Trim(R.c_mercosur)) Like UPPER(?) \n "
				  + "	  Or Upper(Co.D_Apellido) Like UPPER(?)) "),
	
	CUENTAS_CANTIDAD_RODADOS("Select Count(*) as Cantidad \n "
						   + "From Rodados R, Contrib_Cuentas Cc, Contribuyentes Co, Calles C  \n "
						   + "Where R.C_Cuenta In (Select Unique Cuenta \n "
						   + "                     From Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n "
						   + "                     Where Tipo_Cuenta = 4) \n "
						   + "And Cc.C_Cuenta = R.C_Cuenta \n "
						   + "And Cc.C_Sistema = 4 \n "
						   + "And Co.C_Calle = C.C_Calle \n "
						   + "And Co.C_Documento = Cc.C_Documento \n "
						   + "And Co.N_Documento = Cc.N_Documento \n "
						   + "And (Upper('' || R.C_Cuenta) Like UPPER(?) \n "
						   + "	  Or Upper(Trim(R.D_Marca)||' '||R.N_Cilindrada) Like UPPER(?) \n "
						   + "	  Or Upper(Co.D_Nombre) Like UPPER(?) \n "
						   + "	  Or Upper(Trim(R.c_dominio)) Like UPPER(?) \n "
						   + "	  Or Upper(Trim(R.c_mercosur)) Like UPPER(?) \n "
						   + "	  Or Upper(Co.D_Apellido) Like UPPER(?)) "),
	
	BUSCAR_USUARIO("Select U.Id_Usuario, U.Cuit, U.Nombre_Usuario, U.nombre, U.apellido, U.Clave, U.Correo, U.Telefono As Telefono1, U.Fax As Telefono2, U.Fecha As Fecha_Alta, U.Flag_Activacion, U.Flag_Newsletter, U.flag_tyc, U.nivel \n "
				 + "From Web_Recau:Usuario_Cogmvl U \n "
				 + "Where U.Id_Usuario = ? \n"
			     + "AND U.flag_baja = 0 "),
	
	BUSCAR_USUARIO_CORREO("Select U.Id_Usuario, U.Cuit, U.Nombre_Usuario, U.nombre, U.apellido, U.Clave, U.Correo, U.Telefono As Telefono1, U.Fax As Telefono2, U.Fecha As Fecha_Alta, U.Flag_Activacion, U.Flag_Newsletter, U.flag_tyc, U.nivel \n "
					    + "From Web_Recau:Usuario_Cogmvl U \n "
					    + "Where U.Correo = ? \n"
					    + "AND U.flag_baja = 0 "),
	
	BUSCAR_USUARIO_CUIT("Select U.Id_Usuario, U.Cuit, U.Nombre_Usuario, U.nombre, U.apellido, U.Clave, U.Correo, U.Telefono As Telefono1, U.Fax As Telefono2, U.Fecha As Fecha_Alta, U.Flag_Activacion, U.Flag_Newsletter, U.flag_tyc, U.nivel \n "
					  + "From Web_Recau:Usuario_Cogmvl U \n "
					  + "Where U.Cuit = ? \n"
					  + "And U.flag_baja = 0 "),
	
	GET_USUARIOS_X_CUENTA("Select U.Id_Usuario, U.Cuit, U.Nombre_Usuario, U.nombre, U.apellido, U.Clave, U.Correo, U.Telefono As Telefono1, U.Fax As Telefono2, U.Fecha As Fecha_Alta, U.Flag_Activacion, U.Flag_Newsletter, U.flag_tyc, U.nivel \n "
						+ "From Web_Recau:Usuario_Cogmvl U, Web_Recau:Usuario_Cuentas_Rel_Cogmvl C \n"
						+ "Where C.Cuenta = ? \n"
						+ "And C.Tipo_Cuenta = ? \n"
						+ "And U.Id_Usuario = C.Id_Usuario \n"
						+ "And U.flag_baja = 0 \n"
						+ "And U.Id_Usuario Not In (1,100)"),

	BUSCAR_LISTA_USUARIOS_COMPLETO(
			"Select Skip ? First ? \n"
					+ "	   U.Id_Usuario, U.Cuit, U.Nombre_Usuario, U.nombre, U.apellido, U.Clave, U.Correo, U.Telefono As Telefono1, U.Fax As Telefono2, U.Fecha As Fecha_Alta, U.Flag_Activacion, U.Flag_Newsletter, U.flag_tyc, U.nivel \n"
					+ "From Web_Recau:Usuario_Cogmvl U \n "
					+ "Where U.flag_baja = 0 \n"),

	BUSCAR_USUARIOS_POR_CUIT(
			"Select Skip ? First ? \n"
					+ "		  U.Id_Usuario, U.Cuit, U.Nombre_Usuario, U.nombre, U.apellido, U.Clave, U.Correo, U.Telefono As Telefono1, U.Fax As Telefono2, U.Fecha As Fecha_Alta, U.Flag_Activacion, U.Flag_Newsletter, U.flag_tyc, U.nivel \n"
					+ "From Web_Recau:Usuario_Cogmvl U \n "
					+ "Where  U.Cuit Like ? \n"
					+ "And U.flag_baja = 0 \n"),

	BUSCAR_USUARIOS_POR_CORREO(
			"Select Skip ? First ? \n"
					+ "		  U.Id_Usuario, U.Cuit, U.Nombre_Usuario, U.nombre, U.apellido, U.Clave, U.Correo, U.Telefono As Telefono1, U.Fax As Telefono2, U.Fecha As Fecha_Alta, U.Flag_Activacion, U.Flag_Newsletter, U.flag_tyc, U.nivel \n"
					+ "From Web_Recau:Usuario_Cogmvl U \n "
					+ "Where U.Correo Like ?  \n"
					+ "And U.flag_baja = 0 \n"),

	USUARIOS_CANTIDAD(
			"Select Count(*) As Cantidad \n"
					+ "From Web_Recau:Usuario_Cogmvl U \n"
					+ "Where U.flag_baja = 0 \n"),

	CANTIDAD_USUARIOS_POR_CUIT(
			"Select Count(*) As Cantidad \n"
					+ "From Web_Recau:Usuario_Cogmvl U \n "
					+ "Where  U.Cuit Like ? \n"
					+ "And U.flag_baja = 0 \n"),

	CANTIDAD_USUARIOS_POR_CORREO(
			"Select Count(*) As Cantidad \n"
					+ "From Web_Recau:Usuario_Cogmvl U \n "
					+ "Where U.Correo Like ?  \n"
					+ "And U.flag_baja = 0 \n"),
								 
	ELIMINAR_CUENTAS_USUARIO("Delete from Web_Recau:Usuario_Cuentas_Rel_Cogmvl \n"
			   			   + "Where Id_Usuario = ? "),
	
	ELIMINAR_USUARIO("Update Web_Recau:Usuario_Cogmvl \n"
				   	+ "Set flag_baja = 1, fecha_baja = current \n"
					+ "Where Id_Usuario = ? "),
	
	BUSCAR_TODOS_DOCUMENTOS_INTERFAZ_GUIA_TRAMITE("Select d.ID_DOCUMENTO_INTERFAZ, d.NOMBRE, d.DESCRIPCION, d.FECHA_ALTA, d.FECHA_ACTUALIZACION, c.NOMBRE as CATEGORIA \n"
												 + "From documento_interfaz d, categoria_interfaz c \n"
												 + "Where c.NOMBRE = 'Guías de trámite' \n"
												 + "And d.ID_CATEGORIA_INTERFAZ = c.ID_CATEGORIA_INTERFAZ "),
														
	BUSCAR_ARCHIVOS_DOCUMENTO("Select a.ID_ARCHIVO, a.NOMBRE as NOMBRE_ARCHIVO, a.TIPO_CONTENIDO, a.FECHA, a.DATA \n"
							+ "From documento_interfaz_archivo_rel d, archivo a \n"
							+ "Where d.ID_DOCUMENTO_INTERFAZ = ? \n"
							+ "And a.ID_ARCHIVO = d.ID_ARCHIVO "),
												
	BUSCAR_TRAMITE_PAGO_DOBLE("Select ID_TRAMITE_PAGO_MULTIPLE, TRIBUTO, CUENTA, PERIODO, ESTADO, FECHA_CREACION, FECHA_ACTUALIZACION, CORREO \n"
							+ "From tramite_pago_multiple "),
	
	BUSCAR_PAGO_DOBLE("Select pm.ID_PAGO_MULTIPLE, pm.NUMERO_COMPROBANTE, pm.IMPORTE, (Select NOMBRE From lugar_pago Where ID_LUGAR_PAGO = pm.ID_LUGAR_PAGO) as LUGAR_PAGO, pm.FECHA  \n"
					+ "From tramite_pago_multiple_rel tpmr, pago_multiple pm  \n"
					+ "Where tpmr.ID_TRAMITE_PAGO_MULTIPLE = ? \n"
					+ "And pm.ID_PAGO_MULTIPLE = tpmr.ID_PAGO_MULTIPLE "),
			
	BUSCAR_ARCHIVO_PAGO_DOBLE("Select a.ID_ARCHIVO, a.NOMBRE, a.TIPO_CONTENIDO, a.FECHA, a.DATA \n"
							+ "From pagomultiple_archivo_rel par, archivo a \n"
							+ "Where par.ID_PAGO_MULTIPLE = ? \n"
							+ "And a.ID_ARCHIVO = par.ID_ARCHIVO "), 
							
	ELIMINAR_RELACION_DOCUMENTO_INTERFAZ_ARCHIVO("Delete From documento_interfaz_archivo_rel \n"
											   + "Where ID_DOCUMENTO_INTERFAZ = ? "), 
	
	ELIMINAR_DOCUMENTO_INTERFAZ("Delete From documento_interfaz \n"
			   				  + "Where ID_DOCUMENTO_INTERFAZ = ? "), 
	
	INSERTAR_DOCUMENTO_INTERFAZ("Insert Into documento_interfaz \n"
							  + "	(NOMBRE, DESCRIPCION, ID_CATEGORIA_INTERFAZ) \n"
							  + "Values \n"
							  + "	( ? , ? , (Select ID_CATEGORIA_INTERFAZ From categoria_interfaz Where NOMBRE = ?) ) "), 
	
	INSERTAR_RELACION_DOCUMENTO_INTERFAZ_ARCHIVO("Insert Into documento_interfaz_archivo_rel \n"
											   + "	(ID_DOCUMENTO_INTERFAZ, ID_ARCHIVO) \n"
											   + "Values"
											   + "	( ? , ? )"), 
	
	ACTUALIZAR_DOCUMENTO_INTERFAZ("Update documento_interfaz \n"
								+ "Set NOMBRE = ?, DESCRIPCION = ?, CATEGORIA = ?, FECHJA_ACTUALIZACION = ? \n"
								+ "Where ID_DOCUMENTO_INTERFAZ = ? "),
	
	INSERT_ARCHIVO("Insert Into web_recau:archivo \n"
				 + "    ( nombre, tipo_contenido, ruta ) \n"
				 + "Values  \n"
				 + "    ( ? , ? , ? ) "), 
	
	ELIMINAR_ARCHIVO("Delete From web_recau:archivo \n "
				   + "Where ID_ARCHIVO = ? "), 
				   
	BUSCAR_CATEGORIAS("Select NOMBRE \n"
					+ "From categorias_interfaz \n"
					+ "Order By NOMBRE "),
					
	RELACIONAR_PANTALLA_GRUPO("INSERT INTO web_recau:pantalla_grupo_rel (id_pantalla, id_grupo) \n" +
			  "VALUES ( ? , ? ) "),
			  
	RECUPERAR_PANTALLAS("Select * from web_recau:pantalla"),

	RECUPERAR_PANTALLA("Select id, nombre \n"
			         + "from web_recau:pantalla \n"
			         + "Where nombre = ? "),
			         
    RECUPERAR_GRUPOS("Select g.id, g.nombre, g.id_padre, g.disponible, g.orden \n"
				   + "From web_recau:grupo g, web_recau:pantalla p, web_recau:pantalla_grupo_rel r \n"
				   + "where r.id_pantalla = ? \n"
				   + "And g.id = r.id_grupo \n"
				   + "And p.id = r.id_pantalla \n"
				   + "Order By orden asc"),
	
	RECUPERAR_GRUPOS_PADRES("Select * from grupo \n"
						  + "where id_padre is null \n"
						  + "order by orden asc"),
	
    RECUPERAR_GRUPOS_HIJOS("Select * from web_recau:grupo \n"
    					 + "where id_padre = ? \n"
    					 + "order by orden asc"),
						  
	INSERT_GRUPO_SPADRE("Insert Into web_recau:grupo(nombre, orden) \n"
			   + "Values ( ? , ? )"), 
   
	INSERT_GRUPO_CPADRE("Insert Into web_recau:grupo(nombre, orden , id_padre) \n"
			   + "Values ( ? , ? , ? )"), 
						   			   
			   
	UPDATE_GRUPO("Update web_recau:grupo \n"
			   + "Set nombre = ? \n"
			   + "Where id = ? "),
	
	UPDATE_REUBICAR_GRUPO("Update web_recau:grupo \n"
					    + "Set id_padre = ? \n"
					    + "Where id = ? "),
		
	AGREGAR_ITEM("insert into web_recau:item (titulo, descripcion, orden) \n"
			   + "values ( ? , ? , ? )"),
	
	BUSCAR_ITEM("Select id \n"
			  + "From web_recau:item \n"
			  + "Where titulo = ? \n"
			  + "And descripcion = ? "),
	
	AGREGAR_RELACION_ITEM_GRUPO("insert into web_recau:grupo_item_rel (id_grupo, id_item) \n"
						      + "values ( ? , ? )"),
	
	AGREGAR_RELACION_ARCHIVO_ITEM("insert into web_recau:item_archivo_rel(id_item, id_archivo) \n"
								+ "values ( ? , ? )"),
								
    AGREGAR_RELACION_ARCHIVO_GRUPO("insert into web_recau:grupo_archivo_rel(id_grupo, id_archivo) \n"
								+ "values ( ? , ? )"),								
	
	EDITAR_ITEM("Update web_recau:item \n"
			  + "Set titulo = ?, descripcion = ? \n"
			  + "Where id = ? "),
	
	ELIMINAR_ITEM("Delete From web_recau:item \n"
				+ "Where id = ? "),
				
	ELIMINAR_RELACION_GRUPO_ITEM("Delete From web_recau:grupo_item_rel \n"
							   + "Where id_item = ? "),
				
	ESPERO_ARCHIVO("select 1 from grupo_archivo_rel \n"
				+  "where id_grupo = ?"),
				
	RECUPERAR_ITEMS("Select id, titulo, descripcion, disponible, orden \n"
				  + "From web_recau:item i, web_recau:grupo_item_rel r \n"
				  + "Where r.id_grupo = ? \n"
				  + "And i.id = r.id_item \n "
				  + "Order By orden asc"),

	RECUPERAR_ID_ARCHIVO("Select max(id_archivo) as id_archivo \n"
					   + "From web_recau:archivo \n"
					   + "Where nombre = ?"),
				  
	RECUPERAR_ID_ARCHIVO_ITEM("Select id_archivo \n"
							+ "From web_recau:item_archivo_rel \n"
							+ "Where id_item = ? "), 
							
	RECUPERAR_ID_ARCHIVO_GRUPO("Select id_archivo \n"
							+ "From web_recau:grupo_archivo_rel \n"
							+ "Where id_grupo = ? "), 
							
	ELIMINAR_RELACION_ITEM_ARCHIVO("Delete from web_recau:item_archivo_rel \n"
								 + "Where id_item = ? "), 
	
    ELIMINAR_RELACION_GRUPO_ARCHIVO("Delete from web_recau:grupo_archivo_rel \n"
  							      + "Where id_grupo = ? "), 
								 
    RECUPERAR_ID_GRUPO("Select max(id) as idgrupo \n"
  					+  "From web_recau:grupo \n"
  					+  "Where nombre = ?"),
  				
    RECUPERAR_GRUPO_PREGUNTAS("Select id, nombre, id_padre, disponible, orden \n "
				    		+ "From web_recau:grupo \n "
				    		+ "Where Nombre = 'Todas' "
				    		+ "Order By orden asc"),
				    		
	ELIMINAR_GRUPO("Delete From web_recau:grupo \n"
				 + "Where id = ? "), 
	
	BUSCAR_ARCHIVO("Select ID_ARCHIVO, NOMBRE, TIPO_CONTENIDO, FECHA, RUTA \n"
   				 + "From web_recau:archivo \n"
   				 + "Where ID_ARCHIVO = ? "), 
   				 
	RECUPERAR_GRUPO_POR_PADRE("select id, nombre, disponible, orden \n "
							+ "from web_recau:grupo g \n "
							+ "where g.id_padre = ? \n "
							+ "and g.orden = ?" ),

	RECUPERAR_GRUPO_POR_PANTALLA("select id, nombre, disponible, orden \n "
							   + "from 	web_recau:grupo g, web_recau:pantalla_grupo_rel pgr \n "
							   + "where g.id = pgr.id_grupo \n "
							   + "and 	pgr.id_pantalla = ? \n "
							   + "and 	g.orden = ?" ), 
							   
	ACTUALIZAR_ORDEN_GRUPO("Update web_recau:grupo \n"
						 + "Set orden = ? \n"
						 + "Where id = ? "), 
	RECUPERAR_MAXIMO_ORDEN_POR_PADRE("select max(orden) as max_orden \n "
			   + "from web_recau:grupo g \n "
			   + "where g.id_padre = ?"),
						   
    RECUPERAR_MAXIMO_ORDEN_POR_PANTALLA("select max(orden) as max_orden \n "
										+ "from web_recau:grupo g, web_recau:pantalla_grupo_rel pgr \n "
										+ "where g.id = pgr.id_grupo \n "
										+ "and 	pgr.id_pantalla = ? "),
				  
	ACTUALIZAR_DISPONIBILIDAD_GRUPO("Update web_recau:grupo \n"
								   + "Set disponible = ? \n"
								   + "Where id = ? "),
						 
	RECUPERAR_ITEM("select i.id, i.titulo, i.descripcion, i.disponible, i.orden \n "
				 + "from web_recau:grupo_item_rel gir, web_recau:item i \n "
				 + "where i.id = gir.id_item \n "
				 + "and 	gir.id_grupo = ? \n "
				 + "and 	i.orden = ? "),

				  
	ACTUALIZAR_ORDEN_ITEM("Update web_recau:item \n"
						 + "Set orden = ? \n"
						 + "Where id = ? "),
				 
	 RECUPERAR_MAXIMO_ORDEN_ITEM("select max(i.orden) as max_orden \n "
							 + "from web_recau:grupo_item_rel gir, web_recau:item i \n "
							 + "where i.id = gir.id_item \n "
							 + "and 	gir.id_grupo = ? "),
							 
							   
	 ACTUALIZAR_DISPONIBILIDAD_ITEM("Update web_recau:item \n"
								  + "Set disponible = ? \n"
								  + "Where id = ? "),
	 
	 /**
	  * 1->FechaDesde
	  * 2->FechaHasta
	  * 3->Tecnologia
	  */
	 RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_DIAS("SELECT  TO_CHAR(Meses, '%d/%m/%Y') AS Fecha,\r\n" + 
	 		                                    "        SUM(Interbanking) AS Interbanking,\r\n" + 
	 		                                    "        SUM(PMC) AS PMC,\r\n" + 
	 		                                    "        SUM(Credito) AS Credito,\r\n" + 
	 		                                    "        SUM(Recibos) AS Recibos\r\n" + 
	 		                                    "FROM (SELECT fecha AS Meses,\r\n" + 
	 		                                    "             SUM(total_interbanking) AS Interbanking,\r\n" + 
	 		                                    "             SUM(total_pmc) AS PMC,\r\n" + 
	 		                                    "             SUM(total_tarjeta_credito) AS Credito,\r\n" + 
	 		                                    "             SUM(total_recibos) AS Recibos\r\n" + 
	 		                                    "      FROM web_recau:estadistica_general\r\n" + 
	 		                                    "      WHERE TO_CHAR(fecha, '%Y/%m/%d') BETWEEN ? and ?\r\n" + 
	 		                                    "      AND id_aplicacion = ?\r\n" + 
	 		                                    "      GROUP BY fecha\r\n" + 
	 		                                    "      ORDER BY Fecha DESC) x\r\n" + 
	 											"GROUP BY fecha;"),
	 
	 /**
	  * 1->FechaDesde
	  * 2->FechaHasta
	  * 3->Tecnologia
	  */
	 RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_SEMANAS("SELECT  web_recau:week_of_year(meses) || '/' || TO_CHAR(Meses, '%Y') as Fecha,\n"
			   									 + "        SUM(Interbanking) as Interbanking,\n"
			   									 + "        SUM(PMC) as PMC,\n"
			   									 + "        SUM(Credito) as Credito,\n"
			   									 + "        SUM(Recibos) as Recibos\n"
			   									 + "FROM (SELECT fecha AS Meses,\n"
			   									 + "             SUM(total_interbanking) as Interbanking,\n"
			   									 + "             SUM(total_pmc) as PMC,\n"
			   									 + "             SUM(total_tarjeta_credito) AS Credito,\n"
			   									 + "             SUM(total_recibos) AS Recibos\n"
			   									 + "      FROM web_recau:estadistica_general\n"
			   									 + "      WHERE TO_CHAR(fecha, '%Y/%m/%d') between ? and ?\n"
			   									 + "      AND id_aplicacion = ?\n"
			   									 + "      GROUP BY fecha\n"
			   									 + "      ORDER BY Fecha DESC) x\n"
			   									 + "GROUP BY fecha;"),
	 
	 /**
	  * 1->FechaDesde
	  * 2->FechaHasta
	  * 3->Tecnologia
	  */
	 RECUPERAR_ESTADISTICAS_GENERAl_ORIGEN_MESES("SELECT  TO_CHAR(Meses, '%m/%Y') as Fecha,\n"
	 										   + "        SUM(Interbanking) as Interbanking,\n"
	 										   + "        SUM(PMC) as PMC,\n"
	 										   + "        SUM(Credito) as Credito,\n"
	 										   + "        SUM(Recibos) as Recibos\n"
	 										   + "FROM (SELECT fecha AS Meses,\n"
	 										   + "             SUM(total_interbanking) as Interbanking,\n"
	 										   + "             SUM(total_pmc) as PMC,\n"
	 										   + "             SUM(total_tarjeta_credito) AS Credito,\n"
	 										   + "             SUM(total_recibos) AS Recibos\n"
	 										   + "      FROM web_recau:estadistica_general\n"
	 										   + "      WHERE TO_CHAR(fecha, '%Y/%m/%d') between ? and ?\n"
	 										   + "      AND id_aplicacion = ?\n"
	 										   + "      GROUP BY fecha\n"
	 										   + "      ORDER BY Fecha DESC) x\n"
	 										   + "GROUP BY fecha;"),
	 /**
	  * 1-> FechaDesde
	  * 2-> FechaHasta
	  */
	 RECUPERAR_ESTADISTICAS_GENERAL_COMPLETA_DIAS("SELECT Dia,\n"
	 											+ "       medio AS Medio,\n"
	 											+ "       tributo AS Tributo,\n"
	 											+ "       SUM(Web) AS Web,\n"
	 											+ "       SUM(Mobile) AS Mobile,\n"
	 											+ "       SUM(Total) AS Total\n"
	 											+ "FROM(SELECT TO_CHAR(fecha, '%Y/%m/%d') AS Dia,\n"
	 											+ "            medio AS Medio,\n"
	 											+ "            tributo AS Tributo,\n"
	 											+ "            SUM(total_web) AS Web,\n"
	 											+ "            SUM(total_mobile) AS Mobile,\n"
	 											+ "            SUM(total) AS Total\n"
	 											+ "     FROM web_recau:estadistica_completa\n"
	 											+ "     where TO_CHAR(fecha, '%Y/%m/%d') between ? and ?\n"
	 											+ "     GROUP BY fecha, medio, tributo\n"
	 											+ "     ORDER BY fecha DESC) x\n"
	 											+ "GROUP by medio, tributo,dia ORDER BY Dia DESC;"), 
	 
	 /**
	  * 1-> FechaDesde
	  * 2-> FechaHasta
	  */
	 RECUPERAR_ESTADISTICAS_GENERAL_COMPLETA_SEMANAS("SELECT Semana,\n"
												   + "       medio AS Medio,\n"
												   + "       tributo AS Tributo,\n"
												   + "       SUM(Web) AS Web,\n"
												   + "       SUM(Mobile) AS Mobile,\n"
												   + "       SUM(Total) AS Total\n"
												   + "FROM(SELECT TO_CHAR(fecha, '%Y/%U') AS Semana,\n"
												   + "            medio AS Medio,\n"
												   + "            tributo AS Tributo,\n"
												   + "            SUM(total_web) AS Web,\n"
												   + "            SUM(total_mobile) AS Mobile,\n"
												   + "            SUM(total) AS Total\n"
												   + "     FROM web_recau:estadistica_completa\n"
												   + "     where TO_CHAR(fecha, '%Y/%m/%d') between ? and ?\n"
												   + "     GROUP BY fecha, medio, tributo\n"
												   + "     ORDER BY fecha DESC) x\n"
												   + "GROUP by medio, tributo,semana ORDER BY Semana DESC;"),
	 
	 /**
	  * 1-> FechaDesde
	  * 2-> FechaHasta
	  */
	 RECUPERAR_ESTADISTICAS_GENERAL_COMPLETA_MESES("SELECT Mes,\n"
												 + "       medio AS Medio,\n"
												 + "       tributo AS Tributo,\n"
												 + "       SUM(Web) AS Web,\n"
												 + "       SUM(Mobile) AS Mobile,\n"
												 + "       SUM(Total) AS Total\n"
												 + "FROM(SELECT TO_CHAR(fecha, '%Y/%m') AS Mes,\n"
												 + "            medio AS Medio,\n"
												 + "            tributo AS Tributo,\n"
												 + "            SUM(total_web) AS Web,\n"
												 + "            SUM(total_mobile) AS Mobile,\n"
												 + "            SUM(total) AS Total\n"
												 + "     FROM web_recau:estadistica_completa\n"
												 + "     where TO_CHAR(fecha, '%Y/%m/%d') between ? and ?\n"
												 + "     GROUP BY fecha, medio, tributo\n"
												 + "     ORDER BY fecha DESC) x\n"
												 + "GROUP by medio, tributo,mes ORDER BY mes DESC;"), 
	 /**
	  * 1 >> inicio</br>
	  * 2 >> cantidad</br>
	  * 3 >> filtro
	  * @return
	  * Devuelve una lista de cuentas
	  */
	 CUENTAS_CEMENTERIO("Select skip ? first ? 'CEMENTERIO' Categoria, cuenta as Cuenta \n"
	 		          + "From web_recau:usuario_cuentas_rel_cogmvl \n"
	 		          + "Where tipo_cuenta = 5 \n"
	 		          + "and Upper( '' || cuenta) Like UPPER(?) \n"
	 		          + "GROUP by cuenta"),
	 
	 /**
	  * 1 >> filtro
	  * @return
	  * Devuelve la cantidad de cuentas.
	  */
	 CUENTAS_CANTIDAD_CEMENTERIO("Select count(*) as Cantidad \n"
	 		                   + "From web_recau:usuario_cuentas_rel_cogmvl \n"
	 		                   + "Where tipo_cuenta = 5 \n"
	 		                   + "and Upper( '' || cuenta) Like UPPER(?)"), 

	 OBTENER_CATEGORIAS_CONSULTAS("select c.id_consulta_categoria,\n"
	 		                    + "       c.nombre,\n"
	 		                    + "       cd.id_consulta_categoria_dato,\n"
	 		                    + "       cd.dato,\n"
	 		                    + "       cd.requerido,\n"
	 		                    + "       cd.expresion_regular,\n"
	 		                    + "       cd.mensaje_error,\n"
	 		                    + "       p.idpermiso,\n"
	 		                    + "       p.nombre as permiso\n"
	 		                    + "from web_recau:consulta_categoria c,\n"
	 		                    + "     web_recau:consulta_categoria_dato cd,\n"
	 		                    + "     web_recau:permiso p\n"
	 		                    + "where c.id_consulta_categoria = cd.id_consulta_categoria\n"
	 		                    + "AND p.idPermiso = c.id_permiso"),
	 
	 /**
	  * 1 » ID CATEGORIA 
	  * **/
	 OBTENER_SUBCATEGORIAS_CATEGORIA("select id_consulta_motivo,nombre from consulta_motivo where id_consulta_categoria = ?	 \n"),
	 /**
	  * 1 » ID SUBCATEGORIA
	  * **/
	 OBTENER_DATO_SUBCATEGORIA("select id_consulta_datos ,nombre,requerido,expresion_regular from consulta_datos where id_consulta_motivo = ?"),
	 /**
	  * 1 » ID SUBCATEGORIA
	  * **/
	 OBTENER_CARACTERES("select c.id_consulta_caracter,\n"
	 		          + "       c.nombre\n"
	 		          + "from web_recau:consulta_caracter c,\n"
	 		          + "     web_recau:consulta_categoria_caracter cc\n"
	 		          + "where c.id_consulta_caracter=cc.id_caracter\n"
	 		          + "and cc.id_categoria = ?"),
    OBTENER_CARACTERES_GENERALES("select * \n" +
			"from web_recau:consulta_caracter \n" +
			"where id_consulta_caracter in ( \n" +
			"select id_caracter \n" +
			"from web_recau:consulta_categoria_caracter \n" +
			"group by id_caracter \n" +
			"having count(id_categoria) = 13)"),
	 OBTENER_TIPO_CONSULTA("select id_tipo,\n"
	 		             + "       nombre\n"
	 		             + "from web_recau:consulta_tipo \n"),
	 
	 INSERTAR_FORMULARIO("insert into web_recau:consulta_formulario(nombre, \n "
						 + "                                apellido, \n "
						 + "                                correo, \n "
						 + "                                telefono_seleccionado, \n "
						 + "                                telefono, \n "
						 + "                                telefono_2, \n "
						 + "                                cuit, \n "
						 + "                                idTipoConsulta, \n "
						 + "                                idCategoria, \n "
						 + "                                idCaracter, \n "
						 + "                                idDato, \n "
						 + "                                descripcion, \n "
						 + "                                dato, \n"
						 + "								fecha_actualizacion) \n "
						 + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?); \n "),
	 /**
	  * 1 » CUIT 
	  * **/
	 OBTENER_ID_FORMULARIO("select max(id_consulta_formulario) as id from web_recau:consulta_formulario where cuit = ? \n"),
	 /**
	  * 1 » ID DEL FORMULARIO
	  * 2 » NOMBRE DEL ARCHIVO
	  * 3 » TIPO DE ARCHIVO
	  * 4 » DATO (EN BYTES)
	  * **/
	 INSERTAR_ARCHIVO_FORMULARIO ("insert into web_recau:consulta_archivo ( id_consulta_formulario,nombre,tipo_contenido,data,archivo_consultante) \n "
			 					+ "values (?,?,?,?,?) \n "),
	 /**
	  * 1 » ID DE LA CONSULTA
	  * 2 » NOMBRE DEL ARCHIVO
	  * 3 » TIPO DE ARCHIVO
	  * 4 » DATO (EN BYTES)
	  * **/
	 INSERTAR_ARCHIVO_CONSULTA ("insert into web_recau:consulta_asociada_archivo ( id_consulta_asociada,nombre,tipo_contenido,data) \n "
			 + "values (?,?,?,?) \n "),
	 
	 /**
	  * 1 SKIP<br>
	  * 2 FIRST<br>
	  * 3 ESTADO<br>
	  * 4 a 8 TEXT
	  * 9 a 10 fecha 
	  * **/
	 OBTENER_CONSULTA("SELECT SKIP ? FIRST ?\r\n" + 
	 		"	  f.id_consulta_formulario, \r\n" + 
	 		"       f.nombre,  \r\n" + 
	 		"       f.apellido,\r\n" + 
	 		"       f.dato,   \r\n" + 
	 		"       f.estado,   \r\n" + 
	 		"       f.idCategoria,   \r\n" +
	        "       t.nombre AS tipo,   \r\n" + 
	 		"       c.nombre AS categoria,   \r\n" + 
	 		"       f.fecha_sistema,   \r\n" + 
	 		"       f.fecha_actualizacion  \r\n" + 
	 		"FROM web_recau:consulta_categoria c,   \r\n" + 
	 		"     web_recau:consulta_formulario f,   \r\n" + 
	 		"     web_recau:consulta_tipo t   \r\n" + 
	 		"WHERE f.idTipoConsulta = t.id_tipo \r\n" + 
	 		"AND f.idCategoria IN ( __VALORES__ )\r\n" + 
	 		"AND f.estado IN ( __ESTADO__ ) \n" +
			 "AND f.idCategoria = c.id_consulta_categoria   \r\n" +
			 "AND (? = 1 or f.estado != ?)\r\n" +
             "AND  TO_CHAR( f.fecha_sistema, '%Y-%m-%d' ) BETWEEN ? AND ? \r\n" +
			 "AND ( UPPER( f.nombre ) LIKE( ? ) OR\r\n" +
	 		"	 UPPER( f.apellido ) LIKE( ? ) OR\r\n" + 
	 		"	 UPPER( '' || f.id_consulta_formulario ) LIKE( ? ) OR\r\n" +
             "	 UPPER( f.dato ) LIKE( ? ) OR\r\n" +
             "	 UPPER( t.nombre ) LIKE( ? ) )\r\n" +
	 		"ORDER BY f.estado = 4 DESC, f.id_consulta_formulario DESC"),

	OBTENER_CONSULTA_SIN_FECHA("SELECT SKIP ? FIRST ?\r\n" +
			"	  f.id_consulta_formulario, \r\n" +
			"       f.nombre,  \r\n" +
			"       f.apellido,\r\n" +
			"       f.dato,   \r\n" +
			"       f.estado,   \r\n" +
			"       f.idCategoria,   \r\n" +
			"       t.nombre AS tipo,   \r\n" +
			"       c.nombre AS categoria,   \r\n" +
			"       f.fecha_sistema,   \r\n" +
			"       f.fecha_actualizacion  \r\n" +
			"FROM web_recau:consulta_categoria c,   \r\n" +
			"     web_recau:consulta_formulario f,   \r\n" +
			"     web_recau:consulta_tipo t   \r\n" +
			"WHERE f.idTipoConsulta = t.id_tipo \r\n" +
			"AND f.idCategoria IN ( __VALORES__ )\r\n" +
			"AND f.estado IN ( __ESTADO__ ) \n" +
			"AND f.idCategoria = c.id_consulta_categoria   \r\n" +
			"AND (? = 1 or f.estado != ?)\r\n" +
			"AND ( UPPER( f.nombre ) LIKE( ? ) OR\r\n" +
			"	 UPPER( f.apellido ) LIKE( ? ) OR\r\n" +
			"	 UPPER( '' || f.id_consulta_formulario ) LIKE( ? ) OR\r\n" +
			"	 UPPER( f.dato ) LIKE( ? ) OR\r\n" +
			"	 UPPER( t.nombre ) LIKE( ? ) )\r\n" +
			"ORDER BY f.estado = 4 DESC, f.id_consulta_formulario DESC"),
	 
	 OBTENER_CANTIDAD_CONSULTAS("SELECT COUNT(*) \r\n" +
	 		"       FROM web_recau:consulta_categoria c,\r\n" + 
	 		"            web_recau:consulta_formulario f,\r\n" + 
	 		"            web_recau:consulta_tipo t\r\n" +
			 "       WHERE f.idTipoConsulta = t.id_tipo\r\n" +
	 		"       AND f.idCategoria IN ( __VALORES__ )\r\n" +
			 "       AND f.estado IN ( __ESTADO__ ) \n" +
			 "		AND (? = 1 or f.estado != ?)\r\n" +
             "      AND TO_CHAR(f.fecha_sistema, '%Y-%m-%d') BETWEEN ? AND ?\r\n" +
	 		"       AND ( UPPER( f.nombre ) LIKE( ? ) OR\r\n" + 
	 		"             UPPER( f.apellido ) LIKE( ? ) OR\r\n" + 
	 		"             UPPER( '' || f.id_consulta_formulario ) LIKE( ? ) OR\r\n" +
             "             UPPER( f.dato ) LIKE( ? ) OR\r\n" +
             "             UPPER( t.nombre ) LIKE( ? ) " +
	 		"           )\r\n" + 
	 		"       AND f.idCategoria = c.id_consulta_categoria "),

	OBTENER_CANTIDAD_CONSULTAS_SIN_FECHA("SELECT COUNT(*) \r\n" +
			"       FROM web_recau:consulta_categoria c,\r\n" +
			"            web_recau:consulta_formulario f,\r\n" +
			"            web_recau:consulta_tipo t\r\n" +
			"       WHERE f.idTipoConsulta = t.id_tipo\r\n" +
			"       AND f.idCategoria IN ( __VALORES__ )\r\n" +
			"       AND f.estado IN ( __ESTADO__ ) \n" +
			"		AND (? = 1 or f.estado != ?)\r\n" +
			"       AND ( UPPER( f.nombre ) LIKE( ? ) OR\r\n" +
			"             UPPER( f.apellido ) LIKE( ? ) OR\r\n" +
			"             UPPER( '' || f.id_consulta_formulario ) LIKE( ? ) OR\r\n" +
			"             UPPER( f.dato ) LIKE( ? ) OR\r\n" +
			"             UPPER( t.nombre ) LIKE( ? ) " +
			"           )\r\n" +
			"       AND f.idCategoria = c.id_consulta_categoria "),

	OBTENER_CANTIDAD_CONSULTAS_POR_ID("SELECT COUNT(*) AS VALOR FROM web_recau:consulta_formulario f WHERE f.id_consulta_formulario = ? "),

	OBTENER_CONSULTAS_POR_ID("SELECT SKIP ? FIRST ? "
			+ "	  f.id_consulta_formulario, "
			+ "       f.nombre, "
			+ "       f.apellido, "
			+ "       f.dato, "
			+ "       f.estado, "
			+ "       f.idCategoria, "
			+ "       t.nombre AS tipo, "
			+ "       c.nombre AS categoria, "
			+ "       f.fecha_sistema, "
			+ "       f.fecha_actualizacion "
			+ "FROM "
			+ "web_recau:consulta_categoria c,"
			+ "web_recau:consulta_formulario f, "
			+ "web_recau:consulta_tipo t "
			+ "WHERE f.idTipoConsulta = t.id_tipo "
			+ "AND f.idCategoria = c.id_consulta_categoria "
			+ "AND f.id_consulta_formulario = ? "
			+ "ORDER BY f.estado = 4 DESC, f.id_consulta_formulario DESC"),

	 /**
	  * 1 » ID DE CONSULTA
	  * **/
	 OBTENER_ARCHIVOS_CONSULTA("select id_consulta_archivo,\n"
	 		                 + "       nombre,\n"
	 		                 + "       tipo_contenido,\n"
	 		                 + "       ruta,\n"
	 		                 + "       fecha_sistema,\n"
	 		                 + "       archivo_consultante\n"
	 		                 + "from web_recau:consulta_archivo\n"
	 		                 + "where id_consulta_formulario = ? \n"),
	 
	 OBTENER_ARCHIVO_CONSULTA( "select * from web_recau:consulta_archivo where id_consulta_formulario = ?"  ),
	 
	 OBTENER_ARCHIVOS_CONSULTA_ASOCIADA("select id_consulta_archivo,nombre,tipo_contenido,data,fecha_sistema from web_recau:consulta_asociada_archivo where id_consulta_asociada = ? \n"),
	 
	 /**
	  * 1 » ID CONSULTA
	  * 2 » ASUNTO
	  * 3 » CONTENIDO CORREO 
	  * 4 » NOMBRE USUARIO
	  * **/
	 GUARDAR_CORREO ("insert into web_recau:consulta_correo (id_consulta_formulario,asunto,correo, usuario ) values ( ? , ? , ?, ? ) \n"),
	 
	 /**
		 * 1 » FECHA DE SISTEMA
		 * 2 » 1 o 0 DEPENDIENDO SI ESTA CERRADO O ABIERTO
		 * 3 » ID CONSULTA
		 * **/
		
	ACTUALIZAR_CONSULTA("update web_recau:consulta_formulario set fecha_actualizacion = ?, estado = ?, idCategoria= ? where id_consulta_formulario = ? \n"),

    /**
	 * 1 » ID CONSULTA
	 * **/
	OBTENER_LOG_CONSULTA("select asunto,\n"
			           + "       correo,\n"
			           + "       usuario,\n"
			           + "       fecha_sistema\n"
			           + "from web_recau:consulta_correo\n"
			           + "where id_consulta_formulario = ?"),
	
	OBTENER_CATEGORIA_CORREO("select cc.correo as Correo \n"
						   + "from web_recau:consulta_categoria ca, \n"
						   + "     web_recau:consulta_categoria_correo cc \n"
						   + "where cc.id_consulta_categoria = ca.id_consulta_categoria \n"
						   + "and ca.id_consulta_categoria = ?;"), 
	
	OBTENER_LISTA_CORREO_OFICIAL("SELECT cc.id_consulta_categoria_correo AS ID, \n"
						 + "       ca.nombre AS Categoria, \n"
						 + "       cc.correo AS Correo \n"
						 + "FROM web_recau:consulta_categoria ca, \n"
						 + "     web_recau:consulta_categoria_correo cc \n"
						 + "where ca.id_consulta_categoria = cc.id_consulta_categoria;"), 
	
	CREAR_CORREO_OFICIAL("INSERT into web_recau:consulta_categoria_correo(id_consulta_categoria, correo) VALUES(?, ?);"), 
	
	ELIMINAR_CORREO_OFICIAL("DELETE FROM web_recau:consulta_categoria_correo WHERE id_consulta_categoria_correo = ?;"), 
	
	ACTUALIZAR_CORREO_OFICIAL("UPDATE web_recau:consulta_categoria_correo SET correo = ? where id_consulta_categoria = ?;"),

	OBTENER_TRIBUTOS("SELECT id_consulta_categoria nombre from consulta_categoria"),
	AGREGAR_NUEVA_CONSULTA("insert into web_recau:consulta_asociada (id_consulta_asociada,nueva_consulta,consulta_usuario, usuario_consulta) values( ? , ?, ?, ? )" ),
	OBTENER_NUEVAS_CONSULTAS("select ca.nueva_consulta,\n" +
			"       ca.consulta_usuario,\n" +
			"       ca.fecha_sistema,\n" +
			"       CONCAT(CONCAT(uc.nombre, ' '), uc.apellido) as usuario_consulta\n" +
			"from web_recau:consulta_asociada ca \n" +
			"left join web_recau:usuario_configuracion uc\n" +
			"on ca.usuario_consulta = uc.nombre_usuario   \n" +
			"where id_consulta_asociada = ?" ),
	CONSULTA_YA_CERRADA("select * from web_recau:consulta_formulario where id_consulta_formulario = ? and estado = 1 " ),
	OBTENER_CONSULTA_POR_ID( "SELECT f.id_consulta_formulario,    \n "
+ "                             f.nombre,    \n "
+ "                             f.apellido,    \n "
+ "                             f.correo,    \n "
+ "                             f.telefono,    \n "
+ "                             f.telefono_seleccionado,    \n "
+ "                             f.telefono_2,    \n "
+ "                             f.cuit,    \n "
+ "                             f.descripcion,    \n "
+ "                             f.dato,    \n	 "
+ "                             f.estado,    \n "
+ "                             f.idCategoria,    \n "
+ "                             t.nombre AS tipo,    \n "
+ "                             c.nombre AS categoria,    \n "
+ "                             car.nombre AS caracter,    \n "
+ "                             td.dato AS tipo_dato,   \n "
+ "                                f.fecha_sistema,    \n "
+ "                                f.fecha_actualizacion   \n "
+ "                        FROM   web_recau:consulta_categoria c,  \n "
+ "                             web_recau:consulta_categoria_dato td,    \n "
+ "                             web_recau:consulta_tipo t,    \n "
+ "                             web_recau:consulta_formulario f    \n "
+ "                        LEFT JOIN web_recau:consulta_caracter car\n"
+"                              on f.idCaracter = car.id_consulta_caracter       \n "
+ "                          where f.id_consulta_formulario = ? \n "
+ "                          and f.idTipoConsulta = t.id_tipo  \n "
+ "                          and f.idCategoria = c.id_consulta_categoria    \n "
+ "                          and f.idDato = td.id_consulta_categoria_dato; \n "
),
	OBTENER_CONSULTAS_POR_CUIT( "SELECT f.id_consulta_formulario,    \n "
+ "                             f.nombre,    \n "
+ "                             f.apellido,    \n "
+ "                             f.correo,    \n "
+ "                             f.telefono,    \n "
+ "                             f.telefono_seleccionado,    \n "
+ "                             f.telefono_2,    \n "
+ "                             f.cuit,    \n "
+ "                             f.descripcion,    \n "
+ "                             f.dato,    \n	 "
+ "                             f.estado,    \n "
+ "                             f.idCategoria,    \n "
+ "                             t.nombre AS tipo,    \n "
+ "                             c.nombre AS categoria,    \n "
+ "                             car.nombre AS caracter,    \n "
+ "                             td.dato AS tipo_dato,   \n "
+ "                                f.fecha_sistema,    \n "
+ "                                f.fecha_actualizacion   \n "
+ "                        FROM web_recau:consulta_categoria c,    \n "
+ "                             web_recau:consulta_categoria_dato td,    \n "
+ "                             web_recau:consulta_tipo t,\n "
+ "                              web_recau:consulta_formulario f    \n "
+ "                       LEFT JOIN web_recau:consulta_caracter car\n"
+	"                           ON f.idCaracter = car.id_consulta_caracter    \n "
+ "                          where f.cuit = ? \n "
+ "                          and f.estado <> 5  \n "
+ "                          and f.idTipoConsulta = t.id_tipo  \n "
+ "                          and f.idCategoria = c.id_consulta_categoria    \n "
+ "                          and f.idDato = td.id_consulta_categoria_dato \n "
+ "							 order by 1 desc \n"
),
	OBTENER_CHAT_POR_ID_CONSULTA("select * from web_recau:consulta_asociada where id_consulta_asociada = ?\n"),

	OBTENER_CONSULTAS_POR_CUIT_CUENTA(
			"SELECT f.id_consulta_formulario,     \n" + 
			"      f.nombre,     \n" + 
			"      f.apellido,     \n" + 
			"      f.correo,     \n" + 
			"      f.telefono,     \n" + 
			"      f.telefono_seleccionado,     \n" + 
			"      f.telefono_2,     \n" + 
			"      f.cuit,     \n" + 
			"      f.descripcion,     \n" + 
			"      f.dato,     \n" + 
			"          f.estado,     \n" + 
			"      f.idCategoria,     \n" + 
			"      t.nombre AS tipo,     \n" + 
			"      c.nombre AS categoria,     \n" + 
			"      car.nombre AS caracter,     \n" + 
			"      td.dato AS tipo_dato,    \n" + 
			"         f.fecha_sistema,     \n" + 
			"         f.fecha_actualizacion    \n" +
			"      FROM web_recau:consulta_categoria c,    \n " +
			"       web_recau:consulta_categoria_dato td,    \n " +
			"       web_recau:consulta_tipo t,\n " +
			"       web_recau:consulta_formulario f    \n " +
			"       LEFT JOIN web_recau:consulta_caracter car\n" +
			"         ON f.idCaracter = car.id_consulta_caracter    \n " +
			"       where f.cuit = ? \n " +
			"       and f.dato = ? \n " +
			"       and f.estado <> 5  \n " +
			"       and f.idTipoConsulta = t.id_tipo  \n " +
			"       and f.idCategoria = c.id_consulta_categoria    \n " +
			"       and f.idDato = td.id_consulta_categoria_dato \n " +
			"		order by 1 desc \n" ),
	
	OBTENER_CONSULTAS_POR_CORREO( "SELECT f.id_consulta_formulario,    \n "
			+ "                             f.nombre,    \n "
			+ "                             f.apellido,    \n "
			+ "                             f.correo,    \n "
			+ "                             f.telefono,    \n "
			+ "                             f.telefono_seleccionado,    \n "
			+ "                             f.telefono_2,    \n "
			+ "                             f.cuit,    \n "
			+ "                             f.descripcion,    \n "
			+ "                             f.dato,    \n "
			+ "                             f.estado,    \n "
			+ "                             f.idCategoria,    \n "
			+ "                             t.nombre AS tipo,    \n "
			+ "                             c.nombre AS categoria,    \n "
			+ "                             car.nombre AS caracter,    \n "
			+ "                             td.dato AS tipo_dato,   \n "
			+ "                                f.fecha_sistema,    \n "
			+ "                                f.fecha_actualizacion   \n "+
			"      FROM web_recau:consulta_categoria c,    \n " +
					"       web_recau:consulta_categoria_dato td,    \n " +
					"       web_recau:consulta_tipo t,\n " +
					"       web_recau:consulta_formulario f    \n " +
					"       LEFT JOIN web_recau:consulta_caracter car\n" +
					"         ON f.idCaracter = car.id_consulta_caracter    \n " +
					"       where f.correo = ? \n " +
					"       and f.estado <> 5  \n " +
					"       and f.idTipoConsulta = t.id_tipo  \n " +
					"       and f.idCategoria = c.id_consulta_categoria    \n " +
					"       and f.idDato = td.id_consulta_categoria_dato \n " +
					"		order by 1 desc \n"
			),
	OBTENER_NUEVO_ID_C_A( "select max(id_consulta_asociada) from web_recau:consulta_asociada" ),
	ELIMINAR_CONSULTA_ASOCIADA( "delete from web_recau:consulta_asociada where id_consulta_nueva = ?" ),
	OBTENER_ULTIMO_ID_ARCHIVO( "select max(id_consulta_archivo) from web_recau:consulta_archivo" ),
	ELIMINAR_ARCHIVO_CONSULTA( "delete from web_recau:consulta_archivo where id_consulta_archivo = ?" ),
	OBTENER_ULTIMO_ID_CONSULTA_ASOCIADA( "select max(id_consulta_nueva) from web_recau:consulta_asociada;"),
	
	RECUPERAR_USUARIO("select id_usuario, cuit, correo, telefono, fax, fecha, flag_tyc, cuit, flag_activacion, flag_newsletter \n"
					+ "from web_recau:usuario_cogmvl \n"
					+ "where cuit = ? \n"
					+ "and clave = ? "), 
	
	OBTENER_CATEGORIA_POR_NOMBRE("select c.id_consulta_categoria,\n"
			                   + "       c.nombre,\n"
			                   + "       cd.id_consulta_categoria_dato,\n"
			                   + "       cd.dato,cd.requerido,\n"
			                   + "       cd.expresion_regular,\n"
			                   + "       cd.mensaje_error,\n"
			                   + "       p.idpermiso,p.nombre as permiso\n"
			                   + "from web_recau:consulta_categoria c,\n"
			                   + "     web_recau:consulta_categoria_dato cd,\n"
			                   + "     web_recau:permiso p \n"
			                   + "where c.nombre = ? \n" 
			                   + "and c.id_consulta_categoria = cd.id_consulta_categoria  \n" 
			                   + "AND p.idPermiso = c.id_permiso  \n" ), 
	
	OBTENER_CARACTER_POR_NOMBRE("select c.id_consulta_caracter, \n"
								+ " c.nombre \n"
								+ " from web_recau:consulta_caracter c \n"
								+ " where c.nombre = ?" ),
	
	
	OBTENER_TIPO_CONSULTA_NOMBRE( "select id_tipo,\n"
			                    + "       nombre\n"
			                    + "from web_recau:consulta_tipo\n"
			                    + "where nombre = ? \n"),

	ACTUALIZAR_CUENTA_ALIAS(
			"update web_recau:usuario_cuentas_rel_cogmvl \n" +
					"set descripcion = ?              \n" +
					"where id_usuario = ?              \n" +
					"and cuenta = ?                       \n"), 
	
	BUSCAR_PERMISOS_USUARIO("select tipo_cuenta, cuenta, ps.clave \n"
						  + "from web_recau:permiso_gestion pg \n"
						  + "inner join web_recau:permiso_sat ps on ps.id_permiso_sat = pg.id_permiso_sat \n"
						  + "where id_usuario_gestor = ?"), 
	
	ACTIVAR_BOLETA_ELECTRONICA("insert into boleta_electronica(c_cuenta, c_sistema) \n"
							 + "values ( ? , ? ) "), 
	
	DESACTIVAR_BOLETA_ELECTRONICA("delete from boleta_electronica \n"
								+ "where c_cuenta = ? \n"
								+ "and c_sistema = ?"),
	
	REGISTRAR_EVENTO_BOLETA_ELECTRONICA("Insert Into web_recau:historial_be (id_usuario, tipo_cuenta, cuenta, evento) \n"
									  + "Values ( ? , ? , ? , ? )"), 
	
	EXISTE_BOLETA_ELECTRONICA("select 1 from boleta_electronica where c_sistema = ? and c_cuenta = ?"),

	;

	private String sql;

	private Queries(String sql) {
		this.setSql(sql);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
