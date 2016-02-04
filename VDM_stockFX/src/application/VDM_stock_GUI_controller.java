package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import utils.MongoAccess;

public class VDM_stock_GUI_controller implements Initializable {
	
	// FXML DECLARATIONS
	@FXML
	private Button nouvelle_op_button;
	@FXML
	private Button rechercher_button;
	@FXML
	private Button ajouter_button;
	
	@FXML
	private HBox centre_hbox;
	
	// OTHER DECLARATIONS
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		MongoAccess.connect();
		
		centre_hbox.setSpacing(10);

		ajouter_button.setOnAction(a -> {
			centre_hbox = Centre_ajout_controller.init(centre_hbox);
		});
		
		nouvelle_op_button.setOnAction(a -> {
			centre_hbox = Centre_operation_controller.init(centre_hbox);
		});
		
		rechercher_button.setOnAction(a -> {
			centre_hbox = Centre_chercher_controller.init(centre_hbox);
		});
		
		nouvelle_op_button.fire();
		
		
	}
}
