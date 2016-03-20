package controllers;

import javafx.scene.layout.VBox;
import models.Enregistrable;

/**
 * Interface exposant les méthodes attendues par tous les controlleurs.
 */
public interface SuperController {
	/**
	 * Fournit l'objet à transmettre pour la sauvegarde dans la base de données.
	 * @return l'objet à enregistrer en base de données.
	 */
	public Enregistrable getEnregistrable();
	
	/**
	 * Peuple le conteneur fournit en parametre et le retourne.
	 * @param form le conteneur (initialisé) à peupler
	 * @return le conteneur VBox complété
	 */
	public VBox init(VBox form);
	
	/**
	 * Recharge la série des éléments graphiques affichés
	 */
	public void reinit();
	
	/**
	 * Réactive la modification des champs affichés.
	 */
	public void unfreeze();

}
