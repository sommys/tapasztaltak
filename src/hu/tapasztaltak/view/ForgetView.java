package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Forget;

import javax.swing.*;

public class ForgetView extends AgentView {
	public ForgetView(Forget f){
		this.icon = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/Forget_agent.png");
		this.a = f;
	}

	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}
}
