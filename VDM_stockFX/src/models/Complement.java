package models;

import controllers.Centre_ajout_controller;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.MongoAccess;

public class Complement extends Commun implements Enregistrable {
	
	@Override
	public void save() {
		MongoAccess.save("complement",this);
		Centre_ajout_controller.fire3();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setForm(VBox form) {
		
		this.setNom(((ComboBox<String>) 
                ((HBox) form.getChildren().get(0))
                .getChildren().get(1))
              .editorProperty().get().getText());
		


	    this.setCommentaire(((TextArea) 
                        ((HBox) form.getChildren().get(1))
                        .getChildren().get(1))
                      .getText());
		
	}
}
