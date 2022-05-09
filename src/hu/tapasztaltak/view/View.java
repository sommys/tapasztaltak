package hu.tapasztaltak.view;



public abstract class View {
	protected String posX;
	protected String posY;
	public abstract void draw();
	public void clicked(int x, int y) {
	}
}
