package hu.tapasztaltak.model;

import java.util.List;
import java.util.Random;

/**
 * A játék alapmezőit reprezentáló osztály.
 */
public class Field implements ISteppable {
	/**
	 * A mezőre létrejövő tárgyak megjelenéséig hátralévő körök száma.
	 */
	private int refreshCounter;
	/**
	 * A mezővel szomszédos mezők listája.
	 */
	private List<Field> neighbours;
	/**
	 * A mezőn tartózkodó virológusok listája.
	 */
	private List<Virologist> virologists;

	/**
	 * A {@code v} virológusnak adja, a mezőn található dolgo[ka]t.
	 * @param v a {@link Virologist}, aki a dolgo[ka]t kapja.
	 */
	public void getItem(Virologist v) {
		// Todo: Peti, hogyan fogok listát átadni? setInventory(getinventory() + lista) jó megoldásnak tűnik, viszont akkor a setben kéne sizechecket végezni
		// Todo: Vagy idáig nem juthat el végül túlméretezés?
	}

	/**
	 * Újratölti a mezőre jellemző dolgo[ka]t.
	 */
	public void refresh() {}

	/**
	 * Refreshcountert csökkenti és ha nulla, akkor a refresh függvényt meghívja.
	 */
	public void step() {
		// Todo: Peti, leírás szerint csak ha szükséges, akkor csökkentünk, de ez mit akar pontosan jelenteni?
		if (refreshCounter > 0) {
			refreshCounter--;
		}
		else {
			// Todo: Peti, hibakezelés
			System.out.println("HIBA: Nem várt működés a futás során");
		}

		if (refreshCounter == 0) {
			refresh();
		}
	}

	/**
	 * Megállapítja {@code f} mezőről, hogy szomszédos-e.
	 * @param f a {@link Field}, ami a kérdéses szomszéd
	 * @return szomszédos-e a mező
	 */
	public boolean isNeighbour(Field f) { return neighbours.contains(f); }

	/**
	 * Visszatér egy random szomszédos mezővel.
	 * @return random szomszédos {@link Field}
	 */
	public Field getRandomNeighbour() {
		Random random = new Random();
		return neighbours.get(random.nextInt(neighbours.size()));
	}

	//region GETTEREK ÉS SETTEREK

	/**
	 * Visszaadja, hogy hány kör van még hátra a mező frissítéséig.
	 * @return frissítésig hátralévő körök száma
	 */
	public int getRefreshCounter() { return refreshCounter; }

	/**
	 * Beállítja, hogy hány kör van még hátra a mező frissítéséig.
	 * @param refreshCounter frissítésig hátralévő körök száma
	 */
	public void setRefreshCounter(int refreshCounter) { this.refreshCounter = refreshCounter; }

	/**
	 * Visszaadja a szomszédos mezők listáját.
	 * @return a szomszédos {@link Field} lista
	 */
	public List<Field> getNeighbours() { return neighbours; }

	/**
	 * Beállítja a szomszédos mezők listáját.
	 * @param neighbours a szomszédos {@link Field} lista
	 */
	public void setNeighbours(List<Field> neighbours) { this.neighbours = neighbours; }

	/**
	 * Hozzáadja a szomszédos mezőkhöz {@code neighbour}-t.
	 * @param neighbour a hozzáadandó {@link Field}
	 */
	public void addNeighbours(Field neighbour) { this.neighbours.add(neighbour); }

	/**
	 * Törli {@code neighbour}-t a szomszédos mezők közül
	 * @param neighbour a törlendő {@link Field}
	 */
	public void removeNeighbours(Field neighbour) { this.neighbours.remove(neighbour); }

	/**
	 * Visszaadja a mezőn tartózkodó virológusok listáját.
	 * @return a mezőn tartózkodó {@link Virologist} lista
	 */
	public List<Virologist> getVirologists() { return virologists; }

	/**
	 * Beállítja a mezőn tartózkodó virológusok listáját.
	 * @param virologists a mezőn tartózkodó {@link Virologist} lista
	 */
	public void setVirologists(List<Virologist> virologists) { this.virologists = virologists; }

	/**
	 * Hozzáadja a mezőn tartózkodó virológusokhoz {@code virologist}-ot.
	 * @param virologist a hozzáadandó {@link Virologist}
	 */
	public void addVirologists(Virologist virologist) { this.virologists.add(virologist); }

	/**
	 * Törli {@code virologist}-ot a mezőn tartózkodó virológusok közül.
	 * @param virologist a törlendő {@link Virologist}
	 */
	public void removeVirologists(Virologist virologist) { this.virologists.remove(virologist); }

	//endregion
}
