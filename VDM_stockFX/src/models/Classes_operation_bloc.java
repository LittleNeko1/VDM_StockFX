package models;

import controllers.*;

/**
 * Enumaration qui permet de lister et de lier les classes des controlleurs à celles des modèles.
 */
public enum Classes_operation_bloc {
	
	NOUVELLE("nouvelle", Operation_nouvelle_controller.class, Operation.class),
	DERNIERES("dernières", Operation_dernieres_controller.class, null);
	
	private String nom;
	private Class<?> classe;
	private Class<Enregistrable> operation;
	
	
	/**
	 * Constructeur de l'énumération.
	 * @param nom nom du bloc
	 * @param classe la classe du controleur
	 * @param operation la classe du modèle lié au controleur
	 */
	Classes_operation_bloc(String nom, Class<?> classe, Class<?> operation){
		
		this.nom = nom;
		this.classe = classe;
		this.operation = (Class<Enregistrable>) operation;
	}

	/**
	 * Retourne e nom du bloc
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
	public Class<Enregistrable> getOperation() {
		return operation;
	}

	/**
	 * Modifie la classe du modèle lié au controleur
	 * @param operation la classe du modèle lié au controleur
	 */
	public void setOperation(Class<Enregistrable> operation) {
		this.operation = operation;
	}
	
	

}
