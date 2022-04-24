package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import hu.tapasztaltak.proto.ProtoMain;
import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.skeleton.Logger.LogType.*;

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
		Logger.log(this, "move", CALL, f);
		if(stunned||moved){
			Logger.log(this, "", RETURN);
			return;
		}
		Scanner sc = new Scanner(System.in);
		Logger.log(this, "Szomszédos mezőre akar lépni a virológus? (I/N):", QUESTION);
		String neighbourDecision = sc.nextLine();
		if(neighbourDecision.equalsIgnoreCase("I")){
			field.addNeighbour(f);
			f.addNeighbour(field);
		}
		if(field.isNeighbour(f)){
			field.removeVirologist(this);
			f.addVirologist(this);
			field = f;
		}
		Logger.log(this, "", RETURN);
	}

	/**
	 * Felveszi {@code s} felszerelést, ha még nincs rajta 3 felszerelés.
	 * @param s a {@link Suite}, amit fel szeretne venni
	 */
	public void putOnSuite(Suite s) {
		Logger.log(this, "putOnSuite", CALL, s);
		if(stunned||moved){
			Logger.log(this, "", RETURN);
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
		}
		Logger.log(this, "", RETURN);
	}

	/**
	 * A {@code g} genetikai kódhoz tartozó ágenst állítja elő,
	 * ha van elég anyag a virológus tárhelyében.
	 * @param g a {@link Gene} amiből az ágens elő tud állni
	 */
	public void makeAgent(Gene g) {
		Logger.log(this, "makeAgent", CALL, g);
		if(stunned||moved){
			Logger.log(this, "", RETURN);
			return;
		}
		g.make(this.inventory);
		ProtoLogger.logMessage(String.format("%s [usable for %s rounds] added for %s", getIdForObject(g.getAgent()), getIdForObject(g.getAgent().timeLeft),  getIdForObject(this)));
		Logger.log(this, "", RETURN);
	}

	/**
	 * A virológus {@code a} ágenst használja {@code v} virológusra.
	 * @param a a felhasznált {@link Agent}
	 * @param v a megkent {@link Virologist}
	 */
	public void useAgent(Agent a, Virologist v) {
		Logger.log(this, "useAgent", CALL, a, v);
		if(stunned || moved || !canReach(v)){
			Logger.log(this, "", RETURN);
			return;
		}
		spreadInitiation(a, v);
		ProtoLogger.logMessage(String.format("%s used %s on %s", getIdForObject(this), getIdForObject(a),  getIdForObject(v)));
		Logger.log(this, "", RETURN);
	}

	/**
	 * Lecseréli {@code from} felszerelést {@code to} felszerelésre.
	 * @param from a lecserélendő {@link Suite}
	 * @param to a felvenni kívánt {@link Suite}
	 */
	public void switchSuite(Suite from, Suite to) {
		Logger.log(this, "switchSuite", CALL, from, to);
		if(stunned||moved){
			Logger.log(this, "", RETURN);
			return;
		}
		from.deactivate(this);
		to.activate(this);
		Logger.log(this, "", RETURN);
	}

	/**
	 * Tapogatózás, a virológus megismeri a mezőn lévő virológusokat és tárgyakat/genetikai kódot.
	 */
	public void scanning() {
		if(stunned||moved){
			return;
		}
	}

	/**
	 * A virológus kiválasztja, az[oka]t a dolgo[ka]t, amiket felvenne a mezőről
	 * @param available a mezőn elérhető tárgyak ({@link IStealable}), amik közül választani kell
	 * @return a kiválaszott tárgyak ({@link IStealable})
	 */
	public List<IStealable> chooseItem(List<IStealable> available) {
		TestSetup.addObject(available, "available");
		Logger.log(this, "chooseItem", CALL, available);
		TestSetup.removeObject("available");
		List<IStealable> chosen = new ArrayList<>();
		Logger.log(null, "Szeretnél felvenni dolgokat? (I/N):", QUESTION);
		Scanner sc = new Scanner(System.in);
		String pickupDecision = sc.nextLine();
		if(!pickupDecision.equalsIgnoreCase("I")){
			Logger.log(this, "chosen=EmptyList", RETURN);
			return chosen;
		}
		int remaining = inventory.getSize() - inventory.getUsedSize();
		for(IStealable a : available){
			Logger.log(null, String.format("Fel szeretnéd venni %s[%s]-t? (I/N):", TestSetup.getName(a), a.getClass().getSimpleName()), QUESTION);
			String currentPickupDecision = sc.nextLine();
			if(currentPickupDecision.equalsIgnoreCase("I")){
				if(remaining>0){
					chosen.add(a);
					remaining--;
					Logger.log(null, "Hátralevő helyek: "+remaining, COMMENT);
				}
			}
		}

		moved = !chosen.isEmpty();

		Logger.log(this, "chosen="+chosen.stream().map(TestSetup::getName).collect(Collectors.joining(",", "[", "]")), RETURN);
		return chosen;
	}

	/**
	 * A virológus megpróbál lopni {@code from} virológustól.
	 * @param from a {@link Virologist}, akitől lopni akar
	 */
	public void steal(Virologist from) {
		if(!from.isStunned()) {
			ProtoLogger.logMessage(String.format("%s can’t steal from %s [not stunned]", getIdForObject(this), getIdForObject(from)));
			return;
		}
		if (stunned) {
			ProtoLogger.logMessage(getIdForObject(this) + " can't steal [stunned]");
			return;
		}
		if(!canReach(from)){
			ProtoLogger.logMessage(getIdForObject(this) + " can't steal [can't reach]");
			return;
		}
		if (moved) {
			ProtoLogger.logMessage(getIdForObject(this) + " can't steal [moved]");
			return;
		}

		ProtoLogger.logMessage(getIdForObject(this) + " stealing from " + getIdForObject(from));
		ProtoLogger.logMessage(getIdForObject(from) + " has the following items:");

		Inventory inv2 = from.getInventory();
		if(inv2.getSuites().isEmpty() && inv2.getMaterials().isEmpty()) {
			ProtoLogger.logMessage(String.format("%s inventory empty, %s can't steal anything", getIdForObject(from), getIdForObject(this)));
			return;
		}

		IStealable item = inv2.pickItem();

		if (inventory.getUsedSize()==inventory.getSize()) {
			ProtoLogger.logMessage("Inventory full, can’t pickup more items");
		}

		from.stolen(this, item);
		Logger.log(this, "", RETURN);
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
		ProtoLogger.logMessage("Chosen item stolen from " + getIdForObject(this));
	}

	/**
	 * A virológus megtanulja a g genetikai kódot.
	 * @param g a megtanulandó {@link Gene}
	 */
	public void learn(Gene g) {
		if(!learnt.contains(g)){
			learnt.add(g);
			ProtoLogger.logMessage(getIdForObject(this) + " learnt " + g.getClass().getSimpleName());
			Game.getInstance().checkEndGame(this);
		} else {
			ProtoLogger.logMessage(getIdForObject(this) + " already learnt this genetic code");
		}
	}

	/**
	 * Újra engedélyezi a virológusnak, hogy léphessen az új körben, a lejárt ágenseket megszűnteti
	 */
	public void step() {
		Logger.log(this, "step", CALL);
		moved=false;

		inventory.getAgents().forEach(a -> {
			if(a.getTimeLeft() <= 0){
				ProtoLogger.logMessage(String.format("%s expired", getIdForObject(a)));
			}
		});
		inventory.getAgents().removeIf(a -> a.getTimeLeft() <= 0);
		modifiers.forEach(m -> {
			if(!m.isActive()){
				ProtoLogger.logMessage(String.format("%s expired", getIdForObject(m)));
			}
		});
		defenses.forEach(d -> {
			if(!d.stillActive()){
				ProtoLogger.logMessage(String.format("%s expired", getIdForObject(d)));
			}
		});
		modifiers.removeIf(m -> !m.isActive());
		defenses.removeIf(d -> !d.stillActive());

		for(SpecialModifier m : modifiers){
			m.effect(this);
		}
		Logger.log(this, "", RETURN);
	}

	/**
	 * Visszaadja, hogy meg tudja-e érinteni {@code v}-t, azaz egy mezőn állnak-e.
	 * @param v a megérinteni kívánt {@link Virologist}
	 * @return érinthető-e
	 */
	public boolean canReach(Virologist v) {
		Logger.log(this, "canReach", CALL, v);
		Logger.log(this, "can="+(v.getField()==field), RETURN);
		return v.getField() == field;
	}

	/**
	 * Megprbálja megkenni {@code a} ágenssel {@code v} virológust.
	 * @param a a használt {@link Agent}
	 * @param v a megkenni kívánt {@link Virologist}
	 */
	public void spreadInitiation(Agent a, Virologist v) {
		Logger.log(this, "spreadInitiation", CALL, a, v);
		inventory.removeAgent(a);
		for(IDefense d : v.getDefenses()){
			if(d.tryToBlock(this, v, a)){
				Logger.log(this, "", RETURN);
				return;
			}
		}
		a.spread(v);
		Logger.log(this, "", RETURN);
	}

	/**
	 * Szól a {@link RoundManager}-nek, hogy vége van a körének, és léptetheti tovább a köröket.
	 */
	public void endRound() {
		if (RoundManager.getInstance().getMovedCounter() == RoundManager.getInstance().getVirologists().size()) {
			ProtoLogger.logMessage(getIdForObject(this) + " ended round. New round starts");
		}
		else {
			ProtoLogger.logMessage(getIdForObject(this) + " ended round");
		}
		RoundManager.getInstance().virologistMoved();
	}

	/**
	 * Kiveszi a virológust a {@link Field}-ről, kiveszi {@link RoundManager} stepable-jei közül.
	 * A virológus meghal, így irányíthatóvá válik.
	 */
	public void die(){
		//TODO
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
		ProtoLogger.logMessage(String.format("%s agent added with %d rounds left from its effect to %s", m.getClass().getSimpleName(), ((Agent)m).getTimeLeft(), getIdForObject(this)));
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
