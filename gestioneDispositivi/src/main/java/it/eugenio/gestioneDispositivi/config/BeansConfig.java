package it.eugenio.gestioneDispositivi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import it.eugenio.gestioneDispositivi.models.Dispositivo;
import it.eugenio.gestioneDispositivi.models.StatoDispositivo;
import it.eugenio.gestioneDispositivi.models.TipoDispositivo;
import it.eugenio.gestioneDispositivi.models.TipoRuolo;
import it.eugenio.gestioneDispositivi.models.Utente;

@Configuration
public class BeansConfig {

	
	@Bean
	@Scope("prototype")
	public Utente utente(String nome, String cognome, String email, String username, String password, TipoRuolo ruolo) {
		return Utente.builder()
				.nome(nome)
				.cognome(cognome)
				.email(email)
				.username(username)
				.password(password)
				.ruolo(ruolo)
				.build();
	}
	
	
	@Bean
	@Scope("prototype")
	public Dispositivo dispositivo(TipoDispositivo td, StatoDispositivo sd) {
		return Dispositivo.builder()
				.tipo(td)
				.stato(sd)
				.build();
	}
}
