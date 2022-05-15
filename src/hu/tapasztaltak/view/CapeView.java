package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Cape;

import javax.swing.*;
import java.awt.*;

public class CapeView extends SuiteView {
	static Icon cape = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/cape.png");
	public CapeView(Cape c) {
		this.s = c;
	}

	public void draw(Graphics g) {
		cape.paintIcon(null, g, posX, posY);
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
