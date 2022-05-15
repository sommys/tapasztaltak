package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Axe;

import javax.swing.*;
import java.awt.*;

public class AxeView extends SuiteView {
	static Icon axe = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/axe.png");
	public AxeView(Axe a){
		this.s = a;
	}
	public void draw(Graphics g) {
		axe.paintIcon(null, g, posX, posY);
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}

	@Override
	public String getItemString() {
		return ((Axe)s).isUsed() ? "au" : "a";
	}
}
