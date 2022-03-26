package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.List;

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
		Logger.log(this, "blockingSuccess=true", RETURN);
		return true;
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
		TestSetup.addObject(protectN1, "protectN1");
		TestSetup.addObject(protectN2, "protectN2");
		TestSetup.addObject(protectA1, "protectA1");
		TestSetup.addObject(protectA2, "protectA2");
		return List.of(protectA1, protectA2, protectN1, protectN2);
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
