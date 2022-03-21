package hu.tapasztaltak.model;

public interface IDefense {
	public abstract Boolean tryToBlock(Virologist atc, Virologist vict, Agent a);
}
