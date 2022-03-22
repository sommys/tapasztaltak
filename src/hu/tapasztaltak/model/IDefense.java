package hu.tapasztaltak.model;

public interface IDefense {
	/**
	 * Egy másik virológus által felkenni próbált ágenst próbál meg levédeni
	 * @param atc a támadó {@link Virologist}
	 * @param vict a megtámadott {@link Virologist}
	 * @param a a használt {@link Agent}
	 * @return sikeres volt-e a támadás
	 */
	boolean tryToBlock(Virologist atc, Virologist vict, Agent a);
}
