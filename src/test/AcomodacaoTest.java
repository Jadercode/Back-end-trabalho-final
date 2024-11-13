package test;

import exception.AcomodacaoException;
import model.Funcionario;
import service.AcomodacaoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AcomodacaoTest implements Test {

    /*
    1L = João Costa Oliveira
    2L = Beatriz Martínez García
     */

    // Atributos
    private AcomodacaoService acomodacaoService;
    private Long targetId;

    // Construtor
    public AcomodacaoTest(AcomodacaoService acomodacaoService) {
        this.acomodacaoService = acomodacaoService;
    }

    // Métodos de testes

    // Método de teste para listar todas as acomodacoes
    public String listar() throws AcomodacaoException {
        return acomodacaoService.listar();
    }

    // Método de teste para cadastrar uma nova acomodacao
    public String cadastrar() throws AcomodacaoException {
        return acomodacaoService.cadastrar(
                "Domo",
                600.0,
                2,
                "Ideal para casal",
                1L
        );
    };

    // Método que recebe o id do elemento de teste
    public void setTargetId() throws  AcomodacaoException {
        targetId = acomodacaoService.targetId();
    }

    // Método de teste para atualizar um elemento já existente em acomodacao
    public String alterar() throws AcomodacaoException {
        setTargetId();
        return acomodacaoService.alterar(
                targetId,
                "Cabana",
                400.0,
                3,
                "Ideal para família",
                2L
        );
    }

    // Método de teste para deletar um elemento já existente em acomodacao
    public String excluir() throws AcomodacaoException {
        return acomodacaoService.excluir(targetId);
    }

}
