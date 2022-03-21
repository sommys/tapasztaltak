package hu.tapasztaltak.model;

public class Virologist implements ISteppable {
	private Boolean moved;
	private Boolean stunned;
	private Gene learnt;
	private Field field;
	private SpecialModifier modifiers;
	private Inventory inventory;
	private IDefense defenses;
	public void move(Field f) {
	}
	
	public void putOnSuite(Suite s) {
	}
	
	public void makeAgent(Gene g) {
	}
	
	public void useAgent(Agent a, Virologist v) {
	}
	
	public void switchSuite(Suite from, Suite to) {
	}
	
	public void scanning() {
	}
	
	public List<IStealable> chooseItem() {
	}
	
	public void steal(Virologist from) {
	}
	
	public void stolen(Virologist stealer, IStealable what) {
	}
	
	public void learn(Gene g) {
	}
	
	public void step() {
	}
	
	public Boolean canReach(Virologist v) {
	}
	
	public void spreadInitiation(Agent a, Virologist v) {
	}
	
	public void endRound() {
	}
}
