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
 * Modèle lié au formulaire {@link controllers.Ajout_destinataire_controller}
 * @see Classes_ajout_bloc
 */
public class Destinataire extends Commun implements Enregistrable {
    
	private String patronyme;
	private String prenom;
	private String fonction;
	private String societe;
	private boolean usb3;
	
	private ArrayList<String> wrong_tags;

	@Override
	public void save() {
		
        wrong_tags = Messages.getAdc().getTags();
		System.out.println(wrong_tags);
		
		MongoAccess.save("destinataire",this);
		Centre_ajout_controller.fire1();	
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
                        ((HBox) form.getChildren().get(6))
                        .getChildren().get(1))
                      .getText());
		
	}

	public String getPrenom() {
		return prenom == null || prenom.trim().equals("" ) ? null : prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getFonction() {
		return fonction == null || fonction.trim().equals("" ) ? null : fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public String getSociete() {
		return societe == null || societe.trim().equals("") ? null : societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}

	public String getPatronyme() {
		return patronyme == null || patronyme.trim().equals("") ? null : patronyme ;
	}
	
	public void setPatronyme(String patronyme) {
		this.patronyme = patronyme;
	}
    
	@Override
	public String getNom() {
		return getPatronyme() != null ? patronyme :
				 getSociete() != null ? societe :
				  getPrenom() != null ? prenom : "";
	}
	
	public ArrayList<String> getTags() {
		return wrong_tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.wrong_tags = tags;
	}

	public boolean isUsb3() {
		return usb3;
	}

	public void setUsb3(boolean usb3) {
		this.usb3 = usb3;
	}
}
