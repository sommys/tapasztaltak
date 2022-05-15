package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Aminoacid;

import javax.swing.*;
import java.awt.*;

public class AminoacidView extends MaterialView {
	static Icon amino = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/aminoacid.png");
	public AminoacidView(Aminoacid a){
		this.m = a;
	}
	public void draw(Graphics g) {
		amino.paintIcon(null, g, posX, posY);
	}

	@Override
	public void update() {

	}
}
