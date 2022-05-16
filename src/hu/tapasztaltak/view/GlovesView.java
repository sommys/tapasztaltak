package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Gloves;

import javax.swing.*;

public class GlovesView extends SuiteView {
	public GlovesView(Gloves g) {
		icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/gloves.png");
		this.s = g;
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}

	@Override
	public String getItemString() {
		return "g";
	}
}
