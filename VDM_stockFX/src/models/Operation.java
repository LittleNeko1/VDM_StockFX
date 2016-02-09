package models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javafx.scene.layout.VBox;
import utils.MongoAccess;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation extends Commun implements Enregistrable {
	
	private Expediteur expediteur;
	private Materiel materiel;
	private Destinataire destinataire;
	private Complement complement;
	private Date date_operation;
	private Date dateRetour;
	
	

	@Override
	public void save() {
		MongoAccess.save("operation",this);
		
	}

	@Override
	public void setForm(VBox form) {
		
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}
}
