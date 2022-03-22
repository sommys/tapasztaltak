package hu.tapasztaltak.model;

/**
 * A védő ágenst reprezentáló osztály.
 * Ha ez fel van kenve egy virológusra, nem lehet rá ágenst kenni.
 */
public class Protect extends Agent implements IDefense {
	/**
	 * Egy virológus által felkenni próbált ágenst véd le 100% hatékonysággal
	 * @param atc a támadó {@link Virologist}
	 * @param vict a megtámadott {@link Virologist}
	 * @param a a használt {@link Agent}
	 * @return sikeres volt-e a támadás
	 */
	public boolean tryToBlock(Virologist atc, Virologist vict, Agent a) {
		return true;
	}

	/**
	 * Hozzáadja a felkent ágenst v éppen aktívan rajta lévő védő dolgok listájához.
	 * @param v a {@link Virologist}, akire felkenődik
	 */
	public void spread(Virologist v) {
		//todo Soma
	}
}
