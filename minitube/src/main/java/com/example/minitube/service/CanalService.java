package com.example.minitube.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.minitube.model.Canal;
import com.example.minitube.model.Video;

@Service
public class CanalService {
	private final List<Canal> canales = new ArrayList<>();
	private final AtomicLong canalId = new AtomicLong(1);
	private final AtomicLong videoId = new AtomicLong(1);
	
	public List<Canal> obtenerCanales() {
		return canales;
	}
	
	public Canal crearCanal(
			String nombre, 
			String usuario, 
			String descripcion) {
		Canal canal = new Canal(
				canalId.getAndIncrement(),
				nombre,
				usuario,
				descripcion);
		canales.add(canal);
		return canal;
	}
	
	public Canal buscarCanal(Long id) {
		for(Canal canal: canales) {
			if(canal.getId().equals(id)) {
				return canal;
			}
		} return null;
	}
	
	public void agregarVideo(
			Long canalId,
			String titulo,
			String urlYoutube) {
		Canal canal = buscarCanal(canalId);
		
		if(canal == null) {
			return;
		}
		
		String youtubeId = obtenerYoutubeId(urlYoutube);
		
		Video video = new Video(
				videoId.getAndIncrement(),
				titulo,
				urlYoutube,
				youtubeId);
		
		canal.agregarVideo(video);
	}
	
	private String obtenerYoutubeId(String url) {
		if(url == null) {
			return "";
		}
		
		if(url.contains("v=")) {
			String id = url.substring(url.indexOf("v=") + 2);
			
			if (id.contains("&")) {
				id = id.substring(0, id.indexOf("&"));
			}
			return id;
		}
		
		if(url.contains("youtu.be/")) {
			String id = url.substring(url.indexOf("youtub.be/") + 9);
			
			if(id.contains("?")) {
				id= id.substring(0, id.indexOf("?"));
			}
			return id;
		}
		
		if(url.contains("/shorts/")) {
			String id = url.substring(url.indexOf("/shorts/") + 8);
			
			if(id.contains("?")) {
				id = id.substring(0, id.indexOf("?"));
			}
			return id;
		}
		
		return "";
	}
}
