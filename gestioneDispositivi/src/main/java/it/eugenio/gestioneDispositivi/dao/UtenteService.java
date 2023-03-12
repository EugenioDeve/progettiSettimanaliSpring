package it.eugenio.gestioneDispositivi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eugenio.gestioneDispositivi.models.Utente;

@Service
public class UtenteService {
	
	@Autowired
	private UtenteRepository uRepo;
	
	public List<Utente> getAll() {
		return uRepo.findAll();
	}
	
	public Optional<Utente> getBydId(int id) {
		return uRepo.findById(id);
	}
	
	public Utente save(Utente p) {
		return uRepo.save(p);
	}
	
	public void delete(Utente p) {
		uRepo.delete(p);
	}
	
}
