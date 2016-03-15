package utils;

import application.VDM_stock_GUI_controller;
import controllers.Ajout_materiel_controller;
import controllers.Centre_ajout_controller;
import controllers.Centre_operation_controller;
import controllers.Operation_nouvelle_controller;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Messages {
	private static Stage primary;
	private static Scene scene;
	private static Operation_nouvelle_controller onc;
	private static Centre_operation_controller coc;
	private static Centre_ajout_controller cac;
	private static Ajout_materiel_controller amc;
	private static VDM_stock_GUI_controller vsgc;
	
	private static String lastExpediteur; 
	private static String lastDestinataire; 
	private static String lastComplement; 
	
	private static boolean valeur_connue;
	
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
		
		System.out.println("scene = " + scene);
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		Messages.scene = scene;
	}

	public static Operation_nouvelle_controller getOnc() {
		return onc;
	}

	public static void setOnc(Operation_nouvelle_controller onc) {
		Messages.onc = onc;
	}

	public static Centre_operation_controller getCoc() {
		return coc;
	}

	public static void setCoc(Centre_operation_controller coc) {
		Messages.coc = coc;
	}

	public static VDM_stock_GUI_controller getVsgc() {
		return vsgc;
	}

	public static void setVsgc(VDM_stock_GUI_controller vsgc) {
		Messages.vsgc = vsgc;
	}

	public static boolean isValeur_connue() {
		return valeur_connue;
	}

	public static void setValeur_connue(boolean valeur_connue) {
		Messages.valeur_connue = valeur_connue;
	}

	public static Centre_ajout_controller getCac() {
		return cac;
	}

	public static void setCac(Centre_ajout_controller cac) {
		Messages.cac = cac;
	}

	public static Ajout_materiel_controller getAmc() {
		return amc;
	}

	public static void setAmc(Ajout_materiel_controller amc) {
		Messages.amc = amc;
	}

	public static String getLastExpediteur() {
		return lastExpediteur;
	}

	public static void setLastExpediteur(String lastExpediteur) {
		Messages.lastExpediteur = lastExpediteur;
	}

	public static String getLastDestinataire() {
		return lastDestinataire;
	}

	public static void setLastDestinataire(String lastDestinataire) {
		Messages.lastDestinataire = lastDestinataire;
	}

	public static String getLastComplement() {
		return lastComplement;
	}

	public static void setLastComplement(String lastComplement) {
		Messages.lastComplement = lastComplement;
	}

	public static Stage getPrimary() {
		return primary;
	}

	public static void setPrimary(Stage primary) {
		Messages.primary = primary;
	}
	
	

}
