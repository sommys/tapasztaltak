package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Field;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static hu.tapasztaltak.model.Game.objectViewHashMap;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

public class FieldView extends View {
	private Field field;
	private int fieldNum = 1;
	BufferedImage fieldImg;
	private boolean visited = false;

	public FieldView(){
		field = new Field();
		objectViewHashMap.put(field, this);
		try {
			fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/simple/hex_unk.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
		if(visited){
			try {
				switch (fieldNum) {
					case 0:
						break;
					case 1:
						fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/simple/hex_field.png"));
						break;
					case 2:
						fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/double/double_field.png"));
						break;
					case 3:
						fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/triple/triple_field.png"));
						break;
					default: throw new IOException("Hibás oldalszám!");
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(int fieldNum) {
		this.fieldNum = fieldNum;
		try {
			switch (fieldNum) {
				case 0:
					break;
				case 1:
					fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/simple/hex_unk.png"));
					break;
				case 2:
					fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/double/double_unk.png"));
					break;
				case 3:
					fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/triple/triple_unk.png"));
					break;
				default: throw new IOException("Hibás oldalszám!");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public Field getField() {
		return field;
	}

	public void setField(Field f) {
		this.field = f;
	}

	;
	public void draw(Graphics g) {
		if(fieldNum == 0) return;
		g.drawImage(fieldImg, posX, posY, null);
		g.drawString(getIdForObject(field), posX+50*Integer.min(2,fieldNum), posY+50*Integer.min(2,fieldNum));
	}
}
