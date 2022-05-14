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
		red("red"), blue("blu"), green("gre"), yellow("yel"), pink("pin"), purple("pur");
		private final String name;

		VirColor(String s) {
			name = s;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private Virologist vir;
	private VirColor color;
	private String playerName;

	public VirologistView(){

	}

	public VirologistView(Virologist vir, VirColor col){
		this.vir = vir;
		this.color = col;
		updateImage();
	}

	public String getPlayerName() {
		return playerName;
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


}