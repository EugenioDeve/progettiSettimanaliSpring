package it.eugenio.GestioneIncendi.models;

public class ComunicazioneAllarme {
	
	
	private String url = "http://host/alarm";
	
	
	public void invioAllarme(int id, double lat, double lng, int fumo) {
		System.out.println("-------------ALLARME INCENDIO-------------");
		System.out.println(url + "?=idSonda=" + id + "&lat=" + lat + "&lon=" + lng + "&smokelevel=" + fumo);
		System.out.println("I soccorsi sono stati allertati!");
	}
	
}
