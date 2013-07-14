package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Debito;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.DebitoDTO;



public interface DebitoDAO extends GenericoDAO<Debito>{
	
	public Collection<Debito> listaDebitos(Usuario usuario);
	
	public Collection<Debito> listarDebitosPorData(DebitoDTO debitoDTO);
	
	public Collection<Debito> listarDebitosPorDescricao(DebitoDTO debitoDTO);
	
	public Collection<Debito> listarDebitosPorGrupo(DebitoDTO debitoDTO);
	
	public Collection<Debito> listarDebitosPorSuperGrupo(DebitoDTO debitoDTO);
	
	public Collection<Debito> listarDebitosPorCartao(DebitoDTO debitoDTO);

	public Debito carregaDebitoPorCodigo(Integer debitoCod);
	
	public Collection<Debito> listarParcelasDoDebito(DebitoDTO debitoDTO);
}
