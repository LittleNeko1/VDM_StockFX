package utils;

import application.VDM_stock_GUI_controller;
import controllers.Ajout_destinataire_controller;
import controllers.Ajout_materiel_controller;
import controllers.Centre_ajout_controller;
import controllers.Centre_operation_controller;
import controllers.Operation_nouvelle_controller;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Collection de propriétés statiques pour référencer les instances d'objets à accéder.
 */
public class Messages {
	private static Stage primary;
	private static Scene scene;
	private static Operation_nouvelle_controller onc;
	private static Centre_operation_controller coc;
	private static Centre_ajout_controller cac;
	private static Ajout_materiel_controller amc;
	private static VDM_stock_GUI_controller vsgc;
	
	private static Ajout_destinataire_controller adc;
	private static VBox destinataire_form;
	
	private static String lastExpediteur; 
	private static String lastDestinataire; 
	private static String lastComplement; 
	
	private static boolean valeur_connue;
	
	/**
	 * Constructeur qui initialise les valeurs.
	 */
	public Messages(){
		primary = new Stage();
		scene = new Scene(new Pane());
		onc = new Operation_nouvelle_controller();
		coc = new Centre_operation_controller();
		cac = new Centre_ajout_controller();
		amc = new Ajout_materiel_controller();
		vsgc = new VDM_stock_GUI_controller();
		valeur_connue = false;
		
		lastExpediteur = "";
		lastDestinataire = "";
		lastComplement = "";
		
		adc = new Ajout_destinataire_controller();
		destinataire_form = new VBox();
		
		System.out.println("scene = " + scene);
	}

	/**
	 * Retourne l'instance de {@link javafx.scene.Scene} définie dans {@link application.Main}
	 * @return l'instance de {@link javafx.scene.Scene} définie dans {@link application.Main}
	 */
	public static Scene getScene() {
		return scene;
	}

	/**
	 * Modifie la scene définie dans {@link application.Main}
	 * @param scene la scene définie dans {@link application.Main}
	 */
	public static void setScene(Scene scene) {
		Messages.scene = scene;
	}

	/**
	 * Retourne l'instance du controleur définie dans {@link controllers.Operation_nouvelle_controller}
	 * @return l'instance du controleur définie dans {@link controllers.Operation_nouvelle_controller}
	 */
	public static Operation_nouvelle_controller getOnc() {
		return onc;
	}

	/**
	 * Modifie l'instance du controleur définie dans {@link controllers.Operation_nouvelle_controller}
	 * @param onc l'instance du controleur définie dans {@link controllers.Operation_nouvelle_controller}
	 */
	public static void setOnc(Operation_nouvelle_controller onc) {
		Messages.onc = onc;
	}

	/**
	 * Retourne l'instance du controleur définie dans {@link controllers.Centre_operation_controller}
	 * @return l'instance du controleur définie dans {@link controllers.Centre_operation_controller}
	 */
	public static Centre_operation_controller getCoc() {
		return coc;
	}

	/**
	 * Modifie l'instance du controleur définie dans {@link controllers.Centre_operation_controller}
	 * @param coc l'instance du controleur définie dans {@link controllers.Centre_operation_controller}
	 */
	public static void setCoc(Centre_operation_controller coc) {
		Messages.coc = coc;
	}

	/**
	 * Retourne l'instance du controleur définie dans {@link application.VDM_stock_GUI_controller}
	 * @return l'instance du controleur définie dans {@link application.VDM_stock_GUI_controller}
	 */
	public static VDM_stock_GUI_controller getVsgc() {
		return vsgc;
	}

	/**
	 * Modifie l'instance du controleur définie dans {@link application.VDM_stock_GUI_controller}
	 * @param vsgc l'instance du controleur définie dans {@link application.VDM_stock_GUI_controller}
	 */
	public static void setVsgc(VDM_stock_GUI_controller vsgc) {
		Messages.vsgc = vsgc;
	}

	/**
	 * Retourne true si le nom lu par l'application existe déjà dans la base de données, false sinon.
	 * @return true si le nom lu par l'application existe déjà dans la base de données, false sinon.
	 */
	public static boolean isValeur_connue() {
		return valeur_connue;
	}

	/**
	 * Modifie le paramètre : true si le nom lu par l'application existe déjà dans la base de données, false sinon.
	 * @param valeur_connue true si le nom lu par l'application existe déjà dans la base de données, false sinon.
	 */
	public static void setValeur_connue(boolean valeur_connue) {
		Messages.valeur_connue = valeur_connue;
	}

	/**
	 * Retourne l'instance du controleur définie dans {@link controllers.Centre_ajout_controller}
	 * @return l'instance du controleur définie dans {@link controllers.Centre_ajout_controller}
	 */
	public static Centre_ajout_controller getCac() {
		return cac;
	}

	/**
	 * Modifie l'instance du controleur définie dans {@link controllers.Centre_ajout_controller}
	 * @param cac l'instance du controleur définie dans {@link controllers.Centre_ajout_controller}
	 */
	public static void setCac(Centre_ajout_controller cac) {
		Messages.cac = cac;
	}

	/**
	 * Retourne l'instance du controleur définie dans {@link controllers.Ajout_materiel_controller}
	 * @return l'instance du controleur définie dans {@link controllers.Ajout_materiel_controller}
	 */
	public static Ajout_materiel_controller getAmc() {
		return amc;
	}

	/**
	 * Modifie l'instance du controleur définie dans {@link controllers.Ajout_materiel_controller}
	 * @param amc l'instance du controleur définie dans {@link controllers.Ajout_materiel_controller}
	 */
	public static void setAmc(Ajout_materiel_controller amc) {
		Messages.amc = amc;
	}

	/**
	 * Retourne le nom du dernier expéditeur selectionné
	 * @return le nom du dernier expéditeur selectionné
	 */
	public static String getLastExpediteur() {
		return lastExpediteur;
	}

	/**
	 * Modifie le nom du dernier expéditeur selectionné
	 * @param lastExpediteur le nom du dernier expéditeur selectionné
	 */
	public static void setLastExpediteur(String lastExpediteur) {
		Messages.lastExpediteur = lastExpediteur;
	}

	/**
	 * Retourne le nom du dernier destinataire selectionné
	 * @return le nom du dernier destinataire selectionné
	 */
	public static String getLastDestinataire() {
		return lastDestinataire;
	}

	/**
	 * Modifie le nom du dernier destinataire selectionné
	 * @param lastDestinataire le nom du dernier destinataire selectionné
	 */
	public static void setLastDestinataire(String lastDestinataire) {
		Messages.lastDestinataire = lastDestinataire;
	}

	/**
	 * Retourne le nom du dernier complément selectionné
	 * @return le nom du dernier complément selectionné
	 */
	public static String getLastComplement() {
		return lastComplement;
	}

	/**
	 * Modifie le nom du dernier complément selectionné
	 * @param lastComplement le nom du dernier complément selectionné
	 */
	public static void setLastComplement(String lastComplement) {
		Messages.lastComplement = lastComplement;
	}

	/**
	 * Retourne l'instance de {@link javafx.stage.Stage} définie dans {@link application.Main}
	 * @return l'instance de {@link javafx.stage.Stage} définie dans {@link application.Main}
	 */
	public static Stage getPrimary() {
		return primary;
	}

	/**
	 * Modifie l'instance de {@link javafx.stage.Stage} définie dans {@link application.Main}
	 * @param primary l'instance de {@link javafx.stage.Stage} définie dans {@link application.Main}
	 */
	public static void setPrimary(Stage primary) {
		Messages.primary = primary;
	}

	/**
	 * Retourne l'instance du controleur définie dans {@link controllers.Ajout_destinataire_controller}
	 * @return l'instance du controleur définie dans {@link controllers.Ajout_destinataire_controller}
	 */
	public static Ajout_destinataire_controller getAdc() {
		return adc;
	}

	/**
	 * Retourne l'instance du controleur définie dans {@link controllers.Ajout_destinataire_controller}
	 * @param adc l'instance du controleur définie dans {@link controllers.Ajout_destinataire_controller}
	 */
	public static void setAdc(Ajout_destinataire_controller adc) {
		Messages.adc = adc;
	}

	/**
	 * Retourne l'instance du conteneur de l'affichage du destinataire
	 * @return l'instance du conteneur de l'affichage du destinataire
	 */
	public static VBox getDestinataire_form() {
		return destinataire_form;
	}

	/**
	 * Modifie l'instance du conteneur de l'affichage du destinataire
	 * @param destinataire_form l'instance du conteneur de l'affichage du destinataire
	 */
	public static void setDestinataire_form(VBox destinataire_form) {
		Messages.destinataire_form = destinataire_form;
	}
}
