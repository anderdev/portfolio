package br.com.organizer.util;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class CreateTables {

	public static void main(String[] args) {

		System.out.println("Criando Tabelas...");
		String sql = "../organizer/sql/scriptSql.sql";
		Configuration conf = new AnnotationConfiguration();
		conf.configure("/hibernate.cfg.xml");		
		SchemaExport se = new SchemaExport(conf);	
		se.setOutputFile(sql);		
		se.setDelimiter(";");
		se.create(true, true);
		System.out.println("Tabelas Criadas...");
	}
}
