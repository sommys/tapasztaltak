package hu.tapasztaltak.model;

/**
 * A védelmeket összefogó interfész, valamilyen védelmet biztosítanak a virológusnak az ágensekkel szemben.
 */
public interface IDefense {
	/**
	 * Egy másik virológus által felkenni próbált ágenst próbál meg levédeni
	 * @param atc a támadó {@link Virologist}
	 * @param vict a megtámadott {@link Virologist}
	 * @param a a használt {@link Agent}
	 * @return sikeres volt-e a támadás
	 */
	boolean tryToBlock(Virologist atc, Virologist vict, Agent a) throws Exception;

	/**
	 * Megadja, hogy aktívan hat-e még a védő
	 * @return aktívan hat-e még a védő
	 */
	boolean stillActive();
}
