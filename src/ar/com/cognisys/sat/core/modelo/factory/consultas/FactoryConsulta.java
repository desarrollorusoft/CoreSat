package ar.com.cognisys.sat.core.modelo.factory.consultas;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.comun.ArchivoConsulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Categoria;
import ar.com.cognisys.sat.core.modelo.comun.consultas.Consulta;
import ar.com.cognisys.sat.core.modelo.comun.consultas.ConsultaAsociada;
import ar.com.cognisys.sat.core.modelo.enums.EstadoConsulta;
import ar.com.cognisys.sat.v2.vista.modelo.interfaz.IRutaNombreView;

public class FactoryConsulta {
	
	public static Consulta generarInstancia(){
		Consulta c = new Consulta();
		c.setArchivos(new ArrayList<ArchivoConsulta>());
		return c;
	}

	public static Consulta generarInstancia(Long id,
											String nombre,
											String apellido,
											String dato,
											EstadoConsulta estado,
											String tipoConsulta,
											Categoria categoria,
											Timestamp fechaCreacion,
											Timestamp fechaActualizacion) {
		Consulta c = generarInstancia();
		
		c.setId(id);
		c.setNombre(nombre);
		c.setApellido(apellido);
		c.setTipoConsulta(tipoConsulta);
		c.setCategoria(categoria);
		c.setDato(dato);
		c.setEstado( estado );
		c.setFechaCreacion(fechaCreacion);
		c.setFechaActualizacion(fechaActualizacion);
		
		return c;
	}

	public static Consulta generarInstancia(Long id,
											String nombre,
											String apellido,
											String correo,
											boolean telefonoSeleccionado,
											String telefono,
											String telefono2,
											String cuit,
											String descripcion,
											String dato,
											EstadoConsulta estado,
											String tipoConsulta,
											Categoria categoria,
											String caracter,
											String tipoDato,
											Timestamp fechaCreacion,
											Timestamp fechaActualizacion,
											List<ConsultaAsociada> listaConsultasAsociadas) {
		Consulta c = generarInstancia();
		
		c.setId(id);
		c.setNombre(nombre);
		c.setApellido(apellido);
		c.setCorreo(correo);
		c.setTelefonoSeleccionado( telefonoSeleccionado );
		c.setTelefono(telefono);
		c.setTelefono2(telefono2);
		c.setCuit(cuit);
		c.setTipoConsulta(tipoConsulta);
		c.setCategoria(categoria);
		c.setCaracter(caracter);
		c.setDescripcion(descripcion);
		c.setDato(dato);
		c.setTipoDato(tipoDato);
		c.setEstado( estado );
		c.setFechaCreacion(fechaCreacion);
		c.setFechaActualizacion(fechaActualizacion);
		c.setListaConsultasAsociadas( listaConsultasAsociadas );
		
		return c;
	}
	
	public static Consulta generarInstancia(Long id,
			String nombre,
			String apellido,
			String correo,
			boolean telefonoSeleccionado,
			String telefono,
			String telefono2,
			String cuit,
			String descripcion,
			String dato,
			EstadoConsulta estado,
			String tipoConsulta,
			Categoria categoria,
			String caracter,
			String tipoDato,
			Timestamp fechaCreacion,
			Timestamp fechaActualizacion,
			List<ConsultaAsociada> listaConsultasAsociadas,
			List<IRutaNombreView> listaArchivos) {
		Consulta c = generarInstancia();
		
		c.setId(id);
		c.setNombre(nombre);
		c.setApellido(apellido);
		c.setCorreo(correo);
		c.setTelefonoSeleccionado( telefonoSeleccionado );
		c.setTelefono(telefono);
		c.setTelefono2(telefono2);
		c.setCuit(cuit);
		c.setTipoConsulta(tipoConsulta);
		c.setCategoria(categoria);
		c.setCaracter(caracter);
		c.setDescripcion(descripcion);
		c.setDato(dato);
		c.setTipoDato(tipoDato);
		c.setEstado( estado );
		c.setFechaCreacion(fechaCreacion);
		c.setFechaActualizacion(fechaActualizacion);
		c.setListaConsultasAsociadas( listaConsultasAsociadas );
		c.setListaArchivos( listaArchivos );
		
		return c;
	}
	public static Consulta generarInstancia(Long id,
											String nombre,
											String apellido,
											String correo,
											boolean telefonoSeleccionado,
											String telefono,
											String telefono2,
											String cuit,
											String descripcion,
											String dato,
											EstadoConsulta estado,
											String tipoConsulta,
											Categoria categoria,
											String caracter,
											String tipoDato,
											Timestamp fechaCreacion,
											Timestamp fechaActualizacion) {
		Consulta c = generarInstancia();
		
		c.setId(id);
		c.setNombre(nombre);
		c.setApellido(apellido);
		c.setCorreo(correo);
		c.setTelefonoSeleccionado( telefonoSeleccionado );
		c.setTelefono(telefono);
		c.setTelefono2(telefono2);
		c.setCuit(cuit);
		c.setTipoConsulta(tipoConsulta);
		c.setCategoria(categoria);
		c.setCaracter(caracter);
		c.setDescripcion(descripcion);
		c.setDato(dato);
		c.setTipoDato(tipoDato);
		c.setEstado( estado );
		c.setFechaCreacion(fechaCreacion);
		c.setFechaActualizacion(fechaActualizacion);
		
		return c;
	}

	public static Consulta generarInstancia(String cuit, String correo, String telefono1, String telefono2) {
		Consulta c = generarInstancia();
		
		c.setCuit(cuit);
		c.setCorreo(correo);
		c.setTelefono(telefono1);
		c.setTelefono2(telefono2);
		
		return c;
	}

	public static Consulta generarInstancia(String nombre, String apellido, String cuit, String correo, String telefono1, String telefono2) {
		Consulta c = generarInstancia();

		c.setNombre(nombre);
		c.setApellido(apellido);
		c.setCuit(cuit);
		c.setCorreo(correo);
		c.setTelefono(telefono1);
		c.setTelefono2(telefono2);
		
		return c;
	}
	
}
