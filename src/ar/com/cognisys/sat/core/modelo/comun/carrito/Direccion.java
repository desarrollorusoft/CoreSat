package ar.com.cognisys.sat.core.modelo.comun.carrito;

public class Direccion {

	private String addressId = "";
	private String street = "";
	private String no = "";
	private String level = "";
	private String division = "";
	private String phoneNo = "";
	private String countryId = "";
	private String stateId = "";
	private String stateDescrip = "";
	private String city = "";
	private String postalCode = "";
	private String comment = "";
	private String addressNick = "";
	
	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStateDescrip() {
		return stateDescrip;
	}

	public void setStateDescrip(String stateDescrip) {
		this.stateDescrip = stateDescrip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAddressNick() {
		return addressNick;
	}

	public void setAddressNick(String addressNick) {
		this.addressNick = addressNick;
	}

	public String toXML() {

		String xmlElementType = "";
		xmlElementType += "\n<Address>\n";
		xmlElementType += "	 <AddressId>"+this.addressId+"</AddressId>\n";
		xmlElementType += "  <Street>"+this.street+"</Street>\n";
		xmlElementType += "  <No>"+this.no+"</No>\n";
		xmlElementType += "	 <Level>"+this.level+"</Level>\n";
		xmlElementType += "	 <Division>"+this.division+"</Division>\n";
		xmlElementType += "	 <CountryId>"+this.countryId+"</CountryId>\n";
		xmlElementType += "	 <StateId>"+this.stateId+"</StateId>\n";
		xmlElementType += "	 <StateDescrip>"+this.stateDescrip+"</StateDescrip>\n";
		xmlElementType += "	 <City>"+this.city+"</City>\n";
		xmlElementType += "	 <PhoneNo>"+this.phoneNo+"</PhoneNo>\n";
		xmlElementType += "	 <PostalCode>"+this.postalCode+"</PostalCode>\n";
		xmlElementType += "	 <Comment>"+this.comment+"</Comment>\n";
		xmlElementType += "	 <AddressNick>"+this.addressNick+"</AddressNick>\n";
		xmlElementType += "</Address>\n";
		return xmlElementType;
	}
}
