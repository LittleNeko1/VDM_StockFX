package controllers;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TabPane;
import models.Classes_ajout_bloc;
import models.Classes_recherche_bloc;

public class Centre_chercher_controller{
    
	private static boolean aff_flag;
	static VBox form;
	static TabPane tabpane;
	static Tab tab1, tab2, tab3;

	public static HBox init(HBox centre_hbox) {
		
		aff_flag = false;

		centre_hbox.getChildren().clear();
		
		VBox liste = new VBox();
		liste.setSpacing(10);
		
		ToggleGroup tg = new ToggleGroup();
		
		tabpane = new TabPane();
		
		form = new VBox();
		form.setSpacing(25);
		form.setPrefWidth(1350);

		for (Classes_recherche_bloc cl : Classes_recherche_bloc.values()){
			ToggleButton tg1 = new ToggleButton(cl.getNom());
			tg1.setPrefWidth(150);
			tg1.setToggleGroup(tg);

			tg1.setOnAction(a -> {
				Class b;
				try {
					b = cl.getClasse();
					SuperController b_instance = (SuperController) b.newInstance();
					form = b_instance.init(form);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			});
			
			liste.getChildren().add(tg1);
			if (! aff_flag){
				tg1.fire();
				tg1.setSelected(true);
				aff_flag = true;
			}
			else {
				tg1.setSelected(false);
			}
		}
		
		centre_hbox.getChildren().add(liste);
		centre_hbox.getChildren().add(new Separator(Orientation.VERTICAL));
		
		tab1 = new Tab("Fiche", form);
		tab2 = new Tab("Recherche");
		tab3 = new Tab("Statistiques");
		
		tabpane.getTabs().addAll(tab1, tab2, tab3);

		centre_hbox.getChildren().add(tabpane);

		return centre_hbox;
	}

}
