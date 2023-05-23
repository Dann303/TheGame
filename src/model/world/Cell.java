package model.world;

import model.characters.Hero;
import model.collectibles.Vaccine;

public abstract class Cell {

	private boolean isVisible;

	private String icon = "";

	public Cell() {

	}
	
	public boolean isVisible() {
		return this.isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}