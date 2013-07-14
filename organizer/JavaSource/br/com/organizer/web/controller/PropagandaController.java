package br.com.organizer.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.PropagandaBO;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.PropagandaDTO;
import br.com.organizer.util.ServiceFinder;

@Component("propagandaController")
@Scope("request")
public class PropagandaController implements Serializable{

	private static final long serialVersionUID = 1L;

	PropagandaBO propagandaBO = (PropagandaBO) ServiceFinder.findBean("propagandaBOTarget");

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	private List<PropagandaDTO> listaDePropagandas = new ArrayList<PropagandaDTO>();

	private Integer tamanhoLista;

	private PropagandaDTO propagandaDTO;

	private SelectItem[] tipos;
	
	private SelectItem[] ativo;
	
	private SelectItem[] idioma;
	
	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

	public PropagandaController() {
		carregarDadosTela();
	}
	
	public void carregarDadosTela(){
		this.propagandaDTO = new PropagandaDTO();
		this.tipos = new SelectItem[5];
		tipos[0] = new SelectItem("-1", new LocaleController().getLocale().equals("pt_BR") ? "Selecione..." : "Select...");
		tipos[1] = new SelectItem(new LocaleController().getLocale().equals("pt_BR") ? "Atualização" : "Update");
		tipos[2] = new SelectItem(new LocaleController().getLocale().equals("pt_BR") ? "Erro" : "Error");
		tipos[3] = new SelectItem(new LocaleController().getLocale().equals("pt_BR") ? "Informação" : "Information");
		tipos[4] = new SelectItem(new LocaleController().getLocale().equals("pt_BR") ? "Propaganda" : "Advertise");
		
		this.ativo = new SelectItem[3];
		ativo[0] = new SelectItem("-1", new LocaleController().getLocale().equals("pt_BR") ? "Selecione..." : "Select...");
		ativo[1] = new SelectItem(new LocaleController().getLocale().equals("pt_BR") ? "Não" : "No");
		ativo[2] = new SelectItem(new LocaleController().getLocale().equals("pt_BR") ? "Sim" : "Yes");
		
		this.idioma = new SelectItem[3];
		idioma[0] = new SelectItem("-1", new LocaleController().getLocale().equals("pt_BR") ? "Selecione..." : "Select...");
		idioma[1] = new SelectItem(new LocaleController().getLocale().equals("pt_BR") ? "Português" : "Portuguese");
		idioma[2] = new SelectItem(new LocaleController().getLocale().equals("pt_BR") ? "Inglês" : "English");
		
		this.listaDePropagandas = (List<PropagandaDTO>) propagandaBO.listar();
	}

	public void salvar() {
		propagandaDTO.setIdioma_usuario(usuarioLogado.getIdioma());
		propagandaBO.salvar(propagandaDTO);
		carregarDadosTela();
	}

	public void cancelar() {
		carregarDadosTela();
	}

	public String listarPropagandas() {
		carregarDadosTela();
		return "listarPropagandas";
	}

	public void excluir() {
		propagandaBO.excluir(propagandaDTO);
		carregarDadosTela();
	}

	public void editar() {
		propagandaDTO = propagandaBO.carregarPorCodigo(propagandaDTO.getCodigo());
	}

	public void novo() {
		propagandaDTO = new PropagandaDTO();
	}

	public Integer getTamanhoLista() {
		return tamanhoLista;
	}

	public void setTamanhoLista(Integer tamanhoLista) {
		this.tamanhoLista = tamanhoLista;
	}

	public List<PropagandaDTO> getListaDePropagandas() {
		return listaDePropagandas;
	}

	public void setListaDePropagandas(List<PropagandaDTO> listaDePropagandas) {
		this.listaDePropagandas = listaDePropagandas;
	}

	public SelectItem[] getTipos() {
		return tipos;
	}

	public void setTipos(SelectItem[] tipos) {
		this.tipos = tipos;
	}

	public SelectItem[] getAtivo() {
		return ativo;
	}

	public void setAtivo(SelectItem[] ativo) {
		this.ativo = ativo;
	}

	public PropagandaDTO getPropagandaDTO() {
		return propagandaDTO;
	}

	public void setPropagandaDTO(PropagandaDTO propagandaDTO) {
		this.propagandaDTO = propagandaDTO;
	}

	public SelectItem[] getIdioma() {
		return idioma;
	}

	public void setIdioma(SelectItem[] idioma) {
		this.idioma = idioma;
	}
}
