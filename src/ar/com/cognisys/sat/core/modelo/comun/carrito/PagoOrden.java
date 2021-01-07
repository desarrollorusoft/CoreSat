package ar.com.cognisys.sat.core.modelo.comun.carrito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PagoOrden {

	private Orden order = new Orden();
	private UsuarioCarrito user = new UsuarioCarrito();
	private List<Direccion> addresses = new ArrayList<Direccion>();
	private List<Destinatario> receivers = new ArrayList<Destinatario>();
	
	public Orden getOrder() {
		return order;
	}
	public void setOrder(Orden order) {
		this.order = order;
	}
	public UsuarioCarrito getUser() {
		return user;
	}
	public void setUser(UsuarioCarrito user) {
		this.user = user;
	}
	public List<Direccion> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Direccion> addresses) {
		this.addresses = addresses;
	}
	public List<Destinatario> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<Destinatario> receivers) {
		this.receivers = receivers;
	}
	
	public String toXML() {
		
		String xmlSchema = "";

		xmlSchema +=" <XPU_ExternalEnvelope>\n";
		xmlSchema +=" 	<Header>\n";
		xmlSchema +=" 		<TransactionId>XPU_PayOrder</TransactionId>\n";
		xmlSchema +=" 	</Header>\n";
		xmlSchema +=" 	<Body>\n";
		
			
		xmlSchema += "<XPU_PayOrder>\n";
		xmlSchema += this.order.toXML();

		if(this.addresses.isEmpty()){
			xmlSchema += "  <Addresses/>\n";
		}else{
			xmlSchema += "  <Addresses>\n";
			Iterator<Direccion> itAddr = this.addresses.iterator();
			while(itAddr.hasNext()) {
				xmlSchema += itAddr.next().toXML();
			}
			xmlSchema += "  </Addresses>\n";
		}

		if(this.receivers == null || this.receivers.isEmpty()){
			xmlSchema += "  <Receivers/>\n";
		}else{	
			xmlSchema += "  <Receivers>\n";
			Iterator<Destinatario> itRecevers = this.receivers.iterator();
			while(itRecevers.hasNext()) {
				xmlSchema += itRecevers.next().toXML();
			}
			xmlSchema += "  </Receivers>\n";
		}

	    xmlSchema += this.user.toXML();

		xmlSchema += "</XPU_PayOrder>\n";

		xmlSchema += "	</Body>\n";
		xmlSchema += "</XPU_ExternalEnvelope>\n";
		
		return xmlSchema;
	}
}
