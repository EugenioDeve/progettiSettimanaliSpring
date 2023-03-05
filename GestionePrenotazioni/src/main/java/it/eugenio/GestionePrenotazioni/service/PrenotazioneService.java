package it.eugenio.GestionePrenotazioni.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eugenio.GestionePrenotazioni.entity.Prenotazione;
import it.eugenio.GestionePrenotazioni.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {

	@Autowired
	private PrenotazioneRepository prRepo;
	
	public void insert(Prenotazione p) {
		if(prRepo.controlloPrenotazione(p.getData(), p.getPostazione().getId()) == null) {
			if(prRepo.controlloPrenotazioneUtente(p.getData(), p.getUtente().getId()) == null) {
				prRepo.save(p);
				System.out.println("Prenotazione inserita con successo");					
			} else {
				System.out.println("Impossibile inserire la prenotazione. L'utente con id " + p.getUtente().getId() + " ha già una prenotazione attiva per il giorno " + p.getData());
			}
		} else {
			System.out.println("La postazione selezionata è già occupata per la data " + p.getData());
		}
	}
	
	public List<Prenotazione> getAll() {
		return prRepo.findAll();
	}
	
	public Optional<Prenotazione> getById(int id) {
		return prRepo.findById(id);
	}
	
	public void stampaLista(List<Prenotazione> l) {
		for(Prenotazione li : l) {
			System.out.println(li);
		}
	}
	
}
