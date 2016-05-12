package models;

import javafx.scene.control.CheckBox;

/**
 * Enumération des tags et de leur état.
 * @see Destinataire Materiel
 */
public enum Tags {
	
	SD(false),
	CF(false),
	XC1(false),
	XC2(false),
	UHS1(false),
	UHS3(false),
	CLASSE8(false),
	CLASSE10(false),
	SDHC(false),
	SDXC(false),
	UDMA7(false),
	USB2(false);

	private boolean selected;
	
	/**
	 * Contructeur avec un état initial.
	 * @param b
	 */
	Tags(boolean b) {
		this.selected = b;
	}

	/**
	 * Retourne true si le tag est sélectionné, false sinon.
	 * @return true si le tag est sélectionné, false sinon
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Modifie l'état du tag
	 * @param selected true si le tag est sélectionné, false sinon
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	


}
