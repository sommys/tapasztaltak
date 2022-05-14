package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Bag;

import java.awt.*;

public class BagView extends ItemView {
	private Bag b;
	public BagView(Bag b){
		this.b = b;
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
		return "b";
	}
}
