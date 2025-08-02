package com.erickborges.novasfuncionalidades.service;

import com.erickborges.novasfuncionalidades.entity.funcionario;
import com.erickborges.novasfuncionalidades.repository.FuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class funcionarioServiceImpl implements funcionarioService{

    //criando injeção de dependência do funcionárioRepository
    @Autowired //Métado para fazer a injeção de dependência
    private FuncionariosRepository funcionariosRepository;

    @Override
    public List<funcionario> listAll(){
        //Recebendo os funcionário
        List<funcionario> funcionarios = new ArrayList<>();
        funcionariosRepository.findAll().forEach(funcionarios::add); //Usando modelo lambda
        return funcionarios;
    }

}
