package it.eugenio.GestioneIncendi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.eugenio.GestioneIncendi.models.CentroControllo;
import it.eugenio.GestioneIncendi.models.Sonda;

@SpringBootApplication
public class GestioneIncendiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GestioneIncendiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		CentroControllo cc = new CentroControllo();
		
		Sonda s1 = new Sonda(1, 11.111, 22.222);
		Sonda s2 = new Sonda(2, 11.111, 22.222);
		
		s1.registerObserver(cc);
		s2.registerObserver(cc);
		
		s1.rilevaFumo(6);
		s2.rilevaFumo(7);
		
		s1.unregisterObserver(cc);
		s2.unregisterObserver(cc);
		
		
	}

}
