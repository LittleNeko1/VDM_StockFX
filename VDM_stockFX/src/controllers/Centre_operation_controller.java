package controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
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
	
	private static List<ToggleButton> list_toggles;

	public static HBox init(HBox centre_hbox) {
		
		System.out.println("centre_operation.init()");
		
		centre = centre_hbox;
		
		list_toggles = new ArrayList<>();

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
			
			list_toggles.add(tg1);
			
			liste.getChildren().add(tg1);
		}
		
		centre_hbox.getChildren().add(liste);
		centre_hbox.getChildren().add(new Separator(Orientation.VERTICAL));

		centre_hbox.getChildren().add(form);
		
		System.out.println("centre_operation_list_toggles.get(0).fire()");
		
		list_toggles.get(0).fire();
		
		System.out.println("centre_operation_list_toggles.get(0).fired()");
		list_toggles.get(0).setSelected(true);
		
		return centre_hbox;
	}

	public static HBox getCentre() {
		return centre;
	}

	public static List<ToggleButton> getList_toggles() {
		return list_toggles;
	}

}
