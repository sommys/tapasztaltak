package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Cape;

import javax.swing.*;

public class CapeView extends SuiteView {
	public CapeView(Cape c) {
		icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/cape.png");
		this.s = c;
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}

	@Override
	public String getItemString() {
		return "c";
	}
}
