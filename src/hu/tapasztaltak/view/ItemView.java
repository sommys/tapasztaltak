package hu.tapasztaltak.view;


import javax.swing.*;

public abstract class ItemView extends View {

    public String getItemString() {
        return "";
    }

    // todo ez nagyon nem jo igy :(
    public Icon getItemImage() { return new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png"); }
}
