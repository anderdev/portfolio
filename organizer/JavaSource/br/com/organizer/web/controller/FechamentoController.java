package br.com.organizer.web.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.FechamentoBO;
import br.com.organizer.business.MoedaBO;
import br.com.organizer.business.ParametrosBO;
import br.com.organizer.business.UsuarioBO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Fechamento;
import br.com.organizer.model.Moeda;
import br.com.organizer.model.Parametros;
import br.com.organizer.model.TipoFechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.FechamentoDTO;
import br.com.organizer.util.ServiceFinder;

@Component("fechamentoController")
@Scope("request")
public class FechamentoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Logger logger = Logger.getLogger(FechamentoController.class);

	FechamentoBO fechamentoBO = (FechamentoBO) ServiceFinder.findBean("fechamentoBOTarget");

	UsuarioBO usuarioBO = (UsuarioBO) ServiceFinder.findBean("usuarioBOTarget");

	ParametrosBO parametrosBO = (ParametrosBO) ServiceFinder.findBean("parametrosBOTarget");

	MoedaBO moedaBO = (MoedaBO) ServiceFinder.findBean("moedaBOTarget");

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

	Usuario usuarioMaster = new Usuario();

	Parametros parametros = new Parametros();

	private List<Fechamento> listaDeFechamentos = new ArrayList<Fechamento>();

	private List<TipoFechamento> listaDeTipoFechamento = new ArrayList<TipoFechamento>();

	private List<Moeda> listaDeMoedas = new ArrayList<Moeda>();

	private FechamentoDTO fechamentoDTO;
	
	private FechamentoDTO fechamentoDTOConsulta;

	private Double totalCredito = 0.0;

	private Double totalDebito = 0.0;

	private Double totalGeral = 0.0;

	private Integer tamanhoLista = 0;
	
	private String tela = new String();
	
	private void atualizarDadosdaLista() {
		logger.warn("Acessou AtualizarDadosdaLista");
		this.totalCredito = 0.0;
		this.totalDebito = 0.0;
		this.totalGeral = 0.0;
		for (Fechamento fechamento : listaDeFechamentos) {
			logger.info("Total Credito: " + totalCredito);
			logger.info("Total Debito: " + totalDebito);
			logger.info("Total Geral: " + totalGeral);
			this.totalCredito += fechamento.getTotalCredito();
			this.totalDebito += fechamento.getTotalDebito();
			this.totalGeral += fechamento.getTotalGeral();
		}
		tamanhoLista = this.listaDeFechamentos.size();
		logger.info("TamanhoLista: " + tamanhoLista);
		logger.warn("Finalizou AtualizarDadosdaLista");
	}

	public FechamentoController() {
		logger.warn("Acessou construtor FechamentoController");
		carregarMasterUsuario(usuarioLogado);
		logger.warn("Finalizou construtor FechamentoController");
	}
	
	private void listarFechamentos(FechamentoDTO fechamentoDTOTemp) {
		logger.warn("Acessou ListarDebitos");
		logger.info("FechamentoDTO: " + fechamentoDTOTemp);
		if (fechamentoDTOTemp != null) {
			this.listaDeFechamentos = (List<Fechamento>) fechamentoBO.listarFechamentosPorData(fechamentoDTOTemp);
		} else {
			this.listaDeFechamentos = (List<Fechamento>) fechamentoBO.listarFechamentos(usuarioMaster);
		}
		atualizarDadosdaLista();
		logger.warn("Finalizou ListarDebitos");
	}
	
	private void carregarDadosTela() {
		logger.warn("Acessou CarregarDadosTela");
		this.fechamentoDTO = new FechamentoDTO();
		this.listarFechamentos(null);
		this.listaDeTipoFechamento = (List<TipoFechamento>) parametrosBO.listarTiposFechamento();
		this.listaDeMoedas = (List<Moeda>) moedaBO.listarMoedas();
		this.parametros = parametrosBO.carregaParametroPorCodigo(usuarioMaster.getParametro().getCodigo());
		if (usuarioMaster.getParametro() == null) {
			this.parametros = new Parametros();
		} else {
			this.parametros = parametrosBO.carregaParametroPorCodigo(usuarioMaster.getParametro().getCodigo());
			this.fechamentoDTO.setMoeda(parametros.getMoeda().getSigla());
			this.fechamentoDTO.setTipo(parametros.getTipoFechamento().getTipoFechamento());
		}
		this.fechamentoDTO.setData(new Date());
		this.fechamentoDTO.setTipo(parametros.getTipoFechamento().getTipoFechamento());
		this.fechamentoDTO.setMoeda(parametros.getMoeda().getSigla());
		logger.info("FechamentoDTO: " + this.fechamentoDTO);
		this.fechamentoDTOConsulta = null;
		logger.info("fechamentoDTOConsulta: " + this.fechamentoDTOConsulta);
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
		fechamentoDTO.setUsuario(usuarioMaster);
		fechamentoDTOConsulta = fechamentoDTO;
		this.listarFechamentos(fechamentoDTOConsulta);
		logger.warn("Finalizou Consultar");
	}
	
	public String listarFechamentos() {
		logger.warn("Acessou ListarFechamento");
		carregarDadosTela();
		logger.warn("Finalizou ListarFechamento");
		return "listarFechamentos";
	}
	
	public String manterFechamento(){
		logger.warn("Acessou ManterFechamento");
		carregarDadosTela();
		logger.warn("Finalizou ManterFechamento");
		return "manterFechamento";
	}
	
	public void salvar() {
		logger.warn("Acessou Salvar");
		try {
			
			fechamentoDTO.setUsuario(usuarioMaster);
			logger.info("UsuarioMaster: " + usuarioMaster);
			fechamentoBO.salvar(fechamentoDTO);
			logger.info("FechamentoDTOConsulta: " + fechamentoDTOConsulta);
			if (fechamentoDTOConsulta == null) {
				this.carregarDadosTela();
			} else {
				this.listarFechamentos(fechamentoDTOConsulta);
			}
		} catch (OrganizerException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou Salvar");
	}

	public void calcular() throws ParseException {
		logger.warn("Acessou Calcular");
		fechamentoDTO.setUsuario(usuarioMaster);
		fechamentoDTO = fechamentoBO.calcularFechamento(fechamentoDTO);
		logger.warn("Finalizou Calcular");
	}

	public String cancelar() {
		logger.warn("Acessou Cancelar");
		this.fechamentoDTO = new FechamentoDTO();
		logger.info("FechamentoDTO: " + this.fechamentoDTO);
		logger.warn("Finalizou Cancelar");
		return "logado";
	}

	public void excluir() {
		logger.warn("Acessou Excluir");
		try {
			Fechamento fec = new Fechamento();
			fec.setCodigo(fechamentoDTO.getCodigo());
			fechamentoBO.deletar(fechamentoDTO);
			carregarDadosTela();
		} catch (Exception e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		logger.warn("Finalizou Excluir");
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Fechamento> getListaDeFechamentos() {
		return listaDeFechamentos;
	}

	public void setListaDeFechamentos(List<Fechamento> listaDeFechamentos) {
		this.listaDeFechamentos = listaDeFechamentos;
	}

	public FechamentoDTO getFechamentoDTO() {
		return fechamentoDTO;
	}

	public void setFechamentoDTO(FechamentoDTO fechamentoDTO) {
		this.fechamentoDTO = fechamentoDTO;
	}

	public List<TipoFechamento> getListaDeTipoFechamento() {
		return listaDeTipoFechamento;
	}

	public void setListaDeTipoFechamento(List<TipoFechamento> listaDeTipoFechamento) {
		this.listaDeTipoFechamento = listaDeTipoFechamento;
	}

	public List<Moeda> getListaDeMoedas() {
		return listaDeMoedas;
	}

	public void setListaDeMoedas(List<Moeda> listaDeMoedas) {
		this.listaDeMoedas = listaDeMoedas;
	}

	public Double getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(Double totalCredito) {
		this.totalCredito = totalCredito;
	}

	public Double getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(Double totalDebito) {
		this.totalDebito = totalDebito;
	}

	public Double getTotalGeral() {
		return totalGeral;
	}

	public void setTotalGeral(Double totalGeral) {
		this.totalGeral = totalGeral;
	}

	public Integer getTamanhoLista() {
		return tamanhoLista;
	}

	public void setTamanhoLista(Integer tamanhoLista) {
		this.tamanhoLista = tamanhoLista;
	}

	public FechamentoDTO getFechamentoDTOConsulta() {
		return fechamentoDTOConsulta;
	}

	public void setFechamentoDTOConsulta(FechamentoDTO fechamentoDTOConsulta) {
		this.fechamentoDTOConsulta = fechamentoDTOConsulta;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}
}
