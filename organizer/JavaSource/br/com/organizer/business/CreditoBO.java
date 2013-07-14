package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Credito;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.CreditoDTO;



public interface CreditoBO extends GenericoBO<Credito>{
	
	public Collection<Credito> listarCreditos(Usuario usuario) throws OrganizerException ;

	public Collection<Credito> listarCreditosAvancado(CreditoDTO creditoDTO) throws OrganizerException ;
	
	public void salvar(CreditoDTO creditoDTO) throws OrganizerException ;

	public void deletar(CreditoDTO creditoDTO);

	public CreditoDTO carregarCreditoPorCodigo(Integer creditoCod) throws OrganizerException ;
	
}
