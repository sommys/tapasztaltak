package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A Nucleotid anyagot reprezentáló osztály.
 */
public class Nucleotid implements IMaterial {
	/**
	 * Eldönti {@link IMaterial}-ról, hogy kompatibilis-e a nucleotiddal.
	 * @param m a vizsgált anyag
	 * @return ugyan az-e a két anyag
	 */
	public boolean isCompatible(IMaterial m) {
		Logger.log(this, "isCompatibleNucleotid", CALL, m);
		if (m.getClass() == Nucleotid.class) {
			Logger.log(this, "kompatibilis="+(m.getClass() == Nucleotid.class), RETURN);
			return true;
		}
		Logger.log(this, "kompatibilis="+(m.getClass() == Nucleotid.class), RETURN);
		return false;
	}
}
