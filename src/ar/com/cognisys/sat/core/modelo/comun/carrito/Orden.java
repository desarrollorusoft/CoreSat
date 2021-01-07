package ar.com.cognisys.sat.core.modelo.comun.carrito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Orden implements Serializable {

	private static final long serialVersionUID = 3859494272459494041L;
	private String id = "";
	private String currencyId = "";
	private String subTotal = "";
	private String shipping = "";
	private String discount = "";
	private String taxes = "";
	private String total = "";
	private String payments = "";
	private String deliverable = "";
	private String oneShipment = "";
	private String deliveryDate = "";
	private String shipToMultAddresses = "";
	private String addressId = "";
	private String receiverId = "";
	private String availableDays = "";
	private String expirationHours = "";
	private List<ItemOrden> items = new ArrayList<ItemOrden>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getTaxes() {
		return taxes;
	}
	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPayments() {
		return payments;
	}
	public void setPayments(String payments) {
		this.payments = payments;
	}
	public String getDeliverable() {
		return deliverable;
	}
	public void setDeliverable(String deliverable) {
		this.deliverable = deliverable;
	}
	public String getOneShipment() {
		return oneShipment;
	}
	public void setOneShipment(String oneShipment) {
		this.oneShipment = oneShipment;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getShipToMultAddresses() {
		return shipToMultAddresses;
	}
	public void setShipToMultAddresses(String shipToMultAddresses) {
		this.shipToMultAddresses = shipToMultAddresses;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
	}
	public String getExpirationHours() {
		return expirationHours;
	}
	public void setExpirationHours(String expirationHours) {
		this.expirationHours = expirationHours;
	}
	public List<ItemOrden> getItems() {
		return items;
	}
	public void setItems(List<ItemOrden> items) {
		this.items = items;
	}
	
	public String toXML() {

		String xmlElementType = "";

		xmlElementType += "\n  <Order>\n";
		xmlElementType += "    <Id>"+this.id+"</Id>\n";
		xmlElementType += "    <CurrencyId>"+this.currencyId+"</CurrencyId>\n";
		xmlElementType += "    <SubTotal>"+this.subTotal+"</SubTotal>\n";
		xmlElementType += "    <Shipping>"+this.shipping+"</Shipping>\n";
		xmlElementType += "    <Discount>"+this.discount+"</Discount>\n";
		xmlElementType += "    <Taxes>"+this.taxes+"</Taxes>\n";
		xmlElementType += "    <Total>"+this.total+"</Total>\n";
		xmlElementType += "    <Payments>"+this.payments+"</Payments>\n";
		xmlElementType += "    <Deliverable>"+this.deliverable+"</Deliverable>\n";
		xmlElementType += "    <OneShipment>"+this.oneShipment+"</OneShipment>\n";
		xmlElementType += "    <DeliveryDate>"+this.deliveryDate+"</DeliveryDate>\n";
		xmlElementType += "    <ShipToMultAddresses>"+this.shipToMultAddresses+"</ShipToMultAddresses>\n";
		xmlElementType += "    <AddressId>"+this.addressId+"</AddressId>\n";
		xmlElementType += "    <ReceiverId>"+this.receiverId+"</ReceiverId>\n";
		xmlElementType += "    <AvailableDays>"+this.availableDays+"</AvailableDays>\n";
		xmlElementType += "    <ExpirationHours>"+this.expirationHours+"</ExpirationHours>\n";
		xmlElementType += "    <Items>\n";
		Iterator<ItemOrden> itItem = this.items.iterator();
		
		while(itItem.hasNext()) {
			xmlElementType += itItem.next().toXML();
		}
		
		xmlElementType += "\n";
		xmlElementType += "    </Items>\n";
		xmlElementType += "  </Order>\n";

		return xmlElementType;
	}
}
