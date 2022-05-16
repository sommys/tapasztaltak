package hu.tapasztaltak.view;

import hu.tapasztaltak.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class MapPanel extends JPanel {
	private List<FieldView> fields = new ArrayList<>();

	public MapPanel(){
		super();
		setFocusable(true);
		setBackground(new Color(27, 42, 39));
		initComponents();
	}

	private void initComponents() {
		//LEHELYEZES POZIBA
		for(int k = 0; k < 7; k++){
			for(int j = 0; j < (k % 2 == 0 ? 9 : 8); j++){
				int x = 160*j;
				int y = 121*k;
				if(k % 2 == 1){
					x += 80;
				}
				FieldView fv = new FieldView();
				fv.setPosX(x);
				fv.setPosY(y);
				fields.add(fv);
			}
		}
		List<FieldView> triplets = new ArrayList<>();
		List<FieldView> doubles = new ArrayList<>();
		List<Integer> processed = new ArrayList<>();
		/**
		 * SZOMSZEDSAGOK
		 */
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
			if(chance == 3) triplets.add(fields.get(i)); /**sorsolja, hogy tripla field legyen-e*/
			else if(chance < 3) doubles.add(fields.get(i)); /**sorsolja, hogy dupla field legyen-e*/
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

		/**
		 * MAGIC dupla tripla generálások
		 */
		for(FieldView fv : fields){
			if(doubles.contains(fv)){
				if(fv.getFieldNum() != 1) continue;
				FieldView disturb1 = fields.get(fields.indexOf(fv)-1);
				FieldView disturb2 = fields.get(fields.indexOf(fv)+1);
				FieldView disturb3 = fields.get(fields.indexOf(fv)+8);
				if(disturb1.getFieldNum() != 1 || disturb2.getFieldNum() != 1 || disturb3.getFieldNum() != 1) continue;
				fv.setFieldNum(2);
				FieldView other = fields.get(fields.indexOf(fv)+1);
				other.setFieldNum(0);
				Field f = fv.getField();
				for(Field n : other.getField().getNeighbours()){
					n.removeNeighbour(other.getField());
					if(!f.getNeighbours().contains(n) || n == f || n == other.getField()){
						f.addNeighbour(n);
					}
					if(!n.getNeighbours().contains(f))n.addNeighbour(f);
				}
				f.removeNeighbour(other.getField());
				f.removeNeighbour(f);
			} else if(triplets.contains(fv)){
				if(fv.getFieldNum() != 1) continue;
				FieldView disturb = fields.get(fields.indexOf(fv)-1);
				if(disturb.getFieldNum() != 1) continue;
				fv.setFieldNum(3);
				FieldView other1 = fields.get(fields.indexOf(fv)+9);
				FieldView other2 = fields.get(fields.indexOf(fv)+8);
				other1.setFieldNum(0);
				other2.setFieldNum(0);
				Field f = fv.getField();
				Set<Field> neighbours = new HashSet<>(f.getNeighbours());
				neighbours.addAll(other1.getField().getNeighbours());
				neighbours.addAll(other2.getField().getNeighbours());
				neighbours.remove(other1.getField());
				neighbours.remove(other2.getField());
				neighbours.remove(f);
				for(Field n : neighbours){
					n.removeNeighbour(other1.getField());
					n.removeNeighbour(other2.getField());
					if(!n.getNeighbours().contains(f)) n.addNeighbour(f);
				}
				f.setNeighbours(new ArrayList<>(neighbours));
				fv.setPosX(other2.getPosX());
			}
		}
		randomizeMap();
		setIndexes();
		addSpecialFields();
		setIndexes();
	}

	private void addSpecialFields() {
		List<FieldView> stillExist = fields.stream().filter(it -> it.getFieldNum() != 0 && it.getFieldNum() != 3).collect(Collectors.toList());
		int originalSize = stillExist.size();
		Random r = new Random();
		//random pár labor (legalább 4)
		for(int i = 0; i < 4; i++){
			Labor l;
			int infected = r.nextInt(10);
			int changeIdx = r.nextInt(stillExist.size());
			FieldView toChange = stillExist.get(changeIdx);
			FieldView lv ;
			if(infected < 3){
				l = new InfLabor();
				lv = new InfLaborView((InfLabor)l, toChange.getFieldNum());
			} else {
				l = new Labor();
				lv = new LaborView(l, toChange.getFieldNum());
			}
			l.setGene(Game.genes[i]);
			lv.setPosX(toChange.getPosX());
			lv.setPosY(toChange.getPosY());
			//lecsereljuk
			fields.set(fields.indexOf(toChange),lv);
			for(Field n : toChange.getField().getNeighbours()){
				n.removeNeighbour(toChange.getField());
				lv.getField().addNeighbour(n);
				n.addNeighbour(lv.getField());
			}
			stillExist.remove(toChange);
		}
		int lab = r.nextInt( (int)(originalSize * 0.1));
		for(int i = 0; i < lab; i++){
			int changeIdx = r.nextInt(stillExist.size());
			FieldView toChange = stillExist.get(changeIdx);
			FieldView lv ;
			int infected = r.nextInt(10);
			if(infected < 3){
				lv = new InfLaborView(toChange.getFieldNum());
			} else {
				lv = new LaborView(toChange.getFieldNum());
			}
			((Labor)lv.getField()).setGene(Game.genes[r.nextInt(4)]);
			lv.setPosX(toChange.getPosX());
			lv.setPosY(toChange.getPosY());
			//lecsereljuk
			fields.set(fields.indexOf(toChange),lv);
			for(Field n : toChange.getField().getNeighbours()){
				n.removeNeighbour(toChange.getField());
				lv.getField().addNeighbour(n);
				n.addNeighbour(lv.getField());
			}
			stillExist.remove(toChange);
		}
		//random pár warehouse (legalább 2)
		int wh = r.nextInt( (int)(originalSize * 0.1)) + 2;
		for(int i = 0; i < wh; i++){
			int changeIdx = r.nextInt(stillExist.size());
			FieldView toChange = stillExist.get(changeIdx);
			WarehouseView wv = new WarehouseView(toChange.getFieldNum());
			wv.setPosX(toChange.getPosX());
			wv.setPosY(toChange.getPosY());
			//lecsereljuk
			fields.set(fields.indexOf(toChange),wv);
			for(Field n : toChange.getField().getNeighbours()){
				n.removeNeighbour(toChange.getField());
				wv.getField().addNeighbour(n);
				n.addNeighbour(wv.getField());
			}
			stillExist.remove(toChange);
		}
		//random pár shelter (legaLább 2)
		int sh = r.nextInt( (int)(originalSize * 0.1)) + 2;
		for(int i = 0; i < sh; i++){
			int changeIdx = r.nextInt(stillExist.size());
			FieldView toChange = stillExist.get(changeIdx);
			ShelterView sv = new ShelterView(toChange.getFieldNum());
			sv.setPosX(toChange.getPosX());
			sv.setPosY(toChange.getPosY());
			//lecsereljuk
			fields.set(fields.indexOf(toChange),sv);
			for(Field n : toChange.getField().getNeighbours()){
				n.removeNeighbour(toChange.getField());
				sv.getField().addNeighbour(n);
				n.addNeighbour(sv.getField());
			}
			stillExist.remove(toChange);
		}
	}

	private void setIndexes() {
		int i = 1;
		for(FieldView fv : fields.stream().filter(it -> it.getFieldNum() != 0).collect(Collectors.toList())){
			RoundManager.getInstance().addSteppable(fv.getField());
			fv.setFieldIdx(i++);
		}
	}

	/**
	 * KITORLESEK (max 10, csak szingliket, mert Soma őket utálja)
	 */
	private void randomizeMap(){
		Random r = new Random();
		int toDelete = r.nextInt(15);
		for(int i = 0; i < toDelete; i++){
			int delIdx;
			do {
				delIdx = r.nextInt(fields.size());
			} while(fields.get(delIdx).getFieldNum() != 1);
			FieldView fv = fields.get(delIdx);
			Field f = fv.getField();
			//szomszedsag kezelese
			boolean canDelete = true;
			for(Field n : f.getNeighbours()){
				if(n.getNeighbours().size() < 3){
					canDelete = false;
					break;
				}
			}
			if(!canDelete) continue;
			fv.setFieldNum(0);
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

	public void update(){
		for(FieldView fv : fields.stream().filter(it -> it.getFieldNum() != 0).collect(Collectors.toList())){
			fv.update();
		}
		revalidate();
		repaint();
	}

	public List<FieldView> getFields() {
		return fields;
	}
}
