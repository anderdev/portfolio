package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.DebitoDAO;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.DebitoDTO;
import br.com.organizer.util.Utils;

public class DebitoHIB extends GenericoHIB<Debito> implements DebitoDAO {

	public Debito carregaDebitoPorCodigo(Integer debitoCod) {
		return (Debito) getHibernateTemplate().load(Debito.class, debitoCod);
	}

	@SuppressWarnings("unchecked")
	public Collection<Debito> listaDebitos(Usuario usuario) {
		Object[] obj = { usuario };
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.usuario = ? ");
		sql.append(" order by deb.data desc ");
		
		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public Collection<Debito> listarDebitosPorData(DebitoDTO debitoDTO) {
		
		String dataInicial = Utils.dataToString(debitoDTO.getDataInicial()) ;
		String dataFinal = Utils.dataToString(debitoDTO.getDataFinal());	
		
		Object[] obj = { debitoDTO.getUsuario(), dataInicial, dataFinal };
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.usuario = ? ");
		sql.append(" and deb.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		sql.append(" order by deb.data desc ");
		
		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;		
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Debito> listarDebitosPorDescricao(DebitoDTO debitoDTO) {
		String dataInicial = Utils.dataToString(debitoDTO.getDataInicial()) ;
		String dataFinal = Utils.dataToString(debitoDTO.getDataFinal());
		
		Object[] obj = { debitoDTO.getUsuario() };
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.usuario = ? ");
		sql.append(" and deb.descricao like '%"+debitoDTO.getDescricao()+"%'");
		if(debitoDTO.getUsaData()){
			sql.append(" and deb.data between str_to_date('"+dataInicial+"', '%d/%m/%Y')");
			sql.append(" and str_to_date('"+dataFinal+"', '%d/%m/%Y') ");
		}
		sql.append(" order by deb.data desc ");
		
		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;	
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Debito> listarDebitosPorGrupo(
			DebitoDTO debitoDTO) {
		String dataInicial = Utils.dataToString(debitoDTO.getDataInicial()) ;
		String dataFinal = Utils.dataToString(debitoDTO.getDataFinal());
		
		Object[] obj = { debitoDTO.getUsuario() };
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.usuario = ? ");
		sql.append(" and deb.grupo like '%"+debitoDTO.getGrupo()+"%'");
		if(debitoDTO.getUsaData()){
			sql.append(" and deb.data between str_to_date('"+dataInicial+"', '%d/%m/%Y')");
			sql.append(" and str_to_date('"+dataFinal+"', '%d/%m/%Y') ");
		}
		sql.append(" order by deb.data desc ");
		
		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;	
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Debito> listarDebitosPorSuperGrupo(
			DebitoDTO debitoDTO) {
		String dataInicial = Utils.dataToString(debitoDTO.getDataInicial()) ;
		String dataFinal = Utils.dataToString(debitoDTO.getDataFinal());
		
		Object[] obj = { debitoDTO.getUsuario() };
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.usuario = ? ");
		sql.append(" and deb.superGrupo like '%"+debitoDTO.getSuperGrupo()+"%'");
		if(debitoDTO.getUsaData()){
			sql.append(" and deb.data between str_to_date('"+dataInicial+"', '%d/%m/%Y')");
			sql.append(" and str_to_date('"+dataFinal+"', '%d/%m/%Y') ");
		}
		sql.append(" order by deb.data desc ");
		
		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;	
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Debito> listarDebitosPorCartao(
			DebitoDTO debitoDTO) {
		String dataInicial = Utils.dataToString(debitoDTO.getDataInicial()) ;
		String dataFinal = Utils.dataToString(debitoDTO.getDataFinal());
		
		Object[] obj = { debitoDTO.getUsuario() };
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select deb from Debito deb, Cartao cart ");
		sql.append(" where deb.cartao = cart.codigo ");
		sql.append(" and deb.usuario = ? ");
		if(debitoDTO.getTodosCartoes()){
			sql.append(" and deb.cartao > 0 ");
		}else{
			sql.append(" and cart.descricao = '"+debitoDTO.getCartaoConsulta()+"' ");
		}
		if(debitoDTO.getUsaData()){
			sql.append(" and deb.data between str_to_date('"+dataInicial+"', '%d/%m/%Y')");
			sql.append(" and str_to_date('"+dataFinal+"', '%d/%m/%Y') ");
		}
		sql.append(" order by deb.data desc ");
		
		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;	
	}

	@SuppressWarnings("unchecked")
	public Collection<Debito> listarParcelasDoDebito(DebitoDTO debitoDTO) {
		Object[] obj = { debitoDTO.getParcelaCodigo() ,debitoDTO.getContabilizado()};
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.parcelaCodigo = ? ");
		sql.append(" and deb.contabilizado = ? ");
		sql.append(" order by deb.data desc ");
		
		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}

}
