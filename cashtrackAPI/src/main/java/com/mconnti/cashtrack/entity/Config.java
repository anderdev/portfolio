package com.mconnti.cashtrack.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="config")
@XmlRootElement
public class Config implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;
	
	@ManyToOne(cascade = { CascadeType.REFRESH }, targetEntity = Currency.class)
	@JoinColumn(name = "currency_id", foreignKey = @ForeignKey(name="FK_CONFIG_CURRENCY"))
	private Currency currency;
	
	@ManyToOne(cascade = { CascadeType.REFRESH }, targetEntity = TypeClosure.class)
	@JoinColumn(name = "typeclosure_id", foreignKey = @ForeignKey(name="FK_CONFIG_TYPE_CLOSURE"))
	private TypeClosure typeClosure;
	
	@ManyToOne(cascade = { CascadeType.REFRESH }, targetEntity = User.class)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="FK_CONFIG_USER"))
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public TypeClosure getTypeClosure() {
		return typeClosure;
	}

	public void setTypeClosure(TypeClosure typeClosure) {
		this.typeClosure = typeClosure;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
