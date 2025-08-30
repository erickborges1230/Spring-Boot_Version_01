package com.erickborges.novasfuncionalidades.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "funcionarios")
public class funcionario {

    //Atributos
    protected String nome;
    protected int idade;
    protected String cargo;
    protected String departamento;
    protected String cidade;
    protected String estado;
    protected String formatoTrablho;
    protected Double salario;

    public funcionario(String nome, int idade, String cargo, String departamento, String cidade, String estado, String formatoTrablho, Double salario) {
        this.nome = nome;
        this.idade = idade;
        this.cargo = cargo;
        this.departamento = departamento;
        this.cidade = cidade;
        this.estado = estado;
        this.formatoTrablho = formatoTrablho;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFormatoTrablho() {
        return formatoTrablho;
    }

    public void setFormatoTrablho(String formatoTrablho) {
        this.formatoTrablho = formatoTrablho;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof funcionario that)) return false;
        return idade == that.idade && Objects.equals(nome, that.nome) && Objects.equals(cargo, that.cargo) && Objects.equals(departamento, that.departamento) && Objects.equals(cidade, that.cidade) && Objects.equals(estado, that.estado) && Objects.equals(formatoTrablho, that.formatoTrablho) && Objects.equals(salario, that.salario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, idade, cargo, departamento, cidade, estado, formatoTrablho, salario);
    }

    @Override
    public String toString() {
        return "funcionario{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", cargo='" + cargo + '\'' +
                ", departamento='" + departamento + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", formatoTrablho='" + formatoTrablho + '\'' +
                ", salario='" + salario + '\'' +
                '}';
    }
}
