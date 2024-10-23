package test;

import exception.AcomodacaoException;
import model.Funcionario;
import service.AcomodacaoService;

public class AcomodacaoTest implements Test {

    // Atributos
    private AcomodacaoService acomodacaoService;
    private Long targetId;

    // Construtor
    public AcomodacaoTest(AcomodacaoService acomodacaoService) {
        this.acomodacaoService = acomodacaoService;
    }

    // Métodos de testes

    public String listar() throws AcomodacaoException {
        return acomodacaoService.listar();
    }

    public String cadastrar() throws AcomodacaoException {
        return acomodacaoService.cadastrar(
                "a",
                1.5,
                2,
                "a2",
                new Funcionario()
        );
    };

    public void setTargetId() throws  AcomodacaoException {
        targetId = acomodacaoService.targetId();
    }

    public String alterar() throws AcomodacaoException {
        setTargetId();
        return acomodacaoService.alterar(
                targetId,
                "aadadsda",
                2.5,
                3,
                "a5",
                new Funcionario()
        );
    }

    public String excluir() throws AcomodacaoException {
        return acomodacaoService.excluir(targetId);
    }

}
