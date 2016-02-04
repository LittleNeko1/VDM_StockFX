package controllers;

import application.VDM_stock_GUI_controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Enregistrable;

public class Ajout_enregistrer_controller{
	
	private static String type;
	
	public static void abandon(){
		Centre_operation_controller.init(Centre_operation_controller.getCentre());
		
	}
	
	public static void enregistrer(){
		
        Enregistrable.save();
		
	}
	
	
	public static HBox init(String type_){
		
		type = type_;
        
		HBox hb = new HBox();
		hb.setSpacing(50);
		hb.setAlignment(Pos.CENTER);
		
		Button abandon = new Button("Abandonner");
		abandon.setOnAction(a -> abandon());
		hb.getChildren().add(abandon);
		
		Button enregistrer = new Button("Enregistrer");
		enregistrer.setOnAction(a -> enregistrer());
		hb.getChildren().add(enregistrer);
		
		return hb;
	
	}

}
