package hu.tapasztaltak.model;

public class Aminoacid implements IMaterial {
	/**
	 * Eldönti {@link IMaterial}-ról, hogy kompatibilis-e az aminosavval
	 * @param m
	 * @return
	 */
	public boolean isCompatible(IMaterial m) {
		if(m.getClass() == Aminoacid.class)
			return true;
		return false;
	}
}
