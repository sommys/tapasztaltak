package hu.tapasztaltak.model;

/**
 * Az ágenseknek az összefoglaló absztrakt ősosztálya,
 * ebből számaznak le a különböző ágensek.
 */
public abstract class Agent implements ISteppable {
	/**
	 * Az ágens hatásából hátralevő idő
	 */
	private int timeLeft = 0;

	/**
	 * Lépteti az ágens hátralevő idejét.
	 */
	public void step() {
		if(timeLeft>0) timeLeft--;
	}

	/**
	 * Felkenődik v-re
	 * @param v a {@link Virologist}, akire felkenődik
	 */
	public abstract void spread(Virologist v);

	//region GETTEREK ÉS SETTEREK
	/**
	 * Visszadja az ágens hatásából hátralevő időt
	 * @return az ágens hatásából hátralevő idő
	 */
	public Integer getTimeLeft() {
		return timeLeft;
	}

	/**
	 * Beállítja az ágens hatásából hátralevő időt {@code timeLeft}-re
	 * @param timeLeft a beállítani kívánt hátralevő idő
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	//endregion
}
