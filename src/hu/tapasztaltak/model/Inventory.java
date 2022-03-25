package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.TestSetup;

import java.util.List;
import java.util.Scanner;

/**
 * A virológus tárhelyét reprezentáló osztály.
 */
public class Inventory {
	/**
	 * Az aktuális kapacitást mutatja meg, ami kezdetben 10.
	 */
	private int size = 10;
	/**
	 * A tárolt anyagok listája.
	 */
	private List<IMaterial> materials;
	/**
	 * A tárolt felszerelések listája.
	 */
	private List<Suite> suites;
	/**
	 * A felhasználható ágensek listája.
	 */
	private List<Agent> agents;

	/**
	 * Elem kiválasztása lopásnál.
	 * @return kiválasztott {@link IStealable} vagy null, ha nincs ellopható elem
	 */
	public IStealable pickItem() {
		if (materials.isEmpty() && suites.isEmpty()){
			System.out.println("A virológus tárja üres.");
			return null;
		}

		int id = 1;
		System.out.println("Válassz a tárolt elemek közül:");

		for (var m : materials) {
			System.out.printf("%d. %s\n", id, TestSetup.getName(m));
			id++;
		}

		for (var s : suites) {
			System.out.printf("%d. %s\n", id, TestSetup.getName(s));
			id++;
		}

		int stealableSize = materials.size() + suites.size();

		System.out.println("Add meg a választott elem indexét:");
		Scanner input = new Scanner(System.in);
		int value = input.nextInt();
		while(value < 1 || value > stealableSize) {
			System.out.printf("Érvénytelen index, kérlek adj meg 1 és %d közötti számot!\n", stealableSize);
			value = input.nextInt();
		}

		IStealable selected = value > materials.size()
				? suites.remove(value - materials.size() - 1)
				: materials.remove(value - 1);

		return selected;
	}

	//region GETTEREK és SETTEREK

	/**
	 * Visszaadja a tárhely kapacitását.
	 * @return tárhely kapacitása.
	 */
	public int getSize() { return size; }

	/**
	 * Beállítja a tárhely kapacitását.
	 * @param size tárhely kapacitása.
	 */
	public void setSize(int size) { this.size = size; }

	/**
	 * Visszaadja a tárolt anyagok listáját.
	 * @return tárolt {@link IMaterial} lista.
	 */
	public List<IMaterial> getMaterials() { return materials; }

	/**
	 * Beállítja a tárolt anyagok listáját.
	 * @param materials tárolt {@link IMaterial} lista.
	 */
	public void setMaterials(List<IMaterial> materials) { this.materials = materials; }

	/**
	 * Hozzáadja a tárolt anyagok listájához {@code material}-t.
	 * @param material a hozzáadandó {@link IMaterial}
	 */
	public void addMaterials(IMaterial material) { materials.add(material); }

	/**
	 * Törli a tárolt anyagok listájából {@code material}-t.
	 * @param material a törlendő {@link IMaterial}
	 */
	public void removeMaterials(IMaterial material) { materials.remove(material); }

	/**
	 * Visszaadja a tárolt védőfelszerelések listáját.
	 * @return tárolt {@link Suite} lista.
	 */
	public List<Suite> getSuites() { return suites; }

	/**
	 * Beállítja a tárolt védőfelszerelések listáját.
	 * @param suites tárolt {@link Suite} lista.
	 */
	public void setSuites(List<Suite> suites) { this.suites = suites; }

	/**
	 * Hozzáadja {@code suite}-ot a tárolt védőfelszerelések listáját.
	 * @param suite a hozzáadandó {@link Suite}
	 */
	public void addSuite(Suite suite) { suites.add(suite); }

	/**
	 * Törli {@code suite}-ot a tárolt védőfelszerelések listájából.
	 * @param suite a törlendő {@link Suite}
	 */
	public void removeSuite(Suite suite) { suites.remove(suite); }

	/**
	 * Visszaadja a tárolt ágensek listáját.
	 * @return tárolt {@link Agent} lista.
	 */
	public List<Agent> getAgents() { return agents;	}

	/**
	 * Beállítja a tárolt ágensek listáját.
	 * @param agents tárolt {@link Agent} lista.
	 */
	public void setAgents(List<Agent> agents) { this.agents = agents; }

	/**
	 * Hozzáadja {@code agent}-et a tárolt ágensek listájához.
	 * @param agent a hozzáadandó {@link Agent}
	 */
	public void addAgent(Agent agent) { agents.add(agent);	}

	/**
	 * Törli {@code agent}-et a tárolt ágensek listájából
	 * @param agent a törlendő {@link Agent}
	 */
	public void removeAgent(Agent agent) { agents.remove(agent); }

	//endregion
}
