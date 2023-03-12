package it.eugenio.gestioneDispositivi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eugenio.gestioneDispositivi.models.Dispositivo;

@Service
public class DispositiviService {

	@Autowired
	private DispositivoRepository dRepo;
	
	public List<Dispositivo> getAll() {
		return dRepo.findAll();
	}
	
	public Optional<Dispositivo> getBydId(int id) {
		return dRepo.findById(id);
	}
	
	public Dispositivo save(Dispositivo p) {
		return dRepo.save(p);
	}
	
	public void delete(Dispositivo p) {
		dRepo.delete(p);
	}
	
}
