package com.agendaapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.agendaapp.models.Pessoa;
import com.agendaapp.repository.PessoaRepository;

@Controller
public class AgendaController {
	
	@Autowired
	private PessoaRepository pr;
	
	@RequestMapping(value="/insertContato", method=RequestMethod.GET)
	public ModelAndView cadastroForm(){
		
		ModelAndView mv = new ModelAndView("agenda/insertContato");
		
		mv.addObject("title", "Adicionar Contato");
		
		return mv;
	}
	
	@RequestMapping(value="/insertContato", method=RequestMethod.POST)
	public ModelAndView cadastroForm(@Valid Pessoa pessoa, BindingResult result){
		
		ModelAndView mv = new ModelAndView("agenda/insertContato");
		
		mv.addObject("title", "Adicionar Contato");
		
		
		System.out.print(result.hasErrors());
		if(result.hasErrors()) {
			mv.addObject("mensagem", "Verifique os campos");
			return mv;
		}
		
		mv.addObject("mensagem", "Cadastrado");
		
		pr.save(pessoa);
		
		return mv;
	
	}
	
}
