package com.mconnti.moneymanager.business.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.ClosureBO;
import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.ClosureDAO;
import com.mconnti.moneymanager.persistence.CreditDAO;
import com.mconnti.moneymanager.persistence.DebitDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.Crypt;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class ClosureBOImpl extends GenericBOImpl<Closure> implements ClosureBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CreditDAO creditDAO;

	@Autowired
	private DebitDAO debitDAO;
	
	@Autowired
	private ClosureDAO closureDAO;

	private User getUser(Closure closure) {
		try {
			return userDAO.findById(User.class, closure.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn save(Closure closure) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(closure);
		if (user != null) {
			try {

				closure.setUser(user);

				closeValues(closure, Utils.dateToString(closure.getStartDate()), Utils.dateToString(closure.getDate()));

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(closure.getDate());
				calendar.add(Calendar.DAY_OF_MONTH, +1);

				if (closure.getTotalGeneral() > 0) {
					Credit credit = new Credit();
					credit.setDate(calendar.getTime());
					if (closure.getUser().getLanguage().equals("pt_BR")) {
						credit.setDescricao(Criptografa.encrypt("Saldo mês anterior"));
						credit.setSuperGrupo(Criptografa.encrypt("Fechamento de Contas"));
					} else {
						credit.setDescricao(Criptografa.encrypt("Previous month balance"));
						credit.setSuperGrupo(Criptografa.encrypt("Closing of accounts"));
					}
					credit.setCurrency(closure.getUser().getConfig().getCurrency());
					credit.setUser(closure.getUser());
					credit.setAmount(Crypt.encryptValor(closure.getTotalGeneral()));
					credit.setClosed(false);
					creditDAO.save(credit);
				} else {
					Debit debito = new Debit();
					debito.setDate(calendar.getTime());
					if (closure.getUser().getLanguage().equals("pt_BR")) {
						debito.setDescricao(Criptografa.encrypt("Saldo mês anterior"));
						debito.setGrupo(Criptografa.encrypt("Fechamento de Contas"));
						debito.setSuperGrupo(Criptografa.encrypt("Fechamento de Contas"));
					} else {
						debito.setDescricao(Criptografa.encrypt("Previous month balance"));
						debito.setGrupo(Criptografa.encrypt("Closing of accounts"));
						debito.setSuperGrupo(Criptografa.encrypt("Closing of accounts"));
					}
					debito.setCurrency(closure.getUser().getConfig().getCurrency());
					debito.setUser(closure.getUser());
					debito.setAmount(Crypt.encryptValor(closure.getTotalGeneral()));
					debito.setClosed(false);
					debitDAO.save(debito);
				}
				saveGeneric(closure);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setClosure(closure);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && closure.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_closure_saved", closure.getUser().getCity().getState().getCountry().getLocale()));
				libReturn.setClosure(closure);
			} else if (libReturn.getMessage() == null && closure.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_closure_updated", closure.getUser().getCity().getState().getCountry().getLocale()));
				libReturn.setClosure(closure);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_closure_not_found", "en"));
		}

		return libReturn;
	}
	
	private void closeValues(Closure closure, String startDate, String endDate) {

		Credit creditoBean = new Credit();

		Debit debitoBean = new Debit();

		closure.setCreditsAlreadyClosed(closureDAO.datasCreditosJaContabilizados(closure.getUser(), startDate, endDate));
		
		closure.setDebitsAlreadyClosed(closureDAO.datasDebitosJaContabilizados(closure.getUser(), startDate, endDate));

		Collection<Credit> collectionCredit = closureDAO.fecharCredito(closure.getUser(), startDate, endDate, false);

		Collection<Debit> collectionDebit = closureDAO.fecharDebito(closure.getUser(), startDate, endDate, false);

		for (Credit credit : collectionCredit) {
			creditoBean = creditDAO.findById(Credit.class, credit.getId());
			creditoBean.setClosed(true);
			creditDAO.save(creditoBean);
		}

		for (Debit debit : collectionDebit) {
			debitoBean = debitDAO.findById(Debit.class, debit.getId());
			debitoBean.setClosed(true);
			debitDAO.save(debitoBean);
		}

	}

	public List<Closure> list() throws Exception {
		return list(Closure.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Closure closure = null;
		try {
			closure = findById(Closure.class, id);
			if (closure == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_closure_not_found", "en"));
			} else {
				String locale = closure.getUser().getCity().getState().getCountry().getLocale();
				remove(closure);
				libReturn.setMessage(MessageFactory.getMessage("lb_closure_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Closure getById(Long id) {
		try {
			return findById(Closure.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
