package com.erickborges.novasfuncionalidades.controller;

import com.erickborges.novasfuncionalidades.entity.funcionario;
import com.erickborges.novasfuncionalidades.service.funcionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

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
    //Ordenando por maior salário e depos transformando em uma lista
    @GetMapping(value = "/listarFuncionariosOrdenados")
    public ResponseEntity<List<funcionario>> listarFuncionariosOrdenadoso()
    {
        List<funcionario> funcionarios = funcionarioService.listAll().stream().sorted((f1, f2)-> Double.compare(f1.getSalario(), f2.getSalario())).toList(); //depois transforma em lista
        return new ResponseEntity<List<funcionario>>(funcionarios, HttpStatus.OK);
    }
    @GetMapping(value = "/listarAnoNascimentoFuncionariosMaisAntigo")
    public ResponseEntity<String> listarAnoNascimentoFuncionariosMaisAntigo()
    {
        List<funcionario> funcionarios = funcionarioService.listAll();
        Optional<funcionario> funcionario = funcionarios.stream().reduce((f1, f2)-> f1.getIdade()> f2.getIdade() ? f1:f2);
        int maiorIdade = 0;
        String nome = "";
        if (funcionario.isPresent())
        {
            maiorIdade = funcionario.get().getIdade();
            nome = funcionario.get().getNome();
        }
        //Para saber a idade do funcionário
        LocalDate hoje = LocalDate.now();

       int anoNascimentoProvavel = hoje.getYear() - maiorIdade;

        String response = """
                {
                    "Nome do funcionário ": %s
                    "Provável Ano de Nascimento": %d
                }
                """.formatted(nome, maiorIdade);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }
    //Inserindo dados no meu banco de dados
    @GetMapping(value = "/inserirNovoFuncionarioJSON")
    public ResponseEntity<String> inserirNovoFuncionarioJSON() {
        var funcionarios = funcionarioService.listAll();
        String response = "";
        try {
            String novoFuncionario = """
                    "nome": "Pedro Ramos",
                    "idade": 23,
                    "cargo": "Engenheiro de Software",
                    "departamento": "Ti",
                    salário": 100000,
                    "endereço":{
                        "rua": "Avenida Nazaré",
                        "Numero": 33,
                        "cidade": "Belém",
                        "estado": "Pará",
                        "cep": 8888-111,
                    """;
            //passando o caminho para inserir os dados
            Files.writeString(Path.of("C:/Users/erick/OneDrive/Documentos/Material para estudo_Java/novasfuncionalidades/src/main/java/com/erickborges/novasfuncionalidades/funcionarios.json"),
                    novoFuncionario, StandardOpenOption.APPEND);
            response = Files.readString(Path.of("C:/Users/erick/OneDrive/Documentos/Material para estudo_Java/novasfuncionalidades/src/main/java/com/erickborges/novasfuncionalidades/funcionarios.json"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }
    @GetMapping("/listarAtividadesFuncionarios")
    public ResponseEntity<Map<?, ?>> listarAtividadesFuncionarios()
    {
        List<funcionario> funcionarios = new LinkedList<>();
        var nomeAtividadeMap = new LinkedHashMap<>();
        funcionarios.forEach(funcionario -> { //Uso do forEach => para cada funcionario
            String atividade = switch (funcionario.getDepartamento())
            {
                case "vendas" -> "Atividade Principal ==> realizar venda";
                case "Marketing" -> "Atividade Principal ==> realizar divugação";
                //Só definir o restante dos cases
                default -> "Ação não definidade";
            };
            nomeAtividadeMap.put(funcionario.getNome(), atividade);
        });
        return new ResponseEntity<>(nomeAtividadeMap, HttpStatus.OK);
    }
    @GetMapping(value = "/gerarSalarioNovoFuncionario")
    public ResponseEntity<String> gerarSalarioNovoFuncionario()
    {
        var funcionarios = funcionarioService.listAll();
        Optional <funcionario> funcionario = funcionarios.stream().reduce((f1, f2)-> f1.getSalario()<f2.getSalario()? f1:f2);
        double menorSalario = funcionario.get().getSalario();

        funcionario = funcionarios.stream().reduce((f1, f2)-> f1.getSalario()>f2.getSalario()? f1:f2);

        double maiorSalario = funcionario.get().getSalario();

        //criando um salário de forma aleatória com algoritimo RandomGenerator
        RandomGenerator randomGenerator = RandomGeneratorFactory.of("Teste").create(999);

        double novoSalario = randomGenerator.nextDouble(maiorSalario - menorSalario + 1) + menorSalario;
        String response = """
                {
                    "Salário novo funcionário": %d
                }
                """.formatted(novoSalario);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }

}
