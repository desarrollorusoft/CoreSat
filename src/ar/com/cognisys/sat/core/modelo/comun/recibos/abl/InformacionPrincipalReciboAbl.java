package ar.com.cognisys.sat.core.modelo.comun.recibos.abl;

public class InformacionPrincipalReciboAbl {

	private String d_apellido;
	private String d_nombre;
	private String c_cuenta;
	private String n_verificador;
	private String d_calle_tit;
	private String n_nro_tit;
	private String d_loc_tit;
	private String c_postal;
	private String n_circ;
	private String c_seccion;
	private String n_fraccion;
	private String n_manz;
	private String d_lmanz;
	private String n_parc;
	private String d_lparc;
	private String n_ufun;
	private String d_lufun;
	private String n_poli;
	private String s_deuda;
	private String f_proceso;
	private String d_calle_pro;
	private String n_nro_pro;
	private String n_cate;
	private String n_partida;
	private String n_frente;
	private String n_valua;
	private String c_eximido;
	private String n_reparto;
	private String n_orden;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c_cuenta == null) ? 0 : c_cuenta.hashCode());
		result = prime * result + ((c_eximido == null) ? 0 : c_eximido.hashCode());
		result = prime * result + ((c_postal == null) ? 0 : c_postal.hashCode());
		result = prime * result + ((c_seccion == null) ? 0 : c_seccion.hashCode());
		result = prime * result + ((d_apellido == null) ? 0 : d_apellido.hashCode());
		result = prime * result + ((d_calle_pro == null) ? 0 : d_calle_pro.hashCode());
		result = prime * result + ((d_calle_tit == null) ? 0 : d_calle_tit.hashCode());
		result = prime * result + ((d_lmanz == null) ? 0 : d_lmanz.hashCode());
		result = prime * result + ((d_loc_tit == null) ? 0 : d_loc_tit.hashCode());
		result = prime * result + ((d_lparc == null) ? 0 : d_lparc.hashCode());
		result = prime * result + ((d_lufun == null) ? 0 : d_lufun.hashCode());
		result = prime * result + ((d_nombre == null) ? 0 : d_nombre.hashCode());
		result = prime * result + ((f_proceso == null) ? 0 : f_proceso.hashCode());
		result = prime * result + ((n_cate == null) ? 0 : n_cate.hashCode());
		result = prime * result + ((n_circ == null) ? 0 : n_circ.hashCode());
		result = prime * result + ((n_fraccion == null) ? 0 : n_fraccion.hashCode());
		result = prime * result + ((n_frente == null) ? 0 : n_frente.hashCode());
		result = prime * result + ((n_manz == null) ? 0 : n_manz.hashCode());
		result = prime * result + ((n_nro_pro == null) ? 0 : n_nro_pro.hashCode());
		result = prime * result + ((n_nro_tit == null) ? 0 : n_nro_tit.hashCode());
		result = prime * result + ((n_orden == null) ? 0 : n_orden.hashCode());
		result = prime * result + ((n_parc == null) ? 0 : n_parc.hashCode());
		result = prime * result + ((n_partida == null) ? 0 : n_partida.hashCode());
		result = prime * result + ((n_poli == null) ? 0 : n_poli.hashCode());
		result = prime * result + ((n_reparto == null) ? 0 : n_reparto.hashCode());
		result = prime * result + ((n_ufun == null) ? 0 : n_ufun.hashCode());
		result = prime * result + ((n_valua == null) ? 0 : n_valua.hashCode());
		result = prime * result + ((n_verificador == null) ? 0 : n_verificador.hashCode());
		result = prime * result + ((s_deuda == null) ? 0 : s_deuda.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InformacionPrincipalReciboAbl other = (InformacionPrincipalReciboAbl) obj;
		if (c_cuenta == null) {
			if (other.c_cuenta != null)
				return false;
		} else if (!c_cuenta.equals(other.c_cuenta))
			return false;
		if (c_eximido == null) {
			if (other.c_eximido != null)
				return false;
		} else if (!c_eximido.equals(other.c_eximido))
			return false;
		if (c_postal == null) {
			if (other.c_postal != null)
				return false;
		} else if (!c_postal.equals(other.c_postal))
			return false;
		if (c_seccion == null) {
			if (other.c_seccion != null)
				return false;
		} else if (!c_seccion.equals(other.c_seccion))
			return false;
		if (d_apellido == null) {
			if (other.d_apellido != null)
				return false;
		} else if (!d_apellido.equals(other.d_apellido))
			return false;
		if (d_calle_pro == null) {
			if (other.d_calle_pro != null)
				return false;
		} else if (!d_calle_pro.equals(other.d_calle_pro))
			return false;
		if (d_calle_tit == null) {
			if (other.d_calle_tit != null)
				return false;
		} else if (!d_calle_tit.equals(other.d_calle_tit))
			return false;
		if (d_lmanz == null) {
			if (other.d_lmanz != null)
				return false;
		} else if (!d_lmanz.equals(other.d_lmanz))
			return false;
		if (d_loc_tit == null) {
			if (other.d_loc_tit != null)
				return false;
		} else if (!d_loc_tit.equals(other.d_loc_tit))
			return false;
		if (d_lparc == null) {
			if (other.d_lparc != null)
				return false;
		} else if (!d_lparc.equals(other.d_lparc))
			return false;
		if (d_lufun == null) {
			if (other.d_lufun != null)
				return false;
		} else if (!d_lufun.equals(other.d_lufun))
			return false;
		if (d_nombre == null) {
			if (other.d_nombre != null)
				return false;
		} else if (!d_nombre.equals(other.d_nombre))
			return false;
		if (f_proceso == null) {
			if (other.f_proceso != null)
				return false;
		} else if (!f_proceso.equals(other.f_proceso))
			return false;
		if (n_cate == null) {
			if (other.n_cate != null)
				return false;
		} else if (!n_cate.equals(other.n_cate))
			return false;
		if (n_circ == null) {
			if (other.n_circ != null)
				return false;
		} else if (!n_circ.equals(other.n_circ))
			return false;
		if (n_fraccion == null) {
			if (other.n_fraccion != null)
				return false;
		} else if (!n_fraccion.equals(other.n_fraccion))
			return false;
		if (n_frente == null) {
			if (other.n_frente != null)
				return false;
		} else if (!n_frente.equals(other.n_frente))
			return false;
		if (n_manz == null) {
			if (other.n_manz != null)
				return false;
		} else if (!n_manz.equals(other.n_manz))
			return false;
		if (n_nro_pro == null) {
			if (other.n_nro_pro != null)
				return false;
		} else if (!n_nro_pro.equals(other.n_nro_pro))
			return false;
		if (n_nro_tit == null) {
			if (other.n_nro_tit != null)
				return false;
		} else if (!n_nro_tit.equals(other.n_nro_tit))
			return false;
		if (n_orden == null) {
			if (other.n_orden != null)
				return false;
		} else if (!n_orden.equals(other.n_orden))
			return false;
		if (n_parc == null) {
			if (other.n_parc != null)
				return false;
		} else if (!n_parc.equals(other.n_parc))
			return false;
		if (n_partida == null) {
			if (other.n_partida != null)
				return false;
		} else if (!n_partida.equals(other.n_partida))
			return false;
		if (n_poli == null) {
			if (other.n_poli != null)
				return false;
		} else if (!n_poli.equals(other.n_poli))
			return false;
		if (n_reparto == null) {
			if (other.n_reparto != null)
				return false;
		} else if (!n_reparto.equals(other.n_reparto))
			return false;
		if (n_ufun == null) {
			if (other.n_ufun != null)
				return false;
		} else if (!n_ufun.equals(other.n_ufun))
			return false;
		if (n_valua == null) {
			if (other.n_valua != null)
				return false;
		} else if (!n_valua.equals(other.n_valua))
			return false;
		if (n_verificador == null) {
			if (other.n_verificador != null)
				return false;
		} else if (!n_verificador.equals(other.n_verificador))
			return false;
		if (s_deuda == null) {
			if (other.s_deuda != null)
				return false;
		} else if (!s_deuda.equals(other.s_deuda))
			return false;
		return true;
	}



	public String getD_apellido() {
		return d_apellido;
	}

	public void setD_apellido(String d_apellido) {
		this.d_apellido = d_apellido;
	}

	public String getD_nombre() {
		return d_nombre;
	}

	public void setD_nombre(String d_nombre) {
		this.d_nombre = d_nombre;
	}

	public String getC_cuenta() {
		return c_cuenta;
	}

	public void setC_cuenta(String c_cuenta) {
		this.c_cuenta = c_cuenta;
	}

	public String getN_verificador() {
		return n_verificador;
	}

	public void setN_verificador(String n_verificador) {
		this.n_verificador = n_verificador;
	}

	public String getD_calle_tit() {
		return d_calle_tit;
	}

	public void setD_calle_tit(String d_calle_tit) {
		this.d_calle_tit = d_calle_tit;
	}

	public String getN_nro_tit() {
		return n_nro_tit;
	}

	public void setN_nro_tit(String n_nro_tit) {
		this.n_nro_tit = n_nro_tit;
	}

	public String getD_loc_tit() {
		return d_loc_tit;
	}

	public void setD_loc_tit(String d_loc_tit) {
		this.d_loc_tit = d_loc_tit;
	}

	public String getC_postal() {
		return c_postal;
	}

	public void setC_postal(String c_postal) {
		this.c_postal = c_postal;
	}

	public String getN_circ() {
		return (n_circ != null) ? n_circ : "";
	}

	public void setN_circ(String n_circ) {
		this.n_circ = n_circ;
	}

	public String getC_seccion() {
		return (c_seccion != null) ? c_seccion : "";
	}

	public void setC_seccion(String c_seccion) {
		this.c_seccion = c_seccion;
	}

	public String getN_fraccion() {
		return (n_fraccion != null) ? n_fraccion : "";
	}

	public void setN_fraccion(String n_fraccion) {
		this.n_fraccion = n_fraccion;
	}

	public String getN_manz() {
		return (n_manz != null) ? n_manz : "";
	}

	public void setN_manz(String n_manz) {
		this.n_manz = n_manz;
	}

	public String getD_lmanz() {
		return (d_lmanz != null) ? d_lmanz : "";
	}

	public void setD_lmanz(String d_lmanz) {
		this.d_lmanz = d_lmanz;
	}

	public String getN_parc() {
		return (n_parc != null) ? n_parc : "";
	}

	public void setN_parc(String n_parc) {
		this.n_parc = n_parc;
	}

	public String getD_lparc() {
		return (d_lparc != null) ? d_lparc : "";
	}

	public void setD_lparc(String d_lparc) {
		this.d_lparc = d_lparc;
	}

	public String getN_ufun() {
		return (n_ufun != null) ? n_ufun : "";
	}

	public void setN_ufun(String n_ufun) {
		this.n_ufun = n_ufun;
	}

	public String getD_lufun() {
		return (d_lufun != null) ? d_lufun : "";
	}

	public void setD_lufun(String d_lufun) {
		this.d_lufun = d_lufun;
	}

	public String getN_poli() {
		return (n_poli != null) ? n_poli : "";
	}

	public void setN_poli(String n_poli) {
		this.n_poli = n_poli;
	}

	public String getS_deuda() {
		return s_deuda;
	}

	public void setS_deuda(String s_deuda) {
		this.s_deuda = s_deuda;
	}

	public String getF_proceso() {
		return f_proceso;
	}

	public void setF_proceso(String f_proceso) {
		this.f_proceso = f_proceso;
	}

	public String getD_calle_pro() {
		return d_calle_pro;
	}

	public void setD_calle_pro(String d_calle_pro) {
		this.d_calle_pro = d_calle_pro;
	}

	public String getN_nro_pro() {
		return n_nro_pro;
	}

	public void setN_nro_pro(String n_nro_pro) {
		this.n_nro_pro = n_nro_pro;
	}

	public String getN_cate() {
		return n_cate;
	}

	public void setN_cate(String n_cate) {
		this.n_cate = n_cate;
	}

	public String getN_partida() {
		return n_partida;
	}

	public void setN_partida(String n_partida) {
		this.n_partida = n_partida;
	}

	public String getN_frente() {
		return n_frente;
	}

	public void setN_frente(String n_frente) {
		this.n_frente = n_frente;
	}

	public String getN_valua() {
		return n_valua;
	}

	public void setN_valua(String n_valua) {
		this.n_valua = n_valua;
	}

	public String getC_eximido() {
		return c_eximido;
	}

	public void setC_eximido(String c_eximido) {
		this.c_eximido = c_eximido;
	}



	public String getN_reparto() {
		return n_reparto;
	}

	public void setN_reparto(String n_reparto) {
		this.n_reparto = n_reparto;
	}

	public String getN_orden() {
		return n_orden;
	}

	public void setN_orden(String n_orden) {
		this.n_orden = n_orden;
	}
}