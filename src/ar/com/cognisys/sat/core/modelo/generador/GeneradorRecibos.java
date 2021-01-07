package ar.com.cognisys.sat.core.modelo.generador;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;

import ar.com.cognisys.sat.core.modelo.comun.deudas.Cuota;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public abstract class GeneradorRecibos extends GeneradorBoletaPago {
	
	private String pathArchibo;
	private byte[] datos;
	
	public GeneradorRecibos(String numeroComprobante, Date fechaVencimiento, Float monto) {
		
		super(numeroComprobante, fechaVencimiento, monto);
	}
	
	public abstract void generarRecibo() throws ExcepcionControladaError;
	
	protected abstract String generarCodigoBarras();
	
	protected abstract String generarCodigoBarras(String tasa);
	
	protected String generarArchivo(String nombreArchivo, HashMap<String, Object> mapaValores, InputStream inputJasper) throws ExcepcionControladaError {
		
		try {		    
			this.setDatos( JasperRunManager.runReportToPdf(inputJasper, mapaValores, new JREmptyDataSource()) );
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
	
	protected String generarImagenCodigoBarras(String numero) throws ExcepcionControladaError {
		
		try {
			AbstractBarcodeBean barcode = new Interleaved2Of5Bean();
			barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
	        
	        barcode.setModuleWidth(1.0); 
	        barcode.setBarHeight(40.0);
	        barcode.setFontSize(10.0);
	        barcode.setQuietZone(10.0);
	        barcode.doQuietZone(true);                
	        BarcodeDimension dim = barcode.calcDimensions(numero);
	        int width = (int) dim.getWidth(0) + 20;
	        int height = (int) dim.getHeight(0);        
	        
	        BufferedImage imgtext = new BufferedImage(width, height,  BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = imgtext.createGraphics();
	        
	        g2d.setColor(Color.WHITE);
	        g2d.fillRect(0, 0, width, height);
	        
	        g2d.setColor(Color.BLACK);
	        
	        File file = File.createTempFile("barcode-" + Long.toString(new Date().getTime()), ".jpg");
	        
	        try {
	            barcode.generateBarcode(new Java2DCanvasProvider(g2d, 0), numero);
	            ImageIO.write(imgtext, "JPEG", file);
	        	
	        } catch (Exception e) {
	            g2d.drawRect(0, 0, width - 1, height - 1);
	            g2d.drawString(numero, 2, height - 3);
	        } finally {
	        	g2d.dispose();
	        }
	        
	        return file.getAbsolutePath();
		} catch (Exception e) {
			throw new ExcepcionControladaError("No se pudo generar el código de barras", e);
		}
	}
	
	protected static JRDataSource generarDataSource(List<Cuota> cuotas) {

		List<Cuota> cuotasParser = new ArrayList<Cuota>();
		
		for (Cuota cuota : cuotas) {
			String[] partes = cuota.getPeriodo().trim().split("-"); 
			cuota.setPeriodo(partes[0]+"-"+String.format("%02d", Integer.parseInt(partes[1])));
			cuotasParser.add(cuota);
		}
		
	    return new JRBeanCollectionDataSource(cuotasParser);
	}
	
	protected InputStream recuperarPathArchivoReporte(String nombreArchivo) {			
		return GeneradorRecibos.class.getResourceAsStream("/"+nombreArchivo);
	}
	
	protected InputStream recuperarPathImagenReporte(String nombreImagen) {
		return GeneradorRecibos.class.getResourceAsStream("/recibos/"+nombreImagen);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(datos);
		result = prime * result + ((pathArchibo == null) ? 0 : pathArchibo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneradorRecibos other = (GeneradorRecibos) obj;
		if (!Arrays.equals(datos, other.datos))
			return false;
		if (pathArchibo == null) {
			if (other.pathArchibo != null)
				return false;
		} else if (!pathArchibo.equals(other.pathArchibo))
			return false;
		return true;
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
}