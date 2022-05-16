package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Bag;

import javax.swing.*;

public class BagView extends SuiteView {
	public BagView(Bag b){
		icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/bag.png");
		this.s = b;
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}

	@Override
	public String getItemString() {
		return "b";
	}
}
