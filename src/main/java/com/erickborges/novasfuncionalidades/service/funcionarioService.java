package com.erickborges.novasfuncionalidades.service;

import com.erickborges.novasfuncionalidades.entity.funcionario;
import com.erickborges.novasfuncionalidades.repository.FuncionariosRepository;

import java.util.List;

public interface funcionarioService {
    List<funcionario> listAll(); //Criando uma lista de funcionário e será um listAll;
    //Implementando métados privados em Interfaces
    default void printAll(){
        List<funcionario> funcionarios = listAll();
        funcionarios.forEach(System.out::println);
    }
    static void calcularBonusFuncionario(funcionario funcionario)
    {
        List<Double> lista = List.of(0.1, 0.15, 0.20);
        double bonus = 0;
        if (funcionario.getSalario() < 5000)
            bonus = funcionario.getSalario()*lista.get(2);
        else if (funcionario.getSalario() <= 6000)
            bonus = funcionario.getSalario()*lista.get(1);
        else
            bonus = funcionario.getSalario()*lista.get(0);
        System.out.println(bonus);
    }
    private void init()
    {
        System.out.println("Listando os funcionários da aplicação");
    }
}
