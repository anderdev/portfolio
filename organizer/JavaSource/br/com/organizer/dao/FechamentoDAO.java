package br.com.organizer.dao;

import java.io.Serializable;
import java.util.Collection;

import br.com.organizer.model.Credito;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Fechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.FechamentoDTO;

public interface FechamentoDAO extends GenericoDAO<Fechamento>{
	
	public Collection<Fechamento> listaFechamentos(Usuario usuario);
	
	public Collection<Fechamento> listarFechamentosPorData(FechamentoDTO fechamentoDTO);

	public Serializable salvar(Serializable obj);

	public Serializable deletar(Serializable obj);

	public Fechamento carregaFechamentoPorCodigo(Integer fechamentoCod);
	
	public Collection<Credito> fecharCredito(Usuario usuario, String dataInicial, String dataFinal, Boolean contabilizado);
	
	public Collection<Debito> fecharDebito(Usuario usuario, String dataInicial, String dataFinal, Boolean contabilizado);
	
	public Collection<String> datasPagamentosJaContabilizados(Usuario usuario, String dataInicial, String dataFinal);
	
	public Collection<String> datasDebitosJaContabilizados(Usuario usuario, String dataInicial, String dataFinal);
	
	public Collection<String> datasCreditosJaContabilizados(Usuario usuario, String dataInicial, String dataFinal);
}
