package it.eugenio.GestionePrenotazioni;

import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.eugenio.GestionePrenotazioni.config.Configuration;
import it.eugenio.GestionePrenotazioni.entity.Edificio;
import it.eugenio.GestionePrenotazioni.entity.Postazione;
import it.eugenio.GestionePrenotazioni.entity.Prenotazione;
import it.eugenio.GestionePrenotazioni.entity.Utente;
import it.eugenio.GestionePrenotazioni.enums.TipoPostazione;
import it.eugenio.GestionePrenotazioni.service.EdificioService;
import it.eugenio.GestionePrenotazioni.service.PostazioneService;
import it.eugenio.GestionePrenotazioni.service.PrenotazioneService;
import it.eugenio.GestionePrenotazioni.service.UtenteService;

@SpringBootApplication
public class GestionePrenotazioniApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GestionePrenotazioniApplication.class, args);
	}

	@Autowired
	EdificioService edServ;

	@Autowired
	PostazioneService posServ;

	@Autowired
	PrenotazioneService prenServ;

	@Autowired
	UtenteService utServ;

	ApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration.class);

	Scanner input = new Scanner(System.in);

	@Override
	public void run(String... args) throws Exception {

//		popolaDB();
		menu();

	}

	public void menu() {
		
		int scelta=1;
		do {
			System.out.println("-----------------------------");
			System.out.println("SISTEMA GESTIONE PRENOTAZIONI");
			System.out.println("-----------------------------");
			System.out.println("Cosa vuoi fare?");
			System.out.println("1)Cerca Postazione per Citta e Tipologia");
			System.out.println("2)Inserisci Prenotazione");
			System.out.println("3)Inserisci Nuovo Utente");
			System.out.println("4)Inserisci Nuovo Edificio");
			System.out.println("5)Inserisci Nuova Postazione");
			System.out.println("6)Stampa Liste");
			System.out.println("0)Termina Programma");

			try {
				scelta = Integer.parseInt(input.nextLine());

				menu: switch (scelta) {
				case 1:
					cercaPost();
					break;

				case 2:
					utServ.stampaLista(utServ.getAll());
					System.out.println("Seleziona un utente digitando il suo ID:");
					int idU = Integer.parseInt(input.nextLine());

					cercaPost();
					System.out.println("Seleziona una postazione digitando il suo ID:");
					int idP = Integer.parseInt(input.nextLine());

					System.out.println("Inserisci la data in cui vuoi prenotare (Formato: YYYY-MM-DD):");
					String data = input.nextLine();

					inserisciPrenotazione(utServ.getById(idU).get(), posServ.getById(idP).get(), LocalDate.parse(data));
					break;

				case 3:
					System.out.println("Inserisci il tuo nome:");
					String nome = input.nextLine();

					System.out.println("Inserisci il tuo cognome:");
					String cognome = input.nextLine();

					System.out.println("Inserisci il tuo username:");
					String nick = input.nextLine();

					System.out.println("Inserisci la tua email:");
					String email = input.nextLine();

					inserisciUtente(nick, nome, cognome, email);
					break;

				case 4:
					System.out.println("Inserisci il nome dell'edificio:");
					String nomeED = input.nextLine();

					System.out.println("Inserisci l'indirizzo dell'edificio:");
					String indirizzoED = input.nextLine();

					System.out.println("Inserisci la città dell'edificio:");
					String cittaED = input.nextLine();

					inserisciEdificio(nomeED, indirizzoED, cittaED);
					break;

				case 5:
					System.out.println("Inserisci una descrizione della postazione:");
					String desc = input.nextLine();

					System.out.println("Seleziona il Tipo di Postazione tra:");
					System.out.println("1)Openspace");
					System.out.println("2)Privata");
					System.out.println("3)Sala Riunioni");
					TipoPostazione tipo = null;
					int tp = Integer.parseInt(input.nextLine());
					switch (tp) {
					case 1:
						tipo = TipoPostazione.OPENSPACE;
						break;
					case 2:
						tipo = TipoPostazione.PRIVATO;
						break;
					case 3:
						tipo = TipoPostazione.SALA_RIUNIONI;
						break;
					default:
						System.out.println("Selezione non valida");
						break;
					}

					System.out.println("Inserisci il numero massimo di occupanti:");
					int nMax = Integer.parseInt(input.nextLine());

					edServ.stampaLista(edServ.getAll());
					System.out.println("Seleziona un edifico digitando l'ID corrispondente:");
					int edi = Integer.parseInt(input.nextLine());

					inserisciPostazione(desc, tipo, nMax, edServ.getById(edi).get());
					break;

				case 6:
					System.out.println("-----------------------------");
					System.out.println("STAMPA LISTE");
					System.out.println("-----------------------------");
					System.out.println("1)Stampa Postazioni");
					System.out.println("2)Stampa Edifici");
					System.out.println("3)Stampa Utenti");
					System.out.println("4)Stampa Prenotazioni");
					System.out.println("0)Menu principale");

					int liste = Integer.parseInt(input.nextLine());

					liste: switch (liste) {
					case 1:
						posServ.stampaLista(posServ.getAll());
						break;
					case 2:
						edServ.stampaLista(edServ.getAll());
						break;
					case 3:
						utServ.stampaLista(utServ.getAll());
						break;
					case 4:
						prenServ.stampaLista(prenServ.getAll());
						break;
					case 0:
						break liste;
					}
					break;

				case 0:
					System.out.println("PROGRAMMA TERMINATO CON SUCCESSO!");
					break menu;

				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while (scelta != 0);

		input.close();
	}

	public void cercaPost() {
		System.out.println("Inserisci il nome della città:");
		String citta = input.nextLine();

		System.out.println("Quale tipologia di postazione vuoi cercare:");
		System.out.println("1)Openspace");
		System.out.println("2)Privata");
		System.out.println("3)Sala Riunioni");
		int tipo = Integer.parseInt(input.nextLine());

		switch (tipo) {
		case 1:
			posServ.stampaLista(posServ.cercaPostazione("OPENSPACE", citta));
			break;
		case 2:
			posServ.stampaLista(posServ.cercaPostazione("PRIVATO", citta));
			break;
		case 3:
			posServ.stampaLista(posServ.cercaPostazione("SALA_RIUNIONI", citta));
			break;
		default:
			System.out.println("Selezione non valida");
			break;
		}
	}

	public Edificio inserisciEdificio(String n, String i, String c) {
		Edificio ed = (Edificio) ctx.getBean("edificio", n, i, c);
		edServ.insert(ed);
		return ed;
	}

	public Utente inserisciUtente(String u, String n, String c, String e) {
		Utente u1 = (Utente) ctx.getBean("utente", u, n, c, e);
		utServ.insert(u1);
		return u1;
	}

	public Postazione inserisciPostazione(String d, TipoPostazione t, int n, Edificio e) {
		Postazione p = (Postazione) ctx.getBean("postazione", d, t, n, e);
		posServ.insert(p);
		return p;
	}

	public Prenotazione inserisciPrenotazione(Utente u, Postazione p, LocalDate d) {
		Prenotazione pos = (Prenotazione) ctx.getBean("prenotazione", u, p, d);
		prenServ.insert(pos);
		return pos;
	}

	public void popolaDB() {

		Edificio ed1 = inserisciEdificio("A", "Via Roma,65", "Ischia-Napoli");
		Edificio ed2 = inserisciEdificio("B", "Via Pera,8", "Roma");
		Edificio ed3 = inserisciEdificio("C", "Via Melone,15", "Milano");

		Utente u1 = inserisciUtente("Giorgio", "Marco", "Mattera", "giorgio@mattera.it");
		Utente u2 = inserisciUtente("Manfredi", "Luca", "simonetti", "manfredi@simonetti.it");
		Utente u3 = inserisciUtente("Mariana", "Maria", "Bianchi", "mariana@bianchi.it");

		Postazione p1 = inserisciPostazione("Postazione Open", TipoPostazione.OPENSPACE, 20, ed1);
		Postazione p2 = inserisciPostazione("Sala Riunioni A", TipoPostazione.SALA_RIUNIONI, 10, ed1);
		Postazione p3 = inserisciPostazione("Sala Riunioni B", TipoPostazione.SALA_RIUNIONI, 12, ed1);
		Postazione p4 = inserisciPostazione("Postazione Singola", TipoPostazione.PRIVATO, 1, ed2);
		Postazione p5 = inserisciPostazione("Postazione Doppia", TipoPostazione.PRIVATO, 2, ed2);
		Postazione p6 = inserisciPostazione("Postazione Open", TipoPostazione.OPENSPACE, 20, ed2);
		Postazione p7 = inserisciPostazione("Postazione Open", TipoPostazione.OPENSPACE, 30, ed3);
		Postazione p8 = inserisciPostazione("Sala Riunioni X", TipoPostazione.SALA_RIUNIONI, 8, ed3);
		Postazione p9 = inserisciPostazione("Postazione Quadrupla", TipoPostazione.PRIVATO, 4, ed3);

		inserisciPrenotazione(u1, p1, LocalDate.parse("2023-04-03"));
		inserisciPrenotazione(u1, p4, LocalDate.parse("2023-05-03"));
	}

}
