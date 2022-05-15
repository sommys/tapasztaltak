package hu.tapasztaltak.view;

import hu.tapasztaltak.model.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class VirologistView extends View {
	static BufferedImage stun;

	static {
		try {
			stun = ImageIO.read(new File("src/hu/tapasztaltak/textures/virologists/stunned.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	BufferedImage virImg;
	public enum VirColor{
		red("red",Color.RED), blue("blu", new Color(69, 125, 229)), green("gre",Color.GREEN), yellow("yel",Color.YELLOW), pink("pin", Color.MAGENTA), purple("pur", new Color(153, 0, 255));
		private final String name;
		private final Color color;
		VirColor(String s, Color c) {
			name = s;
			color = c;
		}

		@Override
		public String toString() {
			return name;
		}

		public Color getColor() {
			return color;
		}
	}
	private Virologist vir;
	private VirColor color;
	private String name;

	public VirologistView(Virologist vir, String col, String name){
		this.vir = vir;
		this.name = name;
		switch (col){
			case "kék":
				color = VirColor.blue;
				break;
			case "piros":
				color = VirColor.red;
				break;
			case "zöld":
				color = VirColor.green;
				break;
			case "sárga":
				color = VirColor.yellow;
				break;
			case "rózsaszín":
				color = VirColor.pink;
				break;
			case "lila":
				color = VirColor.purple;
				break;
		}
		updateImage();
	}

	public String getPlayerName() {
		return name;
	}

	public VirColor getColor() {
		return color;
	}

	public void updateImage(){
		TreeSet<String> stuff = new TreeSet<String>();
		String identifier = vir.getModifiers().stream().anyMatch(it -> it instanceof Bear) ? "bear" : "vir";
		String colorId = color.toString();
		List<Suite> suites = vir.getInventory().getSuites().stream().filter(Suite::isActive).collect(Collectors.toList());
		if(identifier.equals("bear")){
			suites = suites.stream().filter(it -> it instanceof Cape).collect(Collectors.toList());
		}
		for(Suite s : suites){
			ItemView i = (ItemView) Game.objectViewHashMap.get(s);
			stuff.add(i.getItemString());
		}
		String image = identifier + "_" + colorId + (stuff.isEmpty() ? "" : "_" + String.join("",stuff))+".png";
		try {
			System.out.println("virologistImage: "+image);
			virImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/virologists/" + image));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void draw(Graphics g) {
		g.drawImage(virImg, posX, posY, null);
		if(vir.isStunned()){
			g.drawImage(stun, posX, posY, null);
		}
	}

	@Override
	public void update() {

	}

	public BufferedImage getVirImg() {
		return virImg;
	}


	public void setTextColortoVir(Component c){
		switch (color){
			case red:
				c.setForeground(Color.RED);
				break;
			case blue:
				c.setForeground(Color.BLUE);
				break;
			case green:
				c.setForeground(Color.GREEN);
				break;
			case yellow:
				c.setForeground(Color.YELLOW);
				break;
			case pink:
				c.setForeground(Color.MAGENTA);
				break;
			case purple:
				c.setForeground(new Color(153, 0, 255));
				break;
			default:
				return;
		}
	}
}