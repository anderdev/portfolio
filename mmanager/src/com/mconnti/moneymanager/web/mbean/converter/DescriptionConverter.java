package com.mconnti.moneymanager.web.mbean.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.mconnti.moneymanager.entity.Description;

@FacesConverter("descriptionConverter")
public class DescriptionConverter implements Converter {

	public static List<Description> descriptionList;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		if (submittedValue.trim().equals("")) {
			return null;
		} else {
			try {
				for (Description p : descriptionList) {
					if (p.getDescription().equals(submittedValue)) {
						return p;
					}
				}

			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
			}
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Description) value).getId());  
        }  
    } 

	public static List<Description> getDescriptionList() {
		return descriptionList;
	}

	public static void setDescriptionList(List<Description> descriptionList) {
		DescriptionConverter.descriptionList = descriptionList;
	}

}
