package controllers;

import javafx.scene.layout.VBox;
import models.Enregistrable;

public interface SuperController {
	
	public Enregistrable getEnregistrable();
	public VBox init(VBox form);
	public void reinit();
	public void unfreeze();

}
