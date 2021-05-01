package com.agendaapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agendaapp.models.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, String>{
	
	@Query("select p from Pessoa p where p.email like %?1% or p.primeiroNome like %?1%")
	List<Pessoa> findByEmailAndName(String busca);
	
	Pessoa findById(Integer id);

}
