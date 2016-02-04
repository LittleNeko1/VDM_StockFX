package models;

import utils.MongoAccess;

public class Expediteur implements Enregistrable {
	
	private String nom;
	

	public Expediteur(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public void save() {
		MongoAccess.save("expediteur", this);
		
	}	

}
