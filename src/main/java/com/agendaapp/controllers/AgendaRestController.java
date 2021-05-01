package com.agendaapp.controllers;


import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendaapp.models.Pessoa;
import com.agendaapp.repository.PessoaRepository;

@RestController
@RequestMapping("restapi")
public class AgendaRestController {
	
	@Autowired
	private PessoaRepository pr;
	
	@PostMapping("adicionar")
	public ResponseEntity<Pessoa> adicionar(@Valid Pessoa pessoa, BindingResult result) {
		
		if(result.hasErrors()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(pr.save(pessoa));
	}
	

	@GetMapping("listar")
	public ResponseEntity<Iterable<Pessoa>> listar() {
		
		Iterable<Pessoa> pessoas = pr.findAll();
		
		return ResponseEntity.ok(pessoas);
	}
	
	@GetMapping("buscar/{busca}")
	public ResponseEntity<Iterable<Pessoa>> busca(@PathVariable("busca") String busca) {
		
		Iterable<Pessoa> pessoas = pr.findByEmailAndName(busca);
		
		if (pessoas == null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(pessoas);
	}
	
	@PutMapping("editar/{id}")
	public ResponseEntity<Pessoa> editContatoPost(@PathVariable Integer id, @Valid @RequestBody Pessoa pessoa) {
		
		Pessoa existente = pr.findById(id);
		
		if (existente == null) return ResponseEntity.notFound().build();
		
		BeanUtils.copyProperties(pessoa, existente,"id");
		
		return ResponseEntity.ok(pr.save(existente));
		
	}
	
	@DeleteMapping("deletar/{id}")
	public ResponseEntity<Void> deleteContato(@PathVariable Integer id) {
		
		Pessoa existente = pr.findById(id);
		
		if (existente == null) return ResponseEntity.notFound().build();
		
		pr.delete(existente);
		
		return ResponseEntity.noContent().build(); 
		
	}

}
