package com.example.minitube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.minitube.model.Canal;
import com.example.minitube.service.CanalService;

@Controller
public class CanalController {
	private final CanalService canalService;
	
	public CanalController(CanalService canalService) {
		this.canalService = canalService;
	}
	
	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("canales", canalService.obtenerCanales());
		return "index";
	}
	
	@GetMapping("/canales/nuevo")
	public String nuevoCanal() {
		return "nuevo-canal";
	}
	
	@PostMapping("/canales")
	public String crearCanal(
			@RequestParam String nombre,
			@RequestParam String usuario,
			@RequestParam String descripcion) {
		
		Canal canal = canalService.crearCanal(nombre, usuario, descripcion);
		
		return "redirect:/canales/"+canal.getId();
	}
	
	@GetMapping("/canales/{id}")
	public String verCanal(
			@PathVariable Long id,
			Model model) {
		
		Canal canal = canalService.buscarCanal(id);
		
		if (canal == null) {
			return "redirect:/";
		}
		
		model.addAttribute("canal", canal);
		return "canal";
	}
	
	@PostMapping("/canales/{id}/videos")
	public String agregarVideo(
			@PathVariable Long id,
			@RequestParam String titulo,
			@RequestParam String urlYoutube) {
		
		canalService.agregarVideo(id, titulo, urlYoutube);
		
		return "redirect:/canales/"+ id;
	}
	
}
