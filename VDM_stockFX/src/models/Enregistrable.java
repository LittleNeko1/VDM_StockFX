package models;

import org.bson.types.ObjectId;

import javafx.scene.layout.VBox;


public interface Enregistrable {
	
	
	
	public void save();

	public void setForm(VBox form);
	
	public ObjectId get_id();

}
