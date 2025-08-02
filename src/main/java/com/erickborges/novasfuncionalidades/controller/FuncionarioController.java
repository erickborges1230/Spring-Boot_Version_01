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

    //Chamando a camada de service
    @Autowired
    private funcionarioService funcionarioService;
    @GetMapping("/")
    public ResponseEntity<String> inicio()
    {
       String response = """ 
               <html>
                <body>
                    <a href="listarFuncionarios">1. Listar Funcionários
                </body>
               </html>
               """;
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping("/listarFuncionarios")
    public ResponseEntity<List<funcionario>> listarFuncionairos()
    {
        List<funcionario> funcionarios = funcionarioService.listAll();
        return new ResponseEntity<List<funcionario>>(funcionarios, HttpStatus.OK);
    }

}
