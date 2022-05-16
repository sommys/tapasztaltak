package hu.tapasztaltak.view;


import javax.swing.*;
import java.awt.*;

public abstract class ItemView extends View {
    protected Icon icon;

    public String getItemString() {
        return "";
    }

    public Icon getItemImage() { return icon; }

    @Override
    public void draw(Graphics g) {
        icon.paintIcon(null, g, posX, posY);
    }
}
