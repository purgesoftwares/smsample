package com.allowify.model;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Card {

	@Id 
	private String id;
	
	@NotNull(message = "Card number should not be blank.")	
	private Long cardNumber;
	
	@NotEmpty(message = "Bank name should not be blank.")
	private String bankName;
	
	@NotNull(message = "preference should not be blank.")
	private Preference preference;
	
	public Card() {
		
    }
	
	public Card(Long cardNumber, String bankName) {
        this.cardNumber = cardNumber;
        this.bankName = bankName;
    }
	
	public Card(Long cardNumber, String bankName, Preference preference) {
        this.cardNumber = cardNumber;
        this.bankName = bankName;
        this.preference = preference;
    }
	
	public Long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getId() {
	  return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the preference
	 */
	public Preference getPreference() {
		return preference;
	}

	/**
	 * @param preference the preference to set
	 */
	public void setPreference(Preference preference) {
		this.preference = preference;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Card [id=" + id + ", cardNumber=" + cardNumber + ", bankName="
				+ bankName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bankName == null) ? 0 : bankName.hashCode());
		result = prime * result
				+ ((cardNumber == null) ? 0 : cardNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (cardNumber == null) {
			if (other.cardNumber != null)
				return false;
		} else if (!cardNumber.equals(other.cardNumber))
			return false;
		return true;
	}
	
	

}
