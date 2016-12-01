package com.allowify.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class VIP_Purchased implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String user_id;
	
	@DBRef
	private VIPCode vipCode;
	
	private String user_code_pi_id;
	
	private String invoice_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public VIPCode getVipCode() {
		return vipCode;
	}

	public void setVipCode(VIPCode vipCode) {
		this.vipCode = vipCode;
	}

	public String getUser_code_pi_id() {
		return user_code_pi_id;
	}

	public void setUser_code_pi_id(String user_code_pi_id) {
		this.user_code_pi_id = user_code_pi_id;
	}

	public String getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(String invoice_id) {
		this.invoice_id = invoice_id;
	}
	

}
