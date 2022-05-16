package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Axe;

import javax.swing.*;

public class AxeView extends SuiteView {
	public AxeView(Axe a){
		icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/axe.png");
		this.s = a;
	}

	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {
		if(((Axe)s).isUsed()){
			icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/axe_used.png");
		}
	}

	@Override
	public String getItemString() {
		return ((Axe)s).isUsed() ? "au" : "a";
	}
}
