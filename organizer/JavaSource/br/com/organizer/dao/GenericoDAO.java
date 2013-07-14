package br.com.organizer.dao;

import java.io.Serializable;

public interface GenericoDAO <T>{
	
	public Serializable salvar(Serializable obj);
	
	public Serializable excluir(Serializable obj);
	
}
