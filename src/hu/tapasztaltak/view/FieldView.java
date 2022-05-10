package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Field;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FieldView extends View {
	private Field field = new Field();
	private int fieldNum = 1;
	BufferedImage fieldImg;

	public FieldView(){
		try {
			fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/hex_field.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(int fieldNum) {
		this.fieldNum = fieldNum;
		try {
			switch (fieldNum) {
				case 1:
					fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/hex_field.png"));
					break;
				case 2:
					fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/double_shelter.png"));
					break;
				case 3:
					fieldImg = ImageIO.read(new File("src/hu/tapasztaltak/textures/triple_field.png"));
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
		g.drawImage(fieldImg, posX, posY, null);
	}
}
