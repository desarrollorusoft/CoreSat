package ar.com.cognisys.sat.core.modelo.comun;

public class TasaAbl {

	private String n_ano;
	private String n_cuota;
	private String i_deuda_orig;
	private String f_prim_vto;
	private String i_rec_prim_vto;
	private String f_pago;
	private String i_deuda;
	private String f_seg_vto;
	private String i_rec_seg_vtou;
	private String n_comprob;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f_pago == null) ? 0 : f_pago.hashCode());
		result = prime * result + ((f_prim_vto == null) ? 0 : f_prim_vto.hashCode());
		result = prime * result + ((f_seg_vto == null) ? 0 : f_seg_vto.hashCode());
		result = prime * result + ((i_deuda == null) ? 0 : i_deuda.hashCode());
		result = prime * result + ((i_deuda_orig == null) ? 0 : i_deuda_orig.hashCode());
		result = prime * result + ((i_rec_prim_vto == null) ? 0 : i_rec_prim_vto.hashCode());
		result = prime * result + ((i_rec_seg_vtou == null) ? 0 : i_rec_seg_vtou.hashCode());
		result = prime * result + ((n_ano == null) ? 0 : n_ano.hashCode());
		result = prime * result + ((n_comprob == null) ? 0 : n_comprob.hashCode());
		result = prime * result + ((n_cuota == null) ? 0 : n_cuota.hashCode());
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
		TasaAbl other = (TasaAbl) obj;
		if (f_pago == null) {
			if (other.f_pago != null)
				return false;
		} else if (!f_pago.equals(other.f_pago))
			return false;
		if (f_prim_vto == null) {
			if (other.f_prim_vto != null)
				return false;
		} else if (!f_prim_vto.equals(other.f_prim_vto))
			return false;
		if (f_seg_vto == null) {
			if (other.f_seg_vto != null)
				return false;
		} else if (!f_seg_vto.equals(other.f_seg_vto))
			return false;
		if (i_deuda == null) {
			if (other.i_deuda != null)
				return false;
		} else if (!i_deuda.equals(other.i_deuda))
			return false;
		if (i_deuda_orig == null) {
			if (other.i_deuda_orig != null)
				return false;
		} else if (!i_deuda_orig.equals(other.i_deuda_orig))
			return false;
		if (i_rec_prim_vto == null) {
			if (other.i_rec_prim_vto != null)
				return false;
		} else if (!i_rec_prim_vto.equals(other.i_rec_prim_vto))
			return false;
		if (i_rec_seg_vtou == null) {
			if (other.i_rec_seg_vtou != null)
				return false;
		} else if (!i_rec_seg_vtou.equals(other.i_rec_seg_vtou))
			return false;
		if (n_ano == null) {
			if (other.n_ano != null)
				return false;
		} else if (!n_ano.equals(other.n_ano))
			return false;
		if (n_comprob == null) {
			if (other.n_comprob != null)
				return false;
		} else if (!n_comprob.equals(other.n_comprob))
			return false;
		if (n_cuota == null) {
			if (other.n_cuota != null)
				return false;
		} else if (!n_cuota.equals(other.n_cuota))
			return false;
		return true;
	}

	public String getN_ano() {
		return n_ano;
	}

	public void setN_ano(String n_ano) {
		this.n_ano = n_ano;
	}

	public String getN_cuota() {
		return n_cuota;
	}

	public void setN_cuota(String n_cuota) {
		this.n_cuota = n_cuota;
	}

	public String getI_deuda_orig() {
		return i_deuda_orig;
	}

	public void setI_deuda_orig(String i_deuda_orig) {
		this.i_deuda_orig = i_deuda_orig;
	}

	public String getF_prim_vto() {
		return f_prim_vto;
	}

	public void setF_prim_vto(String f_prim_vto) {
		this.f_prim_vto = f_prim_vto;
	}

	public String getI_rec_prim_vto() {
		return i_rec_prim_vto;
	}

	public void setI_rec_prim_vto(String i_rec_prim_vto) {
		this.i_rec_prim_vto = i_rec_prim_vto;
	}

	public String getF_pago() {
		return f_pago;
	}

	public void setF_pago(String f_pago) {
		this.f_pago = f_pago;
	}

	public String getI_deuda() {
		return i_deuda;
	}

	public void setI_deuda(String i_deuda) {
		this.i_deuda = i_deuda;
	}

	public String getF_seg_vto() {
		return f_seg_vto;
	}

	public void setF_seg_vto(String f_seg_vto) {
		this.f_seg_vto = f_seg_vto;
	}

	public String getI_rec_seg_vtou() {
		return i_rec_seg_vtou;
	}

	public void setI_rec_seg_vtou(String i_rec_seg_vtou) {
		this.i_rec_seg_vtou = i_rec_seg_vtou;
	}


	public String getN_comprob() {
		return n_comprob;
	}

	public void setN_comprob(String n_comprob) {
		this.n_comprob = n_comprob;
	}
}