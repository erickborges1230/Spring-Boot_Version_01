package com.erickborges.novasfuncionalidades.controller;

import com.erickborges.novasfuncionalidades.entity.funcionario;
import com.erickborges.novasfuncionalidades.service.funcionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
                     <a href="CalcularFolhaFuncionarios">3. Calcular Folha dos Funcionários</a><br/>
                     <a href="listarFuncionariosIdadeMenor30">4. Listar Funcionários com idade menor que 30 anos</a><br/>
                     <a href="listarFuncionariosIdadeMaior30">5. Listar Funcionários com idade maior que 30 anos</a><br/>
                     <a href="listarFuncionariosMaiorSalário">6. Listar Funcionários com maior salário</a><br/>
                     <a href="listarFuncionariosMenorSalário">7. Listar Funcionários com menor salário</a><br/>
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
    //Erro => Precisa converte de String para Double
//    @GetMapping(value = "/CalcularFolhaFuncionarios")
//    public ResponseEntity<List<String>> CalcularFolhaFuncionarios()
//    {
//        List<funcionario> funcionarios = funcionarioService.listAll();
//        List<String> somaSalarios = funcionarios.stream().map(funcionario::getSalario).toList();
//        return new ResponseEntity<List<String>>(somaSalarios, HttpStatus.OK);
//    }
    @GetMapping(value = "/listarFuncionariosIdadeMaior30")
    public ResponseEntity<String> listarFuncionariosIdadeMaior30()
    {
        List<funcionario> funcionarios = funcionarioService.listAll();
        long totalFuncionarios = funcionarios.stream().filter(f -> f.getIdade() < 30).count();
        double somaSalario = funcionarios.stream().
                filter(f -> f.getIdade() >= 30).mapToDouble(funcionario::getSalario).sum();
        double mediaSalarial = somaSalario/totalFuncionarios;
        //Conceito de Text Blocks do Java 13;
        String response = """
                {
                    "Total de funcionários >= 30 anos": %d,
                    "Média Salarial": %.2f
                }
                """.formatted(totalFuncionarios, mediaSalarial);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }
    //Listando o maior salário
    @GetMapping(value = "/listarFuncionariosMaiorSalário")
    public ResponseEntity<Optional<funcionario>> listarFuncionariosMaiorSalário()
    {
        List<funcionario> funcionarios = funcionarioService.listAll();
        //Para encontar o maior salário: primeiro fazer uma redução depois fazer uma comparação
        Optional<funcionario> funcionario = funcionarios.stream().reduce((f1, f2)-> f1.getSalario()> f2.getSalario() ? f1:f2);
        return new ResponseEntity<Optional<funcionario>>(funcionario, HttpStatus.OK);
    }
    @GetMapping(value = "/listarFuncionariosMenorSalário")
    public ResponseEntity<Optional<funcionario>> listarFuncionariosMenorSalário()
    {
        List<funcionario> funcionarios = funcionarioService.listAll();
        //Para encontar o maior salário: primeiro fazer uma redução depois fazer uma comparação
        Optional<funcionario> funcionario = funcionarios.stream().reduce((f1, f2)-> f1.getSalario()< f2.getSalario() ? f1:f2);
        return new ResponseEntity<Optional<funcionario>>(funcionario, HttpStatus.OK);
    }


}
