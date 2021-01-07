package ar.com.cognisys.sat.core.modelo.filtrador;

import ar.com.cognisys.sat.core.modelo.Queries;

public class FiltradorUsuariosPorCuit extends FiltradorUsuarios {

    @Override
    protected Queries queryFiltro() {
        return Queries.BUSCAR_USUARIOS_POR_CUIT;
    }

    @Override
    protected Queries queryFiltroTotal() {
        return Queries.CANTIDAD_USUARIOS_POR_CUIT;
    }
}
