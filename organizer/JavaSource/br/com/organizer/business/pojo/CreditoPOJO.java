package br.com.organizer.business.pojo;

import java.util.ArrayList;
import java.util.Collection;

import br.com.organizer.business.CreditoBO;
import br.com.organizer.dao.CreditoDAO;
import br.com.organizer.dao.UsuarioDAO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Credito;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.CreditoDTO;
import br.com.organizer.util.Constantes;
import br.com.organizer.util.Criptografa;
import br.com.organizer.util.Utils;

public class CreditoPOJO extends GenericoPOJO<Credito> implements CreditoBO {

	CreditoDAO creditoDAO;

	UsuarioDAO usuarioDAO;

	public void setCreditoDAO(CreditoDAO creditoDAO) {
		this.creditoDAO = creditoDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public CreditoDTO carregarCreditoPorCodigo(Integer creditoCod) throws OrganizerException {
		CreditoDTO creditoDTO = new CreditoDTO();

		Credito credito = creditoDAO.carregaCreditoPorCodigo(creditoCod);

		creditoDTO.setCodigo(credito.getCodigo());
		creditoDTO.setData(credito.getData());
		creditoDTO.setDescricao(Criptografa.decrypt(credito.getDescricao()));
		creditoDTO.setSuperGrupo(Criptografa.decrypt(credito.getSuperGrupo()));
		creditoDTO.setMoeda(credito.getMoeda());
		creditoDTO.setValor(Utils.formataValor(Criptografa.decryptValor(credito.getValor())).toString());
		return creditoDTO;
	}

	public void deletar(CreditoDTO creditoDTO) {
		Credito credito = creditoDAO.carregaCreditoPorCodigo(creditoDTO.getCodigo());
		creditoDAO.excluir(credito);
	}

	public Collection<Credito> listarCreditos(Usuario usuario) throws OrganizerException {
		Collection<Credito> listaDecriptada = new ArrayList<Credito>();
		Collection<Credito> listaEncriptada = creditoDAO.listaCreditos(usuario);

		for (Credito credito : listaEncriptada) {
			listaDecriptada.add(carregarCredito(credito));
		}
		return listaDecriptada;
	}

	public void salvar(CreditoDTO creditoDTO) throws OrganizerException {
		Credito credito = new Credito();

		if (creditoDTO.getCodigo() != null) {
			credito.setCodigo(creditoDTO.getCodigo());
		}

		credito.setData(creditoDTO.getData());
		credito.setDescricao(Criptografa.encrypt(creditoDTO.getDescricao()));
		credito.setSuperGrupo(Criptografa.encrypt(creditoDTO.getSuperGrupo()));
		credito.setMoeda(creditoDTO.getMoeda());
		credito.setValor(Criptografa.encryptValor(Double.parseDouble(creditoDTO.getValor().replace(".", "").replaceAll(",", "."))));
		credito.setContabilizado(false);

		credito.setUsuario(creditoDTO.getUsuario());

		creditoDAO.salvar(credito);

	}

	private Credito carregarCredito(Credito credito) throws OrganizerException {
		Credito cred = new Credito();
		cred.setCodigo(credito.getCodigo());
		cred.setContabilizado(credito.getContabilizado());
		cred.setData(credito.getData());
		cred.setSuperGrupo(Criptografa.decrypt(credito.getSuperGrupo()));
		cred.setDescricao(Criptografa.decrypt(credito.getDescricao()));
		cred.setMoeda(credito.getMoeda());
		cred.setUsuario(credito.getUsuario());
		cred.setValor(Criptografa.decryptValor(credito.getValor()));
		return cred;
	}

	public Collection<Credito> listarCreditosAvancado(CreditoDTO creditoDTO) throws OrganizerException {
		Collection<Credito> listaDecriptada = new ArrayList<Credito>();
		Collection<Credito> listaEncriptada = new ArrayList<Credito>();

		creditoDTO.setDescricao(Criptografa.encrypt(creditoDTO.getDescricaoConsulta()));
		creditoDTO.setSuperGrupo(Criptografa.encrypt(creditoDTO.getSuperGrupoConsulta()));

		if (creditoDTO.getDataInicial() != null && creditoDTO.getDataFinal() != null) {
			creditoDTO.setUsaData(true);
		} else {
			creditoDTO.setUsaData(false);
		}

		if (creditoDTO.getTipoConsulta().equals(Constantes.DATA)) {
			listaEncriptada = creditoDAO.listarCreditosPorData(creditoDTO);
		} else if (creditoDTO.getTipoConsulta().equals(Constantes.DESCRICAO)) {
			listaEncriptada = creditoDAO.listarCreditosPorDescricao(creditoDTO);
		} else if (creditoDTO.getTipoConsulta().equals(Constantes.SUPER_GRUPO)) {
			listaEncriptada = creditoDAO.listarCreditosPorSuperGrupo(creditoDTO);
		}

		for (Credito credito : listaEncriptada) {
			listaDecriptada.add(carregarCredito(credito));
		}

		return listaDecriptada;
	}

}
