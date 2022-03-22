package hu.tapasztaltak.model;

import java.util.List;

/**
 * A játékosok által irányított virológusokat reprezentáló osztály.
 */
public class Virologist implements ISteppable {
	/**
	 * A virológus mozgott-e már az aktuális körben.
	 */
	private boolean moved;
	/**
	 * A virológus jelenleg le van-e bénulva.
	 */
	private boolean stunned;
	/**
	 * A megtanult genetikai kódok listája.
	 */
	private List<Gene> learnt;
	/**
	 * A mező, amin a virológus tartózkodik.
	 */
	private Field field;
	/**
	 * A virológusra hatással levő speciális módosítók listája.
	 */
	private List<SpecialModifier> modifiers;
	/**
	 * A virológus tárhelye, amiben dolgokat tárol.
	 */
	private Inventory inventory;
	/**
	 * A virológust ágenskenéstől védelmező védőfelszerelések listája.
	 */
	private List<IDefense> defenses;

	/**
	 * A virológus az {@code f} mezőre mozog.
	 * @param f a {@link Field}, amire mozogni szeretne
	 */
	public void move(Field f) {
		//todo Soma
	}

	/**
	 * Felveszi {@code s} felszerelést, ha még nincs rajta 3 felszerelés.
	 * @param s a {@link Suite}, amit fel szeretne venni
	 */
	public void putOnSuite(Suite s) {
		//todo Soma
	}

	/**
	 * A {@code g} genetikai kódhoz tartozó ágenst állítja elő,
	 * ha van elég anyag a virológus tárhelyében.
	 * @param g a {@link Gene} amiből az ágens elő tud állni
	 */
	public void makeAgent(Gene g) {
		//todo Soma
	}

	/**
	 * A virológus {@code a} ágenst használja {@code v} virológusra.
	 * @param a a felhasznált {@link Agent}
	 * @param v a megkent {@link Virologist}
	 */
	public void useAgent(Agent a, Virologist v) {
		//todo Soma
	}

	/**
	 * Lecseréli {@code from} felszerelést {@code to} felszerelésre.
	 * @param from a lecserélendő {@link Suite}
	 * @param to a felvenni kívánt {@link Suite}
	 */
	public void switchSuite(Suite from, Suite to) {
		//todo Soma
	}

	/**
	 * Tapogatózás, a virológus megismeri a mezőn lévő virológusokat és tárgyakat/genetikai kódot.
	 */
	public void scanning() {
		//todo Soma
	}

	/**
	 * A virológus kiválasztja, az[oka]t a dolgo[ka]t, amiket felvenne a mezőről
	 * @return a kiválaszott tárgyak ({@link IStealable})
	 */
	public List<IStealable> chooseItem() {
		//todo Soma
		return null;
	}

	/**
	 * A virológus megpróbál lopni {@code from} virológustól.
	 * @param from a {@link Virologist}, akitől lopni akar
	 */
	public void steal(Virologist from) {
		//todo Soma
	}

	/**
	 * Megnézi, hogy a lopás, amit {@code stealer} kezdeményezett,
	 * végbe tud-e menni úgy, hogy {@code stealer} megkapja {@code what}-ot.
	 * @param stealer a támadó {@link Virologist}
	 * @param what az ellopni kívánt {@link IStealable}
	 */
	public void stolen(Virologist stealer, IStealable what) {
		//todo Soma
	}

	/**
	 * A virológus megtanulja a g genetikai kódot.
	 * @param g a megtanulandó {@link Gene}
	 */
	public void learn(Gene g) {
		//todo Soma
	}

	/**
	 * Újra engedélyezi a virológusnak, hogy léphessen az új körben, a lejárt ágenseket megszűnteti
	 */
	public void step() {
		//todo Soma
	}

	/**
	 * Visszaadja, hogy meg tudja-e érinteni {@code v}-t, azaz egy mezőn állnak-e.
	 * @param v a megérinteni kívánt {@link Virologist}
	 * @return érinthető-e
	 */
	public boolean canReach(Virologist v) {
		//todo Soma
		return false;
	}

	/**
	 * Megprbálja megkenni {@code a} ágenssel {@code v} virológust.
	 * @param a a használt {@link Agent}
	 * @param v a megkenni kívánt {@link Virologist}
	 */
	public void spreadInitiation(Agent a, Virologist v) {
		//todo Soma
	}

	/**
	 * Szól a {@link RoundManager}-nek, hogy vége van a körének, és léptetheti tovább a köröket.
	 */
	public void endRound() {
		//todo Soma
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
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
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
	//endregion
}
