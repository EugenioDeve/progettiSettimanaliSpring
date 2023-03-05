package it.eugenio.GestionePrenotazioni.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eugenio.GestionePrenotazioni.entity.Edificio;
import it.eugenio.GestionePrenotazioni.repository.EdificioRepository;

@Service
public class EdificioService {
	
	@Autowired
	private EdificioRepository edRepo;
	
	public void insert(Edificio e) {
		edRepo.save(e);
		System.out.println("Edificio inserito con successo");
	}
	
	public Optional<Edificio> getEdificioById(int id) {
		return edRepo.findById(id);
	}
	
	public List<Edificio> getAll() {
		return edRepo.findAll();
	}
	
	public Optional<Edificio> getById(int id) {
		return edRepo.findById(id);
	}
	
	public void stampaLista(List<Edificio> l) {
		for(Edificio li : l) {
			System.out.println(li);
		}
	}
}
