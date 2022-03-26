package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

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
		Logger.log(this, "tryToBlock", CALL, atc, vict, a);
		Logger.log(this, "success=true", RETURN);
		return true;
	}

	/**
	 * Hozzáadja a felkent ágenst v éppen aktívan rajta lévő védő dolgok listájához.
	 * @param v a {@link Virologist}, akire felkenődik
	 */
	public void spread(Virologist v) {
		Logger.log(this, "spread", CALL, v);
		v.addDefense(this);
		Logger.log(this, "success=true", RETURN);
	}

	/**
	 * Készít egy felhasználható másolatot az ágensből
	 * @return felhasználható másolat
	 */
	public Agent clone(){
		Logger.log(this, "clone", CALL);
		Protect newAgent = new Protect();
		newAgent.setTimeLeft(3);
		Logger.log(this, "newAgent", RETURN);
		return newAgent;
	}
}
