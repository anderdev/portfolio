package br.com.organizer.business;

import java.text.ParseException;
import java.util.Collection;

import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Fechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.FechamentoDTO;

public interface FechamentoBO extends GenericoBO<Fechamento>{
	
	public void salvar(FechamentoDTO fechamentoDTO) throws OrganizerException;

	public void deletar(FechamentoDTO fechamentoDTO) throws ParseException ;

	public FechamentoDTO carregaFechamentoPorCodigo(Integer fechamentoCod);
	
	public FechamentoDTO calcularFechamento(FechamentoDTO fechamentoDTO) throws ParseException ;

	public Collection<Fechamento> listarFechamentos(Usuario usuario);
	
	public Collection<Fechamento> listarFechamentosPorData(FechamentoDTO fechamentoDTO);
}
