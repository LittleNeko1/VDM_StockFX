package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.*;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import jssc.SerialPortException;
import utils.AutoCompletion;
import utils.ComInput;
import utils.Messages;
import utils.MongoAccess;
import utils.Retour;

/**
 * Controleur principal de l'application.
 * Les controleurs des différentes parties graphiques sont réparties dans les controleurs du package {@link controllers}
 * 
 *  * */
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
	
	/**
	 * Réalise l'initialisation (ou la réinitalisation) de {@link controllers.Operation_nouvelle_controller}.
	 */
	public final void fire_button(){
		nouvelle_op_button.fire();
	}
	
	private String valeur_lue;
	
	
	/**
	 * L'initialisation réalise :
	 * <ul>
	 * <li> la connexion à la base MongoDB</li>
	 * <li> l'enregistrement de l'instance courante <b>this</b> dans {@link Messages}</li>
	 * <li> la création du conteneur qui accueillera les éléments graphiques</li>
	 * <li> la définition des actions des 3 boutons du haut (relicats du fichier fxml)</li>
	 * <li> le lancement d'une action sur le bouton "Nouvelle opération"</li>
	 * <li> l'initialisation du la connexion série {@link utils.ComInput}</li>
	 * <li> l'initialisation la boucle écoutant le port série {@link #taskFactory()}</li>
	 * </ul>
	 */
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
		

		ComInput.init();
		
		taskFactory();
		

	}
	
	/**
	 * Fabrique la tache qui va écouter et lire ce qui arrive sur le port série (douchette).
	 * 
	 * Dans sa méthode OnSucceeded(), le comportement diffère si la valeur lue est connue de la base ou non :
	 * <ul>
	 * <li>si la valeur est connue, c'est la classe {@link controllers.Operation_nouvelle_controller} qui est appelée</li>
	 * <li>si la valeur est inconnue, c'est la classe {@link controllers.Ajout_materiel_controller} qui est appelée</li>
	 * </ul>
	 * Cette méthode s'appelle récursivement pour se ré-armer en fin de lecture.
	 * 
	 */
	public void taskFactory(){
		
		final Task<Void> task = new Task<Void>() {
		    @Override
		    public Void call() throws Exception {
		        // do work here...	
		    	try {
					valeur_lue = ComInput.read();
					ObservableList<String> ol = AutoCompletion.perfectMatch("materiel", "nom", valeur_lue) ;
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
		    	// update here ...
		    	
		    	System.out.println("task.handle()");
		    	
		    	if (Messages.isValeur_connue()){
		    		nouvelle_op_button.fire();
			    	Messages.getOnc().reinit(valeur_lue.trim());
			    	Retour.retour(valeur_lue.trim());
			    	Messages.getOnc().getList_choiceboxes().get(0).requestFocus();
		    	}
		    	else {
		    		
		    		
		    		ajouter_button.fire();
		    		Centre_ajout_controller.list_toggles.get(2).fire();
		    		Messages.getAmc().reinit(valeur_lue.trim());
		    		
		    	}
		    	
		    	
		    	taskFactory(); //loop
		    }
		});

		Thread t = new Thread(task);
		t.setDaemon(true); // thread will not prevent application shutdown
		t.start();
	}
}
