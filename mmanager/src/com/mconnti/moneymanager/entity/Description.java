package com.mconnti.moneymanager.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Description implements Serializable {

	private static final long serialVersionUID = 7999831669893410987L;

	private Long id;

	private String description;

	private TypeAccount typeAccount;

	private User user;
	
    private Boolean isCredit;
    
    private Boolean isCreditOriginal;
	
	private Boolean isDebit;
	
	private Boolean isDebitOriginal;
	
	private Boolean isGroup;
	
	private Boolean isGroupOriginal;
	
	private Boolean isSuperGroup;
	
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
		return description;
	}

	public void setDescription(String description) {
		this.description =  description;
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
