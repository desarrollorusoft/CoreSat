package ar.com.cognisys.sat.core.modelo.comun.carrito;

import java.io.Serializable;

public class UsuarioCarrito implements Serializable {
	
	private static final long serialVersionUID = -2322751917155487520L;
	private String firstName = "";
	private String lastName = "";
	private String docId = "";
	private String docNo = "";
	private String ivaId = "";
	private String email = "";
	private String courtTitleId = "";
	private String occupationId = "";
	private String phoneNo = "";
	private String mobileNo = "";
	private String faxNo = "";
	private String birthDay = "";
	private String countryId = "";
	private String addToEmailList = "";
	private String pmtMethodId = "";
	private String pmtMethodAddressId = "";
	
	public String toXML() {

		String xmlElementType = "";
		xmlElementType += "\n<User>\n";
		xmlElementType += "  <FirstName>"+this.firstName+"</FirstName>\n";
		xmlElementType += "  <LastName>"+this.lastName+"</LastName>\n";
		xmlElementType += "  <DocId>"+this.docId+"</DocId>\n";
		xmlElementType += "	 <DocNo>"+this.docNo+"</DocNo>\n";
		xmlElementType += "	 <IvaId>"+this.ivaId+"</IvaId>\n";
		xmlElementType += "	 <Email>"+this.email+"</Email>\n";
		xmlElementType += "	 <CourtTitleId>"+this.courtTitleId+"</CourtTitleId>\n";
		xmlElementType += "	 <OccupationId>"+this.occupationId+"</OccupationId>\n";
		xmlElementType += "	 <PhoneNo>"+this.phoneNo+"</PhoneNo>\n";
		xmlElementType += "	 <MobileNo>"+this.mobileNo+"</MobileNo>\n";
		xmlElementType += "	 <FaxNo>"+this.faxNo+"</FaxNo>\n";
		xmlElementType += "	 <BirthDay>"+this.birthDay+"</BirthDay>\n";
		xmlElementType += "	 <CountryId>"+this.countryId+"</CountryId>\n";
		xmlElementType += "	 <AddToEmailList>"+this.addToEmailList+"</AddToEmailList>\n";
		xmlElementType += "</User>\n";
		return xmlElementType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getIvaId() {
		return ivaId;
	}

	public void setIvaId(String ivaId) {
		this.ivaId = ivaId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCourtTitleId() {
		return courtTitleId;
	}

	public void setCourtTitleId(String courtTitleId) {
		this.courtTitleId = courtTitleId;
	}

	public String getOccupationId() {
		return occupationId;
	}

	public void setOccupationId(String occupationId) {
		this.occupationId = occupationId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getAddToEmailList() {
		return addToEmailList;
	}

	public void setAddToEmailList(String addToEmailList) {
		this.addToEmailList = addToEmailList;
	}

	public String getPmtMethodId() {
		return pmtMethodId;
	}

	public void setPmtMethodId(String pmtMethodId) {
		this.pmtMethodId = pmtMethodId;
	}

	public String getPmtMethodAddressId() {
		return pmtMethodAddressId;
	}

	public void setPmtMethodAddressId(String pmtMethodAddressId) {
		this.pmtMethodAddressId = pmtMethodAddressId;
	}
}