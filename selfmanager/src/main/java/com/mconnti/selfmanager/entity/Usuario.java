package com.mconnti.selfmanager.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -5645425703632609531L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Integer codigo;
	
	@Column(name="master_codigo")
	private Integer masterCodigo;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String email;
	
	@NotNull
	@Column(name="data_nascimento")
	private String dtNasc;
	
	@Column(name="data_cadastro")
	private Date dtCadastro;
	
	private String idioma;
	
	private Boolean administrador;
	
	@Column(name="palavra_secreta")
	private String palavraSecreta;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Cidade.class)
	@JoinColumn(name = "cidade_codigo")
	@ForeignKey(name = "FK_US_CIDADE")
	private Cidade cidade;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Parametros.class)
	@JoinColumn(name = "parametro_codigo")
	@ForeignKey(name = "FK_US_PARAMETRO")
	private Parametros parametro;
	
	@NotNull
	private String usuario;
	
	@NotNull
	private String senha;
	
	private Boolean excluido;
	
	public Usuario() {
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Codigo: "+ getCodigo());
		builder.append("\n Idioma: "+ getIdioma());
		builder.append("\n MasterCodigo: "+ getMasterCodigo());
		builder.append("\n Usuario: "+ getUsuario());
		builder.append("\n Administrador: "+ getAdministrador());
		builder.append("\n Excluido: "+ getExcluido());
		return builder.toString();
	}
	
	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public String getPalavraSecreta() {
		return palavraSecreta;
	}

	public void setPalavraSecreta(String palavraSecreta) {
		this.palavraSecreta = palavraSecreta;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public Usuario(String usuario , String email) {
		this.usuario = usuario;
		this.email = email;
	}

	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Usuario)) {
			return false;
		}
		return getCodigo().equals(((Usuario) obj).getCodigo());
	}
	
	public Boolean validaSenha(String password2) {
		return this.senha != null && this.senha.equals(password2);
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(String dtnasc) {
		this.dtNasc = dtnasc;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Integer getMasterCodigo() {
		return masterCodigo;
	}

	public void setMasterCodigo(Integer masteCodigo) {
		this.masterCodigo = masteCodigo;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Parametros getParametro() {
		return parametro;
	}

	public void setParametro(Parametros parametro) {
		this.parametro = parametro;
	}
}
