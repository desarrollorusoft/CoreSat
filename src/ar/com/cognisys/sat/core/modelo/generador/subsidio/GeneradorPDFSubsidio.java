package ar.com.cognisys.sat.core.modelo.generador.subsidio;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.DatosComercio;
import ar.com.cognisys.sat.core.modelo.comun.tramiteSubsidio.TramiteSubsidio;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.generador.GeneradorRecibos;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GeneradorPDFSubsidio {

	private TramiteSubsidio tramite;
	private String pathArchibo;
	private byte[] datos;

	public GeneradorPDFSubsidio(TramiteSubsidio tramite) {
		this.tramite = tramite;
	}

	public void generarPDF() throws ExcepcionControladaError {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("CUIT", this.getTramite().getCuit());
		params.put("DNI", this.getTramite().getDni());
		params.put("CBU", this.getTramite().getCbu());
		params.put("BANCO", this.getTramite().getBanco().toUpperCase());
		params.put("NOMBRE_COMPLETO", this.getTramite().getRepresentante().toUpperCase());
		params.put("CORREO", this.getTramite().getCorreo().toLowerCase());
		params.put("TELEFONO", this.getTramite().getTelefono());
		params.put("CELULAR", this.getTramite().getCelular());
		params.put("DFE", this.getTramite().isDfe() ? "SI":"NO");
		params.put("FECHA", this.formatearFecha(new Date()));
		params.put("IMGCABEZA", this.recuperarPathImagenReporte("header-logo.jpg"));

		this.generarArchivo(Integer.toString(new Date().hashCode()), params, this.recuperarPathArchivoReporte("reporte_subsidio.jasper"), this.generarDataSource(this.getListaCuentas()));
	}

	private List<CuentaView> getListaCuentas() {
		List<CuentaView> lista = new ArrayList<CuentaView>();
		for (DatosComercio d: this.getTramite().getDatos())
			lista.add(new CuentaView(d.getCuenta().toString(),
					d.getNombreFantasia().toUpperCase(),
					d.isPropietario() ? "SI":"NO",
					"4119/"+d.getExpediente(),
					d.isPropietario() ? "-" : this.formatearFechaBarras(d.getInicioContrato()) + " - " + this.formatearFechaBarras(d.getFinContrato()),
					d.getCuentaAbl().toString(),
					d.isResponsableAbl() ? "SI":"NO"));

		return lista;
	}

	private String generarArchivo(String nombreArchivo, HashMap<String, Object> mapaValores, InputStream inputJasper, JRDataSource jrDS) throws ExcepcionControladaError {
		try {
			this.setDatos( JasperRunManager.runReportToPdf(inputJasper, mapaValores, jrDS) );
			File file = File.createTempFile(nombreArchivo, ".pdf");

			FileOutputStream stream = new FileOutputStream(file);
			stream.write(this.getDatos());
			stream.flush();
			stream.close();

			this.setPathArchibo(file.getAbsolutePath());
			System.err.println(file.getAbsolutePath());
		} catch (Exception e) {
			throw new ExcepcionControladaError("No se pudo generar el recibo", e);
		}

		return this.getPathArchibo();
	}

	private static JRDataSource generarDataSource(List<CuentaView> cuentas) {
		return new JRBeanCollectionDataSource(cuentas);
	}

	private InputStream recuperarPathArchivoReporte(String nombreArchivo) {
		return GeneradorRecibos.class.getResourceAsStream("/"+nombreArchivo);
	}

	private InputStream recuperarPathImagenReporte(String nombreImagen) {
		return GeneradorRecibos.class.getResourceAsStream("/recibos/"+nombreImagen);
	}

	public static String formatearFecha(Date fecha) {
		return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
	}
	
	public static String formatearFechaBarras(Date fecha) {
		return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
	}
	
	public static String formatearFechaHora(Date fecha) {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fecha);
	}

	public TramiteSubsidio getTramite() {
		return tramite;
	}

	public void setTramite(TramiteSubsidio tramite) {
		this.tramite = tramite;
	}

	public String getPathArchibo() {
		return pathArchibo;
	}

	public void setPathArchibo(String pathArchibo) {
		this.pathArchibo = pathArchibo;
	}

	public byte[] getDatos() {
		return datos;
	}

	public void setDatos(byte[] datos) {
		this.datos = datos;
	}

	public static void main(String args[]) throws ExcepcionControladaError, ParseException {
		TramiteSubsidio t = new TramiteSubsidio(1);
		t.setRepresentante("Gabriela Cecilia Berberian");
		t.setBanco("Banco Comafi");
		t.setCbu("2990045104500127230006");
		t.setDni("17806795");
		t.setCuit("27-17806795-3");
		t.setCorreo("gabrielaberberian@hotmail.com");

		DatosComercio d = new DatosComercio();
		d.setCuenta(69590);
		d.setNombreFantasia("Olivos Deportes");
		d.setExpediente("4439/14");
		d.setPropietario(false);
		d.setInicioContrato(new SimpleDateFormat("yyyy-MM-dd").parse("2017-12-01"));
		d.setFinContrato(new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-30"));
		d.setResponsableAbl(false);
		d.setCuentaAbl(141999);

		DatosComercio d2 = new DatosComercio();
		d2.setPropietario(false);
		d2.setResponsableAbl(false);
		d2.setNombreFantasia("Fasniniosda poije");
		d2.setInicioContrato(new Date());
		d2.setFinContrato(new Date());
		d2.setCuentaAbl(154400);
		d2.setCuenta(8974911);
		d2.setExpediente("1000054110");

		t.getDatos().add(d);
//		t.getDatos().add(d2);

		GeneradorPDFSubsidio g = new GeneradorPDFSubsidio(t);
		g.generarPDF();
		System.out.println(g.pathArchibo);
	}
}