package hu.tapasztaltak.model;

/**
 * A speciális módosítókat összefogó interfész, valamilyen hatást váltanak ki a virológusokra.
 */
public interface SpecialModifier {
	/**
	 * Kifejti a hatását a paraméterben átadott {@link Virologist}-ra
	 * @param v {@link Virologist}, akire kifejti a hatását
	 */
	void effect(Virologist v) throws Exception;

	/**
	 * Megadja, hogy aktívan hat-e még a módosító
	 * @return aktívan hat-e a módosító
	 */
	boolean isActive();
}
