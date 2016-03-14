package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import utils.ComInput;
import utils.Messages;
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
	
	@FXML
	private BorderPane borderPane;
	
	// OTHER DECLARATIONS
	
	
	StringBuffer sb = new StringBuffer();
	boolean flush = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("vdm_stock_connect()");
		
		MongoAccess.connect();
		
		System.out.println("vdm_stock_initialize()");
		
		
		
		centre_hbox.setSpacing(10);
		//centre_hbox.getStyleClass().add("centre");

		ajouter_button.setOnAction(a -> {
			centre_hbox = Centre_ajout_controller.init(centre_hbox);
		});
		
		nouvelle_op_button.setOnAction(a -> {
			centre_hbox = Centre_operation_controller.init(centre_hbox);
		});
		
		rechercher_button.setOnAction(a -> {
			centre_hbox = Centre_chercher_controller.init(centre_hbox);
		});
		
		System.out.println("vdm_stock_nouvelle_op_button.fire()");
		
		nouvelle_op_button.fire();
		
		System.out.println("vdm_stock_nouvelle_op_button.fired()");
		
		//Centre_operation_controller.getList_toggles().get(0).requestFocus();
		
		//System.out.println("Centre_operation_controller.getList_toggles().get(0).requestFocus()");
		
		
		
		borderPane.setOnKeyPressed(new EventHandler<KeyEvent>() {


			@Override
			public void handle(KeyEvent event) {
				
				if (event.getCode().equals(KeyCode.ENTER)){
					
					
					
					System.out.println("contenu du buffer :" + sb);
					flush = true;
					sb = new StringBuffer();
				}	
			}
		});
		
		borderPane.setOnKeyTyped(new EventHandler<KeyEvent>() {


			@Override
			public void handle(KeyEvent event) {
				
				if (! flush){
					sb.append(event.getCharacter());
				}
				else {
					flush = false;
				}
				
			}
		});
        
		Runnable com = new Runnable() {
			
			@Override
			public void run() {
				ComInput.init(Messages.getOnc());
				
			}
		};
		
		Thread t = new Thread(com);
		t.start();
	}
}
