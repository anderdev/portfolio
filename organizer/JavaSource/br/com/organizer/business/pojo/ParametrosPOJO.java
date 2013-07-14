package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.ParametrosBO;
import br.com.organizer.dao.MoedaDAO;
import br.com.organizer.dao.ParametrosDAO;
import br.com.organizer.dao.TipoFechamentoDAO;
import br.com.organizer.dao.UsuarioDAO;
import br.com.organizer.model.Moeda;
import br.com.organizer.model.Parametros;
import br.com.organizer.model.TipoFechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.ParametrosDTO;

public class ParametrosPOJO extends GenericoPOJO<Parametros> implements ParametrosBO {

	ParametrosDAO parametrosDAO;
	
	UsuarioDAO usuarioDAO;
	
	MoedaDAO moedaDAO;
	
	TipoFechamentoDAO tipoFechamentoDAO;
	
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void setParametrosDAO(ParametrosDAO parametrosDAO) {
		this.parametrosDAO = parametrosDAO;
	}

	public Parametros carregaParametroPorCodigo(Integer codigoParametro) {
		return parametrosDAO.carregaParametroPorCodigo(codigoParametro);
	}

	public void deletar(ParametrosDTO parametrosDTO) {
		Parametros parametros = parametrosDAO.carregaParametroPorCodigo(parametrosDTO.getCodigo());
		parametrosDAO.excluir(parametros);
	}

	public void salvar(ParametrosDTO parametrosDTO){
		Parametros parametros = new Parametros();
		Usuario usuario = parametrosDTO.getUsuario();

		if (parametrosDTO.getCodigo()>0) {
			parametros.setCodigo(parametrosDTO.getCodigo());
		}
		
		parametros.setMoeda(moedaDAO.carregarMoedaPorSigla(parametrosDTO.getDescricaoMoeda()));
		parametros.setTipoFechamento(tipoFechamentoDAO.carregarTipoFechamentoPorTipo(parametrosDTO.getDescricaoTipoFechamento()));
		parametros.setUsuario(parametrosDTO.getUsuario());
		
		parametrosDAO.salvar(parametros);
		
		usuario.setParametro(parametrosDAO.carregaParametroPorCodigo(parametros.getCodigo()));
		usuarioDAO.salvar(usuario);
	}

	public void setMoedaDAO(MoedaDAO moedaDAO) {
		this.moedaDAO = moedaDAO;
	}

	public void setTipoFechamentoDAO(TipoFechamentoDAO tipoFechamentoDAO) {
		this.tipoFechamentoDAO = tipoFechamentoDAO;
	}

	public Collection<Moeda> listarMoedas() {
		return moedaDAO.listarMoedas();
	}

	public Collection<TipoFechamento> listarTiposFechamentoPorIdioma(String idioma) {
		return tipoFechamentoDAO.listarTiposFechamentoPorIdioma(idioma);
	}
	
	public Collection<TipoFechamento> listarTiposFechamento() {
		return tipoFechamentoDAO.listarTiposFechamento();
	}
}
