package br.com.organizer.dao.hibernate;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.organizer.dao.CreditoDAO;
import br.com.organizer.model.Credito;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.CreditoDTO;
import br.com.organizer.util.Utils;

@Transactional(propagation = Propagation.REQUIRED)
public class CreditoHIB extends GenericoHIB<Credito> implements CreditoDAO {

	public Credito carregaCreditoPorCodigo(Integer creditoCod) {
		return (Credito) getHibernateTemplate().load(Credito.class, creditoCod);
	}

	@SuppressWarnings("unchecked")
	public Collection<Credito> listaCreditos(Usuario usuario) {

		Object[] obj = { usuario };

		StringBuilder sql = new StringBuilder();
		sql.append(" from Credito cred ");
		sql.append(" where cred.usuario = ? ");
		sql.append(" order by cred.data desc ");

		Collection<Credito> res = getHibernateTemplate().find(sql.toString(), obj);

		return res;
	}

	@SuppressWarnings("unchecked")
	public Collection<Credito> listarCreditosPorData(CreditoDTO creditoDTO) {
		String dataInicial = Utils.dataToString(creditoDTO.getDataInicial());
		String dataFinal = Utils.dataToString(creditoDTO.getDataFinal());

		Object[] obj = { creditoDTO.getUsuario(), dataInicial, dataFinal };

		StringBuilder sql = new StringBuilder();
		sql.append(" from Credito cred ");
		sql.append(" where cred.usuario = ? ");
		sql.append(" and cred.data between str_to_date(?, '%d/%m/%Y')");
		sql.append(" and str_to_date(?, '%d/%m/%Y') ");
		sql.append(" order by cred.data desc ");

		Collection<Credito> res = getHibernateTemplate().find(sql.toString(), obj);

		return res;
	}

	@SuppressWarnings("unchecked")
	public Collection<Credito> listarCreditosPorDescricao(CreditoDTO creditoDTO) {
		String dataInicial = Utils.dataToString(creditoDTO.getDataInicial());
		String dataFinal = Utils.dataToString(creditoDTO.getDataFinal());

		Object[] obj = { creditoDTO.getUsuario() };

		StringBuilder sql = new StringBuilder();
		sql.append(" from Credito cred ");
		sql.append(" where cred.usuario = ? ");
		sql.append(" and cred.descricao like '%" + creditoDTO.getDescricao() + "%'");
		if (creditoDTO.getUsaData()) {
			sql.append(" and cred.data between str_to_date('" + dataInicial + "', '%d/%m/%Y')");
			sql.append(" and str_to_date('" + dataFinal + "', '%d/%m/%Y') ");
		}
		sql.append(" order by cred.data desc ");

		Collection<Credito> res = getHibernateTemplate().find(sql.toString(), obj);

		return res;
	}

	@SuppressWarnings("unchecked")
	public Collection<Credito> listarCreditosPorSuperGrupo(CreditoDTO creditoDTO) {
		String dataInicial = Utils.dataToString(creditoDTO.getDataInicial());
		String dataFinal = Utils.dataToString(creditoDTO.getDataFinal());

		Object[] obj = { creditoDTO.getUsuario() };

		StringBuilder sql = new StringBuilder();
		sql.append(" from Credito cred ");
		sql.append(" where cred.usuario = ? ");
		sql.append(" and cred.superGrupo like '%" + creditoDTO.getSuperGrupo() + "%'");
		if (creditoDTO.getUsaData()) {
			sql.append(" and cred.data between str_to_date('" + dataInicial + "', '%d/%m/%Y')");
			sql.append(" and str_to_date('" + dataFinal + "', '%d/%m/%Y') ");
		}
		sql.append(" order by cred.data desc ");

		Collection<Credito> res = getHibernateTemplate().find(sql.toString(), obj);

		return res;
	}

}
