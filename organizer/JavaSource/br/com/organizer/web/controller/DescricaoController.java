package br.com.organizer.web.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.ContaBO;
import br.com.organizer.business.DescricaoBO;
import br.com.organizer.business.UsuarioBO;
import br.com.organizer.model.Conta;
import br.com.organizer.model.Descricao;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.DescricaoDTO;
import br.com.organizer.util.ServiceFinder;

@Component("descricaoController")
@Scope("request")
public class DescricaoController {

	DescricaoBO descricaoBO = (DescricaoBO) ServiceFinder.findBean("descricaoBOTarget");
	
	UsuarioBO usuarioBO = (UsuarioBO) ServiceFinder.findBean("usuarioBOTarget");

	ContaBO contaBO = (ContaBO) ServiceFinder.findBean("contaBOTarget");
	
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
	
	Usuario usuarioMaster = new Usuario();

	private List<Descricao> listaDeDescricao = new ArrayList<Descricao>();
	
	private List<Conta> listaDeContas = new ArrayList<Conta>();
	
	private DescricaoDTO descricaoDTO;

	public DescricaoController() throws InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		carregarMasterUsuario(usuarioLogado);
	}
	
	private void carregarDadosTela() throws InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidAlgorithmParameterException{
		this.descricaoDTO = new DescricaoDTO();
		this.listaDeDescricao = (List<Descricao>) descricaoBO.listarDescricao(usuarioMaster);
	}
	
	private void carregarMasterUsuario(Usuario usuario){
		if(usuario.getMasterCodigo()!=null){
			this.usuarioMaster = usuarioBO.carregarPorCodigo(usuario, false);
		}else{
			this.usuarioMaster = usuarioBO.carregarPorCodigo(usuario, true);
		}
	}
	
	public void novaDescricao() throws InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidAlgorithmParameterException{
		String locale = usuarioLogado.getIdioma();
		this.listaDeContas = (List<Conta>) contaBO.listarContas(locale);
		this.descricaoDTO = new DescricaoDTO();
	}

	public void salvar() throws InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		descricaoDTO.setUsuario(usuarioMaster);
		descricaoBO.salvar(descricaoDTO);
		carregarDadosTela();
	}
	
	public void cancelar() {
		descricaoDTO = new DescricaoDTO();
	}

	public String listarDescricoes() throws InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		carregarDadosTela();
		return "listarDescricoes";
	}

	public void excluir() throws InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		descricaoBO.deletar(descricaoDTO);
		carregarDadosTela();
	}
	
	public void editar(){
		descricaoDTO = descricaoBO.carregarPorCodigo(descricaoDTO.getCodigo());
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public DescricaoDTO getDescricaoDTO() {
		return descricaoDTO;
	}

	public void setDescricaoDTO(DescricaoDTO descricaoDTO) {
		this.descricaoDTO = descricaoDTO;
	}
	
	public List<Descricao> getListaDeDescricao() {
		return listaDeDescricao;
	}

	public void setListaDeDescricao(List<Descricao> listaDeDescricao) {
		this.listaDeDescricao = listaDeDescricao;
	}
	
	public void setListaDeContas(List<Conta> listaDeContas) {
		this.listaDeContas = listaDeContas;
	}

	public List<Conta> getListaDeContas() {
		return listaDeContas;
	}

}
