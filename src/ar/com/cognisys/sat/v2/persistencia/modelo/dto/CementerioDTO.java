package ar.com.cognisys.sat.v2.persistencia.modelo.dto;

import java.util.Date;

import ar.com.cognisys.sat.v2.persistencia.modelo.interfaz.ICuentaDetalle;

public class CementerioDTO implements ICuentaDetalle {

	private ContribuyenteDTO contribuyente;

	private Integer numeroNomenclador;
	private String nomenclador;
	private Date fechaAlta;
	private Date fechaRenovacion;
	private String lote;
	private String subLote;
	private String letraLote;
	private String tablon;
	private String subTablon;
	private String letraTablon;
	private Integer numeroSeccion;
	private String subSeccion;
	private String deudaLegales;
	private String deudaEspecial;

	public CementerioDTO(ContribuyenteDTO contribuyente, Integer numeroNomenclador, String nomenclador, Date fechaAlta, Date fechaRenovacion, String lote, String subLote, String letraLote,
			String tablon, String subTablon, String letraTablon, Integer numeroSeccion, String subSeccion, String deudaLegales, String deudaEspecial) {
		this.contribuyente = contribuyente;
		this.numeroNomenclador = numeroNomenclador;
		this.nomenclador = nomenclador;
		this.fechaAlta = fechaAlta;
		this.fechaRenovacion = fechaRenovacion;
		this.lote = lote;
		this.subLote = subLote;
		this.letraLote = letraLote;
		this.tablon = tablon;
		this.subTablon = subTablon;
		this.letraTablon = letraTablon;
		this.numeroSeccion = numeroSeccion;
		this.subSeccion = subSeccion;
		this.deudaLegales = deudaLegales;
		this.deudaEspecial = deudaEspecial;
	}
	
	public boolean isDeudaLegales() {
		return this.deudaLegales == null ? false : this.deudaLegales.equals( "S" );
	}
	
	public boolean isDeudaEspecial() {
		return this.deudaEspecial == null ? false : this.deudaEspecial.equals( "S" );
	}
	
	@Override
	public String generarDescripcion() {
		if ( this.contribuyente== null )
			return null;
		
		String aux = this.contribuyente.getApellido();

		if ( aux != null && this.contribuyente.getNombre() != null )
			aux = aux + this.contribuyente.getNombre();
		
		return aux;
	}

	public ContribuyenteDTO getContribuyente() {
		return contribuyente;
	}

	public Integer getNumeroNomenclador() {
		return numeroNomenclador;
	}

	public String getNomenclador() {
		return nomenclador;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public Date getFechaRenovacion() {
		return fechaRenovacion;
	}

	public String getLote() {
		return lote;
	}

	public String getSubLote() {
		return subLote;
	}

	public String getLetraLote() {
		return letraLote;
	}

	public String getTablon() {
		return tablon;
	}

	public String getSubTablon() {
		return subTablon;
	}

	public String getLetraTablon() {
		return letraTablon;
	}

	public Integer getNumeroSeccion() {
		return numeroSeccion;
	}

	public String getSubSeccion() {
		return subSeccion;
	}

	public String getDeudaLegales() {
		return deudaLegales;
	}

	public String getDeudaEspecial() {
		return deudaEspecial;
	}

}
