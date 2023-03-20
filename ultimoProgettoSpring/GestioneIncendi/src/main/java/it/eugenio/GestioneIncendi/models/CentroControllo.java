package it.eugenio.GestioneIncendi.models;

import it.eugenio.GestioneIncendi.interfaces.Observer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CentroControllo implements Observer {
	
	private ComunicazioneAllarme allarme = new ComunicazioneAllarme();
	private Sonda sonda;
		
	@Override
	public void update(Sonda s) {
		this.sonda = s;
		controllo();
	}
	
	//Effettua un secondo controllo sul livello di fumo per evitare "Falsi allarmi"
	public void controllo() {
		if (sonda.getLivelloFumo() > sonda.getLivelloCritico()) {
			allarme.invioAllarme(sonda.getId(), sonda.getLatitudine(), sonda.getLongitudine(), sonda.getLivelloFumo());
		} else {
			System.out.println("Si è trattato di un falso allarme! il livello di fumo della sonda " + sonda.getId() + " è: " + sonda.getLivelloFumo());
		}
	}

}
