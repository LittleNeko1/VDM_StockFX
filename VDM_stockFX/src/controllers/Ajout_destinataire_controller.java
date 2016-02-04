package controllers;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import models.Ajout_bloc;

public class Ajout_destinataire_controller implements Ajout_bloc{
	
	
	public VBox init(VBox form){
		
        form.getChildren().clear();
		
		form.getChildren().add(new Button("dest"))	;
		return form;
	
	}

}
