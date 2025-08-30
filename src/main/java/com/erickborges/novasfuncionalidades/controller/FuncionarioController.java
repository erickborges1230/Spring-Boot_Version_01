package com.erickborges.novasfuncionalidades.controller;

import com.erickborges.novasfuncionalidades.entity.funcionario;
import com.erickborges.novasfuncionalidades.service.funcionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FuncionarioController {

    //Chamando a camada service
    @Autowired
    private funcionarioService funcionarioService;
    @GetMapping(value = "/")
    public ResponseEntity<String> inicio()
    {
       String response = """ 
               <html>
                <body>
                    <a href="listarFuncionarios">1. Listar Funcionários</a><br/>
                    <a href="listarCidadesFuncionarios">2. Listar Cidades Funcionários</a><br/>
                     <a href="CalcularFolhaFuncionarios">2. Calcular Folha dos Funcionários</a><br/>
                </body>
               </html>
               """;
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/listarFuncionarios")
    public ResponseEntity<List<funcionario>> listarFuncionairos()
    {
        List<funcionario> funcionarios = funcionarioService.listAll();
        //Retornando uma lista de String
        return new ResponseEntity<List<funcionario>>(funcionarios, HttpStatus.OK);
    }
    @GetMapping(value = "/listarCidadesFuncionarios")
    public ResponseEntity<List<String>> listarCidadesFuncionairos()
    {
        List<funcionario> funcionarios = funcionarioService.listAll();
        List<String> cidades = funcionarios.stream().map(funcionario::getCidade).toList();//Transformando a lista de funcionário em um Stream e depois mapeando
        return new ResponseEntity<List<String>>(cidades, HttpStatus.OK);
    }
    @GetMapping(value = "/CalcularFolhaFuncionarios")
    public ResponseEntity<List<String>> CalcularFolhaFuncionarios()
    {
        List<funcionario> funcionarios = funcionarioService.listAll();
        List<String> somaSalarios = funcionarios.stream().map(funcionario::getSalario).toList();
        return new ResponseEntity<List<String>>(somaSalarios, HttpStatus.OK);
    }

}
