package br.com.organizer.business.pojo;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.lob.ClobImpl;

import br.com.organizer.business.PropagandaBO;
import br.com.organizer.dao.PropagandaDAO;
import br.com.organizer.model.Propaganda;
import br.com.organizer.model.dto.PropagandaDTO;
import br.com.organizer.util.Utils;

public class PropagandaPOJO extends GenericoPOJO<Propaganda> implements PropagandaBO {
	
	PropagandaDAO propagandaDAO;

	public void setPropagandaDAO(PropagandaDAO propagandaDAO) {
		this.propagandaDAO = propagandaDAO;
	}

	public PropagandaDTO carregarPorCodigo(Integer codigo) {
		PropagandaDTO propagandaDTO = new PropagandaDTO();
		Propaganda propaganda = new Propaganda();
		
		propaganda = propagandaDAO.carregarPorCodigo(codigo);
		
		propagandaDTO.setCodigo(propaganda.getCodigo());
		propagandaDTO.setDescricao(propaganda.getDescricao());
		propagandaDTO.setTexto(Utils.convertClobToString(propaganda.getTexto()));
		propagandaDTO.setIdioma(propaganda.getIdioma());
		propagandaDTO.setIdioma_usuario(propaganda.getIdioma_usuario());
		propagandaDTO.setAtiva(propaganda.getAtiva()?propaganda.getIdioma_usuario().equals("portugues")?"Sim":"Yes":propaganda.getIdioma().equals("portugues")?"Não":"No");
		propagandaDTO.setTipo(propaganda.getTipo());
		return propagandaDTO;
	}

	public Collection<PropagandaDTO> listar() {
		PropagandaDTO propagandaDTO = new PropagandaDTO();
		Collection<Propaganda> listaDePropaganda = propagandaDAO.listar();
		Collection<PropagandaDTO> lista = new ArrayList<PropagandaDTO>();
		
		for (Propaganda propaganda : listaDePropaganda) {
			propagandaDTO.setCodigo(propaganda.getCodigo());
			propagandaDTO.setDescricao(propaganda.getDescricao());
			propagandaDTO.setTexto(propaganda.getTexto().toString());
			
			String idioma = new String();
			
			if(propaganda.getIdioma().equals("pt_BR") && propaganda.getIdioma_usuario().equals("portugues")){
				idioma = "Português";
			}else if(propaganda.getIdioma().equals("pt_BR") && propaganda.getIdioma_usuario().equals("ingles")){
				idioma = "Portuguese";
			}else if(propaganda.getIdioma().equals("en") && propaganda.getIdioma_usuario().equals("portugues")){
				idioma = "Inglês";
			}else{
				idioma = "English";
			}
			
			propagandaDTO.setIdioma_usuario(propaganda.getIdioma_usuario());
			propagandaDTO.setIdioma(idioma);
			propagandaDTO.setAtiva(propaganda.getAtiva()?propaganda.getIdioma_usuario().equals("portugues")?"Sim":"Yes":propaganda.getIdioma_usuario().equals("portugues")?"Não":"No");
			propagandaDTO.setTipo(propaganda.getTipo());
			lista.add(propagandaDTO);
		}
		
		return lista;
	}

	public void excluir(PropagandaDTO propagandaDTO) {
		Propaganda prop = new Propaganda();
		
		prop = propagandaDAO.carregarPorCodigo(propagandaDTO.getCodigo());
		propagandaDAO.excluir(prop);
	}

	public void salvar(PropagandaDTO propagandaDTO) {
		Propaganda prop = new Propaganda();
		prop.setCodigo(propagandaDTO.getCodigo());
		prop.setDescricao(propagandaDTO.getDescricao());
		prop.setTexto(new ClobImpl(propagandaDTO.getTexto()));
		prop.setIdioma_usuario(propagandaDTO.getIdioma_usuario());
		prop.setIdioma(propagandaDTO.getIdioma().equals("Português")?"pt_BR":"en");
		
		Boolean ativa;
		
		if(propagandaDTO.getAtiva().equals("Sim") || propagandaDTO.getAtiva().equals("Yes")){
			ativa = true;
		}else{
			ativa = false;
		}
			
		prop.setAtiva(ativa);
		prop.setTipo(propagandaDTO.getTipo());
		propagandaDAO.salvar(prop);
	}
}
