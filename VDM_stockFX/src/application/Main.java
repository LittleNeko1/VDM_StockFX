package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
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
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

