package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoMain;
import hu.tapasztaltak.view.VirologistView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hu.tapasztaltak.proto.ProtoLogger.loggingSwitch;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

/**
 * A játékosok által irányított virológusokat reprezentáló osztály.
 */
public class Virologist implements ISteppable {
	/**
	 * A virológus mozgott-e már az aktuális körben.
	 */
	private boolean moved = false;
	/**
	 * A virológus jelenleg le van-e bénulva.
	 */
	private boolean stunned = false;
	/**
	 * A megtanult genetikai kódok listája.
	 */
	private List<Gene> learnt = new ArrayList<>();
	/**
	 * A mező, amin a virológus tartózkodik.
	 */
	private Field field = null;
	/**
	 * A virológusra hatással levő speciális módosítók listája.
	 */
	private List<SpecialModifier> modifiers = new ArrayList<>();
	/**
	 * A virológus tárhelye, amiben dolgokat tárol.
	 */
	private Inventory inventory = new Inventory();
	/**
	 * A virológust ágenskenéstől védelmező védőfelszerelések listája.
	 */
	private List<IDefense> defenses = new ArrayList<>();

	/**
	 * A virológus az {@code f} mezőre mozog, ha szomszédos a jelenlegi mezővel és nincs lebénulva.
	 * @param f a {@link Field}, amire mozogni szeretne
	 */
	public void move(Field f) {
		if(stunned||moved){
			return;
		}
		if(field.isNeighbour(f)){
			field.removeVirologist(this);
			f.addVirologist(this);
			field = f;
		}
	}

	/**
	 * Felveszi {@code s} felszerelést, ha még nincs rajta 3 felszerelés.
	 * @param s a {@link Suite}, amit fel szeretne venni
	 */
	public void putOnSuite(Suite s) {
		if(stunned||moved){
			return;
		}
		int activeSuites = 0;
		for(Suite invSuite : inventory.getSuites()){
			if(invSuite.isActive()){
				activeSuites++;
			}
		}
		if(activeSuites < 3){
			s.activate(this);
			Game.objectViewHashMap.get(this).update();
		}
	}

	/**
	 * A {@code g} genetikai kódhoz tartozó ágenst állítja elő,
	 * ha van elég anyag a virológus tárhelyében.
	 * @param g a {@link Gene} amiből az ágens elő tud állni
	 */
	public void makeAgent(Gene g) {
		if(stunned||moved){
			return;
		}
		int originalMatSize = inventory.getMaterials().size();
		g.make(inventory);
		int afterMatSize = inventory.getMaterials().size();
		if(originalMatSize == afterMatSize){
			JOptionPane.showMessageDialog(Game.getInstance().questionPanel,"Cuccok is kellenek hozzá...", "Nana", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * A virológus {@code a} ágenst használja {@code v} virológusra.
	 * @param a a felhasznált {@link Agent}
	 * @param v a megkent {@link Virologist}
	 */
	public void useAgent(Agent a, Virologist v) {
		if(stunned || moved || !canReach(v)){
			return;
		}
		spreadInitiation(a, v);
	}

	/**
	 * Lecseréli {@code from} felszerelést {@code to} felszerelésre.
	 * @param from a lecserélendő {@link Suite}
	 * @param to a felvenni kívánt {@link Suite}
	 */
	public void switchSuite(Suite from, Suite to) {
		if(stunned||moved){
			return;
		}
		boolean originalSwitch = loggingSwitch;
		if(loggingSwitch) loggingSwitch=false;
		from.deactivate(this);
		to.activate(this);
		loggingSwitch=originalSwitch;
	}

	/**
	 * Tapogatózás, a virológus megismeri a mezőn lévő virológusokat és tárgyakat/genetikai kódot.
	 */
	public void scanning() throws Exception {
		if(stunned||moved){
			return;
		}
		field.getItem(this);
	}

	/**
	 * A virológus megpróbál lopni {@code from} virológustól.
	 * @param from a {@link Virologist}, akitől lopni akar
	 */
	public void steal(Virologist from) {
		if (stunned) {
			return;
		}
		if (moved) {
			return;
		}
		if(!canReach(from)){
			return;
		}
		if(!from.isStunned()) {
			return;
		}


		Inventory inv2 = from.getInventory();
		if(inv2.getSuites().isEmpty() && inv2.getMaterials().isEmpty()) {
			return;
		}

		if (inventory.getUsedSize()==inventory.getSize()) {
			return;
		}

		//IStealable item = inv2.pickItem();
		Game.getInstance().getquestionpanel().stealPickItemQuestion(from);
	}

	/**
	 * Megnézi, hogy a lopás, amit {@code stealer} kezdeményezett,
	 * végbe tud-e menni úgy, hogy {@code stealer} megkapja {@code what}-ot.
	 * @param stealer a támadó {@link Virologist}
	 * @param what az ellopni kívánt {@link IStealable}
	 */
	public void stolen(Virologist stealer, IStealable what) {
		if(!stunned){
			return;
		}
		what.remove(inventory);
		what.add(stealer.getInventory());
		Game.objectViewHashMap.get(this).update();
	}

	/**
	 * A virológus megtanulja a g genetikai kódot.
	 * @param g a megtanulandó {@link Gene}
	 */
	public void learn(Gene g){
		if(!learnt.contains(g)){
			learnt.add(g);
			Game.getInstance().checkEndGame(this);
		} else {
		}
	}

	/**
	 * Újra engedélyezi a virológusnak, hogy léphessen az új körben, a lejárt ágenseket megszűnteti
	 */
	public void step() {
		moved=false;

		inventory.getAgents().forEach(a -> {
			if(a.getTimeLeft() <= 0){
			RoundManager.getInstance().removeSteppable(a);
			}
		});
		inventory.getAgents().removeIf(a -> a.getTimeLeft() <= 0);

		modifiers.forEach(m -> {
			if(!m.isActive()){
			}
		});
		defenses.forEach(d -> {
			if(!d.stillActive()){
			}
		});
		modifiers.removeIf(m -> !m.isActive());
		defenses.removeIf(d -> !d.stillActive());

		for(SpecialModifier m : modifiers){
			m.effect(this);
		}
	}

	/**
	 * Visszaadja, hogy meg tudja-e érinteni {@code v}-t, azaz egy mezőn állnak-e.
	 * @param v a megérinteni kívánt {@link Virologist}
	 * @return érinthető-e
	 */
	public boolean canReach(Virologist v) {
		return v.getField() == field;
	}

	/**
	 * Megprbálja megkenni {@code a} ágenssel {@code v} virológust.
	 * @param a a használt {@link Agent}
	 * @param v a megkenni kívánt {@link Virologist}
	 */
	public void spreadInitiation(Agent a, Virologist v) {
		inventory.removeAgent(a);
		for(IDefense d : v.getDefenses()){
			if(d.tryToBlock(this, v, a)){
				return;
			}
		}
		inventory.getSuites().stream().filter(it -> it instanceof Gloves).forEach(it -> ((Gloves) it).setUsed(false));
		v.getInventory().getSuites().stream().filter(it -> it instanceof Gloves).forEach(it -> ((Gloves) it).setUsed(false));
		a.spread(v);
	}

	/**
	 * Szól a {@link RoundManager}-nek, hogy vége van a körének, és léptetheti tovább a köröket.
	 */
	public void endRound() {
		RoundManager.getInstance().virologistMoved();
	}

	public void attack(Axe a, Virologist victim){
		if(!inventory.getSuites().contains(a)){
			return;
		}
		a.use(this, victim);
	}

	/**
	 * Kiveszi a virológust a {@link Field}-ről, kiveszi {@link RoundManager} stepable-jei közül.
	 * A virológus meghal, így irányíthatóvá válik.
	 */
	public void die(){
		field.removeVirologist(this);
		if(RoundManager.getInstance().getVirologists().get(0) == this && RoundManager.getInstance().getMovedCounter()==RoundManager.getInstance().getVirologists().size()-1){
			RoundManager.getInstance().setMovedCounter(RoundManager.getInstance().getMovedCounter()-1);
		}
		RoundManager.getInstance().removeSteppable(this);
		RoundManager.getInstance().removeVirologist(this);
		JOptionPane.showMessageDialog(Game.getInstance().mapPanel,((VirologistView)Game.objectViewHashMap.get(this)).getPlayerName()+" játékos meghalt és kiesett a játékból!", "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	//region GETTEREK ÉS SETTEREK
	/**
	 * Visszaadja, hogy a virológus mozgott-e már a körben.
	 * @return mozgott-e már a körben
	 */
	public boolean isMoved() {
		return moved;
	}
	/**
	 * Beállítja, hogy a virológus mozgott-e már a körben.
	 * @param moved mozgott-e már a körben
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	/**
	 * Visszaadja, hogy a virológus jelenleg le van-e bénulva.
	 * @return le van-e bénulva.
	 */
	public boolean isStunned() {
		return stunned;
	}
	/**
	 * Beállítja, hogy a virológus jelenleg le van-e bénulva.
	 * @param stunned le van-e bénulva
	 */
	public void setStunned(boolean stunned) {
		this.stunned = stunned;
	}
	/**
	 * Visszaadja a megtanult genetikai kódok listáját.
	 * @return a megtanult {@link Gene} lista
	 */
	public List<Gene> getLearnt() {
		return learnt;
	}
	/**
	 * Beállítja a megtanult genetikai kódok listáját.
	 * @param learnt a megtanult {@link Gene} lista
	 */
	public void setLearnt(List<Gene> learnt) {
		this.learnt = learnt;
	}
	/**
	 * Hozzáadja a megtanult genetikai kódokhoz {@code g}-t.
	 * @param g a hozzáadandó {@link Gene}
	 */
	public void addLearnt(Gene g){
		this.learnt.add(g);
	}
	/**
	 * Törli a megtanult genetikai kódok közül {@code g}-t.
	 * @param g a törlendő {@link Gene}
	 */
	public void removeLearnt(Gene g){
		this.learnt.remove(g);
	}
	/**
	 * Visszaadja a mezőt, amin a virológus áll.
	 * @return a {@link Field}, amin a virológus áll
	 */
	public Field getField() {
		return field;
	}
	/**
	 * Beállítja a mezőt, amin a virológus áll.
	 * @param field a {@link Field}, amin a virológus áll
	 */
	public void setField(Field field) {
		this.field = field;
	}
	/**
	 * Visszaadja a virológusra ható módosítók listáját.
	 * @return a virológusra ható {@link SpecialModifier} lista
	 */
	public List<SpecialModifier> getModifiers() {
		return modifiers;
	}
	/**
	 * Beállítja a virológusra ható módosítók listáját.
	 * @param modifiers a virológusra ható {@link SpecialModifier} lista
	 */
	public void setModifiers(List<SpecialModifier> modifiers) {
		this.modifiers = modifiers;
	}
	/**
	 * Hozzáadja {@code m}-et a virológusra ható modósítókhoz.
	 * @param m a hozáadandó {@link SpecialModifier}
	 */
	public void addModifier(SpecialModifier m){
		this.modifiers.add(m);
	}
	/**
	 * Törli {@code m}-et a virológusra ható modósítók közül.
	 * @param m a törlendő {@link SpecialModifier}
	 */
	public void removeModifier(SpecialModifier m){
		this.modifiers.remove(m);
	}
	/**
	 * Visszaadja a virológus tárhelyét, amiben a dolgait tárolja.
	 * @return a virológus tárhelye ({@link Inventory})
	 */
	public Inventory getInventory() {
		return inventory;
	}
	/**
	 * Beállítja a virológus tárhelyét, amiben a dolgait tárolja.
	 * @param inventory a virológus tárhelye ({@link Inventory})
	 */
	public void setInventory(Inventory inventory) {this.inventory = inventory;}
	/**
	 * Visszaadja a virológusra aktuálisan ható védekező dolgokat (ágensek és felszerelések).
	 * @return a virológusra ható {@link IDefense}-ek
	 */
	public List<IDefense> getDefenses() {
		return defenses;
	}
	/**
	 * Beállítja a virológusra aktuálisan ható védekező dolgokat (ágensek és felszerelések).
	 * @param defenses a virológusra ható {@link IDefense}-ek
	 */
	public void setDefenses(List<IDefense> defenses) {
		this.defenses = defenses;
	}
	/**
	 * Hozzáadja {@code d}-t a virológusra aktuálisan ható védekező dolgokhoz.
	 * @param d a hozzáadandó {@link IDefense}
	 */
	public void addDefense(IDefense d){
		this.defenses.add(d);
	}
	/**
	 * Törli {@code d}-t a virológusra aktuálisan ható védekező dolgok közül.
	 * @param d a törlendő {@link IDefense}
	 */
	public void removeDefense(IDefense d){
		this.defenses.remove(d);
	}

	@Override
	public String toString() {
		StringBuilder vDetails = new StringBuilder();
		vDetails.append(getIdForObject(this)).append("\n");
		vDetails.append("\t\t").append(getIdForObject(field)).append("\n");
		List<Suite> wornSuites = inventory.getSuites().stream().filter(Suite::isActive).collect(Collectors.toList());
		if(wornSuites.isEmpty()){
			vDetails.append("\t\t-\n");
		} else {
			vDetails.append("\t\t").append(wornSuites.stream().map(Suite::toString).collect(Collectors.joining(" "))).append("\n");
		}
		List<Suite> storedSuites = inventory.getSuites().stream().filter(it -> !it.isActive()).collect(Collectors.toList());
		if(storedSuites.isEmpty()){
			vDetails.append("\t\t-\n");
		} else {
			vDetails.append("\t\t").append(storedSuites.stream().map(Suite::toString).collect(Collectors.joining(" "))).append("\n");
		}
		if(inventory.getMaterials().isEmpty()){
			vDetails.append("\t\t-\n");
		} else{
			vDetails.append("\t\t").append(inventory.getMaterials().stream().map(ProtoMain::getIdForObject).collect(Collectors.joining(" "))).append("\n");
		}
		if(inventory.getAgents().isEmpty()){
			vDetails.append("\t\t-\n");
		} else {
			vDetails.append("\t\t").append(inventory.getAgents().stream().map(it -> getIdForObject(it)+"["+it.getTimeLeft()+"]").collect(Collectors.joining(" "))).append("\n");
		}
		List<Agent> defenseAgents = defenses.stream().filter(it -> it instanceof Agent).map(it -> (Agent)it).collect(Collectors.toList());
		if(modifiers.isEmpty() && defenseAgents.isEmpty()){
			vDetails.append("\t\t-\n");
		} else {
			vDetails.append("\t\t");
			vDetails.append(modifiers.stream().map(it -> getIdForObject(it)+"["+((Agent)it).getTimeLeft()+"]").collect(Collectors.joining(" ")));
			vDetails.append(defenseAgents.stream().map(it -> getIdForObject(it)+"["+it.getTimeLeft()+"]").collect(Collectors.joining(" ")));
			vDetails.append("\n");
		}
		if(learnt.isEmpty()){
			vDetails.append("\t\t-");
		} else {
			vDetails.append("\t\t").append(learnt.stream().map(ProtoMain::getIdForObject).collect(Collectors.joining(" ")));
		}
		return vDetails.toString();
	}
	//endregion
}
