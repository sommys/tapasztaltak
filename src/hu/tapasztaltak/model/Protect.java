package hu.tapasztaltak.model;


import hu.tapasztaltak.view.ProtectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	 * Megadja, hogy aktívan hat-e még a védő
	 * @return aktívan hat-e még a védő
	 */
	@Override
	public boolean stillActive() {
		return timeLeft > 0;
	}

	/**
	 * Visszaadja az ágens készítéséhez szükséges receptet, azaz hogy milyen anyagok kellenek hozzá
	 * @return milyen anyagok ({@link IMaterial}) kellenek a készítéséhez
	 */
	@Override
	public List<IMaterial> getRecipe() {
		Nucleotid protectN1 = new Nucleotid();
		Nucleotid protectN2 = new Nucleotid();
		Aminoacid protectA1 = new Aminoacid();
		Aminoacid protectA2 = new Aminoacid();
		return new ArrayList<>(Arrays.asList(protectA1, protectA2, protectN1, protectN2));
	}

	/**
	 * Hozzáadja a felkent ágenst v éppen aktívan rajta lévő védő dolgok listájához.
	 * @param v a {@link Virologist}, akire felkenődik
	 */
	public void spread(Virologist v) {
		v.addDefense(this);
		Game.objectViewHashMap.get(v).update();
	}

	/**
	 * Készít egy felhasználható másolatot az ágensből
	 * @return felhasználható másolat
	 */
	public Agent clone(){
		Protect newAgent = new Protect();
		newAgent.setTimeLeft(3);
		Game.addView(newAgent, new ProtectView(newAgent));
		RoundManager.getInstance().addSteppable(newAgent);
		return newAgent;
	}
}
