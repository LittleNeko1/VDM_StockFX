package utils;

import application.VDM_stock_GUI_controller;
import controllers.Centre_operation_controller;
import controllers.Operation_nouvelle_controller;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Messages {
	
	private static Scene scene;
	private static Operation_nouvelle_controller onc;
	private static Centre_operation_controller coc;
	private static VDM_stock_GUI_controller vsgc;
	
	public Messages(){
		scene = new Scene(new Pane());
		onc = new Operation_nouvelle_controller();
		coc = new Centre_operation_controller();
		vsgc = new VDM_stock_GUI_controller();
		
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

	public static Centre_operation_controller getCoc() {
		return coc;
	}

	public static void setCoc(Centre_operation_controller coc) {
		Messages.coc = coc;
	}

	public static VDM_stock_GUI_controller getVsgc() {
		return vsgc;
	}

	public static void setVsgc(VDM_stock_GUI_controller vsgc) {
		Messages.vsgc = vsgc;
	}
	
	

}
