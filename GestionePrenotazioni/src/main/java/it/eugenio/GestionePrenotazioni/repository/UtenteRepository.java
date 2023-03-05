package it.eugenio.GestionePrenotazioni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.eugenio.GestionePrenotazioni.entity.Utente;
@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {

}
