package com.erickborges.novasfuncionalidades.repository;

import com.erickborges.novasfuncionalidades.entity.funcionario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionariosRepository extends MongoRepository<funcionario, ObjectId> { //Vai receber um entity + ObjectId
}
