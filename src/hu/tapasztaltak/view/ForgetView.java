package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Forget;

import javax.swing.*;
import java.awt.*;

public class ForgetView extends AgentView {
	static Icon forget = new ImageIcon("src/hu/tapasztaltak/textures/inventoryItems/Forget_agent.png");
	public ForgetView(Forget f){
		this.a = f;
	}
	public void draw(Graphics g) {
		forget.paintIcon(null, g, posX, posY);
		super.draw(g);
	}
	
	public void clicked(int x, int y) {
	}

	@Override
	public void update() {

	}
}
