package controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Classes_ajout_bloc;
import utils.Messages;

/**
 * Controle la zone d'ajout.
 * 
 */
public class Centre_ajout_controller{

	private static VBox form;
	
	public static List<ToggleButton> list_toggles;
	
	/**
	 * Initialise la zone graphique en complétant le conteneur passé en argument avec le contenu spécifique. 
	 * <ul>
	 * <li>la liste des 4 boutons à gauche</li>
	 * <li>le conteneur du formulaire à afficher</li>
	 * <li>les boutons enregistrer etc ...</li>
	 * </ul>
	 * @param centre_hbox conteneur à compléter
	 * @return le conteneur passé en argument completé avec le contenu spécifique.
	 */
	public static HBox init(HBox centre_hbox) {
		
		list_toggles = new ArrayList<>();

		centre_hbox.getChildren().clear();
		
		VBox liste = new VBox();
		liste.setSpacing(10);
		
		ToggleGroup tg = new ToggleGroup();
		
		form = new VBox();
		form.setSpacing(20);
		form.setPrefWidth(1350);
		
		for (Classes_ajout_bloc cl : Classes_ajout_bloc.values()){
			ToggleButton tg1 = new ToggleButton(cl.getNom());
			tg1.setPrefWidth(150);
			tg1.setToggleGroup(tg);

			tg1.setOnAction(a -> {
				Class b;
				try {
					b = cl.getClasse();
					SuperController b_instance = (SuperController) b.newInstance();
					form = b_instance.init(form);
					form.getChildren().add(Ajout_enregistrer_controller.init(cl.getModel(), form, b_instance));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			});
			
			liste.getChildren().add(tg1);
			
			list_toggles.add(tg1);
		}
		
		centre_hbox.getChildren().add(liste);
		centre_hbox.getChildren().add(new Separator(Orientation.VERTICAL));

		centre_hbox.getChildren().add(form);
		
		list_toggles.get(0).fire();
		list_toggles.get(0).setSelected(true);

		return centre_hbox;
	}
	
	/**
	 * Action sur le bouton n°0 : expéditeur
	 */
	public static void fire0(){
		list_toggles.get(0).fire();
		list_toggles.get(0).setSelected(true);	
	}
	
	/**
	 * Action sur le bouton n°1 : destinataire
	 */
	public static void fire1(){
		list_toggles.get(1).fire();
		list_toggles.get(1).setSelected(true);	
	}
	
	/**
	 * Action sur le bouton n°2 : matériel
	 */
	public static void fire2(){
		list_toggles.get(2).fire();
		list_toggles.get(2).setSelected(true);	
	}
	
	/**
	 * Action sur le bouton n°3 : complément
	 */
	public static void fire3(){
		list_toggles.get(3).fire();
		list_toggles.get(3).setSelected(true);	
	}

}
