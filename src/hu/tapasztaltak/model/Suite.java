package hu.tapasztaltak.model;

public abstract class Suite implements IStealable {
	private Boolean active;
	public abstract void activate(Virologist v);
	public void deactivate(Virologist v) {
	}
	
	public void add(Inventory inv) {
	}
	
	public void remove(Inventory inv) {
	}
}
