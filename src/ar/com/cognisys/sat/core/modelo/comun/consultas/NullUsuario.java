package ar.com.cognisys.sat.core.modelo.comun.consultas;

public class NullUsuario implements UsuarioHistorial {

    @Override
    public String getNombreCompleto() {
        return "-";
    }

    @Override
    public Integer getIdUsuario() {
        throw new UnsupportedOperationException("M�todo utilizado para insertar. No deber�a poderse insertar nuevos con un usuaro nulo");
    }
}
