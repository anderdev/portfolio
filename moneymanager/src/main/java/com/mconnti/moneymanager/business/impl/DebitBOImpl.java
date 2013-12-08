package com.mconnti.moneymanager.business.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.DebitBO;
import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.Parcel;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CreditCardDAO;
import com.mconnti.moneymanager.persistence.CurrencyDAO;
import com.mconnti.moneymanager.persistence.DebitDAO;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
import com.mconnti.moneymanager.persistence.ParcelDAO;
import com.mconnti.moneymanager.persistence.TypeClosureDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.Constants;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class DebitBOImpl extends GenericBOImpl<Debit> implements DebitBO {

	@Autowired
	private DebitDAO debitDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CurrencyDAO currencyDAO;

	@Autowired
	private DescriptionDAO descriptionDAO;

	@Autowired
	private TypeClosureDAO typeClosureDAO;

	@Autowired
	private ParcelDAO parcelDAO;

	@Autowired
	private CreditCardDAO creditCardDAO;

	private User getUser(Debit debit) {
		try {
			return userDAO.findById(User.class, debit.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private CreditCard getCreditCard(Debit debit) {
		try {
			return creditCardDAO.findById(CreditCard.class, debit.getCreditCard().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private TypeClosure getTypeClosure(Long id) {
		try {
			return typeClosureDAO.findById(TypeClosure.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setCreditCardDate(Debit debit) {
		CreditCard creditCard = getCreditCard(debit);
		if (debit.getId() != null) {
			if (debit.getCurrentDate().getTime() != debit.getDate().getTime()) {
				setupDate(debit, creditCard);
			} else if (debit.getCreditCard() == null) {
				setupDate(debit, creditCard);
			}
		} else {
			setupDate(debit, creditCard);
		}
	}

	private void setupDate(Debit debit, CreditCard creditCard) {
		String[] temp = Utils.dateToString(debit.getDate()).split("/");
		Integer dayOfBuy = Integer.parseInt(temp[0]);
		Integer monthOfBuy = Integer.parseInt(temp[1]);
		Integer yearOfBuy = Integer.parseInt(temp[2]);
		if ((dayOfBuy <= creditCard.getPayday()) || (dayOfBuy > creditCard.getPayday() && dayOfBuy < creditCard.getLastDayToBuy())) {
			debit.setDate(Utils.stringToDate(creditCard.getPayday().toString() + "/" + (monthOfBuy + 1) + "/" + yearOfBuy.toString(), false));
		} else {
			debit.setDate(Utils.stringToDate(creditCard.getPayday().toString() + "/" + (monthOfBuy + 2) + "/" + yearOfBuy.toString(), false));
		}
	}

	private void setParcel(Debit debit, Date currentDate, Integer interval, Integer x) {
		Date minhaData = currentDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(minhaData);
		calendar.add(interval, x);
		debit.setDate(calendar.getTime());
	}

	@Override
	@Transactional
	public MessageReturn save(Debit debit) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(debit);
		if (user != null && debit.getCurrency() != null && debit.getSuperGroup() != null) {
			try {
				if (debit.getCreditCard() != null) {
					TypeClosure typeClosure = getTypeClosure(1L);
					debit.setTypeClosure(typeClosure);
					setCreditCardDate(debit);
				}

				if (debit.getNumberParcel() > Constants.SINGLE_PARCEL) {
					Parcel parcel = new Parcel();
					parcel.setParcels(debit.getNumberParcel());
					parcel.setUser(user);
					debit.setParcel(parcelDAO.save(parcel));

					Date currentDate = debit.getDate();

					for (int x = 0; x < debit.getNumberParcel(); x++) {
						if (debit.getTypeClosure().getId().equals(Constants.ANUAL) || debit.getTypeClosure().getId().equals(Constants.YEARLY)) {
							setParcel(debit, currentDate, Calendar.YEAR, x);
						} else if (debit.getTypeClosure().getId().equals(Constants.MENSAL) || debit.getTypeClosure().getId().equals(Constants.MONTHLY)) {
							setParcel(debit, currentDate, Calendar.MONTH, x);
						} else if (debit.getTypeClosure().getId().equals(Constants.QUINZENAL) || debit.getTypeClosure().getId().equals(Constants.FORTNIGHTLY)) {
							setParcel(debit, currentDate, Calendar.DAY_OF_WEEK, x * 15);
						} else if (debit.getTypeClosure().getId().equals(Constants.SEMANAL) || debit.getTypeClosure().getId().equals(Constants.WEEKLY)) {
							setParcel(debit, currentDate, Calendar.WEEK_OF_MONTH, x);
						} else if (debit.getTypeClosure().getId().equals(Constants.DIARIO) || debit.getTypeClosure().getId().equals(Constants.DAYLY)) {
							setParcel(debit, currentDate, Calendar.DAY_OF_WEEK, x);
						}
						saveGeneric(debit);
					}
				} else {
					debit.setDate(Utils.stringToDate(debit.getStrDate(), false));
					saveGeneric(debit);
				}
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setDebit(debit);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && debit.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_saved", user.getCity().getState().getCountry().getLocale()));
				libReturn.setDebit(debit);
			} else if (libReturn.getMessage() == null && debit.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_updated", user.getCity().getState().getCountry().getLocale()));
				libReturn.setDebit(debit);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_debit_not_found", "en"));
		}

		return libReturn;
	}

	public List<Debit> list() throws Exception {
		return list(Debit.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Debit debit = null;
		try {
			debit = findById(Debit.class, id);
			if (debit == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_not_found", "en"));
			} else {
				String locale = debit.getUser().getCity().getState().getCountry().getLocale();
				remove(debit);
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Debit getById(Long id) {
		try {
			return findById(Debit.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
