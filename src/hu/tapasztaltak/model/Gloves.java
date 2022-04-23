package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import java.util.Scanner;

import static hu.tapasztaltak.skeleton.Logger.LogType.*;

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
	 * Azt mutatja, hányszor lehet még a kesztyűt a hámlásig felhasználni
	 */
	private int useCount = 3;

	/**
	 * Visszakenhető vele egy ágens, amelyet rákentek a virológusra.
	 * @param atc a támadó {@link Virologist}
	 * @param vict a megtámadott {@link Virologist}
	 * @param a a használt {@link Agent}
	 * @return az, hogy visszakenjük-e a támadóra
	 */
	public boolean tryToBlock(Virologist atc, Virologist vict, Agent a) {
		Logger.log(this, "tryToBlock", CALL, atc, vict, a);
		if(atc != vict && !used && useCount > 0 && !vict.isStunned()){
			Scanner sc = new Scanner(System.in);
			Logger.log(null, "Visszakennéd a támadóra? (I/N):", QUESTION);
			String input = sc.nextLine();
			if(input.equalsIgnoreCase("I")){
				used = true;
				useCount--;
				vict.useAgent(a,atc);
				if(useCount==0) remove(vict.getInventory());
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

	public int getUseCount() { return useCount; }

	public void setUseCount(int useCount) { this.useCount = useCount; }

	@Override
	public String toString() { return super.toString() + "[" + useCount + "]"; }
	//endregion
}
