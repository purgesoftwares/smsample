package com.allowify.model;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PromoCode implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String promoCode;
	
	private int discountType;
	
	private String discount;
	
	private int useLimitType;
	
	private int userLimit;
	
	private Date validFrom;
	
	private Date validTo;
	
	private int status;
	
	private int usedCounts;
	
	private Date created;
	
	private Date updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public int getUseLimitType() {
		return useLimitType;
	}

	public void setUseLimitType(int useLimitType) {
		this.useLimitType = useLimitType;
	}

	public int getUserLimit() {
		return userLimit;
	}

	public void setUserLimit(int userLimit) {
		this.userLimit = userLimit;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUsedCounts() {
		return usedCounts;
	}

	public void setUsedCounts(int usedCounts) {
		this.usedCounts = usedCounts;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	

}
