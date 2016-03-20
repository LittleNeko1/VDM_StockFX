package models;


import java.util.ArrayList;

import controllers.Centre_ajout_controller;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Messages;
import utils.MongoAccess;

/**
 * Modèle lié au formulaire {@link controllers.Ajout_materiel_controller}
 * @see Classes_ajout_bloc
 */
public class Materiel extends Commun implements Enregistrable {

	private String marque;
	private String modele;
	private String capacite;
	
	private ArrayList<String> tags;

	@Override
	public void save() {
		
		tags = Messages.getAmc().getTags();
		System.out.println(tags);
		
		MongoAccess.save("materiel",this);
		Centre_ajout_controller.fire2();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setForm(VBox form) {
		
		this.setNom(((ComboBox<String>) 
                     ((HBox) form.getChildren().get(0))
                     .getChildren().get(1))
                     .editorProperty().get().getText());
		
		this.marque = ((TextField) 
                ((HBox) form.getChildren().get(1))
                .getChildren().get(1))
                .getText() ;
		
		this.modele = ((TextField) 
                ((HBox) form.getChildren().get(2))
                .getChildren().get(1))
                .getText() ;
		
		this.capacite = ((TextField) 
                ((HBox) form.getChildren().get(3))
                .getChildren().get(1))
                .getText() ;

		this.setCommentaire(((TextArea) 
                             ((HBox) form.getChildren().get(5))
                             .getChildren().get(1))
                             .getText());
		
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public String getCapacite() {
		return capacite;
	}

	public void setCapacite(String capacite) {
		this.capacite = capacite;
	}
    /**
     * Retourne la liste des tags concernant le matériel
     * @see Destinataire#wrong_tags 
     * @return La liste des tags concernant le matériel
     */
	public ArrayList<String> getTags() {
		return tags;
	}

	/**
	 * Modifie la liste des tags concernant le matériel
	 * @param tags la liste des tags cochés dans le formulaire
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
	
    
}
