package ar.com.cognisys.sat.core.modelo.factory.cuenta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.permiso.PermisoUsuario;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePagoAPagar;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.CuentasUsuario;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Perfil;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Permiso;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Usuario;
import ar.com.cognisys.sat.core.modelo.factory.seguimiento.FactorySeguimiento;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryPerfil;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryPermiso;

public class FactoryUsuario {

	public static Usuario generarInstanciaVacia() {
		
		Usuario usuario = new Usuario();
		
		List<Permiso> listaPermisos = new ArrayList<Permiso>();
		listaPermisos.add(FactoryPermiso.generarIntanciaCompleta("Contribuyente"));

		List<Perfil> listaPerfiles = new ArrayList<Perfil>();
		listaPerfiles.add(FactoryPerfil.generarIntanciaCompleta("Contribuyente", listaPermisos));
		
		usuario.setListaPerfiles(listaPerfiles);
		usuario.setFechaUltimoIngreso(new Date());
		usuario.setSeguimiento(FactorySeguimiento.generarInstancia());
		
		usuario.setListaPermisos(new ArrayList<PermisoUsuario>());
		usuario.setPlanes(new ArrayList<PlanDePagoAPagar>());
		
		return usuario;
	}
	
	public static Usuario generarInstanciaCompleta(Integer idUsuario, String cuit, String clave,
												   CuentasUsuario cuentas, String correo, String telefono, 
												   String fax, boolean newsLetter) {
		
		Usuario usuario = generarInstanciaVacia();
		usuario.setNombreUsuario(cuit);
		usuario.setCuit(cuit);
		usuario.setCuentas(cuentas);
		usuario.setCorreo(correo.trim());
		usuario.setTelefono2(fax);
		usuario.setTelefono1(telefono);
		usuario.setIdUsuario(idUsuario);
		usuario.setClave(clave);
		usuario.setNewsLetter(newsLetter);
		
		return usuario;
	}
	
	public static Usuario generarInstanciaCompleta(Integer idUsuario, String cuit, String clave,
				   								   CuentasUsuario cuentas, String correo, String telefono, 
				   								   String fax, boolean newsLetter, boolean activo, Date fechaAlta) {
		
		Usuario usuario = generarInstanciaVacia();
		usuario.setNombreUsuario(cuit);
		usuario.setCuit(cuit);
		usuario.setCuentas(cuentas);
		usuario.setCorreo(correo.trim());
		usuario.setTelefono2(fax);
		usuario.setTelefono1(telefono);
		usuario.setIdUsuario(idUsuario);
		usuario.setClave(clave);
		usuario.setNewsLetter(newsLetter);
		usuario.setActivo(activo);
		usuario.setFechaAlta(fechaAlta);
		
		return usuario;
	}
	
	public static Usuario generarInstanciaCompleta(Integer idUsuario, String nombreUsuario, String clave,
												   String cuit, String nombre, String apellido, String correo, String telefono, 
												   String fax, boolean newsLetter, boolean activo, Date fechaAlta, 
												   boolean aceptoTyC, Integer nivel) {
		
		Usuario usuario = generarInstanciaVacia();
		usuario.setNombreUsuario(nombreUsuario.trim());
		usuario.setCuit(cuit);
		usuario.setCorreo(correo.trim());
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setTelefono2(fax);
		usuario.setTelefono1(telefono);
		usuario.setIdUsuario(idUsuario);
		usuario.setClave(clave);
		usuario.setNewsLetter(newsLetter);
		usuario.setActivo(activo);
		usuario.setFechaAlta(fechaAlta);
		usuario.setAceptoTyC(aceptoTyC);
		usuario.setNivel(nivel);		
		
		return usuario;
	}
	
	public static Usuario generar(Integer idUsuario, String cuit, String correo, String telefono, String telefono2, 
								  boolean newsLetter, boolean activacion, Date fechaAlta) {
		
		Usuario usuario = generarInstanciaVacia();
		usuario.setIdUsuario(idUsuario);
		usuario.setCuit(cuit);
		usuario.setCorreo(correo);
		usuario.setTelefono1(telefono);
		usuario.setTelefono2(telefono2);
		usuario.setNewsLetter(newsLetter);
		usuario.setActivo(activacion);
		usuario.setFechaAlta(fechaAlta);
		
		return usuario;
	}
}