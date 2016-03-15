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


public class Main extends Application {
	
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
			//primaryStage.close();
		        
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

