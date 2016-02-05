package models;

import javafx.scene.layout.VBox;

public interface Enregistrable {
	
	public void save();
	public void update();

	public void setForm(VBox form);
	
	public boolean isUpdate();
	
	

}
