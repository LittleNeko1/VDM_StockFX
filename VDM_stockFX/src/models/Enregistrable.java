package models;

import org.bson.types.ObjectId;

import javafx.scene.layout.VBox;

/**
 * Interface exposant les méthodes attendues par les classes enregistrables.
 */
public interface Enregistrable {
	
	
	/**
	 * Réalise l'enregistrement de l'objet dans la base de données.
	 */
	public void save();
    
	/**
	 * Lit les valeurs des champs du formulaire et les affecte aux attibuts de cet objet.
	 * @param form le conteneur contenant les champs à lire.
	 */
	public void setForm(VBox form);
	
	/**
	 * Retourne l'identifiant de l'objet en base de données.
	 * @return l'identifiant de l'objet en base de données.
	 */
	public ObjectId get_id();

}
