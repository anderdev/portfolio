package br.com.organizer.dao.hibernate;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.organizer.dao.FechamentoDAO;
import br.com.organizer.model.Credito;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Fechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.FechamentoDTO;
import br.com.organizer.util.Utils;

@Transactional(propagation = Propagation.REQUIRED) 
public class FechamentoHIB extends GenericoHIB<Fechamento> implements FechamentoDAO {

	public Fechamento carregaFechamentoPorCodigo(Integer fechamentoCod) {
		return (Fechamento) getHibernateTemplate().load(Fechamento.class, fechamentoCod);
	}

	public Serializable deletar(Serializable obj) {
		getHibernateTemplate().delete(obj);
		return obj;
	}

	@SuppressWarnings("unchecked")
	public Collection<Fechamento> listaFechamentos(Usuario usuario) {
		Object[] obj = { usuario };
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Fechamento fec ");
		sql.append(" where fec.usuario = ? ");
		sql.append(" order by fec.data desc ");
		
		Collection<Fechamento> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}

	public Serializable salvar(Serializable obj) {
		getHibernateTemplate().saveOrUpdate(obj);
		return obj;
	}

	@SuppressWarnings("unchecked")
	public Collection<Credito> fecharCredito(Usuario usuario,String dataInicial, String dataFinal, Boolean contabilizado) {
		Object[] obj = {usuario,contabilizado,dataInicial,dataFinal};
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Credito cred ");
		sql.append(" where cred.usuario = ? ");
		sql.append(" and cred.contabilizado = ? ");
		sql.append(" and cred.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		
		Collection<Credito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Debito> fecharDebito(Usuario usuario,String dataInicial, String dataFinal, Boolean contabilizado) {
		Object[] obj = {usuario,contabilizado,dataInicial,dataFinal};
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.usuario = ? ");
		sql.append(" and deb.contabilizado = ? ");
		sql.append(" and deb.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		
		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> datasPagamentosJaContabilizados(Usuario usuario,String dataInicial, String dataFinal) {
		Object[] obj = {usuario,Boolean.TRUE,dataInicial,dataFinal};
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Pagamento pag ");
		sql.append(" where pag.usuario = ? ");
		sql.append(" and pag.contabilizado = ? ");
		sql.append(" and pag.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		sql.append(" order by pag.data ");
		
		Collection<String> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> datasDebitosJaContabilizados(Usuario usuario,String dataInicial, String dataFinal) {
		Object[] obj = {usuario,Boolean.TRUE,dataInicial,dataFinal};
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.usuario = ? ");
		sql.append(" and deb.contabilizado = ? ");
		sql.append(" and deb.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		sql.append(" order by deb.data ");
		
		Collection<String> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> datasCreditosJaContabilizados(Usuario usuario,String dataInicial, String dataFinal) {
		Object[] obj = {usuario,Boolean.TRUE,dataInicial,dataFinal};
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Credito cred ");
		sql.append(" where cred.usuario = ? ");
		sql.append(" and cred.contabilizado = ? ");
		sql.append(" and cred.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		sql.append(" order by cred.data ");
		
		Collection<String> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Fechamento> listarFechamentosPorData(FechamentoDTO fechamentoDTO) {
		String dataInicial = Utils.dataToString(fechamentoDTO.getDataInicial()) ;
		String dataFinal = Utils.dataToString(fechamentoDTO.getDataFinal());	
		
		Object[] obj = { fechamentoDTO.getUsuario(), dataInicial, dataFinal };
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Fechamento fec ");
		sql.append(" where fec.usuario = ? ");
		sql.append(" and fec.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		sql.append(" order by fec.data desc ");
		
		Collection<Fechamento> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;	
	}
	
}
