package models;

import org.bson.types.ObjectId;

/**
 * Super classe des modèles.
 */
public class Commun {

	private ObjectId _id;
	private String nom;
	private String commentaire;
    
	/**
	 * @return L'identifiant de d'objet dans la base.
	 */
	public ObjectId get_id() {
		return _id;
	}
    
	/**
	 * Méthode inutilisée, conservée pour la compatibilité.
	 * @param _id L'identifiant de d'objet dans la base
	 */
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
    
	/**
	 * @return le nom affiché de l'objet.
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * @param nom  le nom fourni dans le formulaire.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return les commentaires
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * @param commentaire les commentaires du formulaire.
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

}