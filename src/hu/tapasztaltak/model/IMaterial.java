package hu.tapasztaltak.model;

/**
 * Az anyagok összefogó interfésze, belőlük lehet ágenseket készteni.
 * Az IStealable interfész leszármazottja.
 */
public interface IMaterial extends IStealable {
	/**
	 * Eldönti {@link IMaterial}-ról, hogy kompatibilis-e az anyaggal
	 * @param m a vizsgált anyag
	 * @return ugyan az az anyag, mint a vizsgált
	 */
	boolean isCompatible(IMaterial m);

	/**
	 * Kiveszi az inv-ből az ellopható anyagot.
	 * @param inv az {@link Inventory}, amihez hozzáadja
	 */
	default void add(Inventory inv) {
		inv.getMaterials().add(this);
	}

	/**
	 * Hozzáadja az inv-hez az ellopható anyagot.
	 * @param inv az {@link Inventory}, amiből kiveszi
	 */
	default void remove(Inventory inv) {
		inv.getMaterials().remove(this);
	}
}
