package models;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.MongoAccess;

public class Destinataire extends Commun implements Enregistrable {
    
	private String patronyme;
	private String prenom;
	private String fonction;
	private String societe;

	@Override
	public void save() {
		MongoAccess.save("destinataire",this);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setForm(VBox form) {
		
		this.setNom(((ComboBox<String>) 
                ((HBox) form.getChildren().get(0))
                .getChildren().get(1))
              .editorProperty().get().getText());
		
		this.patronyme = ((TextField) 
                ((HBox) form.getChildren().get(1))
                .getChildren().get(1))
              .getText();
		
		this.prenom = ((TextField) 
                ((HBox) form.getChildren().get(2))
                .getChildren().get(1))
              .getText();
		
		this.fonction = ((TextField) 
                ((HBox) form.getChildren().get(3))
                .getChildren().get(1))
              .getText();
		
		this.societe = ((TextField) 
                ((HBox) form.getChildren().get(4))
                .getChildren().get(1))
              .getText();

	    this.setCommentaire(((TextArea) 
                        ((HBox) form.getChildren().get(5))
                        .getChildren().get(1))
                      .getText());
		
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public String getSociete() {
		return societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}

	public String getPatronyme() {
		return patronyme;
	}
	
	public void setPatronyme(String patronyme) {
		this.patronyme = patronyme;
	}
    
	@Override
	public String getNom() {
		return patronyme != null ? patronyme : societe != null ? societe : prenom != null ? prenom : "sans nom";
	}

}
