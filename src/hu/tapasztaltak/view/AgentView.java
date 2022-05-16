package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Agent;

import javax.swing.*;
import java.awt.*;

public abstract class AgentView extends View {
	protected Icon icon;
	protected Agent a;
	public void draw(Graphics g) {
		icon.paintIcon(null, g, posX, posY);
		g.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,20));
		g.setColor(Color.WHITE);
		g.drawString(""+a.getTimeLeft(), posX+42, posY+70);
	}
}
