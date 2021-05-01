package com.agendaapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.agendaapp.models.Pessoa;
import com.agendaapp.repository.PessoaRepository;

@Controller
public class IndexController {
	
	@Autowired
	private PessoaRepository pr;
	
	@RequestMapping(value="/")
	public ModelAndView index() {
		
		Iterable<Pessoa> pessoas = pr.findAll();
		
		ModelAndView mv = new ModelAndView("index");
		
		mv.addObject("title", "Agenda");
		mv.addObject("pessoas", pessoas); 
		
		return mv;
	}
	
	@RequestMapping(value="/busca", method=RequestMethod.POST)
	public ModelAndView busca(@RequestParam("busca") String busca) {
		
		Iterable<Pessoa> pessoas = pr.findByEmailAndName(busca);
		
		ModelAndView mv = new ModelAndView("index");
		
		mv.addObject("pessoas", pessoas);
		
		return mv;
	}
	
	@RequestMapping(value="/{pessoa.id}", method=RequestMethod.GET)
	public String deleteContato(Integer id) {
		
		Pessoa pessoa = pr.findById(id);
		
		pr.delete(pessoa);
		
		return "redirect:/";
		
	}
	
	@RequestMapping(value="editContato", method=RequestMethod.GET)
	public ModelAndView editContatoGet(Integer id) {
		
		ModelAndView mv = new ModelAndView("agenda/editContato");
		
		Pessoa pessoa = pr.findById(id);
		
		mv.addObject("pessoa", pessoa);
		
		return mv;
		
	}
	
	@RequestMapping(value="editContatoRest", method=RequestMethod.POST)
	public ResponseEntity<Pessoa> editContatoPostRest(Integer id, @Valid Pessoa pessoa) {
		
		Pessoa existente = pr.findById(id);
		
		if (existente == null) return ResponseEntity.notFound().build();
		
		pr.save(existente);
		
		
		return ResponseEntity.ok(existente);
		
	}
	
	@RequestMapping(value="editContato", method=RequestMethod.POST)
	public ModelAndView editContatoPost(Integer id, @Valid Pessoa pessoa) {
		
		ModelAndView mv = new ModelAndView("agenda/editContato");
		
		Pessoa existente = pr.findById(id);
		
		if (existente == null) {
			mv.addObject("mensagem", "Id not found");
			return mv;
		}
		
		pr.save(existente);
		
		return mv;
		
	}


}
