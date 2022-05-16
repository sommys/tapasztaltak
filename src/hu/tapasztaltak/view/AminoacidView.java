package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Aminoacid;

import javax.swing.*;

public class AminoacidView extends MaterialView {
	public AminoacidView(Aminoacid a){
		icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/aminoacid.png");
		this.m = a;
	}

	@Override
	public void update() {

	}
}
