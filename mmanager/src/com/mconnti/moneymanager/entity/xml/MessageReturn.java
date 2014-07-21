package com.mconnti.moneymanager.entity.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Config;
import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.Planning;
import com.mconnti.moneymanager.entity.PlanningGroup;
import com.mconnti.moneymanager.entity.PlanningItem;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.Role;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.User;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement
public class MessageReturn {
	
	private String message;
	
	private User user;
	
	private Currency currency;
	
	private List<Currency> currencyList;
	
	private TypeAccount account;
	
	private TypeClosure typeClosure;
	
	private Description description;
	
	private CreditCard creditCard;
	
	private Config config;
	
	private Register register;
	
	private Closure closure;
	
	private Planning planning;
	
	private PlanningGroup planningGroup;
	
	private PlanningItem planningItem;
	
	private Role role;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<Currency> currencyList) {
		this.currencyList = currencyList;
	}

	public TypeAccount getAccount() {
		return account;
	}

	public void setAccount(TypeAccount account) {
		this.account = account;
	}

	public TypeClosure getTypeClosure() {
		return typeClosure;
	}

	public void setTypeClosure(TypeClosure typeClosure) {
		this.typeClosure = typeClosure;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public Closure getClosure() {
		return closure;
	}

	public void setClosure(Closure closure) {
		this.closure = closure;
	}

	public Planning getPlanning() {
		return planning;
	}

	public void setPlanning(Planning planning) {
		this.planning = planning;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public PlanningItem getPlanningItem() {
		return planningItem;
	}

	public void setPlanningItem(PlanningItem planningItem) {
		this.planningItem = planningItem;
	}

	public PlanningGroup getPlanningGroup() {
		return planningGroup;
	}

	public void setPlanningGroup(PlanningGroup planningGroup) {
		this.planningGroup = planningGroup;
	}
}
