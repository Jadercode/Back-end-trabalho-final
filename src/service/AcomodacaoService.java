package service;

import dao.AcomodacaoDAO;
import enumeration.Funcionalidade;
import exception.AcomodacaoException;
import model.Acomodacao;
import model.Funcionario;
import util.Util;

import java.time.LocalDate;
import java.util.ArrayList;

public class AcomodacaoService {

    // Atributos
    private AcomodacaoDAO acomodacaoDAO;

    // Construtor
    public AcomodacaoService(AcomodacaoDAO acomodacaoDAO) {
        this.acomodacaoDAO = acomodacaoDAO;
    }

    // Métodos públicos

    // Método que interage com banco de dados para listar todos os elementos de acomodacao
    public String listar() throws AcomodacaoException {
        ArrayList<Acomodacao> acomodacaos = acomodacaoDAO.selecionar();
        String lista = "";
        if (acomodacaos.size() > 0) {
            for (Acomodacao acomodacao : acomodacaos) {
                lista += acomodacao + "\n";
            }
        } else {
            lista = "Nenhuma acomodacao encontrada.";
        }
        return lista;
    }

    // Método que valida todas as informações recebidas e interage com banco de dados para cadastrar um elemento em acomodacao
    public String cadastrar(
            String nome,
            Double valorDiaria,
            Integer limiteHospedes,
            String descricao,
            Long idFuncionarioResponsavel
    ) throws AcomodacaoException {
        String mensagemErro = validarCampos(Funcionalidade.CADASTRAR, null, nome, valorDiaria, limiteHospedes, idFuncionarioResponsavel);
        if(!mensagemErro.equals("")) throw new AcomodacaoException(mensagemErro);

        Acomodacao acomodacao = new Acomodacao(
                nome,
                valorDiaria,
                limiteHospedes,
                descricao,
                acomodacaoDAO.getFuncionario(idFuncionarioResponsavel)
        );

        if (acomodacaoDAO.inserir(acomodacao)) {
            return "Acomodacao cadastrada com sucesso!";
        } else {
            throw new AcomodacaoException("Não foi possível cadastrar a acomodacao! Por favor, tente novamente.");
        }
    }

    // Método que valida todas as informações recebidas e interage com banco de dados para alterar um elemento já existente de acomodacao
    public String alterar(
            Long id,
            String nome,
            Double valorDiaria,
            Integer limiteHospedes,
            String descricao,
            Long idFuncionarioResponsavel
    ) throws AcomodacaoException {
        String mensagemErro = validarCampos(Funcionalidade.ALTERAR, id, nome, valorDiaria, limiteHospedes, idFuncionarioResponsavel);
        if(!mensagemErro.equals("")) throw new AcomodacaoException(mensagemErro);

        Acomodacao acomodacao = new Acomodacao(
                id,
                nome,
                valorDiaria,
                limiteHospedes,
                descricao,
                acomodacaoDAO.getFuncionario(idFuncionarioResponsavel)
        );

        if (acomodacaoDAO.atualizar(acomodacao)) {
            return "Acomodacao alterada com sucesso!";
        } else {
            throw new AcomodacaoException("Não foi possível alterar a acomodacao!! Por favor, tente novamente.");
        }
    }

    // Método que valida todas as informações recebidas e interage com banco de dados para excluir um elemento já existente de acomodacao
    public String excluir(Long id) throws AcomodacaoException {
        String mensagemErro = validarCampos(Funcionalidade.EXCLUIR, id, null, null, null, null);
        if (!mensagemErro.equals("")) throw new AcomodacaoException(mensagemErro);

        if (acomodacaoDAO.deletar(id)) {
            return "Acomodacao excluída com sucesso!";
        } else {
            throw new AcomodacaoException("Não foi possível excluir a acomodacao! Por favor, tente novamente.");
        }
    }

    // Recebe pelo DAO o id do elemento de teste
    public Long targetId() throws AcomodacaoException {
        return acomodacaoDAO.targetId();
    }

    // Métodos privados

    private String validarCampos(
            Funcionalidade funcionalidade,
            Long id,
            String nome,
            Double valorDiaria,
            Integer limiteHospedes,
            Long idFuncionarioResponsavel
    ) throws AcomodacaoException {
        String erros = "";
        // Verificação de id
        if (funcionalidade == Funcionalidade.ALTERAR || funcionalidade == Funcionalidade.EXCLUIR) {
            if (id == null) {
                erros += "\n- Id é obrigatório.";
            } else if (acomodacaoDAO.selecionarPorId(id) == null) {
                erros += "\n- Id não encontrado.";
            }
        }
        // Verificação de outros campos
        if (funcionalidade == Funcionalidade.CADASTRAR || funcionalidade == Funcionalidade.ALTERAR) {
            // Nome
            if (nome == null || nome.trim().equals("")) {
                erros += "\n- Nome é obrigatório.";
            }

            // Valor diaria
            if (valorDiaria == null) {
                erros += "\n- Valor da diária é obrigatório.";
            }

            // Limite hospedes
            if (limiteHospedes == null) {
                erros += "\n- Limite de hóspedes é obrigatório.";
            }

            // Id funcionario responsavel
            if (idFuncionarioResponsavel == null) {
                erros += "\n- Id do funcionário resposável é obrigatório.";
            } else if (acomodacaoDAO.getFuncionario(idFuncionarioResponsavel) == null) {
                erros += "\n- Id do funcionário resposável não encontrado.";
            }
        }

        // Montagem da mensagem de erro
        String mensagemErro = "";
        if (!erros.equals("")) {
            mensagemErro = "Não foi possível " + funcionalidade.name().toLowerCase() + " a acomodacao! " +
                    "Erro(s) encontrado(s):" + erros;
        }
        return mensagemErro;
    }

}
