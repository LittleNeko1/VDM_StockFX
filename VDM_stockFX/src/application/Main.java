package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.ComInput;
import utils.Messages;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Classe principale de l'application.
 */
public class Main extends Application {
	
	/**
	 * Méthode de lancement de l'application.
	 * <ul>
	 * <li>charge le fichier fxml</li>
	 * <li>charge le fichier css</li>
	 * <li>définit les dimensions de la fenetre</li>
	 * <li>force le positionnement au premeir plan</li>
	 * <li>implémente les modalités de l'arret</li>
	 * </ul>
	 *
	 */
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			Messages m = new Messages();
			
			Scene scene = new Scene((Parent) JfxUtils.loadFxml("VDM_stock_GUI.fxml"), 1550, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Messages.setScene(scene);
			Messages.setPrimary(primaryStage);
			
			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(true);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              System.out.println("Stage is closing");
		              ComInput.close();
		          }
		      });        
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Traditionnelle méthode main(String[] args) ...
	 * @param args les arguments passés à l'application
	 */
	public static void main(String[] args) {
		launch(args);
	}

}

