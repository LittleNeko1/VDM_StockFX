package models;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Messages;
import utils.MongoAccess;

/**
 * Modèle lié aux formulaires {@link controllers.Operation_nouvelle_controller} et {@link controllers.Operation_dernieres_controller}
 * @see Classes_operation_bloc
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation extends Commun implements Enregistrable {
	
	private String expediteur;
	private String materiel;
	private String destinataire;
	private String complement;
	private Date date_operation;
	private Date dateRetour;
	
	@JsonIgnore
	private Operation operation_temp = null;
	
	@JsonIgnore
	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.FRANCE);
	
	@Override
	public void save() {
		
		Messages.setLastExpediteur(this.expediteur);
		Messages.setLastDestinataire(this.destinataire);
		Messages.setLastComplement(this.complement);
		
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

	public String getDate_operation() {
		if (date_operation != null){
			return df.format(date_operation);
		}
		else {
			return "";
		}
	}

	public void setDate_operation(Date date_operation) {
		this.date_operation = date_operation;
	}

	/**
	 * Retourne une chaine de caractères contenant la date ou une chaine vide ("") si le matériel n'est pas encore revenu.
	 * @return une chaine de caractères contenant la date ou une chaine vide ("") si le matériel n'est pas encore revenu.
	 * 
	 */
	public String getDateRetour() {
		
		if (dateRetour != null){
			return df.format(dateRetour);
		}
		else {
			return "";
		}	
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}
	
	

}
