import controller.AcomodacaoController;
import dao.AcomodacaoDAO;
import enumeration.Funcionalidade;
import exception.AcomodacaoException;
import service.AcomodacaoService;
import test.AcomodacaoTest;

import java.util.ArrayList;

public class Principal {

    public static void main(String[] args) throws AcomodacaoException {
        // Inicialização de objetos
        AcomodacaoDAO acomodacaoDAO = new AcomodacaoDAO();
        AcomodacaoService acomodacaoService = new AcomodacaoService(acomodacaoDAO);
        AcomodacaoTest acomodacaoTest = new AcomodacaoTest(acomodacaoService);
        AcomodacaoController acomodacaoController = new AcomodacaoController(acomodacaoTest);

        ArrayList<Funcionalidade> funcionalidades = new ArrayList<>();

        funcionalidades.add(Funcionalidade.LISTAR);
        funcionalidades.add(Funcionalidade.CADASTRAR);
        funcionalidades.add(Funcionalidade.ALTERAR);
        funcionalidades.add(Funcionalidade.EXCLUIR);

        // Informações do teste

        for (Funcionalidade funcionalidade : funcionalidades) {

            System.out.println("FUNCIONALIDADE: " + funcionalidade);
            System.out.println("RESULTADO DO TESTE:");

            try {
                System.out.println(acomodacaoController.testar(funcionalidade));
                if (funcionalidade != Funcionalidade.LISTAR)
                    System.out.println(acomodacaoController.testar(Funcionalidade.LISTAR));
            } catch (AcomodacaoException excecao) {
                System.err.println(excecao.getMessage());
            }
        }
    }

}
