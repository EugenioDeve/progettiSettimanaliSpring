package it.eugenio.gestioneDispositivi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eugenio.gestioneDispositivi.dao.DispositiviService;
import it.eugenio.gestioneDispositivi.models.Dispositivo;

@Controller
public class DispositivoController {
	
	@Autowired
	private DispositiviService serv;
	
	//METODI GET DISPONIBILI PER TUTTI GLI UTENTI PURCHE' SIANO LOGGATI
	
	@GetMapping("/dispositivi")
	@ResponseBody
	public ResponseEntity<List<Dispositivo>> getAll() {
		List<Dispositivo> disp = serv.getAll();
		
		if( disp.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(disp, HttpStatus.OK);
	}
	
	@GetMapping("/dispositivi/{id}")
	@ResponseBody
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		Optional<Dispositivo> dispObj = serv.getBydId(id);
		if( !dispObj.isPresent() ) {
			return new ResponseEntity<>("DISPOSITIVO NON TROVATO",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dispObj.get(), HttpStatus.OK);
	}
	
	//ROTTE SOTTOPOSTE A CONTROLLO SU RUOLO E ACCESIBILI SOLO AD ADMIN
	
	@PostMapping("/dispositivi")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> create(@RequestBody Dispositivo p) {
		Dispositivo disp = serv.save(p);
		
		return new ResponseEntity<>(disp, HttpStatus.CREATED);
	}
	
	@PutMapping("/dispositivi/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> update(
			@PathVariable Integer id,
			@RequestBody Dispositivo _disp) {
		
		Optional<Dispositivo> dispObj = serv.getBydId(id);
		
		if( !dispObj.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Dispositivo disp = dispObj.get();
		disp.setStato( _disp.getStato() );
		serv.save(disp);
		
		return new ResponseEntity<>(disp, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/dispositivi/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		Optional<Dispositivo> dispObj = serv.getBydId(id);
		if( !dispObj.isPresent() ) {
			return new ResponseEntity<>("DISPOSITIVO NON TROVATO",HttpStatus.NOT_FOUND);
		}
		
		serv.delete(dispObj.get());
		return new ResponseEntity<>(
				String.format("Dispositivo con id %d cancellato!", id), HttpStatus.OK);
	}
	
}
