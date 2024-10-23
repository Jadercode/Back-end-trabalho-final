package controller;

import enumeration.Funcionalidade;
import exception.AcomodacaoException;
import service.AcomodacaoService;
import test.AcomodacaoTest;

public class AcomodacaoController {

    // Atributos
    private AcomodacaoTest acomodacaoTest;

    // Construtor
    public AcomodacaoController(AcomodacaoTest acomodacaoTest) {
        this.acomodacaoTest = acomodacaoTest;
    }

    // Gerenciador de testes
    public String testar(Funcionalidade funcionalidade) throws AcomodacaoException {
        switch (funcionalidade) {
            case LISTAR:
                return acomodacaoTest.listar();
            case CADASTRAR:
                return acomodacaoTest.cadastrar();
            case ALTERAR:
                return acomodacaoTest.alterar();
            case EXCLUIR:
                return acomodacaoTest.excluir();
            default:
                return null;
        }
    }

}
