package controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Ajout_bloc;

public class Ajout_complement_controller implements Ajout_bloc{
	
public VBox init(VBox form){
		
		form.getChildren().clear();
		
		Map<String, String> champs_textField = new LinkedHashMap<String, String>();
		
		champs_textField.put("nom", "Nom affiché : ");
		
		
		for (String s : champs_textField.keySet()){
			
			HBox h1 = new HBox();
			h1.setMaxWidth(Double.MAX_VALUE);
			h1.setAlignment(Pos.CENTER_LEFT);
			h1.setSpacing(5);
			
			
			Label l1 = new Label(champs_textField.get(s));
			l1.maxWidth(105);
			l1.setMaxSize(105.0, Control.USE_PREF_SIZE);
			h1.setHgrow(l1, Priority.ALWAYS);
			
			//l1.setStyle("-fx-background-color: rgb(160,160,160)");
			
			TextField tf1 = new TextField();
			tf1.prefWidth(400);
			tf1.setMaxSize(400.0, Control.USE_PREF_SIZE);
			h1.setHgrow(tf1, Priority.ALWAYS);
			
			h1.getChildren().add(l1);
			h1.getChildren().add(tf1);
			form.getChildren().add(h1);	
		}
		
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

}
