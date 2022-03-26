package hu.tapasztaltak.model;

/**
 * Az ellopható dolgok interfésze.
 */
public interface IStealable {
	/**
	 * Kiveszi az inv-ből az ellopható dolgot.
	 * @param inv az {@link Inventory}, amiből kiveszi
	 */
	void remove(Inventory inv);

	/**
	 * Hozzáadja az inv-hez az ellopható dolgot.
	 * @param inv az {@link Inventory}, amihez hozzáadja
	 */
	void add(Inventory inv);
}
