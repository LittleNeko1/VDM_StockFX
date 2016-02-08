package controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Enregistrable;

public class Operation_nouvelle_controller implements SuperController{
	
	
public VBox init(VBox form){
		
		form.getChildren().clear();
		
		HBox h1 = new HBox();
		h1.setSpacing(15);
		h1.setMaxWidth(Double.MAX_VALUE);
		
		Map<String, String> choiceboxes = new LinkedHashMap<String, String>();
		
		choiceboxes.put("expediteur", "Expéditeur : ");
		choiceboxes.put("destinataire", "Destinataire : ");
		choiceboxes.put("materiel", "Matériel : ");
		choiceboxes.put("complement", "Complément : ");
		
		for (String s : choiceboxes.keySet()){
			
			VBox v1 = new VBox();
			v1.setMaxWidth(Double.MAX_VALUE);
			v1.setAlignment(Pos.CENTER);
			v1.setSpacing(5);
			
			
			Label l1 = new Label(choiceboxes.get(s));
			l1.maxWidth(75);
			l1.setMaxSize(120.0, Control.USE_PREF_SIZE);
			
			//l1.setStyle("-fx-background-color: rgb(160,160,160)");
			
			ComboBox<String> cb1 = new ComboBox<String>();
			cb1.prefWidth(400);
			cb1.setMaxSize(400.0, Control.USE_PREF_SIZE);
			cb1.setEditable(true);
			
			
			v1.getChildren().add(l1);
			v1.getChildren().add(cb1);
			h1.getChildren().add(v1);	
			
			HBox.setHgrow(cb1, Priority.ALWAYS);
			HBox.setHgrow(l1, Priority.ALWAYS);
			HBox.setHgrow(v1, Priority.ALWAYS);
		}
		
		form.getChildren().add(h1);	
		
		HBox h5 = new HBox();
		h5.setSpacing(20);
		h5.setPadding(new Insets(30, 0, 0, 0));
		Label l5 = new Label("Commentaire : ");
		TextArea ta5 = new TextArea();
		h5.getChildren().add(l5);
		h5.getChildren().add(ta5);

		form.getChildren().add(h5);	
		
		return form;
	}

@Override
public Enregistrable getEnregistrable() {
	// TODO Auto-generated method stub
	return null;
}

}
