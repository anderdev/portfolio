package br.com.organizer.util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
	private static SessionFactory sessionFactory;

	private static final Logger log = Logger.getLogger(HibernateUtil.class);

	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

	static {
		try {
			sessionFactory = new Configuration().configure(
					"/hibernate.cfg.xml").buildSessionFactory();
			log
					.info("Sessionfactory obtida com sucesso para java:/br/com/treelayer/hibernate/Persistencia");
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static Session currentSession() {
		Session s = session.get();
		// Open a new Session, if this Thread has none yet
		if (s == null) {
			try {
				s = sessionFactory.openSession();
			} catch (HibernateException e) {
				System.out.println("ERRO NA NOVA SESSAO");
			}
			session.set(s);
		}
		return s;
	}

	public static void closeSession() {
		Session s = session.get();
		if (s != null)
			try {
				s.close();
			} catch (HibernateException e) {
				System.out.println("ERRO FECHANDO A SESSAO");
			}
		session.set(null);
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
