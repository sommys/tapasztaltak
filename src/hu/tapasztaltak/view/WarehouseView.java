package hu.tapasztaltak.view;


import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.Warehouse;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class WarehouseView extends FieldView {
	public WarehouseView(int fieldNum){
		field = new Warehouse();
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
					imageName += "simple/hex_" + (visited ? "ware" : "unk.png");
					break;
				case 2:
					imageName += "double/double_"+ (visited ? "ware" : "unk.png");;
					break;
				default: throw new IOException("Hibás oldalszám!");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		if(visited) {
			Warehouse w = (Warehouse) field;
			if (w.getMaterials().isEmpty()) {
				imageName += ".png";
			} else {
				imageName += "_mat.png";
			}
		}
		try {
			fieldImg = ImageIO.read(new File(imageName));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
