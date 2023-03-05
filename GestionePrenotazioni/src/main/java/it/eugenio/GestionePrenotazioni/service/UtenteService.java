package it.eugenio.GestionePrenotazioni.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eugenio.GestionePrenotazioni.entity.Utente;
import it.eugenio.GestionePrenotazioni.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utRepo;
	
	public void insert(Utente u) {
		utRepo.save(u);
		System.out.println("Utente inserito con successo");
	}
	
	public List<Utente> getAll() {
		return utRepo.findAll();
	}
	
	public Optional<Utente> getById(int id) {
		return utRepo.findById(id);
	}
	
	public void stampaLista(List<Utente> l) {
		for(Utente li : l) {
			System.out.println(li);
		}
	}
	
	
}
