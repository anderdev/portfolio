package com.mconnti.moneymanager.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

import com.mconnti.moneymanager.utils.Crypt;

@Entity
@Table(name = "description")
public class Description implements Serializable {

	private static final long serialVersionUID = 7999831669893410987L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	private String description;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = TypeAccount.class)
	@JoinColumn(name = "account_id")
	@ForeignKey(name = "FK_DESCRIPTION_ACCOUNT")
	private TypeAccount typeAccount;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_DESCRIPTION_USER")
	private User user;
	
	@Transient
	private Boolean isCredit;
	
	@Transient
	private Boolean isCreditOriginal;
	
	@Transient
	private Boolean isDebit;
	
	@Transient
	private Boolean isDebitOriginal;
	
	@Transient
	private Boolean isGroup;
	
	@Transient
	private Boolean isGroupOriginal;
	
	@Transient
	private Boolean isSuperGroup;
	
	@Transient
	private Boolean isSuperGroupOriginal;

	public TypeAccount getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(TypeAccount typeAccount) {
		this.typeAccount = typeAccount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		try {
			if(description != null){
				return Crypt.decrypt(description);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setDescription(String description) {
		try {
			if(description != null){
				this.description = Crypt.encrypt(description);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(Boolean isCredit) {
		this.isCredit = isCredit;
	}

	public Boolean getIsDebit() {
		return isDebit;
	}

	public void setIsDebit(Boolean isDebit) {
		this.isDebit = isDebit;
	}

	public Boolean getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Boolean isGroup) {
		this.isGroup = isGroup;
	}

	public Boolean getIsSuperGroup() {
		return isSuperGroup;
	}

	public void setIsSuperGroup(Boolean isSuperGroup) {
		this.isSuperGroup = isSuperGroup;
	}

	public Boolean getIsCreditOriginal() {
		return isCreditOriginal;
	}

	public void setIsCreditOriginal(Boolean isCreditOriginal) {
		this.isCreditOriginal = isCreditOriginal;
	}

	public Boolean getIsDebitOriginal() {
		return isDebitOriginal;
	}

	public void setIsDebitOriginal(Boolean isDebitOriginal) {
		this.isDebitOriginal = isDebitOriginal;
	}

	public Boolean getIsGroupOriginal() {
		return isGroupOriginal;
	}

	public void setIsGroupOriginal(Boolean isGroupOriginal) {
		this.isGroupOriginal = isGroupOriginal;
	}

	public Boolean getIsSuperGroupOriginal() {
		return isSuperGroupOriginal;
	}

	public void setIsSuperGroupOriginal(Boolean isSuperGroupOriginal) {
		this.isSuperGroupOriginal = isSuperGroupOriginal;
	}
}
