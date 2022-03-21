package hu.tapasztaltak.model;

public abstract class Agent implements ISteppable {
	private Integer timeLeft;
	public void step() {
	}
	
	public abstract void spread(Virologist v);
}
