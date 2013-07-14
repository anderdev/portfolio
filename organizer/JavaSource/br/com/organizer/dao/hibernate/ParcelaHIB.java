package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.ParcelaDAO;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Parcela;


public class ParcelaHIB extends GenericoHIB<Parcela> implements ParcelaDAO {

	public Parcela carregarPorCodigo(Integer codigo) {
		return (Parcela) getHibernateTemplate().load(Parcela.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public Collection<Parcela> listarParcelas(Debito debito) {
		Object[] obj = { debito.getCodigo() };
				
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Parcela ");
		sql.append(" where debitoCodigo = ? ");
		sql.append(" order by codigo ");
		
		Collection<Parcela> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}

	public Integer salvar(Parcela parcela) {
		getHibernateTemplate().saveOrUpdate(parcela);
		return parcela.getCodigo();
	}
}
