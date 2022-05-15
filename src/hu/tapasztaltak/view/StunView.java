package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Stun;

import javax.swing.*;
import java.awt.*;

public class StunView extends AgentView {
	static Icon stun = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/Stun_agent.png");
	public StunView(Stun s){
		this.a = s;
	}
	public void draw(Graphics g) {
		stun.paintIcon(null, g, posX, posY);
		super.draw(g);
	}
	
	public void clicked(int x, int y) {

	}

	@Override
	public void update() {

	}
}
