package controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Operation_bloc;

public class Operation_dernieres_controller implements Operation_bloc{
	
	
public VBox init(VBox form){
		
		form.getChildren().clear();
		
		HBox h1 = new HBox();
		h1.setSpacing(15);
		h1.setMaxWidth(Double.MAX_VALUE);
		
		Map<String, String> choiceboxes = new LinkedHashMap<String, String>();
		
		choiceboxes.put("expediteur", "Filtrer par expéditeur : ");
		choiceboxes.put("destinataire", "Filtrer par destinataire : ");
		choiceboxes.put("materiel", "Filtrer par matériel : ");
		choiceboxes.put("complement", "Filtrer par complément : ");
		
		for (String s : choiceboxes.keySet()){
			
			VBox v1 = new VBox();
			v1.setMaxWidth(Double.MAX_VALUE);
			v1.setAlignment(Pos.CENTER);
			v1.setSpacing(5);
			
			
			Label l1 = new Label(choiceboxes.get(s));
			l1.maxWidth(200);
			l1.setMaxSize(200.0, Control.USE_PREF_SIZE);
			
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
		
		TableView<?> tv5 = new TableView();


		form.getChildren().add(tv5);	
		
		return form;
	}

}