package it.eugenio.GestionePrenotazioni.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.eugenio.GestionePrenotazioni.entity.Prenotazione;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {

	@Query(
			nativeQuery = true,
			value = "SELECT * FROM prenotazioni WHERE data = :fn AND postazione_id = :b"
		)
		Prenotazione controlloPrenotazione(@Param("fn") LocalDate fn, @Param("b") int b);
	
	@Query(
			nativeQuery = true,
			value = "SELECT * FROM prenotazioni WHERE data = :fn AND utente_id = :b"
		)
		Prenotazione controlloPrenotazioneUtente(@Param("fn") LocalDate fn, @Param("b") int b);
	
}
