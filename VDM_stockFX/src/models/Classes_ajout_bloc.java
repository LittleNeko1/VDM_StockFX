package models;

import controllers.*;

public enum Classes_ajout_bloc {
	
	EXPEDITEUR("expediteur", Ajout_expediteur_controller.class),
	DESTINATAIRE("destinataire", Ajout_destinataire_controller.class),
	MATERIEL("matériel", Ajout_materiel_controller.class),
	COMPLEMENT("complément", Ajout_complement_controller.class);
	
	private String nom;
	private Class<?> classe;
	
	Classes_ajout_bloc(String nom, Class<?> classe){
		
		this.nom = nom;
		this.classe = classe;
	}

	public String getNom() {
		return nom;
	}

	public Class<?> getClasse() {
		return classe;
	}
}
