package hu.tapasztaltak.model;

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
		if (m.getClass() == Aminoacid.class) {
			return true;
		}
		return false;
	}
}
