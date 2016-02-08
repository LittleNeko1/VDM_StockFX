package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.MongoAccess;


public class Materiel extends Commun implements Enregistrable {
	
	private String nom;
	private String commentaire;
	private String marque;
	private String modele;
	private String capacite;
	
	@JsonIgnore
	private VBox form;

	@Override
	public void save() {
		MongoAccess.insert("materiel",this);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setForm(VBox form) {
		
		this.nom = ((ComboBox<String>) 
                     ((HBox) form.getChildren().get(0))
                     .getChildren().get(1))
                     .editorProperty().get().getText() ;
		
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

		this.commentaire = ((TextArea) 
                             ((HBox) form.getChildren().get(4))
                             .getChildren().get(1))
                             .getText();
		
	}

	@Override
	public boolean isUpdate() {
		
		return this.get_id() != null;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
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

	public VBox getForm() {
		return form;
	}

    
}
