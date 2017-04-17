package graph;

import parser.SymbolBinding;

public abstract class Branch<Child extends Node> implements Node, Iterable<Child> {

	public abstract void replaceChild(Child victim, Child overtaker);
	private boolean locked = true;
	public boolean isLocked() { return locked; }
	public void lock() { locked = true; }
	public void unlock() { locked = false; }
	@Override
	public String getSymbol() { return SymbolBinding.getSymbol(getClass()); }
}
