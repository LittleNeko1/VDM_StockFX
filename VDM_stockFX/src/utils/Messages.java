package utils;

import controllers.Operation_nouvelle_controller;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Messages {
	
	private static Scene scene;
	private static Operation_nouvelle_controller onc;
	
	public Messages(){
		scene = new Scene(new Pane());
		onc = new Operation_nouvelle_controller();
		
		System.out.println("scene = " + scene);
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		Messages.scene = scene;
	}

	public static Operation_nouvelle_controller getOnc() {
		return onc;
	}

	public static void setOnc(Operation_nouvelle_controller onc) {
		Messages.onc = onc;
	}
	
	

}
