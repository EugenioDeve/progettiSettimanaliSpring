package it.eugenio.gestioneDispositivi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.eugenio.gestioneDispositivi.models.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {

}
