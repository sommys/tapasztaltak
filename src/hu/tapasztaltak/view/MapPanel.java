package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.proto.ProtoMain.storage;

public class MapPanel extends JPanel {
	private List<FieldView> fields = new ArrayList<>();

	public MapPanel(){
		super();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				System.out.println("(X;Y)=("+me.getX()+";"+me.getY()+")");
				int x = me.getX();
				int y = me.getY();
				for(FieldView fv : fields){
					if(x > fv.getPosX() && x < fv.getPosX() + Integer.min(2, fv.getFieldNum())*161 &&
					   y > fv.getPosY() && y < fv.getPosY() + (fv.getFieldNum() == 3 ? 322 : 161)){
						System.out.println(fv.getField() + " has " + fv.getField().getNeighbours().size() + " neighbours:");
						for(Field f : fv.getField().getNeighbours()){
							System.out.println(f);
						}
						return;
					}
				}
			}
		});
		initComponents();
	}

	private void initComponents() {
//		(k*120,j*161) k = 0,2,4,6,... és j = 0..9
//
//		(i*120,80+j*161) i = 1,3,5,7,... és j = 0..9
		//paros sor
//		for(int j = 0; j < 9; j++){
//			for(int k = 0; k < 8; k+=2){
//
//				FieldView fv = new FieldView();
//				fv.setPosX(x);
//				fv.setPosY(y);
//				fields.add(fv);
//			}
//		}
		//ptln sor
		for(int k = 0; k < 7; k++){
			for(int j = 0; j < (k % 2 == 0 ? 9 : 8); j++){
				int x = 161*j;
				int y = 121*k;
				if(k % 2 == 1){
					x += 80;
				}
				FieldView fv = new FieldView();
				storage.put(getIdForObject(fv.getField()), fv.getField());
				fv.setPosX(x);
				fv.setPosY(y);
				fields.add(fv);
			}
		}
		List<FieldView> triplets = new ArrayList<>();
		List<FieldView> doubles = new ArrayList<>();
		List<Integer> processed = new ArrayList<>();
		//felso sor
		for(int i = 0; i < 9; i++){
			processed.add(i);
			Field f = fields.get(i).getField();
			if(i == 0){
				Field n1 = fields.get(i+1).getField();
				f.addNeighbour(n1);
				Field n2 = fields.get(i+9).getField();
				f.addNeighbour(n2);
			} else if(i == 8){
				Field n1 = fields.get(i-1).getField();
				f.addNeighbour(n1);
				Field n2 = fields.get(i+8).getField();
				f.addNeighbour(n2);
			} else {
				Field n1 = fields.get(i+1).getField();
				f.addNeighbour(n1);
				Field n2 = fields.get(i+9).getField();
				f.addNeighbour(n2);
				Field n3 = fields.get(i-1).getField();
				f.addNeighbour(n3);
				Field n4 = fields.get(i+8).getField();
				f.addNeighbour(n4);
			}
		}
		//utolso sor
		for(int i = 51; i < 60; i++){
			processed.add(i);
			Field f = fields.get(i).getField();
			if(i == 51){
				Field n1 = fields.get(i+1).getField();
				f.addNeighbour(n1);
				Field n2 = fields.get(i-8).getField();
				f.addNeighbour(n2);
			} else if(i == 59){
				Field n1 = fields.get(i-1).getField();
				f.addNeighbour(n1);
				Field n2 = fields.get(i-9).getField();
				f.addNeighbour(n2);
			} else {
				Field n1 = fields.get(i+1).getField();
				f.addNeighbour(n1);
				Field n2 = fields.get(i-8).getField();
				f.addNeighbour(n2);
				Field n3 = fields.get(i-1).getField();
				f.addNeighbour(n3);
				Field n4 = fields.get(i-9).getField();
				f.addNeighbour(n4);
			}
		}
		//BALSZÉL-PÁROS: -9, -8, +1, +8, +9
		for(int i = 9; i <= 43; i+=17){
			processed.add(i);
			Field f = fields.get(i).getField();
			Field n1 = fields.get(i-9).getField();
			f.addNeighbour(n1);
			Field n2 = fields.get(i-8).getField();
			f.addNeighbour(n2);
			Field n3 = fields.get(i+1).getField();
			f.addNeighbour(n3);
			Field n4 = fields.get(i+8).getField();
			f.addNeighbour(n4);
			Field n5 = fields.get(i+9).getField();
			f.addNeighbour(n5);
		}
		//BALSZÉL-PÁRATLAN: -8, +1, +9
		for(int i = 17; i <= 34; i+=17){
			processed.add(i);
			Field f = fields.get(i).getField();
			Field n1 = fields.get(i-8).getField();
			f.addNeighbour(n1);
			Field n3 = fields.get(i+1).getField();
			f.addNeighbour(n3);
			Field n5 = fields.get(i+9).getField();
			f.addNeighbour(n5);
		}
		//JOBBSZÉL-PÁROS: -9, -8, -1, +8, +9
		for(int i = 16; i <= 50; i+=17){
			processed.add(i);
			Field f = fields.get(i).getField();
			Field n1 = fields.get(i-9).getField();
			f.addNeighbour(n1);
			Field n2 = fields.get(i-8).getField();
			f.addNeighbour(n2);
			Field n3 = fields.get(i-1).getField();
			f.addNeighbour(n3);
			Field n4 = fields.get(i+8).getField();
			f.addNeighbour(n4);
			Field n5 = fields.get(i+9).getField();
			f.addNeighbour(n5);
		}
		//JOBBSZÉL-PÁRATLAN: -9, -1, +8
		for(int i = 25; i <= 42; i+=17){
			processed.add(i);
			Field f = fields.get(i).getField();
			Field n1 = fields.get(i-9).getField();
			f.addNeighbour(n1);
			Field n3 = fields.get(i-1).getField();
			f.addNeighbour(n3);
			Field n5 = fields.get(i+8).getField();
			f.addNeighbour(n5);
		}
		//KÖZÉPSŐ: -9, -8, -1, +1, +8, +9
		Random r = new Random();
		for(int i = 0; i < 60; i++){
			if(processed.contains(i)) continue;
			int chance = r.nextInt(25);
			if(chance == 0) triplets.add(fields.get(i));
			else if(chance == 1) doubles.add(fields.get(i));
			Field f = fields.get(i).getField();
			Field n1 = fields.get(i-9).getField();
			f.addNeighbour(n1);
			Field n2 = fields.get(i-8).getField();
			f.addNeighbour(n2);
			Field n3 = fields.get(i-1).getField();
			f.addNeighbour(n3);
			Field n4 = fields.get(i+1).getField();
			f.addNeighbour(n4);
			Field n5 = fields.get(i+8).getField();
			f.addNeighbour(n5);
			Field n6 = fields.get(i+9).getField();
			f.addNeighbour(n6);
		}
		for(FieldView fv : doubles){
			fv.setFieldNum(2);
			FieldView other = fields.remove(fields.indexOf(fv)+1);
			Field f = fv.getField();
			for(Field n : other.getField().getNeighbours()){
				if(!f.getNeighbours().contains(n)){
					f.addNeighbour(n);
				}
			}
		}
		for(FieldView fv : triplets){
			fv.setFieldNum(3);
			FieldView other1 = fields.remove(fields.indexOf(fv)+9);
			FieldView other2 = fields.remove(fields.indexOf(fv)+8);
			Field f = fv.getField();
			for(Field n : other1.getField().getNeighbours()){
				if(!f.getNeighbours().contains(n)){
					f.addNeighbour(n);
				}
			}for(Field n : other2.getField().getNeighbours()){
				if(!f.getNeighbours().contains(n)){
					f.addNeighbour(n);
				}
			}
			fv.setPosX(other2.getPosX());
		}
		randomizeMap();
	}

	private void randomizeMap(){
		Random r = new Random();
		int toDelete = r.nextInt(10);
		for(int i = 0; i < toDelete; i++){
			int delIdx;
			do {
				delIdx = r.nextInt(fields.size());
			} while(fields.get(delIdx).getFieldNum() != 1);
			FieldView fv = fields.remove(delIdx);
			Field f = fv.getField();
			//szomszedsag kezelese
			for(Field n : f.getNeighbours()){
				n.removeNeighbour(f);
			}
			f.getNeighbours().clear();
		}
	}

	/**
	 * Override-olja a paint komponentet, ezert tudjuk kirajzolni a hatteret
	 * @param g graphics amin van a hatterkep
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(FieldView fv : fields){
			fv.draw(g);
		}
	}
}
