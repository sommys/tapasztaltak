package hu.tapasztaltak.model;

/**
 * A zsák felszerelést reprezentáló osztály.
 * Aktív viselésével megnő az inventory tároló mérete.
 */
public class Bag extends Suite {
	/**
	 * A zsák mérete, ami aktív hordás alatt, ennyivel növeli meg az {@link Inventory} méretét.
	 */
	private int size;

	/**
	 * Aktív viselésre állítja a zsákot v-n.
	 * @param v a {@link Virologist}, aki viselni kezdi a felszerelést
	 */
	public void activate(Virologist v) {
		int currentSize = v.getInventory().getSize();
		v.getInventory().setSize(currentSize + size);
		setActive(true);
	}

	/**
	 * Kiveszi a zsákot a v aktívan viselt felszerelései közül.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public void deactivate(Virologist v) {
		int currentSize = v.getInventory().getSize();
		v.getInventory().setSize(currentSize - size);
		setActive(false);
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
