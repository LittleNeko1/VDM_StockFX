package models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.MongoAccess;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation extends Commun implements Enregistrable {
	
	private String expediteur;
	private String materiel;
	private String destinataire;
	private String complement;
	private Date date_operation;
	private Date dateRetour;
	
	

	@Override
	public void save() {
		MongoAccess.save("operation",this);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setForm(VBox form) {
		
		this.setExpediteur(((ComboBox<String>) 
				            ((VBox)
                              ((HBox) form.getChildren().get(0))
                              .getChildren().get(0))
				           .getChildren().get(1))
                .editorProperty().get().getText());
		
		this.setMateriel(((ComboBox<String>) 
	            ((VBox)
                  ((HBox) form.getChildren().get(0))
                  .getChildren().get(1))
	           .getChildren().get(1))
             .editorProperty().get().getText());
		
		this.setDestinataire(((ComboBox<String>) 
	            ((VBox)
                  ((HBox) form.getChildren().get(0))
                  .getChildren().get(2))
	           .getChildren().get(1))
             .editorProperty().get().getText());
		
		this.setComplement(((ComboBox<String>) 
	            ((VBox)
                  ((HBox) form.getChildren().get(0))
                  .getChildren().get(3))
	           .getChildren().get(1))
             .editorProperty().get().getText());

		
		this.setCommentaire(((TextArea) 
                ((HBox) form.getChildren().get(1))
                .getChildren().get(1))
                .getText());
		
		this.setDate_operation(new Date());
		
		
		
	}

	@Override
	public String getNom() {
		
		return String.format("%s_%s_%s", expediteur, materiel, date_operation);
	}

	public String getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}

	public String getMateriel() {
		return materiel;
	}

	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}

	public String getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public Date getDate_operation() {
		return date_operation;
	}

	public void setDate_operation(Date date_operation) {
		this.date_operation = date_operation;
	}

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}
	
	

}
