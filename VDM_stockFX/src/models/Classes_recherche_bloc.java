package models;

import controllers.*;

/**
 * Enumaration qui permet de lister et de lier les classes des controlleurs à celles des modèles.
 */
public enum Classes_recherche_bloc {
	
	EXPEDITEUR("expediteur", Ajout_expediteur_controller.class, Expediteur.class),
	DESTINATAIRE("destinataire", Ajout_destinataire_controller.class, Destinataire.class),
	MATERIEL("matériel", Ajout_materiel_controller.class, Materiel.class),
	COMPLEMENT("complément", Ajout_complement_controller.class, Complement.class);
	
	private String nom;
	private Class<?> classe;
	private Class<Enregistrable> model;
	
	/**
	 * Constructeur de l'énumération.
	 * @param nom nom du bloc
	 * @param classe la classe du controleur
	 * @param model la classe du modèle lié au controleur
	 */
	@SuppressWarnings("unchecked")
	Classes_recherche_bloc(String nom, Class<?> classe, Class<?> model){
		
		this.nom = nom;
		this.classe = classe;
		this.model = (Class<Enregistrable>) model;
	}
    
	/**
	 * Retourne le nom du bloc
	 * @return le nom du bloc
	 */
	public String getNom() {
		return nom;
	}
    
	/** 
	 * Retourne la classe du controleur
	 * @return la classe du controleur
	 */
	public Class<?> getClasse() {
		return classe;
	}
    
	/**
     * Retourne la classe du modèle
	 * @return la classe du modèle
	 */
	public Class<Enregistrable> getModel() {
		return model;
	}

	/**
	 * Modifie la classe du modèle lié au controleur
	 * @param model la classe du modèle lié au controleur
	 */
	public void setModel(Class<Enregistrable> model) {
		this.model = model;
	}
	
}
