package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
import jssc.SerialPortException;
import utils.AutoCompletion;
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
	
	public final void fire_button(){
		nouvelle_op_button.fire();
	}
	
	private String valeur_lue;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("vdm_stock_connect()");
		
		MongoAccess.connect();
		
		System.out.println("vdm_stock_initialize()");
		
		Messages.setVsgc(this);
		
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
		
		
        
//		Runnable com = new Runnable() {
//			
//			@Override
//			public void run() {
//				ComInput.init(Messages.getOnc());
//				
//			}
//		};
		ComInput.init();
		
		taskFactory();
		

	}
	
	public void taskFactory(){
		final Task<Void> task = new Task<Void>() {
		    @Override
		    public Void call() throws Exception {
		        // do work here...
		    	
		    	try {
					valeur_lue = ComInput.read();
					ObservableList<String> ol = AutoCompletion.autocomplete("materiel", "nom", valeur_lue) ;
				    if (ol.size() == 1 && ol.get(0).equals(valeur_lue.trim())){
				    	System.out.println("valeur connue");
				    	Messages.setValeur_connue(true);
				    }
				    else {
				    	System.out.println("valeur inconnue");
				    	Messages.setValeur_connue(false);
				    }
					
				} catch (SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		        return null ;
		    }
		};

		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		    @Override
		    public void handle(WorkerStateEvent event) {
		    	
		    	System.out.println("task.handle()");
		    	
		    	if (Messages.isValeur_connue()){
		    		nouvelle_op_button.fire();
			    	Messages.getOnc().reinit(valeur_lue.trim());
		    	}
		    	else {
		    		
		    		
		    		ajouter_button.fire();
		    		Centre_ajout_controller.list_toggles.get(2).fire();
		    		Messages.getAmc().reinit(valeur_lue.trim());
		    		
		    	}
		    	
		    	
		    	taskFactory();
		    	
		        // update UI with result
		    }
		});

		Thread t = new Thread(task);
		t.setDaemon(true); // thread will not prevent application shutdown
		t.start();
	}
}
