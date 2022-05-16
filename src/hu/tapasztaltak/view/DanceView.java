package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Dance;

import javax.swing.*;

public class DanceView extends AgentView {
	public DanceView(Dance d){
		this.icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/Dance_agent.png");
		this.a = d;
	}

	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}
}
