package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import hu.tapasztaltak.proto.ProtoMain;
import hu.tapasztaltak.skeleton.Logger;
import java.util.Random;
import java.util.Scanner;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.skeleton.Logger.LogType.*;

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
	public boolean tryToBlock(Virologist atc, Virologist vict, Agent a) throws Exception {
		Random r = new Random();
		double result = r.nextDouble();
		if(ProtoMain.randomness) {
			if (result <= 0.823) {
				ProtoLogger.logMessage(getIdForObject(this) + " protected");
			} else {
				ProtoLogger.logMessage(getIdForObject(this) + " didn't protect");
			}
			return (result <= 0.823);
		}else{
			int choice = ProtoLogger.logQuestion("Do you want " + getIdForObject(this) + " to protect? (Y/N):",true);
			if(choice == 'Y'){
				ProtoLogger.logMessage(getIdForObject(this) + " protected");
				return true;
			} else if(choice == 'N'){
				ProtoLogger.logMessage(getIdForObject(this) + " didn't protect");
				return false;
			} else {
				return false;
			}
		}
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
		v.addDefense(this);
		setActive(true);
		ProtoLogger.logMessage(String.format("%s is now worn by %s", getIdForObject(this),getIdForObject(v)));
	}

	/**
	 * Kiveszi a köpenyt a v aktívan viselt felszerelései közül.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public void deactivate(Virologist v) {
		v.removeDefense(this);
		setActive(false);
	}
}
