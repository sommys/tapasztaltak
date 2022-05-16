package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Nucleotid;

import javax.swing.*;
import java.awt.*;

public class NucleotidView extends MaterialView {
	public NucleotidView(Nucleotid n){
		icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/nucleotid.png");
		this.m = n;
	}

	@Override
	public void update() {

	}
}
