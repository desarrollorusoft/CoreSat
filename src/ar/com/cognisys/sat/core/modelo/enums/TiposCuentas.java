package ar.com.cognisys.sat.core.modelo.enums;

import java.util.ArrayList;

public enum TiposCuentas {

	ABL("A.L.C.V.P. y S.V. y C.P.C.", 2, 1,"Cuenta", 1, "A.L.C.V.P. y S.V."),
	COMERCIOS("Comercios e Industrias", 1, 2,"CUIT", 2, "Comercios e Industrias"),
	VEHICULOS("Automotores", 31, 3,"Dominio", 3, "Automotores"),
	RODADOS("Motovehículos",4,3,"Dominio",4, "Automotores"),
	CEMENTERIO("Cementerio", 9, 5, "Cuenta", 5, "Cementerio"),
	PILETAS("Revisación Médica para Piletas", 9, 6, "Tipo Documento", 6, "Piletas"),

	PPC("Plan de Pagos", 0, 0, "Cuenta", 0, "PPC"),
	;

	private String nombre;
	private Integer codigo;
	private Integer posicionCombo;
	private String documento;
	private Integer codigo_usuario;
	private String categoriaTributo;

	private TiposCuentas(String nombre, Integer codigo, Integer posicionCombo, String documento, Integer codigo_usuario, String categoriaTributo) {

		this.setNombre(nombre);
		this.setCodigo(codigo);
		this.setPosicionCombo(posicionCombo);
		this.setDocumento(documento);
		this.setCodigo_usuario(codigo_usuario);
		this.setCategoriaTributo(categoriaTributo);
	}

	public static TiposCuentas recuperar(String name) {
		for ( TiposCuentas e : values() )
			if ( e.name().equals( name ) )
				return e;

		return null;
	}

	public static String recuperarNombrePorCategoria(String categoria) {

		String nombre = "";

		if (TiposCuentas.ABL.name().equals(categoria)) {
			nombre = TiposCuentas.ABL.getNombre();

		} else if (TiposCuentas.COMERCIOS.name().equals(categoria)) {
			nombre = TiposCuentas.COMERCIOS.getNombre();

		} else if (TiposCuentas.VEHICULOS.name().equals(categoria)) {
			nombre = TiposCuentas.VEHICULOS.getNombre();

		} else if (TiposCuentas.RODADOS.name().equals(categoria)) {
			nombre = TiposCuentas.RODADOS.getNombre();

		} else if ( TiposCuentas.CEMENTERIO.name().equals(categoria)) {
			nombre = TiposCuentas.CEMENTERIO.getNombre();

		} else if ( TiposCuentas.PILETAS.name().equals(categoria) )
			nombre = TiposCuentas.PILETAS.getNombre();

		return nombre;
	}

	public static Integer recuperarCodigoPorNombre(String nombre) {

		Integer codigo = null;

		if (nombre.equals(TiposCuentas.ABL.getNombre())) {
			codigo = TiposCuentas.ABL.getCodigo();

		} else if (nombre.equals(TiposCuentas.COMERCIOS.getNombre())) {
			codigo = TiposCuentas.COMERCIOS.getCodigo();

		} else if (nombre.equals(TiposCuentas.VEHICULOS.getNombre())) {
			codigo = TiposCuentas.VEHICULOS.getCodigo();

		} else if (nombre.equals(TiposCuentas.RODADOS.getNombre())) {
			codigo = TiposCuentas.RODADOS.getCodigo();

		} else if (nombre.equals(TiposCuentas.CEMENTERIO.getNombre())) {
			codigo = TiposCuentas.CEMENTERIO.getCodigo();

		} else if (nombre.equals(TiposCuentas.PILETAS.getNombre())) {
			codigo = TiposCuentas.PILETAS.getCodigo();
		}


		return codigo;
	}

	public static Integer recuperarPosicionPorNombre(String nombre) {

		Integer codigo = null;

		if (nombre.equals(TiposCuentas.ABL.getNombre())) {
			codigo = TiposCuentas.ABL.getPosicionCombo();

		} else if (nombre.equals(TiposCuentas.COMERCIOS.getNombre())) {
			codigo = TiposCuentas.COMERCIOS.getPosicionCombo();

		} else if (nombre.equals(TiposCuentas.VEHICULOS.getNombre())) {
			codigo = TiposCuentas.VEHICULOS.getPosicionCombo();

		}  else if (nombre.equals(TiposCuentas.RODADOS.getNombre())) {
			codigo = TiposCuentas.RODADOS.getPosicionCombo();

		} else if (nombre.equals(TiposCuentas.CEMENTERIO.getNombre())) {
			codigo = TiposCuentas.CEMENTERIO.getPosicionCombo();

		} else if (nombre.equals(TiposCuentas.PILETAS.getNombre())) {
			codigo = TiposCuentas.PILETAS.getPosicionCombo();
		}

		return codigo;
	}

	public static Integer recuperarPosicionCodigoUsuario(String nombre) {

		Integer codigo = null;

		if (nombre.equals(TiposCuentas.ABL.getNombre())) {
			codigo = TiposCuentas.ABL.getCodigo_usuario();

		} else if (nombre.equals(TiposCuentas.COMERCIOS.getNombre())) {
			codigo = TiposCuentas.COMERCIOS.getCodigo_usuario();

		} else if (nombre.equals(TiposCuentas.VEHICULOS.getNombre())) {
			codigo = TiposCuentas.VEHICULOS.getCodigo_usuario();

		}  else if (nombre.equals(TiposCuentas.RODADOS.getNombre())) {
			codigo = TiposCuentas.RODADOS.getCodigo_usuario();

		} else if (nombre.equals(TiposCuentas.CEMENTERIO.getNombre())) {
			codigo = TiposCuentas.CEMENTERIO.getCodigo_usuario();

		} else if (nombre.equals(TiposCuentas.PILETAS.getNombre())) {
			codigo = TiposCuentas.PILETAS.getCodigo_usuario();
		}

		return codigo;
	}

	public static TiposCuentas recuperarTipoCuenta(String nombre) {

		for (TiposCuentas tc : TiposCuentas.values()) {
			if (tc.getNombre().equals(nombre)) {
				return tc;
			}
		}

		return null;
	}

	public static TiposCuentas recuperarTipoCuenta(Integer codigo) {

		for (TiposCuentas tc : TiposCuentas.values()) {
			if (tc.getCodigo().equals(codigo)) {
				return tc;
			}
		}

		return null;
	}

	public static void main(String[] args) {
		System.out.println(recuperarTipoCuenta(3));
	}

	public static TiposCuentas recuperarTipoCuentaPorCategoria(String categoria) {

		for (TiposCuentas tc : TiposCuentas.values()) {
			if (tc.getCategoriaTributo().equals(categoria)) {
				return tc;
			}
		}

		return null;
	}

	public static TiposCuentas recuperarTipoCuentaPorCodUsu(Integer codigo) {

		for (TiposCuentas tc : TiposCuentas.values()) {
			if (tc.getCodigo_usuario().equals(codigo)) {
				return tc;
			}
		}

		return null;
	}

    public static TiposCuentas recuperarParaPPC(Integer sistema) {
		for (TiposCuentas tc : TiposCuentas.values()) {
			if (tc.getCodigo().equals( sistema )) {
				return tc;
			}
		}

		return PPC;
	}

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getPosicionCombo() {
		return posicionCombo;
	}

	public void setPosicionCombo(Integer posicionCombo) {
		this.posicionCombo = posicionCombo;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getCodigo_usuario() {
		return codigo_usuario;
	}

	public void setCodigo_usuario(Integer codigo_usuario) {
		this.codigo_usuario = codigo_usuario;
	}

	public String getCategoriaTributo() {
		return categoriaTributo;
	}

	public void setCategoriaTributo(String categoriaTributo) {
		this.categoriaTributo = categoriaTributo;
	}

	@Override
	public String toString() {
		return getNombre();
	}

	public static ArrayList<TiposCuentas> getTipoCuentas(){
		ArrayList<TiposCuentas> output = new ArrayList<TiposCuentas>();
		output.add(ABL);
		output.add(VEHICULOS);
		output.add(CEMENTERIO);

		return output;
	}

	public boolean sos(TiposCuentas tipoCuenta) {
		return this.equals( tipoCuenta );
	}

	public boolean sos(String name) {
		return this.name().equals( name );
	}

}