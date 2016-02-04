package controllers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Ajout_enregistrer_controller{
	
	public static VBox abandon(){
		return null;
		
	}
	
	public static VBox enregistrer(){
		return null;
		
	}
	
	
	public static HBox init(){
        
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
