package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Credito;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.CreditoDTO;



public interface CreditoDAO extends GenericoDAO<Credito>{
	
	public Collection<Credito> listaCreditos(Usuario usuario);

	public Credito carregaCreditoPorCodigo(Integer creditoCod);
	
	public Collection<Credito> listarCreditosPorData(CreditoDTO creditoDTO);
	
	public Collection<Credito> listarCreditosPorDescricao(CreditoDTO creditoDTO);
	
	public Collection<Credito> listarCreditosPorSuperGrupo(CreditoDTO creditoDTO);
}
