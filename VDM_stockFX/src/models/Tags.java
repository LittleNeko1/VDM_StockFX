package models;

import javafx.scene.control.CheckBox;

public enum Tags {
	
	SD(false),
	CF(false),
	XC1(false),
	XC2(false),
	UHS1(false),
	UHS3(false),
	CLASSE8(false),
	CLASSE10(false),
	UDMA7(false);

	private boolean selected;
	
	Tags(boolean b) {
		this.selected = b;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	


}
