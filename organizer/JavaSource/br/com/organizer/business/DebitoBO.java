package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.DebitoDTO;

public interface DebitoBO extends GenericoBO<Debito>{
	
	public Collection<Debito> listarDebitos(Usuario usuario) throws OrganizerException ;
	
	public Collection<Debito> listarDebitosAvancado(DebitoDTO debitoDTO) throws OrganizerException ;
	
	public Collection<Debito> listarParcelasDoDebito(DebitoDTO debitoDTO) throws OrganizerException ;
	
	public void salvar(DebitoDTO debitoDTO) throws OrganizerException ;

	public void deletar(DebitoDTO debitoDTO);

	public DebitoDTO carregaDebitoPorCodigo(Integer debitoCod) throws OrganizerException ;
}
