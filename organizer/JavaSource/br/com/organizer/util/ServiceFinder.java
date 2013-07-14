package br.com.organizer.util;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServiceFinder {

	public static Object findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();

		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		Object o = appContext.getBean(beanName);

		return o;

	}

}
