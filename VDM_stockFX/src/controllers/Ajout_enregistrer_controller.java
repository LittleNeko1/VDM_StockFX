package controllers;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Ajout_bloc;
import models.Bloc;
import models.Enregistrable;
import models.Expediteur;
import models.Materiel;

public class Ajout_enregistrer_controller{

	private static Class<Enregistrable> classe_attendue;
	private static VBox form;
	private static Object controller;
	
	public static void abandon(){
		Centre_operation_controller.init(Centre_operation_controller.getCentre());
		
	}
	
	public static void enregistrer(){
		
		switch (classe_attendue.getName()) {
		case "models.Expediteur" : 
			
			Expediteur expediteur = ((Ajout_expediteur_controller) controller).getExpediteur();
			expediteur.setForm(form);

			if (expediteur.isUpdate()){
				expediteur.update();
			}
			else {
				expediteur.save();
			}
			
			break;
			
      case "models.Materiel" : 
			
			Materiel materiel = ((Ajout_materiel_controller) controller).getMateriel();
			materiel.setForm(form);

			if (materiel.isUpdate()){
				materiel.update();
			}
			else {
				materiel.save();
			}
			
			break;

		default:
			System.out.println(classe_attendue.getName());
			break;
		}
		
		
		
        
		
	}
	
	
	public static HBox init(Class<Enregistrable> classe_attendue_, VBox form_, Bloc controller_){
		
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
