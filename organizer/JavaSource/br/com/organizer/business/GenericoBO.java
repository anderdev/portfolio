package br.com.organizer.business;

import java.io.Serializable;

public interface GenericoBO<T> {
	
	public void salvar(Serializable obj);
	
	public Serializable excluir(Serializable obj);

}
