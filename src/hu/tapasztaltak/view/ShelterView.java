package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Shelter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ShelterView extends FieldView {
	public ShelterView(int fieldNum){
		field = new Shelter();
		this.fieldNum = fieldNum;
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
					imageName += "simple/hex_" + (visited ? "shelter" : "unk.png");
					break;
				case 2:
					imageName += "double/double_"+ (visited ? "shelter" : "unk.png");;
					break;
				default: throw new IOException("Hibás oldalszám!");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		if(visited) {
			Shelter s = (Shelter) field;
			if (s.getSuite() != null) {
				imageName += ".png";
			} else {
				imageName += "_suite.png";
			}
		}
		try {
			fieldImg = ImageIO.read(new File(imageName));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
