package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Nucleotid;

import javax.swing.*;
import java.awt.*;

public class NucleotidView extends MaterialView {
	static Icon nucleo = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/nucleotid.png");
	public NucleotidView(Nucleotid n){
		this.m = n;
	}
	public void draw(Graphics g) {
		nucleo.paintIcon(null, g, posX, posY);
	}

	@Override
	public void update() {

	}
}
