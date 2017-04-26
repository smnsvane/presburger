package graph;

import java.util.List;

import parser.SymbolBindings;

public abstract class Branch<Child extends Node> implements Node, Iterable<Child> {

	public abstract List<Child> getChildren();
	public abstract void replaceChild(Child victim, Child overtaker);
	private boolean locked = true;
	public boolean isLocked() { return locked; }
	public void lock() { locked = true; }
	public void unlock() { locked = false; }
	@Override
	public final String getSymbol() { return SymbolBindings.getSymbol(getClass()); }
}
