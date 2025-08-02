package com.erickborges.novasfuncionalidades.service;

import com.erickborges.novasfuncionalidades.entity.funcionario;
import com.erickborges.novasfuncionalidades.repository.FuncionariosRepository;

import java.util.List;

public interface funcionarioService {
    List<funcionario> listAll(); //Criando uma lista de funcionário e será um listAll;
}
