package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.Labor;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LaborView extends FieldView {
	public LaborView(int fieldNum){
		field = new Labor();
		this.fieldNum = fieldNum;
		Game.addView(field, this);
		reimportImage();
	}

	public LaborView(Labor l, int fieldNum){
		field = l;
		this.fieldNum = fieldNum;
		Game.addView(field, this);
		reimportImage();
	}

	@Override
	protected void reimportImage(){
		String imageName = "src/hu/tapasztaltak/textures/";
		try {
			switch (fieldNum) {
				case 0:
					break;
				case 1:
					imageName += "simple/hex_" + (visited ? "lab_" : "unk.png");
					break;
				case 2:
					imageName += "double/double_"+ (visited ? "lab_" : "unk.png");;
					break;
				default: throw new IOException("Hibás oldalszám!");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		if(visited) {
			Labor l = (Labor)field;
			imageName += l.getGene().getAgent().getClass().getSimpleName().toLowerCase()+".png";
		}
		try {
			fieldImg = ImageIO.read(new File(imageName));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
