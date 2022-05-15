package hu.tapasztaltak.view;


import hu.tapasztaltak.model.IStealable;

import javax.swing.*;
import java.awt.*;

public abstract class ItemView extends View {
	public void draw(Graphics g) {
	}

    public String getItemString() {
        return "";
    }

    // Todo: Minden viewhoz megírni, amihez kell
    public Icon getItemImage() { return new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png"); }
}
