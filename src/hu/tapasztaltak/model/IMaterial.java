package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

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
		Logger.log(this, "add", CALL, inv);
		inv.getMaterials().add(this);
		Logger.log(this, "", RETURN);
	}

	/**
	 * Hozzáadja az inv-hez az ellopható anyagot.
	 * @param inv az {@link Inventory}, amiből kiveszi
	 */
	default void remove(Inventory inv) {
		Logger.log(this, "remove", CALL, inv);
		inv.getMaterials().remove(this);
		Logger.log(this, "", RETURN);
	}
}
