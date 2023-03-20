package it.eugenio.GestioneIncendi.models;

import java.util.ArrayList;
import java.util.List;

import it.eugenio.GestioneIncendi.interfaces.Observer;
import it.eugenio.GestioneIncendi.interfaces.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sonda implements Subject {
	
	private int id;
	private double longitudine;
	private double latitudine;
	private int livelloFumo = 0;
	private int livelloCritico = 5;
	private List<Observer> observers = new ArrayList<>();
	
	public Sonda(int id, double longitudine, double latitudine) {
		this.id = id;
		this.longitudine = longitudine;
		this.latitudine = latitudine;
	}
	
	
	public void rilevaFumo(int livelloFumo) {
		this.livelloFumo = livelloFumo;
		
		if(livelloFumo > livelloCritico) {
			notifyObservers();
		}
	}


	@Override
	public void registerObserver(Observer o) {
		
		observers.add(o);
		
	}


	@Override
	public void unregisterObserver(Observer o) {
		
		observers.remove(observers.indexOf(o));
		
	}


	@Override
	public void notifyObservers() {
		
		for(int i = 0; i < observers.size(); i++) {
			observers.get(i).update(this);
		}
		
	}
	
	
	
	
	
	
	
}
