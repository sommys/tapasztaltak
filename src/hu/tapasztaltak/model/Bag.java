package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;
import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A zsák felszerelést reprezentáló osztály.
 * Aktív viselésével megnő az inventory tároló mérete.
 */
public class Bag extends Suite {
	/**
	 * A zsák mérete, ami aktív hordás alatt, ennyivel növeli meg az {@link Inventory} méretét.
	 */
	private int size = 10;

	/**
	 * Aktív viselésre állítja a zsákot v-n.
	 * @param v a {@link Virologist}, aki viselni kezdi a felszerelést
	 */
	public void activate(Virologist v) {
		Logger.log(this, "activate(Bag)", CALL, v);
		int currentSize = v.getInventory().getSize();
		v.getInventory().setSize(currentSize + size);
		setActive(true);
		Logger.log(this, "", RETURN);
	}

	/**
	 * Kiveszi a zsákot a v aktívan viselt felszerelései közül.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public void deactivate(Virologist v) {
		Logger.log(this, "deactivate(Bag)", CALL, v);
		int currentSize = v.getInventory().getSize();
		v.getInventory().setSize(currentSize - size);
		setActive(false);
		Logger.log(this, "", RETURN);
	}

	//region GETTEREK ÉS SETTEREK

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	//endregion
}
