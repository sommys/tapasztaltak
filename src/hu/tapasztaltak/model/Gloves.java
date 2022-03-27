package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import java.util.Scanner;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A kesztyű felszerelést reprezentáló osztály.
 * Aktív viselésével egy éppen kenésben lévő ágens visszakenhető a folyamatot indító virológusra.
 */
public class Gloves extends Suite implements IDefense {
	/**
	 * Azt mutatja, hogy egy folyamatban lévő kenésben felhasználták-e már a kesztyüt.
	 */
	private boolean used = false;

	/**
	 * Visszakenhető vele egy ágens, amelyet rákentek a virológusra.
	 * @param atc a támadó {@link Virologist}
	 * @param vict a megtámadott {@link Virologist}
	 * @param a a használt {@link Agent}
	 * @return az, hogy visszakenjük-e a támadóra
	 */
	public boolean tryToBlock(Virologist atc, Virologist vict, Agent a) {
		Logger.log(this, "tryToBlock", CALL, atc, vict, a);
		if(atc != vict && !used){
			Scanner sc = new Scanner(System.in);
			System.out.println("Visszakennéd a támadóra? (I/N)");
			String input = sc.nextLine();
			if(input.equals("I")){
				used = true;
				vict.useAgent(a,atc);
				Logger.log(this, "blockingSuccess="+true, RETURN);
				return true;
			}
		}
		Logger.log(this, "blockingSuccess="+false, RETURN);
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
	 * Aktív viselésre állítja a kesztyűt v-n.
	 * @param v a {@link Virologist}, aki viselni kezdi a felszerelést
	 */
	public void activate(Virologist v) {
		Logger.log(this, "activate", CALL, v);
		v.addDefense(this);
		setActive(true);
		Logger.log(this, "", RETURN);
	}

	/**
	 * Kiveszi a kesztyűt a v aktívan viselt felszerelései közül.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public void deactivate(Virologist v) {
		Logger.log(this, "deactivate", CALL, v);
		v.removeDefense(this);
		setActive(false);
		Logger.log(this, "", RETURN);
	}

	//region GETTEREK ÉS SETTEREK

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	//endregion
}
