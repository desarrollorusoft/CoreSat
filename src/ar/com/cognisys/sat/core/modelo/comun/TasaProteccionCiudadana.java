package ar.com.cognisys.sat.core.modelo.comun;

public class TasaProteccionCiudadana {

	private String i_deuda_orig;
	private String i_rec_prim_vto;
	private String i_deuda;
	private String i_rec_seg_vtou;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i_deuda == null) ? 0 : i_deuda.hashCode());
		result = prime * result + ((i_deuda_orig == null) ? 0 : i_deuda_orig.hashCode());
		result = prime * result + ((i_rec_prim_vto == null) ? 0 : i_rec_prim_vto.hashCode());
		result = prime * result + ((i_rec_seg_vtou == null) ? 0 : i_rec_seg_vtou.hashCode());
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
		TasaProteccionCiudadana other = (TasaProteccionCiudadana) obj;
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
		return true;
	}

	public String getI_deuda_orig() {
		return i_deuda_orig;
	}

	public void setI_deuda_orig(String i_deuda_orig) {
		this.i_deuda_orig = i_deuda_orig;
	}

	public String getI_rec_prim_vto() {
		return i_rec_prim_vto;
	}

	public void setI_rec_prim_vto(String i_rec_prim_vto) {
		this.i_rec_prim_vto = i_rec_prim_vto;
	}

	public String getI_deuda() {
		return i_deuda;
	}

	public void setI_deuda(String i_deuda) {
		this.i_deuda = i_deuda;
	}

	public String getI_rec_seg_vtou() {
		return i_rec_seg_vtou;
	}

	public void setI_rec_seg_vtou(String i_rec_seg_vtou) {
		this.i_rec_seg_vtou = i_rec_seg_vtou;
	}
}