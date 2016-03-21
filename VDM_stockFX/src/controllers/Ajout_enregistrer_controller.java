package controllers;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Enregistrable;
import models.Expediteur;
import models.Materiel;

/**
 * Controle la zone graphique des boutons "enregistrer", "abandonner" et "éditer"
 * 
 */
public class Ajout_enregistrer_controller{

	private static Class<Enregistrable> classe_attendue;
	private static VBox form;
	private static SuperController controller;
	
	private static Button abandon;
	private static Button enregistrer;
	private static Button editer;
	
	/**
	 * Abandonne sans enregistrement / modification.
	 */
	public static void abandon(){
		Centre_operation_controller.init(Centre_operation_controller.getCentre());
		
	}
	
	/**
	 * Enregistre le nouvel objet, ou l'objet modifié.
	 */
	public static void enregistrer(){
		
		Enregistrable enregistrable =  controller.getEnregistrable();
		
		System.out.println("form : " + form);
		System.out.println("enregistrable : " + enregistrable);
		
		enregistrable.setForm(form);
		enregistrable.save();  
		
		controller.reinit();
		
	}
	
	/**
	 * Débloque l'accès en modification aux champs du formulaire
	 */
	public static void editer(){
        
		controller.unfreeze();
	}
	
	/**
	 * Initialisation des boutons "enregistrer", "abandonner" et "éditer", et de leurs écouteurs
	 * @param classe_attendue_ Type de la classe attendue (pour la conversion des éléments récupérés dans la base mongoDB)
	 * @param form_ instance du formulaire
	 * @param controller_ controleur du formulaire
	 * @return un conteneur contenant les boutons "enregistrer", "abandonner" et "éditer"
	 */
	public static HBox init(Class<Enregistrable> classe_attendue_, VBox form_, SuperController controller_){
		
		classe_attendue = classe_attendue_;
		form = form_;
		controller = controller_;

		HBox hb = new HBox();
		hb.setSpacing(50);
		hb.setAlignment(Pos.CENTER);
		
		abandon = new Button("Abandonner");
		abandon.setOnAction(a -> abandon());
		hb.getChildren().add(abandon);
		
		enregistrer = new Button("Enregistrer");
		enregistrer.setOnAction(a -> enregistrer());
		hb.getChildren().add(enregistrer);
		
		editer = new Button("Editer");
		editer.setOnAction(a -> editer());
		editer.setVisible(false);
		hb.getChildren().add(editer);
		
		
		
		return hb;
	
	}

	/**
	 * Retourne la classe du formulaire
	 * @return la classe du formulaire
	 */
	public static Class<Enregistrable> getClasse_attendue() {
		return classe_attendue;
	}

	/**
	 * Modifie la classe du formulaire
	 * @param classe_attendue la classe du formulaire
	 */
	public static void setClasse_attendue(Class<Enregistrable> classe_attendue) {
		Ajout_enregistrer_controller.classe_attendue = classe_attendue;
	}

	/**
	 * Retourne le bouton "Abandonner"
	 * @return le bouton "Abandonner"
	 */
	public static Button getAbandon() {
		return abandon;
	}

	/**
	 * Modifie le bouton "Abandonner"
	 * @param abandon le bouton "Abandonner"
	 */
	public static void setAbandon(Button abandon) {
		Ajout_enregistrer_controller.abandon = abandon;
	}

	/**
	 * Retourne le bouton "Enregistrer"
	 * @return le bouton "Enregistrer"
	 */
	public static Button getEnregistrer() {
		return enregistrer;
	}

	/**
	 * Modifie le bouton "Enregistrer"
	 * @param enregistrer le bouton "Enregistrer"
	 */
	public static void setEnregistrer(Button enregistrer) {
		Ajout_enregistrer_controller.enregistrer = enregistrer;
	}

	/**
	 * Retourne le bouton "Editer"
	 * @return le bouton "Editer"
	 */
	public static Button getEditer() {
		return editer;
	}

	/**
	 * MModifie le bouton "Editer"
	 * @param editer le bouton "Editer"
	 */
	public static void setEditer(Button editer) {
		Ajout_enregistrer_controller.editer = editer;
	}
}
