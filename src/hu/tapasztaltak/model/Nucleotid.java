package hu.tapasztaltak.model;

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
		if(m.getClass() == Nucleotid.class)
			return true;
		return false;
	}
}
