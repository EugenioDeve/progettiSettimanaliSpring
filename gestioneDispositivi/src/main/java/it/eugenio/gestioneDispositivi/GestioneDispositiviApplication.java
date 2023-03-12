package it.eugenio.gestioneDispositivi;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.eugenio.gestioneDispositivi.config.BeansConfig;
import it.eugenio.gestioneDispositivi.dao.DispositiviService;
import it.eugenio.gestioneDispositivi.dao.UtenteService;
import it.eugenio.gestioneDispositivi.models.Dispositivo;
import it.eugenio.gestioneDispositivi.models.StatoDispositivo;
import it.eugenio.gestioneDispositivi.models.TipoDispositivo;
import it.eugenio.gestioneDispositivi.models.TipoRuolo;
import it.eugenio.gestioneDispositivi.models.Utente;


@SpringBootApplication
public class GestioneDispositiviApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(GestioneDispositiviApplication.class, args);
	}
	
	
	@Autowired
	private UtenteService uServ;
	
	@Autowired
	private DispositiviService dServ;
	
	
	
	@Override
	public void run(String... args) throws Exception {
		
//		popolaDB();
		
	}
	
	public void popolaDB() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(BeansConfig.class);

		Utente u1 = (Utente) ctx.getBean("utente", "eugenio","mattera","eugenio@gmail.it", "uggi", "0123456", TipoRuolo.ADMIN);
		Utente u2 = (Utente) ctx.getBean("utente", "Mario","Rossi","marioRossi@gmail.it", "marioRossi", "0123456",TipoRuolo.USER);
		Utente u3 = (Utente) ctx.getBean("utente", "Marco","Neri","marcoNeri@gmail.it", "marcoNeri", "0123456",TipoRuolo.USER);
		Utente u4 = (Utente) ctx.getBean("utente", "Simone","Verdi","simoneVerdi@gmail.it", "simoneVerdi", "0123456",TipoRuolo.USER);
		Utente u5 = (Utente) ctx.getBean("utente", "Giovanni","Bianchi","gianniBianchi@gmail.it", "gianniBianchi", "0123456",TipoRuolo.USER);

		Dispositivo d1 = (Dispositivo) ctx.getBean("dispositivo", TipoDispositivo.TABLET, StatoDispositivo.ASSEGNATO);
		Dispositivo d2 = (Dispositivo) ctx.getBean("dispositivo", TipoDispositivo.LAPTOP, StatoDispositivo.DISPONIBILE);
		Dispositivo d3 = (Dispositivo) ctx.getBean("dispositivo", TipoDispositivo.TELEFONO, StatoDispositivo.DISPONIBILE);
		Dispositivo d4 = (Dispositivo) ctx.getBean("dispositivo", TipoDispositivo.TABLET, StatoDispositivo.DISPONIBILE);
		Dispositivo d5 = (Dispositivo) ctx.getBean("dispositivo", TipoDispositivo.TELEFONO, StatoDispositivo.DISMESSO);
		Dispositivo d6 = (Dispositivo) ctx.getBean("dispositivo", TipoDispositivo.TABLET, StatoDispositivo.MANUTENZIONE);

		
		u1.setDispositivi(new HashSet<>() {
			{
				add(d1);
			}
		});
		
		dServ.save(d1);
		dServ.save(d2);
		dServ.save(d3);
		dServ.save(d4);
		dServ.save(d5);
		dServ.save(d6);
		uServ.save(u1);
		uServ.save(u2);
		uServ.save(u3);
		uServ.save(u4);
		uServ.save(u5);
		
		((AnnotationConfigApplicationContext)ctx).close();
	}

}
