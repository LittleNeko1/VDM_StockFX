package controllers;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Classes_operation_bloc;

public class Centre_operation_controller{

	private static boolean aff_flag;
	private static VBox form;
	private static HBox centre;

	public static HBox init(HBox centre_hbox) {
		
		centre = centre_hbox;
		
		aff_flag = false;

		centre_hbox.getChildren().clear();
		
		VBox liste = new VBox();
		liste.setSpacing(10);
		
		ToggleGroup tg = new ToggleGroup();
		
		form = new VBox();
		form.setSpacing(25);
		form.setPrefWidth(1350);
		form.getStyleClass().add("centre_vbox");
		
		for (Classes_operation_bloc cl : Classes_operation_bloc.values()){
			ToggleButton tg1 = new ToggleButton(cl.getNom());
			tg1.setPrefWidth(150);
			tg1.setToggleGroup(tg);

			tg1.setOnAction(a -> {
				Class<?> b;
				try {
					b = cl.getClasse();
					SuperController b_instance = (SuperController) b.newInstance();
					form = b_instance.init(form);
					
					if(cl.getNom().equals("nouvelle")){
						form.getChildren().add(Ajout_enregistrer_controller.init(cl.getOperation(), form, b_instance));
					}
					
					
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
		}
		
		centre_hbox.getChildren().add(liste);
		centre_hbox.getChildren().add(new Separator(Orientation.VERTICAL));

		centre_hbox.getChildren().add(form);

		return centre_hbox;
	}

	public static HBox getCentre() {
		return centre;
	}

}
