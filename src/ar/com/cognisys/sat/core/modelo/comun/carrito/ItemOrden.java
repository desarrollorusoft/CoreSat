package ar.com.cognisys.sat.core.modelo.comun.carrito;

import java.io.Serializable;

public class ItemOrden implements Serializable, Comparable<ItemOrden> {
	
	private static final long serialVersionUID = -8736613263228481376L;
	private String addressId = "";
	private String availableDays = "";
	private String deliveryDate = "";
	private String descrip = "";
	private String id = "";
	private String price = "";
	private String receiverId = "";
	private String unitPrice = "";
	private String units = "";
	
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	
	public String toXML() {
		
		/*
		 Las cosas que estan comentadas es porque como ninguno de los productos
		 son entregable no es necesario mandarlos.
		*/
				
		String xmlElementType = "";

		xmlElementType += "\n      <Item ";
		xmlElementType += " Id=\""+this.id+"\" ";
		xmlElementType += " Descrip=\""+this.descrip+"\" ";
		xmlElementType += " Units=\""+this.units+"\" ";
		xmlElementType += " Price=\""+this.price+"\" ";
		xmlElementType += " UnitPrice=\""+this.unitPrice+"\" ";

//		Asi es como deberia ser asi, pero como no es un producto entregable no hace falta.
//		xmlElementType += " AddressId=\""+this.addressId+"\" ";
//		xmlElementType += " ReceiverId=\""+this.receiverId+"\" ";
//		xmlElementType += " DeliveryDate=\""+this.deliveryDate+"\" ";
//		xmlElementType += " AvailableDays=\""+this.availableDays+"\" ";
//
//	    Entonces lo dejamos fijo de esta manera.
		xmlElementType += " AddressId=\"0\" ";
		xmlElementType += " AvailableDays=\"\" ";
		xmlElementType += " DeliveryDate=\"\" ";
		xmlElementType += " ReceiverId=\"0\" ";



		xmlElementType += " />\n";

		return xmlElementType;
	}
	
	@Override
	public int compareTo(ItemOrden o) {
		if (o == null) {
			return 1;
		}
		int out = this.getPeriodoAnio().compareTo(o.getPeriodoAnio()); 
		if (out != 0){
			return out;
		} else {
			int out2 = this.getPeriodoMes().compareTo(o.getPeriodoMes()); 
			
			if (out2 != 0){
				return out2;
			} else {
				return this.getDescrip().compareTo(o.getDescrip());
			}
		}			
	}
	
	public String recuperarPeriodo() {
		
		return getDescrip().substring(getDescrip().indexOf("Periodo: ") + ("Periodo: ").length(), (getDescrip().indexOf("Periodo: ") + ("Periodo: ").length())+ 7);
	}
	
	public Integer getPeriodoAnio(){
		String periodo = recuperarPeriodo();
		return new Integer(periodo.split("-")[0]);
	}
	
	public Integer getPeriodoMes(){
		String periodo = recuperarPeriodo();
		return new Integer(periodo.split("-")[1]);
	}
}
