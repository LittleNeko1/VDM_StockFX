package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.layout.VBox;

public interface Enregistrable {
	
	public void save();
	public void update();

	public void setForm(VBox form);
	
	@JsonIgnore
	public boolean isUpdate();
	
	

}
