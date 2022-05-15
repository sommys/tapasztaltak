package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Dance;

import javax.swing.*;
import java.awt.*;

public class DanceView extends AgentView {
	static Icon dance = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/Dance_agent.png");
	public DanceView(Dance d){
		this.a = d;
	}
	public void draw(Graphics g) {
		dance.paintIcon(null, g, posX, posY);
		super.draw(g);
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}
}
