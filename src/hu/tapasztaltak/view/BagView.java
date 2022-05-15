package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Bag;

import javax.swing.*;
import java.awt.*;

public class BagView extends SuiteView {
	static Icon bag = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/bag.png");
	public BagView(Bag b){
		this.s = b;
	}
	public void draw(Graphics g) {
		bag.paintIcon(null, g, posX, posY);
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
