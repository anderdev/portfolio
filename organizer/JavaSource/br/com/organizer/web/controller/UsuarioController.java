package br.com.organizer.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.CidadeBO;
import br.com.organizer.business.EstadoBO;
import br.com.organizer.business.PaisBO;
import br.com.organizer.business.UsuarioBO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Cidade;
import br.com.organizer.model.Estado;
import br.com.organizer.model.Pais;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.UsuarioDTO;
import br.com.organizer.util.MessageFactory;
import br.com.organizer.util.ServiceFinder;

@Component("usuarioController")
@Scope("request")
public class UsuarioController implements Serializable {

	public Logger logger = Logger.getLogger(UsuarioController.class);

	private static final long serialVersionUID = 1L;

	UsuarioBO usuarioBO = (UsuarioBO) ServiceFinder.findBean("usuarioBOTarget");

	PaisBO paisBO = (PaisBO) ServiceFinder.findBean("paisBOTarget");

	EstadoBO estadoBO = (EstadoBO) ServiceFinder.findBean("estadoBOTarget");

	CidadeBO cidadeBO = (CidadeBO) ServiceFinder.findBean("cidadeBOTarget");

	private List<UsuarioDTO> listaDeUsuarios = new ArrayList<UsuarioDTO>();

	private Integer tamanhoLista;

	private String origemTela;

	private UsuarioDTO usuario;

	private SelectItem[] paises;

	private SelectItem[] estados;

	private SelectItem[] cidades;

	private Pais pais = new Pais();

	private Estado estado = new Estado();

	private Cidade cidade = new Cidade();
	
	private Boolean novoUsuario;

	// captura a sessão do contexto criado pelo JavaServer Faces
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

	public UsuarioController() {
		logger.warn("Acessou construtor UsuarioController");
		this.usuario = new UsuarioDTO();
		this.paises = getListaPaises(new LocaleController().getLocale());
		this.estados = new SelectItem[1];
		estados[0] = new SelectItem("0", new LocaleController().getLocale().equals("pt_BR") ? "Selecione..." : "Select...");
		this.cidades = new SelectItem[1];
		cidades[0] = new SelectItem("0", new LocaleController().getLocale().equals("pt_BR") ? "Selecione..." : "Select...");
		logger.warn("Finalizou construtor UsuarioController");
	}

	public String novoUsuario() {
		logger.warn("Acessou novoUsuario");
		this.usuario = new UsuarioDTO();
		logger.warn("Finalizou novoUsuario");
		return "novoUsuario";
	}

	public String novoUsuarioFilho() {
		logger.warn("Acessou novoUsuarioFilho");
		this.usuario = new UsuarioDTO();
		this.usuario.setMasterCodigo(usuarioLogado.getCodigo());
		logger.warn("Finalizou novoUsuarioFilho");
		return "novoUsuarioFilho";
	}

	public String cancelarCadastro() {
		logger.warn("Acessou cancelarCadastro");
		this.usuario = new UsuarioDTO();
		logger.warn("Finalizou cancelarCadastro");
		return "index";
	}

	public String cancelarEdicao() {
		logger.warn("Acessou cancelarEdicao");
		this.usuario = new UsuarioDTO();
		logger.warn("Finalizou cancelarEdicao");
		return "logado";
	}

	public String salvar() {
		logger.warn("Acessou salvar");

		String retorno = new String();
		try {
			String language = (String) session.getAttribute("language");
			logger.info("Language: " + language);
			if (language.equals("pt_BR")) {
				usuario.setIdioma("portugues");
			} else {
				usuario.setIdioma("ingles");
			}
			logger.info("Usuario: " + usuario);
			usuarioBO.salvar(usuario);
			if (usuario.getCodigo() != null && usuario.getMasterCodigo() == null) {
				FacesMessage message = new FacesMessage(MessageFactory.getMessage("info_editadoSucesso"));
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, message);
				retorno = "logado";
			} else if (usuario.getMasterCodigo() != null) {
				FacesMessage message = new FacesMessage(MessageFactory.getMessage("info_cadastroSucesso"));
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, message);
				retorno = "logado";
			} else {
				FacesMessage message = new FacesMessage(MessageFactory.getMessage("info_novoCadastroSucesso"));
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, message);
				retorno = "index";
			}
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.info("Retorno: " + retorno);
		logger.warn("Finalizou salvar");
		return retorno;
	}

	public String carregarUsuario() {
		logger.warn("Acessou carregarUsuario");
		Usuario user = new Usuario();
		try {
			this.usuario = usuarioBO.carregarUsuario(usuarioLogado);
			logger.info("Usuario: " + this.usuario);
			user.setCodigo(this.usuario.getCodigo());
			user = usuarioBO.carregarPorCodigo(user, true);
			logger.info("User: " + user);
			this.usuario = usuarioBO.carregarUsuario(user);
			logger.info("Usuario: " + this.usuario);
			this.estados = getEstadosByPais(this.usuario.getPaisCodigo());
			this.cidades = getCidadeByEstado(this.usuario.getEstadoCodigo());
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou carregarUsuario");
		return "carregarUsuario";
	}

	public String listarUsuarios() {
		logger.warn("Acessou listarUsuarios");
		try {
			this.listaDeUsuarios = (List<UsuarioDTO>) usuarioBO.listarUsuarios(usuarioLogado);
			this.tamanhoLista = this.listaDeUsuarios.size();
			this.origemTela = "";
			logger.info("OrigemTela: "+this.origemTela);
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou listarUsuarios");
		return "listaDeUsuarios";
	}

	public String listarUsuariosAdm() {
		logger.warn("Acessou listarUsuariosAdm");
		try {
			this.listaDeUsuarios = (List<UsuarioDTO>) usuarioBO.listarUsuariosAdm(usuarioLogado);
			this.tamanhoLista = this.listaDeUsuarios.size();
			this.origemTela = "ADM";
			logger.info("OrigemTela: "+this.origemTela);
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou listarUsuariosAdm");
		return "listaDeUsuarios";
	}

	public void excluir() {
		logger.warn("Acessou excluir");
		Usuario user = new Usuario();
		user.setCodigo(usuario.getCodigo());
		user = usuarioBO.carregarPorCodigo(user, true);
		user.setExcluido(true);
		logger.info("Usuario: " + user);
		usuarioBO.salvar(user);
		logger.info("OrigemTela: "+this.origemTela);
		if (this.origemTela != null && this.origemTela.equals("ADM")) {
			listarUsuariosAdm();
		} else {
			listarUsuarios();
		}
		logger.warn("Finalizou excluir");
	}

	public SelectItem[] getListaPaises(String idioma) {
		logger.warn("Acessou getListaPaises");
		logger.info("Idioma: "+idioma);
		String locale = idioma.equals("pt_BR") ? "portugues" : "ingles";
		Collection<Pais> listaPaises = paisBO.carregarPaisesPorLocale(locale);
		List<SelectItem> itens = new ArrayList<SelectItem>(listaPaises.size());

		this.paises = new SelectItem[itens.size()];

		itens.add(new SelectItem(0, idioma.equals("pt_BR") ? "Selecione..." : "Select..."));
		for (Pais p : listaPaises) {
			itens.add(new SelectItem(p.getCodigo(), p.getPais()));
		}
		logger.warn("Finalizou getListaPaises");
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public SelectItem[] getEstadosByPais(int codigoPais) {
		logger.warn("Acessou getEstadosByPais");
		logger.info("codigoPais: "+codigoPais);
		Collection<Estado> listaEstados = estadoBO.carregarEstadosPorPais(codigoPais);
		List<SelectItem> itens = new ArrayList<SelectItem>(listaEstados.size());

		itens.add(new SelectItem(0, new LocaleController().getLocale().equals("pt_BR") ? "Selecione..." : "Select..."));
		for (Estado e : listaEstados) {
			itens.add(new SelectItem(e.getCodigo(), e.getEstado()));
		}
		logger.warn("Finalizou getEstadosByPais");
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public String actionCarregarEstados() {
		logger.warn("Acessou actionCarregarEstados");
		this.cidades = new SelectItem[1];
		cidades[0] = new SelectItem("0", new LocaleController().getLocale().equals("pt_BR") ? "Selecione..." : "Select...");

		this.estados = getEstadosByPais(getPais().getCodigo() == null ? this.usuario.getPaisCodigo() : getPais().getCodigo());
		logger.warn("Finalizou actionCarregarEstados");
		return "SUCCESS";
	}

	public SelectItem[] getCidadeByEstado(int codigoEstado) {
		logger.warn("Acessou getCidadeByEstado");
		logger.info("codigoEstado: "+codigoEstado);
		Collection<Cidade> listaCidades = cidadeBO.carregarCidadesPorEstado(codigoEstado);
		List<SelectItem> itens = new ArrayList<SelectItem>(listaCidades.size());

		itens.add(new SelectItem(0, new LocaleController().getLocale().equals("pt_BR") ? "Selecione..." : "Select..."));
		for (Cidade c : listaCidades) {
			itens.add(new SelectItem(c.getCodigo(), c.getCidade()));
		}
		logger.warn("Finalizou getCidadeByEstado");
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public String actionCarregarCidades() {
		logger.warn("Acessou actionCarregarCidades");
		this.cidades = getCidadeByEstado(getEstado().getCodigo() == null ? this.usuario.getEstadoCodigo() : getEstado().getCodigo());
		logger.warn("Finalizou actionCarregarCidades");
		return "SUCCESS";
	}

	public SelectItem[] getEstados() {
		return estados;
	}

	public void setEstados(SelectItem[] estados) {
		this.estados = estados;
	}

	public SelectItem[] getPaises() {
		return paises;
	}

	public void setPaises(SelectItem[] paises) {
		this.paises = paises;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public SelectItem[] getCidades() {
		return cidades;
	}

	public void setCidades(SelectItem[] cidades) {
		this.cidades = cidades;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getOrigemTela() {
		return origemTela;
	}

	public void setOrigemTela(String origemTela) {
		this.origemTela = origemTela;
	}

	public Integer getTamanhoLista() {
		return tamanhoLista;
	}

	public void setTamanhoLista(Integer tamanhoLista) {
		this.tamanhoLista = tamanhoLista;
	}

	public List<UsuarioDTO> getListaDeUsuarios() {
		return listaDeUsuarios;
	}

	public void setListaDeUsuarios(List<UsuarioDTO> listaDeUsuarios) {
		this.listaDeUsuarios = listaDeUsuarios;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public Boolean getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(Boolean novoUsuario) {
		this.novoUsuario = novoUsuario;
	}
}
