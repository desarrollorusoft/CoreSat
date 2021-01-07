package ar.com.cognisys.sat.core.administrador;

import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.Administrador;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Perfil;
import ar.com.cognisys.sat.core.modelo.comun.usuarioSat.Permiso;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryPerfil;
import ar.com.cognisys.sat.core.modelo.factory.usuario.FactoryPermiso;

public class AdministradorPerfil extends Administrador {

	private static final long serialVersionUID = 3641031111478777185L;

	@Deprecated
	public static List<Perfil> listaPerfilesUsuario() {
		
		List<Permiso> listaPermisos = new ArrayList<Permiso>();
		listaPermisos.add(FactoryPermiso.generarIntanciaCompleta("Contribuyente"));

		List<Perfil> listaPerfiles = new ArrayList<Perfil>();
		listaPerfiles.add(FactoryPerfil.generarIntanciaCompleta("Contribuyente", listaPermisos));
		
		return listaPerfiles;
	}
}