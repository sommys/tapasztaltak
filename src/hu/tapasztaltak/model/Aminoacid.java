package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * Az Aminoacid anyagot reprezentáló osztály.
 */
public class Aminoacid implements IMaterial {
	/**
	 * Eldönti {@link IMaterial}-ról, hogy kompatibilis-e az aminosavval
	 * @param m a vizsgált anyag
	 * @return ugyan az-e a két anyag
	 */
	public boolean isCompatible(IMaterial m) {
		Logger.log(this, "isCompatibleAminoacid", CALL, m);
		if (m.getClass() == Aminoacid.class) {
			Logger.log(this, "kompatibilis="+(m.getClass() == Aminoacid.class), RETURN);
			return true;
		}
		Logger.log(this, "kompatibilis="+(m.getClass() == Aminoacid.class), RETURN);
		return false;
	}
}
