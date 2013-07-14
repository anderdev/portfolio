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

import br.com.organizer.business.CartaoBO;
import br.com.organizer.business.DebitoBO;
import br.com.organizer.business.DescricaoBO;
import br.com.organizer.business.MoedaBO;
import br.com.organizer.business.ParametrosBO;
import br.com.organizer.business.ParcelaBO;
import br.com.organizer.business.UsuarioBO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Cartao;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Descricao;
import br.com.organizer.model.Moeda;
import br.com.organizer.model.Parametros;
import br.com.organizer.model.TipoFechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.DebitoDTO;
import br.com.organizer.model.dto.DescricaoDTO;
import br.com.organizer.util.Constantes;
import br.com.organizer.util.ServiceFinder;
import br.com.organizer.util.Utils;

@Component("debitoController")
@Scope("request")
public class DebitoController implements Serializable {

	public Logger logger = Logger.getLogger(DebitoController.class);

	private static final long serialVersionUID = 1L;

	DebitoBO debitoBO = (DebitoBO) ServiceFinder.findBean("debitoBOTarget");

	ParametrosBO parametrosBO = (ParametrosBO) ServiceFinder.findBean("parametrosBOTarget");

	ParcelaBO parcelaBO = (ParcelaBO) ServiceFinder.findBean("parcelaBOTarget");

	DescricaoBO descricaoBO = (DescricaoBO) ServiceFinder.findBean("descricaoBOTarget");

	MoedaBO moedaBO = (MoedaBO) ServiceFinder.findBean("moedaBOTarget");

	UsuarioBO usuarioBO = (UsuarioBO) ServiceFinder.findBean("usuarioBOTarget");

	CartaoBO cartaoBO = (CartaoBO) ServiceFinder.findBean("cartaoBOTarget");

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

	Usuario usuarioMaster = new Usuario();

	Parametros parametros = new Parametros();

	private List<Debito> listaDeDebitos = new ArrayList<Debito>();

	private List<Debito> listaDeParcelasDoDebito = null;

	private List<Descricao> listaDeDescricoes = new ArrayList<Descricao>();

	private List<Descricao> listaDeGrupos = new ArrayList<Descricao>();

	private List<Descricao> listaDeSuperGrupos = new ArrayList<Descricao>();

	private List<Moeda> listaDeMoedas = new ArrayList<Moeda>();

	private List<Cartao> listaDeCartoes = new ArrayList<Cartao>();

	private List<TipoFechamento> listaDeTipoFechamento = new ArrayList<TipoFechamento>();

	private DebitoDTO debitoDTO;

	private DebitoDTO debitoDTOConsulta = null;

	private Double totalRegistros = 0.0;

	private Integer tamanhoLista = 0;

	private Boolean alterarParcelas = false;

	private Boolean novaDescricao = false;

	private Boolean novoGrupo = false;

	private Boolean novoSuperGrupo = false;

	private Boolean excluirTodas = false;

	private void atualizarDadosdaLista() {
		logger.warn("Acessou AtualizarDadosdaLista");
		totalRegistros = 0.0;
		for (Debito debito : listaDeDebitos) {
			logger.info("TotalRegistros: " + totalRegistros);
			totalRegistros += debito.getValor();
		}
		tamanhoLista = this.listaDeDebitos.size();
		logger.info("TamanhoLista: " + tamanhoLista);
		logger.warn("Finalizou AtualizarDadosdaLista");
	}

	public DebitoController() {
		logger.warn("Acessou construtor DebitoController");
		carregarMasterUsuario(usuarioLogado);
		logger.warn("Finalizou construtor DebitoController");
	}

	private void listarDebitos(DebitoDTO debitoDTOTemp) {
		logger.warn("Acessou ListarDebitos");
		try {
			logger.info("DebitoDTO: " + debitoDTOTemp);
			if (debitoDTOTemp != null) {
				this.listaDeDebitos = (List<Debito>) debitoBO.listarDebitosAvancado(debitoDTOTemp);
			} else {
				this.listaDeDebitos = (List<Debito>) debitoBO.listarDebitos(usuarioMaster);
			}
			atualizarDadosdaLista();
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou ListarDebitos");
	}

	private void carregarDadosTela() {
		logger.warn("Acessou CarregarDadosTela");
		this.debitoDTO = new DebitoDTO();
		this.listarDebitos(null);
		this.listaDeDescricoes = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.DEBITO));
		this.listaDeGrupos = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.GRUPO));
		this.listaDeSuperGrupos = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.SUPER_GRUPO));
		this.listaDeMoedas = (List<Moeda>) moedaBO.listarMoedas();
		this.listaDeCartoes = (List<Cartao>) cartaoBO.listarCartoes(usuarioMaster);
		this.listaDeTipoFechamento = (List<TipoFechamento>) parametrosBO.listarTiposFechamentoPorIdioma(usuarioMaster.getIdioma());
		if (usuarioMaster.getParametro() == null) {
			this.parametros = new Parametros();
		} else {
			this.parametros = parametrosBO.carregaParametroPorCodigo(usuarioMaster.getParametro().getCodigo());
			this.debitoDTO.setMoeda(parametros.getMoeda().getSigla());
			this.debitoDTO.setTipo(parametros.getTipoFechamento().getTipoFechamento());
		}
		this.debitoDTO.setData(new Date());
		this.debitoDTO.setParcelas(1);
		logger.info("debitoDTO: " + this.debitoDTO);
		this.debitoDTOConsulta = null;
		logger.info("debitoDTOConsulta: " + this.debitoDTOConsulta);
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
		debitoDTO.setUsuario(usuarioMaster);
		debitoDTOConsulta = debitoDTO;
		this.listarDebitos(debitoDTOConsulta);
		logger.warn("Finalizou Consultar");
	}

	public String listarDebitos() {
		logger.warn("Acessou ListarDebitos");
		carregarDadosTela();
		logger.warn("Finalizou ListarDebitos");
		return "listarDebitos";
	}

	public String carregarDebito() {
		logger.warn("Acessou CarregarDebito");
		carregarDadosTela();
		logger.warn("Finalizou CarregarDebito");
		return "manterDebito";
	}

	public String cancelar() {
		logger.warn("Acessou Cancelar");
		this.debitoDTO = new DebitoDTO();
		logger.info("DebitoDTO: " + this.debitoDTO);
		logger.warn("Finalizou Cancelar");
		return "logado";
	}

	public void salvar() {
		logger.warn("Acessou Salvar");
		try {
			debitoDTO.setUsuario(usuarioMaster);
			logger.info("UsuarioMaster: " + usuarioMaster);
			logger.info("AlterarParcelas: " + alterarParcelas);
			if (alterarParcelas) {
				logger.info("ListaDeParcelasDoDebito: " + this.listaDeParcelasDoDebito.size());
				for (Debito debito : this.listaDeParcelasDoDebito) {
					for (Debito debito2 : this.listaDeParcelasDoDebito) {
						if (Utils.comparaData(debito2.getData(), this.debitoDTO.getData())) {
							this.debitoDTO.setData(debito.getData());
							this.debitoDTO.setDataAtual(debito.getData());
						}
					}
					this.debitoDTO.setCodigo(debito.getCodigo());
					logger.info("DebitoDTO: " + debitoDTO);
					debitoBO.salvar(debitoDTO);
				}
			} else {
				logger.info("DebitoDTO: " + debitoDTO);
				debitoBO.salvar(debitoDTO);
			}
			logger.info("DebitoDTOConsulta: " + debitoDTOConsulta);
			if (debitoDTOConsulta == null) {
				this.carregarDadosTela();
			} else {
				this.listarDebitos(debitoDTOConsulta);
			}
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou Salvar");
	}

	public void excluir() {
		logger.warn("Acessou Excluir");
		debitoBO.deletar(debitoDTO);
		carregarDadosTela();
		logger.warn("Finalizou Excluir");
	}

	public void excluirDaLista() {
		logger.warn("Acessou ExcluirDaLista");
		logger.info("ExcluirTodas: " + excluirTodas);
		if (excluirTodas) {
			logger.info("ListaDeParcelasDoDebito: " + this.listaDeParcelasDoDebito.size());
			for (Debito debito : this.listaDeParcelasDoDebito) {
				this.debitoDTO.setCodigo(debito.getCodigo());
				logger.info("DebitoDTO: " + debitoDTO);
				debitoBO.deletar(debitoDTO);
			}
		} else {
			logger.info("DebitoDTO: " + debitoDTO);
			debitoBO.deletar(debitoDTO);
		}
		this.listarDebitos(debitoDTOConsulta);
		logger.warn("Finalizou ExcluirDaLista");
	}

	public void verificarParcelas() {
		logger.warn("Acessou VerificarParcelas");
		try {
			logger.info("DebitoDTO da tela: " + debitoDTO);
			this.debitoDTO = debitoBO.carregaDebitoPorCodigo(debitoDTO.getCodigo());
			logger.info("DebitoDTO carregado: " + debitoDTO);
			this.listaDeParcelasDoDebito = (List<Debito>) debitoBO.listarParcelasDoDebito(debitoDTO);
			logger.info("ListaDeParcelasDoDebito: " + this.listaDeParcelasDoDebito.size());
			if (this.listaDeParcelasDoDebito.size() == 0) {
				this.listaDeParcelasDoDebito = null;
			}
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou VerificarParcelas");
	}

	public void editar() {
		logger.warn("Acessou Editar");
		try {
			this.debitoDTO = debitoBO.carregaDebitoPorCodigo(debitoDTO.getCodigo());
			logger.info("DebitoDTO carregado: " + debitoDTO);
			if (this.debitoDTO.getCartao() != null) {
				this.debitoDTO.setDescricaoCartao(this.debitoDTO.getCartao().getDescricao());
			}
			this.debitoDTO.setParcelas(1);
			logger.info("DebitoDTO devolvido: " + debitoDTO);
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou Editar");
	}

	public void editarDaLista() {
		logger.warn("Acessou EditarDaLista");
		try {
			logger.info("DebitoDTO da tela: " + debitoDTO);
			this.debitoDTO = debitoBO.carregaDebitoPorCodigo(debitoDTO.getCodigo());
			logger.info("DebitoDTO carregado: " + debitoDTO);
			this.listaDeParcelasDoDebito = (List<Debito>) debitoBO.listarParcelasDoDebito(debitoDTO);
			logger.info("ListaDeParcelasDoDebito: " + this.listaDeParcelasDoDebito.size());
			if (this.listaDeParcelasDoDebito.size() == 0) {
				this.listaDeParcelasDoDebito = null;
			}
			if (debitoDTO.getCartao() != null) {
				this.debitoDTO.setDescricaoCartao(debitoDTO.getCartao().getDescricao());
			}
			this.debitoDTO.setParcelas(1);
			logger.info("DebitoDTO devolvido: " + debitoDTO);
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou EditarDaLista");
	}

	public void validarDescricao() {
		logger.warn("Acessou ValidarDescricao");
		logger.info("ListaDeDescricoes.size(): " + listaDeDescricoes.size());
		if (listaDeDescricoes.size() > 0) {
			ArrayList<String> descLista = new ArrayList<String>();
			for (Descricao desc : listaDeDescricoes) {
				descLista.add(desc.getDescricao());
			}
			if (!descLista.contains(this.debitoDTO.getDescricao())) {
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

	public void validarGrupo() {
		logger.warn("Acessou ValidarGrupo");
		logger.info("ListaDeGrupos.size(): " + listaDeGrupos.size());
		if (listaDeGrupos.size() > 0) {
			ArrayList<String> descLista = new ArrayList<String>();
			for (Descricao desc : listaDeGrupos) {
				descLista.add(desc.getDescricao());
			}
			if (!descLista.contains(this.debitoDTO.getGrupo())) {
				this.novoGrupo = true;
			} else {
				this.novoGrupo = false;
			}
		} else {
			this.novoGrupo = true;
		}
		logger.info("NovoGrupo: " + novoGrupo);
		logger.warn("Finalizou ValidarGrupo");
	}

	public void validarSuperGrupo() {
		logger.warn("Acessou ValidarSuperGrupo");
		logger.info("ListaDeSuperGrupos.size(): " + listaDeSuperGrupos.size());
		if (listaDeSuperGrupos.size() > 0) {
			ArrayList<String> descLista = new ArrayList<String>();
			for (Descricao desc : listaDeSuperGrupos) {
				descLista.add(desc.getDescricao());
			}
			if (!descLista.contains(this.debitoDTO.getSuperGrupo())) {
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
			descricaoDTO.setDescricao(this.debitoDTO.getDescricao());
			descricaoDTO.setDescricaoTipoConta(Utils.carregarConstante(usuarioMaster, Constantes.DEBITO));
			this.listaDeDescricoes = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.DEBITO));
			this.novaDescricao = false;
		} else if (this.novoGrupo) {
			descricaoDTO.setDescricao(this.debitoDTO.getGrupo());
			descricaoDTO.setDescricaoTipoConta(Utils.carregarConstante(usuarioMaster, Constantes.GRUPO));
			this.listaDeGrupos = (List<Descricao>) descricaoBO.listarDescricaoPorUsuarioEConta(usuarioMaster, Utils.carregarConstante(usuarioMaster, Constantes.GRUPO));
			this.novoGrupo = false;
		} else {
			descricaoDTO.setDescricao(this.debitoDTO.getSuperGrupo());
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

	public List<Debito> getListaDeDebitos() {
		return listaDeDebitos;
	}

	public void setListaDeDebitos(List<Debito> listaDeDebitos) {
		this.listaDeDebitos = listaDeDebitos;
	}

	public List<Descricao> getListaDeDescricoes() {
		return listaDeDescricoes;
	}

	public void setListaDeDescricoes(List<Descricao> listaDeDescricoes) {
		this.listaDeDescricoes = listaDeDescricoes;
	}

	public List<Descricao> getListaDeGrupos() {
		return listaDeGrupos;
	}

	public void setListaDeGrupos(List<Descricao> listaDeGrupos) {
		this.listaDeGrupos = listaDeGrupos;
	}

	public List<Moeda> getListaDeMoedas() {
		return listaDeMoedas;
	}

	public void setListaDeMoedas(List<Moeda> listaDeMoedas) {
		this.listaDeMoedas = listaDeMoedas;
	}

	public DebitoDTO getDebitoDTO() {
		return debitoDTO;
	}

	public void setDebitoDTO(DebitoDTO debitoDTO) {
		this.debitoDTO = debitoDTO;
	}

	public List<TipoFechamento> getListaDeTipoFechamento() {
		return listaDeTipoFechamento;
	}

	public void setListaDeTipoFechamento(List<TipoFechamento> listaDeTipoFechamento) {
		this.listaDeTipoFechamento = listaDeTipoFechamento;
	}

	public List<Cartao> getListaDeCartoes() {
		return listaDeCartoes;
	}

	public void setListaDeCartoes(List<Cartao> listaDeCartoes) {
		this.listaDeCartoes = listaDeCartoes;
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

	public List<Debito> getListaDeParcelasDoDebito() {
		return listaDeParcelasDoDebito;
	}

	public void setListaDeParcelasDoDebito(List<Debito> listaDeParcelasDoDebito) {
		this.listaDeParcelasDoDebito = listaDeParcelasDoDebito;
	}

	public Boolean getAlterarParcelas() {
		return alterarParcelas;
	}

	public void setAlterarParcelas(Boolean alterarParcelas) {
		this.alterarParcelas = alterarParcelas;
	}

	public Boolean getNovaDescricao() {
		return novaDescricao;
	}

	public void setNovaDescricao(Boolean novaDescricao) {
		this.novaDescricao = novaDescricao;
	}

	public Boolean getNovoGrupo() {
		return novoGrupo;
	}

	public void setNovoGrupo(Boolean novoGrupo) {
		this.novoGrupo = novoGrupo;
	}

	public Boolean getNovoSuperGrupo() {
		return novoSuperGrupo;
	}

	public void setNovoSuperGrupo(Boolean novoSuperGrupo) {
		this.novoSuperGrupo = novoSuperGrupo;
	}

	public Boolean getExcluirTodas() {
		return excluirTodas;
	}

	public void setExcluirTodas(Boolean excluirTodas) {
		this.excluirTodas = excluirTodas;
	}
}