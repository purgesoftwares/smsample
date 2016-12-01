package com.allowify.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;

@Document
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@NotEmpty(message = "Email should not be blank.")
	@Email
	private String email;

	@Email
	@JsonIgnore
	private String userName;
	
	@NotEmpty(message = "First Name should not be blank.")
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private Date lastLogin;
	
	private int status;
	
	private Date created;
	
	private Date updated;
	
	private String gender;
	
	private String referenceCode;
	
	private boolean enabled;
	
	private String locale;

	@DBRef
	private UploadedFile profilePicture;
	
	@JsonIgnore
	private String password;
	
	@Transient
	private String newPassword;
	
	@JsonIgnore
	private Integer role;
	
	private int totalPoints;
	
	@Indexed
	private String addressId;
	
	//private Integer notificationType;

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	/*@DBRef
	private Set<Card> cards = new HashSet<Card>();
*/
	public User(String userName, String password, Integer role, Integer notificationType) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        //this.notificationType = notificationType;
    }
	
	public User(String userName, String password, Integer role, Integer notificationType, Set<Card> cards) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        //this.notificationType = notificationType;
        //this.cards = cards;
    }
	
	public User(String email, String userName, String firstName, String lastName, String phoneNumber, String password, Integer role) {
		this.userName = userName;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.status = 1;
        this.updated= new Date();
	}
	
	
	public User() {
		
    }
	
	public User(DBObject dbObject){
		
	}
	
	public User(Collection<SimpleGrantedAuthority> authorities,
			String password2, String userName2, boolean b, boolean c,
			boolean d, boolean e) {
		// TODO Auto-generated constructor stub
	}
	
	public User(String userName, String password, Integer role) {
		this.userName = userName;
        this.password = password;
        this.role = role;
	}

	public String getUserName() {
		return userName;
	}
		
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getEmail() {
		return email;
	}
		
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Integer getRole() {
		return this.role;
	}
	
	public void setRole(Integer role) {
		this.role = role;
	}
	
	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	
	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
		
/*	public Set<Card> getCards() {
		return this.cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

	public Card getCard(Long cardNumber) {
		
		Card card = null;
		Iterator<Card> cardIterator = cards.iterator();
		
		while(cardIterator.hasNext()) {
			Card tmpCard = cardIterator.next();
			if(cardNumber.equals(tmpCard.getCardNumber())) {
				card = tmpCard;
				break;
			}
		}		
		return card;
	}
	
	public Card removeCard(String cardID) {
		Card card = null;
		Iterator<Card> cardIterator = cards.iterator();
		
		while(cardIterator.hasNext()) {
			Card tmpCard = cardIterator.next();
			if(cardID.equals(tmpCard.getId())) {
				card = tmpCard;
				cardIterator.remove();
				break;
			}
		}		
		return card;
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}*/
	
	public String getId() {
	  return id;
	 }
	 public void setId(String id) {
		  this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	 @Override
	    public String toString() {
	        return String.format(
	                "Customer[id=%s, userName='%s', password='%s']", id, userName, password);
	    }

	/**
	 * @return the notificationType
	 */
	/*public Integer getNotificationType() {
		return notificationType;
	}*/

	/**
	 * @param notificationType the notificationType to set
	 */
	/*public void setNotificationType(Integer notificationType) {
		this.notificationType = notificationType;
	}*/

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the profilePicture
	 */
	public UploadedFile getProfilePicture() {
		return profilePicture;
	}

	/**
	 * @param profilePicture the profilePicture to set
	 */
	public void setProfilePicture(UploadedFile profilePicture) {
		this.profilePicture = profilePicture;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	 
}
