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

    public String cadastrar(
            String nome,
            Double valorDiaria,
            Integer limiteHospedes,
            String descricao,
            Funcionario funcionarioResponsavel
    ) throws AcomodacaoException {
//        String mensagemErro = validarCampos(Funcionalidade.CADASTRAR, null, nomeCompleto, dataNascimentoFormatoBR, documento);
//        if(!mensagemErro.equals("")) throw new AcomodacaoException(mensagemErro);

        Acomodacao acomodacao = new Acomodacao(
                nome,
                valorDiaria,
                limiteHospedes,
                descricao,
                funcionarioResponsavel
        );

        if (acomodacaoDAO.inserir(acomodacao)) {
            return "Acomodacao cadastrada com sucesso!";
        } else {
            throw new AcomodacaoException("Não foi possível cadastrar a acomodacao! Por favor, tente novamente.");
        }
    }

    public String alterar(
            Long id,
            String nome,
            Double valorDiaria,
            Integer limiteHospedes,
            String descricao,
            Funcionario funcionarioResponsavel
    ) throws AcomodacaoException {
//        String mensagemErro = validarCampos(Funcionalidade.ALTERAR, id, nomeCompleto, dataNascimentoFormatoBR, documento);
//        if(!mensagemErro.equals("")) throw new AcomodacaoException(mensagemErro);

        Acomodacao acomodacao = new Acomodacao(
                id,
                nome,
                valorDiaria,
                limiteHospedes,
                descricao,
                funcionarioResponsavel
        );

        if (acomodacaoDAO.atualizar(acomodacao)) {
            return "Acomodacao alterada com sucesso!";
        } else {
            throw new AcomodacaoException("Não foi possível alterar a acomodacao!! Por favor, tente novamente.");
        }
    }

    public String excluir(Long id) throws AcomodacaoException {
        String mensagemErro = validarCampos(Funcionalidade.EXCLUIR, id, null, null, null);
        if (!mensagemErro.equals("")) throw new AcomodacaoException(mensagemErro);

        if (acomodacaoDAO.deletar(id)) {
            return "Acomodacao excluída com sucesso!";
        } else {
            throw new AcomodacaoException("Não foi possível excluir a acomodacao! Por favor, tente novamente.");
        }
    }

    public Long targetId() throws AcomodacaoException {
        return acomodacaoDAO.targetId();
    }

    // Métodos privados

    private String validarCampos(
            Funcionalidade funcionalidade,
            Long id,
            String nomeCompleto,
            String dataNascimentoFormatoBR,
            String documento
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
            // Nome completo
            if (nomeCompleto == null || nomeCompleto.trim().equals("")) {
                erros += "\n- Nome completo é obrigatório.";
            }
            // Data de nascimento
            if (dataNascimentoFormatoBR == null || dataNascimentoFormatoBR.trim().equals("")) {
                erros += "\n- Data de nascimento é obrigatória.";
            } else if (!Util.validarDataFormatoBR(dataNascimentoFormatoBR)) {
                erros += "\n- Data de nascimento inválida.";
            }
            // Documento
            if (documento == null || documento.trim().equals("")) {
                erros += "\n- Documento é obrigatório.";
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
