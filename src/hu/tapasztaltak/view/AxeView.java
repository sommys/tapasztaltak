package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Axe;

import java.awt.*;

public class AxeView extends ItemView {
	private Axe a;
	public AxeView(Axe a){
		this.a = a;
	}
	public void draw(Graphics g) {
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}

	@Override
	public String getItemString() {
		return a.isUsed() ? "au" : "a";
	}
}
