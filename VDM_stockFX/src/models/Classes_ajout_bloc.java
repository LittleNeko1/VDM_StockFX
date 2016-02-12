package models;

import controllers.*;

public enum Classes_ajout_bloc {
	
	EXPEDITEUR("expediteur", Ajout_expediteur_controller.class, Expediteur.class),
	DESTINATAIRE("destinataire", Ajout_destinataire_controller.class, Destinataire.class),
	MATERIEL("matériel", Ajout_materiel_controller.class, Materiel.class),
	COMPLEMENT("complément", Ajout_complement_controller.class, Complement.class);
	
	private String nom;
	private Class<?> classe;
	private Class<Enregistrable> model;
	
	@SuppressWarnings("unchecked")
	Classes_ajout_bloc(String nom, Class<?> classe, Class<?> model){
		
		this.nom = nom;
		this.classe = classe;
		this.model = (Class<Enregistrable>) model;
	}

	public String getNom() {
		return nom;
	}

	public Class<?> getClasse() {
		return classe;
	}

	public Class<Enregistrable> getModel() {
		return model;
	}

	public void setModel(Class<Enregistrable> model) {
		this.model = model;
	}
	
}
