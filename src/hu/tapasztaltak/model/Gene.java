package hu.tapasztaltak.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A genetikai kódot reprezentáló osztály.
 * Egy ágenst és az annak előállításához szükséges anyagokat tárolja.
 * Amennyiben a virológusnak van elég és megfelelő típusu anyaga, akkor el is készíti.
 */
public class Gene {
	/**
	 * a genetikai kódhoz tartozó ágens, amit készíteni lehet.
	 */
	private Agent agent = null;
	/**
	 * Tárol annyi és olyan anyagot, amennyi az ágens előállításához szükséges.
	 */
	private List<IMaterial> materials = new ArrayList<>();
	/**
	 * megpróbálja elkészíteni az inventory-ban található anyagokból az ágenst.
	 * Ha van elég anyag, akkor hozzá ad egy felhasználható példányt az {@code inv} {@link Inventory}-hoz.
	 * @param inv az {@link Inventory}, amiben az anyagok vannak és amihez hozzáadja az ágenst, ha sikerült elkészíteni
	 */
	public void make(Inventory inv) {
		//todo Soma
	}
	//region GETTEREK ÉS SETTEREK
	/**
	 * Visszaadja a genetikai kódból készíthető ágenst.
	 * @return az elkészíthető {@link Agent}
	 */
	public Agent getAgent() {
		return agent;
	}
	/**
	 * Beállítja a genetikai kódból készíthető ágenst {@code agent}-re.
	 * @param agent az elkészíthető {@link Agent}
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	/**
	 * Visszaadja az előállításhoz szükséges anyagok listáját.
	 * @return az előállításhoz szükséges {@link IMaterial} lista
	 */
	public List<IMaterial> getMaterials() {
		return materials;
	}
	/**
	 * Beállítja az előállításhoz szükséges anyagok listáját {@code materials}-ra.
	 * @param materials az előállításhoz szükséges {@link IMaterial} lista
	 */
	public void setMaterials(List<IMaterial> materials) {
		this.materials = materials;
	}
	/**
	 * Hozzáadja az előállításhoz szükséges anyagok listájához {@code material}-t.
	 * @param material a hozzáadandó {@link IMaterial}
	 */
	public void addMaterial(IMaterial material){
		this.materials.add(material);
	}
	/**
	 * Hozzáadja az előállításhoz szükséges anyagok listájához {@code material}-t.
	 * @param material a hozzáadandó {@link IMaterial}
	 */
	public void removeMaterial(IMaterial material){
		this.materials.remove(material);
	}
	//endregion
}
