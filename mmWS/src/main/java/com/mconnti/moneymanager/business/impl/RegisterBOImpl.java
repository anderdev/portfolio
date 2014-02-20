package com.mconnti.moneymanager.business.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.RegisterBO;
import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.Parcel;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CreditCardDAO;
import com.mconnti.moneymanager.persistence.CurrencyDAO;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
import com.mconnti.moneymanager.persistence.ParcelDAO;
import com.mconnti.moneymanager.persistence.RegisterDAO;
import com.mconnti.moneymanager.persistence.TypeClosureDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.Constants;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class RegisterBOImpl extends GenericBOImpl<Register> implements RegisterBO {

	private static final Long MONTHLY = 1L;
	
	@Autowired
	private RegisterDAO registerDAO;

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

	private User getUser(Register debit) {
		try {
			return userDAO.findById(User.class, debit.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private CreditCard getCreditCard(Register debit) {
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

	private void setCreditCardDate(Register debit) {
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

	private void setupDate(Register debit, CreditCard creditCard) {
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

	private void setParcel(Register debit, Date currentDate, Integer interval, Integer x) {
		Date minhaData = currentDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(minhaData);
		calendar.add(interval, x);
		debit.setDate(calendar.getTime());
	}

	@Override
	@Transactional
	public MessageReturn save(Register register) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(register);
		if (user != null && register.getCurrency() != null && register.getSuperGroup() != null) {
			try {
				if (register.getCreditCard() != null) {
					TypeClosure typeClosure = getTypeClosure(MONTHLY); //Monthly
					register.setTypeClosure(typeClosure);
					setCreditCardDate(register);
				}

				if (register.getNumberParcel() > Constants.SINGLE_PARCEL) {
					Parcel parcel = new Parcel();
					parcel.setParcels(register.getNumberParcel());
					parcel.setUser(user);
					register.setParcel(parcelDAO.save(parcel));

					Date currentDate = register.getDate();

					for (int x = 0; x < register.getNumberParcel(); x++) {
						if (register.getTypeClosure().getId().equals(Constants.ANUAL) || register.getTypeClosure().getId().equals(Constants.YEARLY)) {
							setParcel(register, currentDate, Calendar.YEAR, x);
						} else if (register.getTypeClosure().getId().equals(Constants.MENSAL) || register.getTypeClosure().getId().equals(Constants.MONTHLY)) {
							setParcel(register, currentDate, Calendar.MONTH, x);
						} else if (register.getTypeClosure().getId().equals(Constants.QUINZENAL) || register.getTypeClosure().getId().equals(Constants.FORTNIGHTLY)) {
							setParcel(register, currentDate, Calendar.DAY_OF_WEEK, x * 15);
						} else if (register.getTypeClosure().getId().equals(Constants.SEMANAL) || register.getTypeClosure().getId().equals(Constants.WEEKLY)) {
							setParcel(register, currentDate, Calendar.WEEK_OF_MONTH, x);
						} else if (register.getTypeClosure().getId().equals(Constants.DIARIO) || register.getTypeClosure().getId().equals(Constants.DAYLY)) {
							setParcel(register, currentDate, Calendar.DAY_OF_WEEK, x);
						}
						register.setCurrentDate(new Date());
						saveGeneric(register);
					}
				} else {
					register.setCurrentDate(new Date());
					saveGeneric(register);
				}
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setRegister(register);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && register.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_saved", user.getCity().getState().getCountry().getLocale()));
				libReturn.setRegister(register);
			} else if (libReturn.getMessage() == null && register.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_updated", user.getCity().getState().getCountry().getLocale()));
				libReturn.setRegister(register);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_debit_not_found", "en"));
		}

		return libReturn;
	}

	public List<Register> list() throws Exception {
		return list(Register.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Register debit = null;
		try {
			debit = findById(Register.class, id);
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
	public Register getById(Long id) {
		try {
			return findById(Register.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
