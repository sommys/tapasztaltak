package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Protect;

import javax.swing.*;
import java.awt.*;

public class ProtectView extends AgentView {
	static Icon protect = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/Protect_agent.png");
	public ProtectView(Protect p){
		this.a = p;
	}
	public void draw(Graphics g) {
		protect.paintIcon(null, g, posX, posY);
		super.draw(g);
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}
}
