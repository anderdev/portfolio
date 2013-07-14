package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.TipoFechamentoBO;
import br.com.organizer.dao.TipoFechamentoDAO;
import br.com.organizer.model.TipoFechamento;
import br.com.organizer.model.dto.ParametrosDTO;
import br.com.organizer.util.Constantes;

public class TipoFechamentoPOJO extends GenericoPOJO<TipoFechamento> implements TipoFechamentoBO {

	TipoFechamentoDAO tipoFechamentoDAO;

	public void setTipoFechamentoDAO(TipoFechamentoDAO tipoFechamentoDAO) {
		this.tipoFechamentoDAO = tipoFechamentoDAO;
	}

	public ParametrosDTO carregarTipoFechamentoPorCodigo(
			Integer tipoFechamentoCodigo) {
		ParametrosDTO parametrosDTO = new ParametrosDTO();
		TipoFechamento tpFechamento = tipoFechamentoDAO.carregaTipoFechamentoPorCodigo(tipoFechamentoCodigo);
		
		parametrosDTO.setCodigo(tpFechamento.getCodigo());
		parametrosDTO.setTipoFechamento(tpFechamento.getTipoFechamento());
		
		return parametrosDTO;
	}

	public void deletar(ParametrosDTO parametrosDTO) {
		TipoFechamento tpFechamento = tipoFechamentoDAO.carregaTipoFechamentoPorCodigo(parametrosDTO.getCodigo());
		tipoFechamentoDAO.excluir(tpFechamento);
	}

	public Collection<TipoFechamento> listarTiposFechamento() {
		return tipoFechamentoDAO.listarTiposFechamento();
	}

	public void salvar(ParametrosDTO parametrosDTO){
		TipoFechamento tpFechamento = new TipoFechamento();

		if (parametrosDTO.getCodigo() != null) {
			tpFechamento.setCodigo(parametrosDTO.getCodigo());
		}

		tpFechamento.setTipoFechamento(parametrosDTO.getTipoFechamento());
		tpFechamento.setPeriodo(gravaPeriodo(parametrosDTO));
		tpFechamento.setIdioma(parametrosDTO.getIdioma());

		tipoFechamentoDAO.salvar(tpFechamento);

	}

	private Integer gravaPeriodo(ParametrosDTO parametrosDTO) {
		Integer periodo = 0;
		if (parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.ANUAL)
				|| parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.YEARLY)) {
			periodo = 1;
		}else
		if (parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.MENSAL)
				|| parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.MONTHLY)) {
			periodo = 1;
		}else
		if (parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.SEMANAL)
				|| parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.WEEKLY)) {
			periodo = 7;
		}else
		if (parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.QUINZENAL)
				|| parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.FORTNIGHTLY)) {
			periodo = 15;
		}else
		if (parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.DIARIO)
				|| parametrosDTO.getTipoFechamento().toLowerCase().equals(Constantes.DAYLY)) {
			periodo = 1;
		}
		return periodo;
	}

	public Collection<TipoFechamento> listarTiposFechamentoPorIdioma(
			String idioma) {
		return tipoFechamentoDAO.listarTiposFechamentoPorIdioma(idioma);
	}

	public TipoFechamento carregarTipoFechamentoPorTipo(String tipo) {
		return tipoFechamentoDAO.carregarTipoFechamentoPorTipo(tipo);
	}

}
