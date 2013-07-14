package br.com.organizer.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.CreditoBO;
import br.com.organizer.business.DescricaoBO;
import br.com.organizer.business.MoedaBO;
import br.com.organizer.business.ParametrosBO;
import br.com.organizer.business.UsuarioBO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Credito;
import br.com.organizer.model.Descricao;
import br.com.organizer.model.Moeda;
import br.com.organizer.model.Parametros;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.CreditoDTO;
import br.com.organizer.model.dto.DescricaoDTO;
import br.com.organizer.util.Constantes;
import br.com.organizer.util.ServiceFinder;
import br.com.organizer.util.Utils;

@Component("creditoController")
@Scope("request")
public class CreditoController implements Serializable {

	public Logger logger = Logger.getLogger(CreditoController.class);

	private static final long serialVersionUID = 1L;

	CreditoBO creditoBO = (CreditoBO) ServiceFinder.findBean("creditoBOTarget");

	ParametrosBO parametrosBO = (ParametrosBO) ServiceFinder.findBean("parametrosBOTarget");

	DescricaoBO descricaoBO = (DescricaoBO) ServiceFinder.findBean("descricaoBOTarget");

	MoedaBO moedaBO = (MoedaBO) ServiceFinder.findBean("moedaBOTarget");

	UsuarioBO usuarioBO = (UsuarioBO) ServiceFinder.findBean("usuarioBOTarget");

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

	Usuario usuarioMaster = new Usuario();

	Parametros parametros = new Parametros();

	private List<Credito> listaDeCreditos = new ArrayList<Credito>();

	private List<Descricao> listaDeDescricoes = new ArrayList<Descricao>();

	private List<Descricao> listaDeGrupos = new ArrayList<Descricao>();

	private List<Descricao> listaDeSuperGrupos = new ArrayList<Descricao>();

	private List<Moeda> listaDeMoedas = new ArrayList<Moeda>();

	private CreditoDTO creditoDTO;

	private CreditoDTO creditoDTOConsulta = null;

	private Double totalRegistros = 0.0;

	private Integer tamanhoLista = 0;

	private Boolean novaDescricao = false;

	private Boolean novoSuperGrupo = false;

	private void atualizarDadosdaLista() {
		logger.warn("Acessou AtualizarDadosdaLista");
		totalRegistros = 0.0;
		for (Credito credito : listaDeCreditos) {
			logger.info("TotalRegistros: " + totalRegistros);
			totalRegistros += credito.getValor();
		}
		tamanhoLista = this.listaDeCreditos.size();
		logger.info("TamanhoLista: " + tamanhoLista);
		logger.warn("Finalizou AtualizarDadosdaLista");
	}

	public CreditoController() {
		logger.warn("Acessou construtor CreditoController");
		carregarMasterUsuario(usuarioLogado);
		logger.warn("Finalizou construtor CreditoController");
	}

	private void listarCreditos(CreditoDTO creditoDTOTemp) {
		logger.warn("Acessou ListarCreditos");
		try {
			logger.info("CreditoDTO: " + creditoDTOTemp);
			if (creditoDTOTemp != null) {
				this.listaDeCreditos = (List<Credito>) creditoBO.listarCreditosAvancado(creditoDTOTemp);
			} else {
				this.listaDeCreditos = (List<Credito>) creditoBO.listarCreditos(usuarioMaster);
			}
			atualizarDadosdaLista();
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou ListarCreditos");
	}

	private void carregarDadosTela() {
		logger.warn("Acessou CarregarDadosTela");
		this.creditoDTO = new CreditoDTO();
		this.listarCreditos(null);
		this.listaDeDescricoes = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.CREDITO));
		this.listaDeSuperGrupos = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.SUPER_GRUPO));
		this.listaDeMoedas = (List<Moeda>) moedaBO.listarMoedas();
		if (usuarioMaster.getParametro() == null) {
			this.parametros = new Parametros();
		} else {
			this.parametros = parametrosBO.carregaParametroPorCodigo(usuarioMaster.getParametro().getCodigo());
			this.creditoDTO.setMoeda(parametros.getMoeda().getSigla());
		}
		this.creditoDTO.setData(new Date());
		creditoDTOConsulta = null;
		logger.info("creditoDTO: " + this.creditoDTO);
		logger.info("creditoDTOConsulta: " + this.creditoDTOConsulta);
		logger.warn("Finalizou CarregarDadosTela");
	}

	private void carregarMasterUsuario(Usuario usuario) {
		logger.warn("Acessou CarregarMasterUsuario");
		logger.info("Usuario: " + usuario);
		if (usuario.getMasterCodigo() != null) {
			this.usuarioMaster = usuarioBO.carregarPorCodigo(usuario, false);
			logger.info("UsuarioMaster: " + this.usuarioMaster);
		} else {
			this.usuarioMaster = usuarioBO.carregarPorCodigo(usuario, true);
			logger.info("UsuarioMaster: " + this.usuarioMaster);
		}
		logger.warn("Finalizou CarregarMasterUsuario");
	}

	public void consultar() {
		logger.warn("Acessou Consultar");
		creditoDTO.setUsuario(usuarioMaster);
		creditoDTOConsulta = creditoDTO;
		this.listarCreditos(creditoDTOConsulta);
		logger.warn("Finalizou Consultar");
	}

	public String listarCreditos() {
		logger.warn("Acessou ListarCreditos");
		carregarDadosTela();
		logger.warn("Finalizou ListarCreditos");
		return "listarCreditos";
	}

	public String carregarCredito() {
		logger.warn("Acessou CarregarCredito");
		carregarDadosTela();
		logger.warn("Finalizou CarregarCredito");
		return "manterCredito";
	}

	public String cancelar() {
		logger.warn("Acessou Cancelar");
		this.creditoDTO = new CreditoDTO();
		logger.info("CreditoDTO: " + this.creditoDTO);
		logger.warn("Finalizou Cancelar");
		return "logado";
	}

	public void salvar() {
		logger.warn("Acessou Salvar");
		try {
			creditoDTO.setUsuario(usuarioMaster);
			logger.info("UsuarioMaster: " + usuarioMaster);
			logger.info("CreditoDTO: " + creditoDTO);
			creditoBO.salvar(creditoDTO);
			logger.info("CreditoDTOConsulta: " + creditoDTOConsulta);
			if (creditoDTOConsulta == null) {
				this.carregarDadosTela();
			} else {
				this.listarCreditos(creditoDTOConsulta);
			}
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou Salvar");
	}

	public void excluir() {
		logger.warn("Acessou Excluir");
		Credito cred = new Credito();
		cred.setCodigo(creditoDTO.getCodigo());
		creditoBO.deletar(creditoDTO);
		carregarDadosTela();
		logger.warn("Finalizou Excluir");
	}

	public void excluirDaLista() {
		logger.warn("Acessou ExcluirDaLista");
		logger.info("CreditoDTO: " + creditoDTO);
		creditoBO.deletar(creditoDTO);
		this.listarCreditos(creditoDTOConsulta);
		logger.warn("Finalizou ExcluirDaLista");
	}

	public void editar() {
		logger.warn("Acessou Editar");
		try {
			creditoDTO = creditoBO.carregarCreditoPorCodigo(creditoDTO.getCodigo());
			logger.info("CreditoDTO carregado: " + creditoDTO);
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou Editar");
	}

	public void validarDescricao() {
		logger.warn("Acessou ValidarDescricao");
		logger.info("ListaDeDescricoes.size(): " + listaDeDescricoes.size());
		if (listaDeDescricoes.size() > 0) {
			ArrayList<String> descLista = new ArrayList<String>();
			for (Descricao desc : listaDeDescricoes) {
				descLista.add(desc.getDescricao());
			}
			if (!descLista.contains(this.creditoDTO.getDescricao())) {
				this.novaDescricao = true;
			} else {
				this.novaDescricao = false;
			}
		} else {
			this.novaDescricao = true;
		}
		logger.info("NovaDescricao: " + novaDescricao);
		logger.warn("Finalizou ValidarDescricao");
	}

	public void validarSuperGrupo() {
		logger.warn("Acessou ValidarSuperGrupo");
		logger.info("ListaDeSuperGrupos.size(): " + listaDeSuperGrupos.size());
		if (listaDeSuperGrupos.size() > 0) {
			ArrayList<String> descLista = new ArrayList<String>();
			for (Descricao desc : listaDeSuperGrupos) {
				descLista.add(desc.getDescricao());
			}
			if (!descLista.contains(this.creditoDTO.getSuperGrupo())) {
				this.novoSuperGrupo = true;
			} else {
				this.novoSuperGrupo = false;
			}
		} else {
			this.novoSuperGrupo = true;
		}
		logger.info("NovoSuperGrupo: " + novoSuperGrupo);
		logger.warn("Finalizou ValidarSuperGrupo");
	}

	public void salvarDescricao() {
		logger.warn("Acessou SalvarDescricao");
		DescricaoDTO descricaoDTO = new DescricaoDTO();
		if (this.novaDescricao) {
			descricaoDTO.setDescricao(this.creditoDTO.getDescricao());
			descricaoDTO.setDescricaoTipoConta(Utils.carregarConstante(usuarioMaster, Constantes.CREDITO));
			this.listaDeDescricoes = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.CREDITO));
			this.novaDescricao = false;
		} else {
			descricaoDTO.setDescricao(this.creditoDTO.getSuperGrupo());
			descricaoDTO.setDescricaoTipoConta(Utils.carregarConstante(usuarioMaster, Constantes.SUPER_GRUPO));
			this.listaDeSuperGrupos = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.SUPER_GRUPO));
			this.novoSuperGrupo = false;
		}
		descricaoDTO.setUsuario(usuarioMaster);
		logger.info("DescricaoDTO: " + descricaoDTO);
		descricaoBO.salvar(descricaoDTO);
		logger.warn("Finalizou SalvarDescricao");
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public CreditoDTO getCreditoDTO() {
		return creditoDTO;
	}

	public void setCreditoDTO(CreditoDTO creditoDTO) {
		this.creditoDTO = creditoDTO;
	}

	public List<Credito> getListaDeCreditos() {
		return listaDeCreditos;
	}

	public void setListaDeCreditos(List<Credito> listaDeCreditos) {
		this.listaDeCreditos = listaDeCreditos;
	}

	public List<Descricao> getListaDeDescricoes() {
		return listaDeDescricoes;
	}

	public void setListaDeDescricoes(List<Descricao> listaDeDescricoes) {
		this.listaDeDescricoes = listaDeDescricoes;
	}

	public List<Moeda> getListaDeMoedas() {
		return listaDeMoedas;
	}

	public void setListaDeMoedas(List<Moeda> listaDeMoedas) {
		this.listaDeMoedas = listaDeMoedas;
	}

	public List<Descricao> getListaDeGrupos() {
		return listaDeGrupos;
	}

	public void setListaDeGrupos(List<Descricao> listaDeGrupos) {
		this.listaDeGrupos = listaDeGrupos;
	}

	public List<Descricao> getListaDeSuperGrupos() {
		return listaDeSuperGrupos;
	}

	public void setListaDeSuperGrupos(List<Descricao> listaDeSuperGrupos) {
		this.listaDeSuperGrupos = listaDeSuperGrupos;
	}

	public Double getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Double totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public Integer getTamanhoLista() {
		return tamanhoLista;
	}

	public void setTamanhoLista(Integer tamanhoLista) {
		this.tamanhoLista = tamanhoLista;
	}

	public Boolean getNovaDescricao() {
		return novaDescricao;
	}

	public void setNovaDescricao(Boolean novaDescricao) {
		this.novaDescricao = novaDescricao;
	}

	public Boolean getNovoSuperGrupo() {
		return novoSuperGrupo;
	}

	public void setNovoSuperGrupo(Boolean novoSuperGrupo) {
		this.novoSuperGrupo = novoSuperGrupo;
	}

}
