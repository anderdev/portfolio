package br.com.organizer.business.pojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import br.com.organizer.business.DebitoBO;
import br.com.organizer.dao.CartaoDAO;
import br.com.organizer.dao.DebitoDAO;
import br.com.organizer.dao.ParcelaDAO;
import br.com.organizer.dao.TipoFechamentoDAO;
import br.com.organizer.dao.UsuarioDAO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Cartao;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Parcela;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.DebitoDTO;
import br.com.organizer.util.Constantes;
import br.com.organizer.util.Criptografa;
import br.com.organizer.util.Utils;

public class DebitoPOJO extends GenericoPOJO<Debito> implements DebitoBO {

	public Logger logger = Logger.getLogger(DebitoPOJO.class);

	DebitoDAO debitoDAO;

	UsuarioDAO usuarioDAO;

	CartaoDAO cartaoDAO;

	TipoFechamentoDAO tipoFechamentoDAO;

	ParcelaDAO parcelaDAO;

	public void setParcelaDAO(ParcelaDAO parcelaDAO) {
		this.parcelaDAO = parcelaDAO;
	}

	public void setTipoFechamentoDAO(TipoFechamentoDAO tipoFechamentoDAO) {
		this.tipoFechamentoDAO = tipoFechamentoDAO;
	}

	public void setCartaoDAO(CartaoDAO cartaoDAO) {
		this.cartaoDAO = cartaoDAO;
	}

	public void setDebitoDAO(DebitoDAO debitoDAO) {
		this.debitoDAO = debitoDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public DebitoDTO carregaDebitoPorCodigo(Integer debitoCod) throws OrganizerException {
		DebitoDTO debitoDTO = new DebitoDTO();

		Debito debito = debitoDAO.carregaDebitoPorCodigo(debitoCod);

		debitoDTO.setCodigo(debito.getCodigo());
		debitoDTO.setData(debito.getData());
		debitoDTO.setDataAtual(debito.getData());
		debitoDTO.setDescricao(Criptografa.decrypt(debito.getDescricao()));
		debitoDTO.setGrupo(Criptografa.decrypt(debito.getGrupo()));
		debitoDTO.setSuperGrupo(Criptografa.decrypt(debito.getSuperGrupo()));
		debitoDTO.setMoeda(debito.getMoeda());
		debitoDTO.setValor(Utils.formataValor(Criptografa.decryptValor(debito.getValor())).toString());
		debitoDTO.setCartao(debito.getCartao());
		debitoDTO.setCartaoAtual(debito.getCartao());
		debitoDTO.setTipo(debito.getTipo());
		debitoDTO.setParcelaCodigo(debito.getParcelaCodigo());
		debitoDTO.setContabilizado(debito.getContabilizado());

		return debitoDTO;
	}

	public void deletar(DebitoDTO debitoDTO) {
		Debito debito = debitoDAO.carregaDebitoPorCodigo(debitoDTO.getCodigo());
		debitoDAO.excluir(debito);
	}

	public Collection<Debito> listarDebitos(Usuario usuario) throws OrganizerException {
		logger.warn("Acessou DebitoPOJO.listarDebitos");
		Collection<Debito> listaDecriptada = new ArrayList<Debito>();
		Collection<Debito> listaEncriptada = debitoDAO.listaDebitos(usuario);

		for (Debito debito : listaEncriptada) {
			logger.info("Debito encriptado: " + debito);
			listaDecriptada.add(carregarDebito(debito));
		}
		logger.warn("Finalizou DebitoPOJO.listarDebitos");
		return listaDecriptada;
	}

	public void salvar(DebitoDTO debitoDTO) throws OrganizerException {
		if (debitoDTO.getDescricaoCartao() != null && !"".equals(debitoDTO.getDescricaoCartao())) {
			debitoDTO.setTipo("Mensal");
			configurarDataCartao(debitoDTO);
		}
		if (debitoDTO.getParcelas() > Constantes.PARCELA_UNICA) {
			Date dataAtual = debitoDTO.getData();

			Parcela parcela = new Parcela();
			parcela.setParcelas(debitoDTO.getParcelas());
			parcela.setUsuario(debitoDTO.getUsuario());
			debitoDTO.setParcelaCodigo(parcelaDAO.salvar(parcela));

			for (int x = 0; x < debitoDTO.getParcelas(); x++) {
				if (debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.ANUAL) || debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.YEARLY)) {
					setParcela(debitoDTO, dataAtual, Calendar.YEAR, x);
				} else if (debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.MENSAL) || debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.MONTHLY)) {
					setParcela(debitoDTO, dataAtual, Calendar.MONTH, x);
				} else if (debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.QUINZENAL) || debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.FORTNIGHTLY)) {
					setParcela(debitoDTO, dataAtual, Calendar.DAY_OF_WEEK, x * 15);
				} else if (debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.SEMANAL) || debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.WEEKLY)) {
					setParcela(debitoDTO, dataAtual, Calendar.WEEK_OF_MONTH, x);
				} else if (debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.DIARIO) || debitoDTO.getTipo().toLowerCase().equalsIgnoreCase(Constantes.DAYLY)) {
					setParcela(debitoDTO, dataAtual, Calendar.DAY_OF_WEEK, x);
				}
			}
		} else {
			salvarDebito(debitoDTO);
		}
	}

	private void salvarDebito(DebitoDTO debitoDTO) throws OrganizerException {
		Debito debito = new Debito();

		if (debitoDTO.getCodigo() != null) {
			debito.setCodigo(debitoDTO.getCodigo());
		}

		if (debitoDTO.getCartao() != null) {
			debito.setCartao(debitoDTO.getCartao());
		}

		debito.setData(debitoDTO.getData());
		debito.setDescricao(Criptografa.encrypt(debitoDTO.getDescricao()));
		debito.setGrupo(Criptografa.encrypt(debitoDTO.getGrupo()));
		debito.setSuperGrupo(Criptografa.encrypt(debitoDTO.getSuperGrupo()));
		debito.setMoeda(debitoDTO.getMoeda());
		debito.setValor(Criptografa.encryptValor(Double.parseDouble(debitoDTO.getValor().replace(".", "").replaceAll(",", "."))));
		debito.setContabilizado(false);
		debito.setTipo(debitoDTO.getTipo());
		debito.setUsuario(debitoDTO.getUsuario());
		debito.setParcelaCodigo(debitoDTO.getParcelaCodigo());

		debitoDAO.salvar(debito);
	}

	private void setParcela(DebitoDTO debitoDTO, Date dataAtual, Integer intervalo, Integer x) throws OrganizerException {
		Date minhaData = dataAtual;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(minhaData);
		calendar.add(intervalo, x);
		debitoDTO.setData(calendar.getTime());
		salvarDebito(debitoDTO);
	}

	private void configurarDataCartao(DebitoDTO debitoDTO) {
		Cartao cartao = cartaoDAO.carregarCartaoPorDescricao(debitoDTO.getDescricaoCartao());
		debitoDTO.setCartao(cartao);
		if (debitoDTO.getCodigo() != null) {
			if (debitoDTO.getDataAtual().getTime() != debitoDTO.getData().getTime()) {
				configurarData(debitoDTO, cartao);
			} else if (debitoDTO.getCartaoAtual() == null) {
				configurarData(debitoDTO, cartao);
			}
		} else {
			configurarData(debitoDTO, cartao);
		}
	}

	private void configurarData(DebitoDTO debitoDTO, Cartao cartao) {
		String[] temp = Utils.split(Utils.dataToString(debitoDTO.getData()), "/");
		Integer diaCompra = Integer.parseInt(temp[0]);
		Integer mesCompra = Integer.parseInt(temp[1]);
		Integer anoCompra = Integer.parseInt(temp[2]);
		if ((diaCompra <= cartao.getDiaVencimento()) || (diaCompra > cartao.getDiaVencimento() && diaCompra < cartao.getDiaCompra())) {
			debitoDTO.setData(Utils.stringToData(cartao.getDiaVencimento().toString() + "/" + (mesCompra + 1) + "/" + anoCompra.toString(), false));
		} else {
			debitoDTO.setData(Utils.stringToData(cartao.getDiaVencimento().toString() + "/" + (mesCompra + 2) + "/" + anoCompra.toString(), false));
		}
	}

	private Debito carregarDebito(Debito debito) throws OrganizerException {
		
		Debito deb = new Debito();
		deb.setCodigo(debito.getCodigo());
		deb.setContabilizado(debito.getContabilizado());
		deb.setData(debito.getData());
		deb.setGrupo(Criptografa.decrypt(debito.getGrupo()));
		deb.setSuperGrupo(Criptografa.decrypt(debito.getSuperGrupo()));
		deb.setDescricao(Criptografa.decrypt(debito.getDescricao()));
		deb.setMoeda(debito.getMoeda());
		deb.setUsuario(debito.getUsuario());
		deb.setCartao(debito.getCartao());
		deb.setParcelaCodigo(debito.getParcelaCodigo());
		deb.setValor(Criptografa.decryptValor(debito.getValor()));
		deb.setContabilizado(debito.getContabilizado());
		logger.info("Debito decriptado: " + deb);
		return deb;
	}

	public Collection<Debito> listarDebitosAvancado(DebitoDTO debitoDTO) throws OrganizerException {
		Collection<Debito> listaDecriptada = new ArrayList<Debito>();
		Collection<Debito> listaEncriptada = new ArrayList<Debito>();

		debitoDTO.setDescricao(Criptografa.encrypt(debitoDTO.getDescricaoConsulta()));
		debitoDTO.setGrupo(Criptografa.encrypt(debitoDTO.getGrupoConsulta()));
		debitoDTO.setSuperGrupo(Criptografa.encrypt(debitoDTO.getSuperGrupoConsulta()));

		if (debitoDTO.getDataInicial() != null && debitoDTO.getDataFinal() != null) {
			debitoDTO.setUsaData(true);
		} else {
			debitoDTO.setUsaData(false);
		}

		if (debitoDTO.getTipoConsulta().equals(Constantes.DATA)) {
			listaEncriptada = debitoDAO.listarDebitosPorData(debitoDTO);
		} else if (debitoDTO.getTipoConsulta().equals(Constantes.DESCRICAO)) {
			listaEncriptada = debitoDAO.listarDebitosPorDescricao(debitoDTO);
		} else if (debitoDTO.getTipoConsulta().equals(Constantes.GRUPO)) {
			listaEncriptada = debitoDAO.listarDebitosPorGrupo(debitoDTO);
		} else if (debitoDTO.getTipoConsulta().equals(Constantes.SUPER_GRUPO)) {
			listaEncriptada = debitoDAO.listarDebitosPorSuperGrupo(debitoDTO);
		} else if (debitoDTO.getTipoConsulta().equals(Constantes.CARTAO)) {
			listaEncriptada = debitoDAO.listarDebitosPorCartao(debitoDTO);
		}

		for (Debito debito : listaEncriptada) {
			listaDecriptada.add(carregarDebito(debito));
		}

		return listaDecriptada;
	}

	public Collection<Debito> listarParcelasDoDebito(DebitoDTO debitoDTO) {
		return debitoDAO.listarParcelasDoDebito(debitoDTO);
	}

}