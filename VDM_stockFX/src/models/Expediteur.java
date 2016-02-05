package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.MongoAccess;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Expediteur extends Commun implements Enregistrable {
	
	private String nom;
	private String commentaire;
	@JsonIgnore
	private static ObservableList<String> autoCompletion;
	@JsonIgnore
	private static Expediteur expediteur;
	@JsonIgnore
	private boolean update = false;
	
	@JsonIgnore
	private VBox form;
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
    
	@Override
	public void save() {
		
		MongoAccess.insert("expediteur",this);
		
	}
	@Override
    public void update() {
		
		MongoAccess.save("expediteur", expediteur);
		
	}
    
	public VBox getForm() {
		return form;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public void setForm(VBox form) {
		this.nom = ((ComboBox<String>) 
                     ((HBox) form.getChildren().get(0))
                     .getChildren().get(1))
                   .editorProperty().get().getText() ;

		this.setCommentaire(((TextArea) 
                             ((HBox) form.getChildren().get(1))
                             .getChildren().get(1))
                           .getText()) ;
		
		if (autoCompletion.contains(this.nom)){
			
			System.out.println("update");
			expediteur.setNom(((ComboBox<String>) 
                    ((HBox) form.getChildren().get(0))
                    .getChildren().get(1))
                  .editorProperty().get().getText()) ;
			expediteur.setCommentaire(((TextArea) 
                    ((HBox) form.getChildren().get(1))
                    .getChildren().get(1))
                  .getText()) ;
		}
		
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public static ObservableList<String> getAutoCompletion() {
		return autoCompletion;
	}

	public static void setAutoCompletion(ObservableList<String> autoCompletion_) {
		autoCompletion = autoCompletion_;
	}

	public static Expediteur getExpediteur() {
		return expediteur;
	}

	public static void setExpediteur(Expediteur expediteur) {
		Expediteur.expediteur = expediteur;
	}
    
	@Override
	public boolean isUpdate() {
		return Expediteur.expediteur != null;
	}

}
