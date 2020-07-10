package com.ifam.sistema_estagio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifam.sistema_estagio.controller.service.CoordenadoraService;
import com.ifam.sistema_estagio.controller.service.ProfessorService;
import com.ifam.sistema_estagio.controller.service.SecurityService;
import com.ifam.sistema_estagio.model.entity.Coordenadora;
import com.ifam.sistema_estagio.model.entity.Professor;
import com.ifam.sistema_estagio.model.entity.interfaces.UsuarioLogavel;

@Controller
public class UsuarioController {

	@Autowired
	private ProfessorService professorService;
	@Autowired
	private CoordenadoraService coordenadoraService;

	@Autowired
	private SecurityService securityService;

	@GetMapping("/registration")
	private String registration(Model model, @RequestParam("u") String usuario) {

		if(usuario == "professor") {
			model.addAttribute("usuario", new Professor());			
		}else if(usuario == "coordenadora") {
			model.addAttribute("usuario", new Coordenadora());			
		}

		return "Cadastro/index";
	}

	@PostMapping("/registration")
	private String registration(@ModelAttribute("usuario") UsuarioLogavel usuario, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "cadastrar";
		}
		
		if(usuario instanceof Coordenadora) {
			coordenadoraService.create((Coordenadora) usuario);
		}else {
			professorService.create((Professor) usuario);			
		}

		securityService.autoLogin(usuario.getUsername(), usuario.getPasswordConfirm());

		return "redirect:/home";
	}

	@GetMapping("/login")
	private String login(Model model, String error, String logout) {

		if (error != null) {
			model.addAttribute("error", "Usuário ou senha inválidos!");
		}
		if (logout != null) {
			model.addAttribute("message", "Você foi logado corretamente!");
			
			return "redirect:/home";
		}

		return "Login/index";
	}

	@GetMapping("/home")
	private String home() {
		return "Homepage/index";
	}
}
