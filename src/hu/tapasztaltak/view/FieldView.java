package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Field;
import hu.tapasztaltak.model.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

public class FieldView extends View {
	protected Field field;
	protected int fieldNum = 1;
	BufferedImage fieldImg;
	protected boolean visited = false;

	public FieldView(){
		field = new Field();
		Game.getInstance().addView(field, this);
		reimportImage();
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
		reimportImage();
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(int fieldNum) {
		if(this.fieldNum == fieldNum) return;
		this.fieldNum = fieldNum;
		reimportImage();
	}

	protected void reimportImage(){
		if(fieldNum == 0) return;
		String imageName = "src/hu/tapasztaltak/textures/";
		try {
			switch (fieldNum) {
				case 1:
					imageName += "simple/hex_"+ (visited ? "field.png" : "unk.png");
					break;
				case 2:
					imageName += "double/double_"+ (visited ? "field.png" : "unk.png");
					break;
				case 3:
					imageName += "triple/triple_"+ (visited ? "field.png" : "unk.png");
					break;
				default: throw new IOException("Hibás oldalszám!");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		try {
			fieldImg = ImageIO.read(new File(imageName));
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

	public void draw(Graphics g) {
		if(fieldNum == 0) return;
		g.drawImage(fieldImg, posX, posY, null);
		g.drawString(getIdForObject(field), posX+50*Integer.min(2,fieldNum), posY+50*Integer.min(2,fieldNum));
	}

	@Override
	public void update() {
		reimportImage();

	}
}
