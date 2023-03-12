package it.eugenio.gestioneDispositivi.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import it.eugenio.gestioneDispositivi.dao.UtenteService;
import it.eugenio.gestioneDispositivi.models.Dispositivo;
import it.eugenio.gestioneDispositivi.models.StatoDispositivo;
import it.eugenio.gestioneDispositivi.models.Utente;

@Controller
public class UtenteController {

	@Autowired
	private UtenteService serv;
	
	@Autowired
	private DispositiviService dServ;
	
	
	//METODI GET DISPONIBILI PER TUTTI GLI UTENTI PURCHE' SIANO LOGGATI
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@PostMapping("/loginSuccess")
	public String ciao() {
		return "loginSuccess";
	}
	
	@GetMapping("/utenti")
	@ResponseBody
	public ResponseEntity<List<Utente>> getAll() {
		List<Utente> utenti = serv.getAll();
		
		if( utenti.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}
	
	@GetMapping("/utenti/{id}")
	@ResponseBody
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		Optional<Utente> utenteObj = serv.getBydId(id);
		if( !utenteObj.isPresent() ) {
			return new ResponseEntity<>("UTENTE NON TROVATO",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(utenteObj.get(), HttpStatus.OK);
	}
	
	
	//ROTTE SOTTOPOSTE A CONTROLLO SU RUOLO E ACCESIBILI SOLO AD ADMIN
	
	@PostMapping("/utenti")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> create(@RequestBody Utente p) {
		Utente utente = serv.save(p);
		
		return new ResponseEntity<>(utente, HttpStatus.CREATED);
	}
	
	@PutMapping("/utenti/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> update(
			@PathVariable Integer id,
			@RequestBody Dispositivo _disp) {
		
		Optional<Utente> utenteObj = serv.getBydId(id);
		
		if( !utenteObj.isPresent() ) {
			return new ResponseEntity<>("UTENTE DA AGGIORNARE NON TROVATO",HttpStatus.NOT_FOUND);
		}
		
		Utente utente = utenteObj.get();
		Set<Dispositivo> setDisp = utente.getDispositivi();
		setDisp.add(_disp);
		utente.setDispositivi(setDisp);
		serv.save(utente);
		
		return new ResponseEntity<>(utente, HttpStatus.CREATED);
	}
	
	
	//METODO PER L'ASSEGNAZIONE DI UN DISPSITIVO AD UN UTENTE. IL PRIMO ID = UTENTE, SECONDO ID = DISPOSITIVO
	@PutMapping("/utenti/{id}/{dispId}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateConID(
			@PathVariable Integer id,
			@PathVariable Integer dispId) {
		
		Optional<Utente> utenteObj = serv.getBydId(id);
		
		if( !utenteObj.isPresent() ) {
			return new ResponseEntity<>("UTENTE DA AGGIORNARE NON TROVATO",HttpStatus.NOT_FOUND);
		}
		
		Optional<Dispositivo> dispObj = dServ.getBydId(dispId);
		
		if( !dispObj.isPresent() ) {
			return new ResponseEntity<>("DISPOSITIVO DA AGGIORNARE NON TROVATO",HttpStatus.NOT_FOUND);
		}
		
		Dispositivo dis = dispObj.get();
		Utente utente = utenteObj.get();
		
		if ( dis.getStato().equals(StatoDispositivo.ASSEGNATO) || dis.getStato().equals(StatoDispositivo.DISMESSO) || dis.getStato().equals(StatoDispositivo.MANUTENZIONE)) {
			return new ResponseEntity<>("DISPOSITIVO DA ASSEGNARE NON DISPONIBILE",HttpStatus.FORBIDDEN);
		}
		Set<Dispositivo> setDisp = utente.getDispositivi();
		setDisp.add(dis);
		utente.setDispositivi(setDisp);
		serv.save(utente);
		dis.setStato(StatoDispositivo.ASSEGNATO);
		dServ.save(dis);
		
		return new ResponseEntity<>(utente, HttpStatus.CREATED);
	}
	
	
	//METODO CHE PERMETTE LA RESTITUZIONE DA PARTE DI UN UTENTE DI UN DISPOSTIVO
	@PutMapping("/restituisci/{id}/{dispId}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> restituisciDisp(
			@PathVariable Integer id,
			@PathVariable Integer dispId) {
		
		Optional<Utente> utenteObj = serv.getBydId(id);
		
		if( !utenteObj.isPresent() ) {
			return new ResponseEntity<>("UTENTE DA AGGIORNARE NON TROVATO",HttpStatus.NOT_FOUND);
		}
		
		Optional<Dispositivo> dispObj = dServ.getBydId(dispId);
		
		if( !dispObj.isPresent() ) {
			return new ResponseEntity<>("DISPOSITIVO DA RESTITUIRE NON TROVATO",HttpStatus.NOT_FOUND);
		}
		
		Dispositivo dis = dispObj.get();
		Utente utente = utenteObj.get();
		
		if ( dis.getStato().equals(StatoDispositivo.DISPONIBILE) || dis.getStato().equals(StatoDispositivo.DISMESSO) || dis.getStato().equals(StatoDispositivo.MANUTENZIONE)) {
			return new ResponseEntity<>("IL DISPOSITIVO NON E' STATO ANCORA ASSEGNATO",HttpStatus.FORBIDDEN);
		}
		
		Set<Dispositivo> setDisp = utente.getDispositivi();
		for (Dispositivo d : setDisp) {
			if(d.getId() == dis.getId()) {
				setDisp.remove(d);
				utente.setDispositivi(setDisp);
				serv.save(utente);
				dis.setStato(StatoDispositivo.DISPONIBILE);
				dServ.save(dis);
			}
		}
		
		return new ResponseEntity<>(utente, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/utenti/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		Optional<Utente> utenteObj = serv.getBydId(id);
		if( !utenteObj.isPresent() ) {
			return new ResponseEntity<>("UTENTE NON TROVATO",HttpStatus.NOT_FOUND);
		}
		
		serv.delete(utenteObj.get());
		return new ResponseEntity<>(
				String.format("Utente con id %d cancellato!", id), HttpStatus.OK);
	}
}
