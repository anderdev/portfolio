package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;

import com.mconnti.moneymanager.entity.xml.SearchObject;
import com.mconnti.moneymanager.utils.Crypt;

@Entity
@Table(name = "user")
@NamedQueries({@NamedQuery(name = "user.findByUsername", query = "SELECT us FROM User us WHERE us.username = :username")})
@XmlRootElement
public class User extends SearchObject implements Serializable{

	private static final long serialVersionUID = -5645425703632609531L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "super_user_id")
	@ForeignKey(name = "FK_USER_USER")
	private User superUser;
	
	private String name;
	
	private String email;
	
	@Column(name="birthdate")
	private Date birthDate;
	
	@Transient
	private String birth;
	
	@Column(name="register")
	private Date register;
	
	private String language;
	
	@Transient
	private Boolean admin;
	
	@Transient
	private Boolean defaultPassword;
	
	@Column(name="phrase")
	private String secretPhrase;
	
	private String username;
	
	private String password;
	
	@ManyToOne(cascade = { CascadeType.REFRESH }, targetEntity = Role.class)
	@JoinColumn(name = "role_id")
	@ForeignKey(name = "FK_USER_ROLE")
	private Role role;
	
	@Transient
	//Rest password
	private String pass;
	
	private Boolean excluded;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getRegister() {
		return register;
	}

	public void setRegister(Date register) {
		this.register = register;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getAdmin() {
		if(role.getId().equals(1L)){
			admin = true;
		}
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getSecretPhrase() {
		return secretPhrase;
	}

	public void setSecretPhrase(String secretPhrase) {
		this.secretPhrase = secretPhrase;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		try {
			this.password = Crypt.encrypt(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean getExcluded() {
		return excluded;
	}

	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getSuperUser() {
		return superUser;
	}

	public void setSuperUser(User superUser) {
		this.superUser = superUser;
	}

	public Boolean getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(Boolean defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

}
