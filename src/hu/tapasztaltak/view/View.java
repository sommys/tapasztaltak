package hu.tapasztaltak.view;


import java.awt.*;

public abstract class View {
	protected int posX;
	protected int posY;
	public abstract void draw(Graphics g);
	public void clicked(int x, int y) {
	}
	public abstract void update();

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
