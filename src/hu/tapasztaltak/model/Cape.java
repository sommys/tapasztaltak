package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import java.util.Random;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A köpeny felszerelést reprezentáló osztály.
 * Aktív viselésével a virológusra kent ágensek hatásba lépését 82,3% valószínűséggel kivédi.
 */
public class Cape extends Suite implements IDefense {
	/**
	 * Random valószínűséget dob arra, hogy kivédi-e a köpeny a támadó ágenst, és ha ez a szám 824 alatt van, akkor sikeres a védés.
	 * @param atc a támadó {@link Virologist}
	 * @param vict a megtámadott {@link Virologist}
	 * @param a a használt {@link Agent}
	 * @return a védés sikeressége
	 */
	public boolean tryToBlock(Virologist atc, Virologist vict, Agent a) {
		Logger.log(this, "tryToBlock", CALL, atc,vict, a);
		Random r = new Random();
		int result = r.nextInt(1000);
		if (result < 824){
			Logger.log(this, "blockingSuccess="+(result < 824), RETURN);
			return true;
		}
		Logger.log(this, "blockingSuccess="+(result < 824), RETURN);
		return false;
	}

	/**
	 * Megadja, hogy aktívan hat-e még a védő
	 * @return aktívan hat-e még a védő
	 */
	@Override
	public boolean stillActive() {
		return active;
	}

	/**
	 * Aktív viselésre állítja a köpenyt v-n.
	 * @param v a {@link Virologist}, aki viselni kezdi a felszerelést
	 */
	public void activate(Virologist v) {
		Logger.log(this, "activate", CALL, v);
		v.addDefense(this);
		setActive(true);
		Logger.log(this, "", RETURN);
	}

	/**
	 * Kiveszi a köpenyt a v aktívan viselt felszerelései közül.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public void deactivate(Virologist v) {
		Logger.log(this, "deactivate", CALL, v);
		v.removeDefense(this);
		setActive(false);
		Logger.log(this, "", RETURN);
	}
}
