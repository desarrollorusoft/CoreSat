package ar.com.cognisys.sat.core.modelo.comun.carrito;

import java.io.Serializable;

public class Destinatario implements Serializable {

	private static final long serialVersionUID = -5389110555472562688L;
	private String docId = "";
	private String docNo = "";
	private String firstName = "";
	private String lastName = "";
	private String referenceTitle = "";
	private java.lang.String id = "";
	
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
	public String getReferenceTitle() {
		return referenceTitle;
	}
	public void setReferenceTitle(String referenceTitle) {
		this.referenceTitle = referenceTitle;
	}	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	public String toXML() {

		String xmlElementType = "";

		xmlElementType += "<Receiver ";
		xmlElementType += " Id=\""+this.id+"\" ";
		xmlElementType += " DocId=\""+this.docId+"\" ";
		xmlElementType += " DocNo=\""+this.docNo+"\" ";
		xmlElementType += " FirstName=\""+this.firstName+"\" ";
		xmlElementType += " LastName=\""+this.lastName+"\" ";
		xmlElementType += " ReferenceTitle=\""+this.referenceTitle+"\" ";
		xmlElementType += " />";

		return xmlElementType;
	}
}
