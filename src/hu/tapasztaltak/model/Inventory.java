package hu.tapasztaltak.model;

import java.util.ArrayList;
import java.util.List;

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
	private List<IMaterial> materials = new ArrayList<>();
	/**
	 * A tárolt felszerelések listája.
	 */
	private List<Suite> suites = new ArrayList<>();
	/**
	 * A felhasználható ágensek listája.
	 */
	private List<Agent> agents = new ArrayList<>();

	//region GETTEREK és SETTEREK

	/**
	 * Visszaadja a felhasznált tárhelyek számát
	 * @return a felhasznált tárhelyek száma
	 */
	public int getUsedSize(){
		int usedSize = materials.size();
		for(Suite s : suites){
			if(!s.isActive()) usedSize++;
		}
		return usedSize;
	}

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
	public void addMaterial(IMaterial material) { if(getUsedSize() < size) materials.add(material); }

	/**
	 * Törli a tárolt anyagok listájából {@code material}-t.
	 * @param material a törlendő {@link IMaterial}
	 */
	public void removeMaterial(IMaterial material) { materials.remove(material); }

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
	public void addSuite(Suite suite) {
		if(getUsedSize() < size) suites.add(suite);
	}

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
	public void addAgent(Agent agent) { agents.add(agent); }

	/**
	 * Törli {@code agent}-et a tárolt ágensek listájából
	 * @param agent a törlendő {@link Agent}
	 */
	public void removeAgent(Agent agent) { agents.remove(agent); }

	//endregion
}
