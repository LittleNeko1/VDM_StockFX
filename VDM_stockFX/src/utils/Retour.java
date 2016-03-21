package utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Operation;

/**
 * Gestion du materiel considéré comme 'retourné'.
 *  
 */
public class Retour {
	
	private static Operation operation_temp = null;
	
	private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.FRANCE);
	
	/**
	 * Ouvre une fenetre pop-up et attends la confirmation du retour.
	 * <ul>
	 * <li>si oui, enregitrement de la date de retour</li>
	 * <li>si non, abandon sans traitement</li>
	 * </ul>
	 * @param materiel le nom du matériel identifié
	 */
	public static void retour(String materiel){
		
		operation_temp = MongoAccess.requestExistPartiel("operation", "materiel", materiel, "date_operation", "dateRetour").as(Operation.class);
		
		if (operation_temp != null ){
			
			Messages.getPrimary().setAlwaysOnTop(false);

			final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialogVbox.setMaxWidth(Double.MAX_VALUE);
            dialogVbox.setAlignment(Pos.CENTER);
            
            Button nonButton = new Button("Non");
            Button ouiButton = new Button("Oui");
            HBox hbox = new HBox();
            HBox.setHgrow(nonButton, Priority.ALWAYS);
			HBox.setHgrow(ouiButton, Priority.ALWAYS);
			hbox.setMaxWidth(Double.MAX_VALUE);
			hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(100);
            hbox.getChildren().addAll(nonButton, ouiButton);
            
            dialogVbox.getChildren().addAll(new Text("enregistrer le retour du matériel ? : " + materiel), hbox);
            
            Scene dialogScene = new Scene(dialogVbox, 500, 100);
            dialog.setScene(dialogScene);
            
            ouiButton.setOnAction(a-> {
            	operation_temp.setDateRetour(new Date());
            	MongoAccess.save("operation",operation_temp);
            	dialog.close();
            	Messages.getPrimary().setAlwaysOnTop(true);
            });
            nonButton.setOnAction(a-> {
            	dialog.close();
            	Messages.getPrimary().setAlwaysOnTop(true);
            });
            
            dialog.showAndWait();
   
		}
		
	}

}
