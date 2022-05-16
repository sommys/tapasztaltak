package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Protect;

import javax.swing.*;

public class ProtectView extends AgentView {
	public ProtectView(Protect p){
		this.icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/Protect_agent.png");
		this.a = p;
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}
}
