package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoMain;
import hu.tapasztaltak.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

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
		Logger.log(this, "make", CALL, inv);
		int i = 0;
		ArrayList<IMaterial> found = new ArrayList<>();

		for (IMaterial m : materials) {
			while (i < inv.getMaterials().size() && !m.isCompatible(inv.getMaterials().get(i))) {
				i++;
			}
			if (i != inv.getMaterials().size()) {
				found.add(inv.getMaterials().get(i));
				inv.getMaterials().remove(i);
				i=0;
			} else {
				inv.getMaterials().addAll(found);
				Logger.log(this, "", RETURN);
				return;
			}
		}
		if(materials.size() == found.size()){
			Agent newAgent = (Agent) agent.clone();
			inv.addAgent(newAgent);
		}
		Logger.log(this, "", RETURN);
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
		if(agent == null) return;
		this.agent = agent;
		materials=agent.getRecipe();
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

	@Override
	public String toString() { return getIdForObject(this)+"["+ProtoMain.getGeneId(this)+"]";	}
	//endregion
}
