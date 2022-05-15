package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Gloves;

import javax.swing.*;
import java.awt.*;

public class GlovesView extends SuiteView {
	static Icon gloves = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/gloves.png");
	public GlovesView(Gloves g) {
		this.s = g;
	}

	public void draw(Graphics g) {
		gloves.paintIcon(null, g, posX, posY);
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
