package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Stun;

import javax.swing.*;

public class StunView extends AgentView {
	public StunView(Stun s){
		this.icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/Stun_agent.png");
		this.a = s;
	}
	
	public void clicked(int x, int y) {

	}

	@Override
	public void update() {

	}
}
