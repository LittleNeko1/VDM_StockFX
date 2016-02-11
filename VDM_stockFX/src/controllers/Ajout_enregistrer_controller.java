package controllers;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Enregistrable;
import models.Expediteur;
import models.Materiel;

public class Ajout_enregistrer_controller{

	private static Class<Enregistrable> classe_attendue;
	private static VBox form;
	private static SuperController controller;
	
	public static void abandon(){
		Centre_operation_controller.init(Centre_operation_controller.getCentre());
		
	}
	
	public static void enregistrer(){
		
		Enregistrable enregistrable =  controller.getEnregistrable();
		
		System.out.println("form : " + form);
		System.out.println("enregistrable : " + enregistrable);
		
		enregistrable.setForm(form);
		enregistrable.save();  
		
		controller.reinit();
		
	}
	
	
	public static HBox init(Class<Enregistrable> classe_attendue_, VBox form_, SuperController controller_){
		
		classe_attendue = classe_attendue_;
		form = form_;
		controller = controller_;

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

	public static Class<Enregistrable> getClasse_attendue() {
		return classe_attendue;
	}

	public static void setClasse_attendue(Class<Enregistrable> classe_attendue) {
		Ajout_enregistrer_controller.classe_attendue = classe_attendue;
	}


}
