package br.com.organizer.business.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import br.com.organizer.business.FechamentoBO;
import br.com.organizer.dao.CreditoDAO;
import br.com.organizer.dao.DebitoDAO;
import br.com.organizer.dao.FechamentoDAO;
import br.com.organizer.dao.MoedaDAO;
import br.com.organizer.dao.ParametrosDAO;
import br.com.organizer.dao.TipoFechamentoDAO;
import br.com.organizer.dao.UsuarioDAO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Credito;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Fechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.FechamentoDTO;
import br.com.organizer.util.Constantes;
import br.com.organizer.util.Criptografa;
import br.com.organizer.util.Utils;

public class FechamentoPOJO extends GenericoPOJO<Fechamento> implements FechamentoBO {

	FechamentoDAO fechamentoDAO;

	TipoFechamentoDAO tipoFechamentoDAO;

	UsuarioDAO usuarioDAO;

	CreditoDAO creditoDAO;

	DebitoDAO debitoDAO;

	ParametrosDAO parametrosDAO;

	MoedaDAO moedaDAO;

	public void setFechamentoDAO(FechamentoDAO fechamentoDAO) {
		this.fechamentoDAO = fechamentoDAO;
	}

	public void setTipoFechamentoDAO(TipoFechamentoDAO tipoFechamentoDAO) {
		this.tipoFechamentoDAO = tipoFechamentoDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void setCreditoDAO(CreditoDAO creditoDAO) {
		this.creditoDAO = creditoDAO;
	}

	public void setDebitoDAO(DebitoDAO debitoDAO) {
		this.debitoDAO = debitoDAO;
	}

	public void setParametrosDAO(ParametrosDAO parametrosDAO) {
		this.parametrosDAO = parametrosDAO;
	}

	public void setMoedaDAO(MoedaDAO moedaDAO) {
		this.moedaDAO = moedaDAO;
	}

	public FechamentoDTO carregaFechamentoPorCodigo(Integer fechamentoCod) {
		FechamentoDTO fechamentoDTO = new FechamentoDTO();

		Fechamento fechamento = fechamentoDAO.carregaFechamentoPorCodigo(fechamentoCod);

		fechamentoDTO.setCodigo(fechamento.getCodigo());
		fechamentoDTO.setData(fechamento.getData());
		fechamentoDTO.setTipo(fechamento.getTipo());
		fechamentoDTO.setMoeda(fechamento.getMoeda());
		fechamentoDTO.setTotalCredito(fechamento.getTotalCredito());
		fechamentoDTO.setTotalDebito(fechamento.getTotalDebito());
		fechamentoDTO.setTotalGeral(fechamento.getTotalGeral());

		return fechamentoDTO;
	}

	public void deletar(FechamentoDTO fechamentoDTO) throws ParseException {

		Credito creditoBean = new Credito();
		Debito debitoBean = new Debito();

		Fechamento fechamento = fechamentoDAO.carregaFechamentoPorCodigo(fechamentoDTO.getCodigo());

		Collection<Credito> collectionCredito = fechamentoDAO.fecharCredito(fechamento.getUsuario(), Utils.dataToStringMySQL(fechamento.getDataInicial()), Utils.dataToStringMySQL(fechamento.getData()), true);

		Collection<Debito> collectionDebito = fechamentoDAO.fecharDebito(fechamento.getUsuario(), Utils.dataToStringMySQL(fechamento.getDataInicial()), Utils.dataToStringMySQL(fechamento.getData()), true);

		for (Credito credito : collectionCredito) {
			creditoBean = creditoDAO.carregaCreditoPorCodigo(credito.getCodigo());
			creditoBean.setContabilizado(false);
			creditoDAO.salvar(creditoBean);
		}

		for (Debito debito : collectionDebito) {
			debitoBean = debitoDAO.carregaDebitoPorCodigo(debito.getCodigo());
			debitoBean.setContabilizado(false);
			debitoDAO.salvar(debitoBean);
		}

		fechamentoDAO.deletar(fechamento);
	}

	public Collection<Fechamento> listarFechamentos(Usuario usuario) {
		return fechamentoDAO.listaFechamentos(usuario);
	}

	public void salvar(FechamentoDTO fechamentoDTO) throws OrganizerException{
		Fechamento fechamento = new Fechamento();

		if (fechamentoDTO.getCodigo() != null) {
			fechamento.setCodigo(fechamentoDTO.getCodigo());
		}

		fechamento.setData(fechamentoDTO.getData());
		fechamento.setMoeda(fechamentoDTO.getMoeda());
		fechamento.setTipo(fechamentoDTO.getTipo());
		fechamento.setTotalCredito(fechamentoDTO.getTotalCredito());
		fechamento.setTotalDebito(fechamentoDTO.getTotalDebito());
		fechamento.setTotalGeral(fechamentoDTO.getTotalGeral());
		fechamento.setDataInicial(fechamentoDTO.getDataInicial());
		fechamento.setDataFinal(fechamentoDTO.getDataFinal());

		fechamento.setUsuario(fechamentoDTO.getUsuario());

		fecharValoresDTO(fechamentoDTO, Utils.dataToString(fechamentoDTO.getDataInicial()), Utils.dataToString(fechamentoDTO.getData()));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechamentoDTO.getData());
		calendar.add(Calendar.DAY_OF_MONTH, +1);

		if (fechamentoDTO.getTotalGeral() > 0) {
			Credito credito = new Credito();
			credito.setData(calendar.getTime());
			if (fechamentoDTO.getUsuario().getIdioma().equals("portugues")) {
				credito.setDescricao(Criptografa.encrypt("Saldo mês anterior"));
				credito.setSuperGrupo(Criptografa.encrypt("Fechamento de Contas"));
			} else {
				credito.setDescricao(Criptografa.encrypt("Previous month balance"));
				credito.setSuperGrupo(Criptografa.encrypt("Closing of accounts"));
			}
			credito.setMoeda(fechamentoDTO.getUsuario().getParametro().getMoeda().getSigla());
			credito.setUsuario(fechamentoDTO.getUsuario());
			credito.setValor(Criptografa.encryptValor(fechamentoDTO.getTotalGeral()));
			credito.setContabilizado(false);
			creditoDAO.salvar(credito);
		} else {
			Debito debito = new Debito();
			debito.setData(calendar.getTime());
			if (fechamentoDTO.getUsuario().getIdioma().equals("portugues")) {
				debito.setDescricao(Criptografa.encrypt("Saldo mês anterior"));
				debito.setGrupo(Criptografa.encrypt("Fechamento de Contas"));
				debito.setSuperGrupo(Criptografa.encrypt("Fechamento de Contas"));
			} else {
				debito.setDescricao(Criptografa.encrypt("Previous month balance"));
				debito.setGrupo(Criptografa.encrypt("Closing of accounts"));
				debito.setSuperGrupo(Criptografa.encrypt("Closing of accounts"));
			}
			debito.setMoeda(fechamentoDTO.getUsuario().getParametro().getMoeda().getSigla());
			debito.setUsuario(fechamentoDTO.getUsuario());
			debito.setValor(Criptografa.encryptValor(-fechamentoDTO.getTotalGeral()));
			debito.setContabilizado(false);
			debitoDAO.salvar(debito);
		}

		fechamentoDAO.salvar(fechamento);
	}

	private HashMap<String, String> carregarDatas(Date data, Integer tipo, Integer dias) {
		HashMap<String, String> map = new HashMap<String, String>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(tipo, dias);
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		String dataInicial = dataFormatada.format(calendar.getTime());
		calendar.setTime(data);
		String dataFinal = dataFormatada.format(calendar.getTime());

		map.put(Constantes.DATA_INICIAL, dataInicial);
		map.put(Constantes.DATA_FINAL, dataFinal);
		return map;
	}

	public FechamentoDTO calcularFechamento(FechamentoDTO fechamentoDTO) throws ParseException {
		Date data = fechamentoDTO.getData();

		HashMap<String, String> retorno = new HashMap<String, String>();

		if (Constantes.MENSAL.equalsIgnoreCase(fechamentoDTO.getTipo().toLowerCase()) || Constantes.MONTHLY.equalsIgnoreCase(fechamentoDTO.getTipo().toLowerCase())) {

			retorno = carregarDatas(data, Calendar.DAY_OF_MONTH, -Utils.getDias(data));

		} else if (Constantes.QUINZENAL.equalsIgnoreCase(fechamentoDTO.getTipo().toLowerCase()) || Constantes.FORTNIGHTLY.equalsIgnoreCase(fechamentoDTO.getTipo().toLowerCase())) {

			retorno = carregarDatas(data, Calendar.DAY_OF_MONTH, -14);

		} else if (Constantes.SEMANAL.equalsIgnoreCase(fechamentoDTO.getTipo().toLowerCase()) || Constantes.WEEKLY.equalsIgnoreCase(fechamentoDTO.getTipo().toLowerCase())) {

			retorno = carregarDatas(data, Calendar.DAY_OF_MONTH, -6);

		} else if (Constantes.DIARIO.equalsIgnoreCase(fechamentoDTO.getTipo().toLowerCase()) || Constantes.DAYLY.equalsIgnoreCase(fechamentoDTO.getTipo().toLowerCase())) {

			retorno = carregarDatas(data, Calendar.DAY_OF_MONTH, 0);
		}
		
		fechamentoDTO = calcularValoresDTO(fechamentoDTO, retorno.get(Constantes.DATA_INICIAL), retorno.get(Constantes.DATA_FINAL));
		
		return fechamentoDTO;
	}

	private FechamentoDTO calcularValoresDTO(FechamentoDTO fechamentoDTO, String dataInicial, String dataFinal) {
		Double somaCredito = 0.0;
		Double somaDebito = 0.0;

		fechamentoDTO.setDatasCreditosJaContabilizados(fechamentoDAO.datasCreditosJaContabilizados(fechamentoDTO.getUsuario(), dataInicial, dataFinal));

		fechamentoDTO.setDatasDebitosJaContabilizados(fechamentoDAO.datasDebitosJaContabilizados(fechamentoDTO.getUsuario(), dataInicial, dataFinal));

		Collection<Credito> collectionCredito = fechamentoDAO.fecharCredito(fechamentoDTO.getUsuario(), dataInicial, dataFinal, false);

		Collection<Debito> collectionDebito = fechamentoDAO.fecharDebito(fechamentoDTO.getUsuario(), dataInicial, dataFinal, false);

		for (Credito credito : collectionCredito) {

			somaCredito += Criptografa.decryptValor(credito.getValor());
		}

		for (Debito debito : collectionDebito) {

			somaDebito += Criptografa.decryptValor(debito.getValor());
		}

		Double totalGeral = somaCredito - somaDebito;
		fechamentoDTO.setTotalCredito(somaCredito);
		fechamentoDTO.setTotalDebito(somaDebito);
		fechamentoDTO.setTotalGeral(totalGeral);
		fechamentoDTO.setDataInicial(Utils.stringToData(dataInicial, true));
		fechamentoDTO.setDataFinal(Utils.stringToData(dataFinal, true));

		return fechamentoDTO;
	}

	private void fecharValoresDTO(FechamentoDTO fechamentoDTO, String dataInicial, String dataFinal) {

		Credito creditoBean = new Credito();

		Debito debitoBean = new Debito();

		fechamentoDTO.setDatasCreditosJaContabilizados(fechamentoDAO.datasCreditosJaContabilizados(fechamentoDTO.getUsuario(), dataInicial, dataFinal));
		
		fechamentoDTO.setDatasDebitosJaContabilizados(fechamentoDAO.datasDebitosJaContabilizados(fechamentoDTO.getUsuario(), dataInicial, dataFinal));

		Collection<Credito> collectionCredito = fechamentoDAO.fecharCredito(fechamentoDTO.getUsuario(), dataInicial, dataFinal, false);

		Collection<Debito> collectionDebito = fechamentoDAO.fecharDebito(fechamentoDTO.getUsuario(), dataInicial, dataFinal, false);

		for (Credito credito : collectionCredito) {
			creditoBean = creditoDAO.carregaCreditoPorCodigo(credito.getCodigo());
			creditoBean.setContabilizado(true);
			creditoDAO.salvar(creditoBean);
		}

		for (Debito debito : collectionDebito) {
			debitoBean = debitoDAO.carregaDebitoPorCodigo(debito.getCodigo());
			debitoBean.setContabilizado(true);
			debitoDAO.salvar(debitoBean);
		}

	}

	public Collection<Fechamento> listarFechamentosPorData(FechamentoDTO fechamentoDTO) {
		return fechamentoDAO.listarFechamentosPorData(fechamentoDTO);
	}

}
