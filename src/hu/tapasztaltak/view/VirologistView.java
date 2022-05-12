package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Virologist;

import java.awt.*;

public class VirologistView extends View {
	public Virologist vir;
	public VirColor color;

	public void draw(Graphics g) {
	}

	@Override
	public void update() {

	}
}

enum VirColor{
	red, blue, green, yellow, pink, purple
}