package model;

import util.Util;

import java.util.Objects;

/*Classe que representa uma acomodação em um sistema de reservas.*/

public class Acomodacao {

    // Atributos
    private Long id; // Identificador da acomodação
    private String nome; // Nome da acomodação (obrigatório)
    private Double valorDiaria; // Valor da diária (obrigatório)
    private Integer limiteHospedes; // Limite de hóspedes (obrigatório)
    private String descricao; // Descrição da acomodação (opcional)
    private Funcionario funcionarioResponsavel; // Funcionário responsável (obrigatório)

    // Construtor vazio
    public Acomodacao() {
    }

    // Construtor sem o id
    public Acomodacao(String nome, Double valorDiaria, Integer limiteHospedes, String descricao, Funcionario funcionarioResponsavel) {
        this.nome = nome;
        this.valorDiaria = valorDiaria;
        this.limiteHospedes = limiteHospedes;
        this.descricao = descricao;
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    // Construtor com todos os atributos
    public Acomodacao(Long id, String nome, Double valorDiaria, Integer limiteHospedes, String descricao, Funcionario funcionarioResponsavel) {
        this.id = id;
        this.nome = nome;
        this.valorDiaria = valorDiaria;
        this.limiteHospedes = limiteHospedes;
        this.descricao = descricao;
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(Double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public Integer getLimiteHospedes() {
        return limiteHospedes;
    }

    public void setLimiteHospedes(Integer limiteHospedes) {
        this.limiteHospedes = limiteHospedes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Funcionario getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    // Outros métodos

    /*Retorna uma representação em string da acomodação.*/

    @Override
    public String toString() {
        return "Id: " + id +
                " | Nome: " + nome +
                " | Valor da diária: " + Util.formatarValorMonetario(valorDiaria) +
                " | Limite de hóspedes: " + limiteHospedes +
                " | Descrição: " + descricao +
                " | Funcionário responsável: " +
                funcionarioResponsavel.getId() + " (" +
                funcionarioResponsavel.getNomeCompleto() + ")";
    }

    /*Compara se dois objetos Acomodacao são iguais com base no id.*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Acomodacao that = (Acomodacao) o;
        return Objects.equals(id, that.id);
    }

    /* Retorna o hash code da acomodação com base no id.*/

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
