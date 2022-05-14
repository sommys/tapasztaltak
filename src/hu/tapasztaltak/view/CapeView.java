package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Cape;

import java.awt.*;

public class CapeView extends ItemView {
	private Cape c;

	public CapeView(Cape c) {
		this.c = c;
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
		return "c";
	}
}
