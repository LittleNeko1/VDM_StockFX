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
import utils.MongoAccess;

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

		if (materiel != null){
			operation_temp = MongoAccess.requestExistPartiel("operation", "materiel", materiel, "date_operation", "dateRetour").as(Operation.class);
		}
	
		if (operation_temp != null ){

			final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialogVbox.setMaxWidth(Double.MAX_VALUE);
            dialogVbox.setAlignment(Pos.CENTER);
            
            Button nonButton = new Button("Non");
            Button ouiButton = new Button("Oui");
            HBox hbox = new HBox();
            HBox.setHgrow(nonButton, Priority.ALWAYS);
			HBox.setHgrow(ouiButton, Priority.ALWAYS);
			hbox.setMaxWidth(Double.MAX_VALUE);
			hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(100);
            hbox.getChildren().addAll(nonButton, ouiButton);
            
            dialogVbox.getChildren().addAll(new Text("enregistrer le retour du matériel ? : " + materiel), hbox);
            
            Scene dialogScene = new Scene(dialogVbox, 500, 100);
            dialog.setScene(dialogScene);
            
            ouiButton.setOnAction(a-> {
            	operation_temp.setDateRetour(new Date());
            	MongoAccess.save("operation",operation_temp);
            	dialog.close();
            });
            nonButton.setOnAction(a-> {
            	dialog.close();
            });
            
            dialog.showAndWait();
            
            
            
            
		}
		else {
			MongoAccess.save("operation",this);
		}
		
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
