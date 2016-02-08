package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	private VBox form;
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
    
	@Override
	public void save() {
		
		MongoAccess.save("expediteur",this);
		
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
		
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
   
	@Override
	public boolean isUpdate() {
		
		System.out.println("isUpdate() -> "  + this.get_id());
		
		return this.get_id() != null;
	}

}
