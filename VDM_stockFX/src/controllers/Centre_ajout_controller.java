package controllers;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Classes_ajout_bloc;

public class Centre_ajout_controller{
    
	private static boolean aff_flag;
	private static VBox form;

	public static HBox init(HBox centre_hbox) {
		
		aff_flag = false;

		centre_hbox.getChildren().clear();
		
		VBox liste = new VBox();
		liste.setSpacing(10);
		
		ToggleGroup tg = new ToggleGroup();
		
		form = new VBox();
		form.setSpacing(25);
		form.setPrefWidth(800);
		
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

		centre_hbox.getChildren().add(form);

		return centre_hbox;
	}

}
