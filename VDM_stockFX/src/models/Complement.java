package models;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.MongoAccess;

public class Complement extends Commun implements Enregistrable {

	private String commentaire;

	@Override
	public void save() {
		MongoAccess.save("complement",this);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setForm(VBox form) {
		
		this.nom = ((ComboBox<String>) 
                ((HBox) form.getChildren().get(0))
                .getChildren().get(1))
              .editorProperty().get().getText() ;
		


	    this.commentaire = ((TextArea) 
                        ((HBox) form.getChildren().get(1))
                        .getChildren().get(1))
                      .getText();
		
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	

}
