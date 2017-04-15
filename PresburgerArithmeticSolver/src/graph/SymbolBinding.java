package graph;

import java.util.ArrayList;

import graph.formula.And;
import graph.formula.Implies;
import graph.formula.Not;
import graph.formula.Or;
import graph.formula.quantifier.Exists;
import graph.formula.quantifier.Forall;

public class SymbolBinding {

	private int precendenceCounter = 100;
	private int nextPrecendence() { return --precendenceCounter; }
	private int samePrecendence() { return precendenceCounter; }
	private ArrayList<Binding> list = new ArrayList<>();
	
	public SymbolBinding() {
		list.add(new Binding("~", new Not(), 10));
		list.add(new Binding("~", new Not(), nextPrecendence()));
		list.add(new Binding("&", new And(), nextPrecendence()));
		list.add(new Binding("/", new Or(), nextPrecendence()));
		list.add(new Binding("->", new Implies(), nextPrecendence()));
		list.add(new Binding("~", new Forall("x"), nextPrecendence()));
		list.add(new Binding("~", new Exists("x"), samePrecendence()));
	}

	class Binding {
		public final String symbol;
		public final Class<? extends Object> clazz;
		public final int precedence;
		public Binding(String symbol, Object instance, int precedence) {
			this.symbol = symbol;
			this.clazz = instance.getClass();
			this.precedence = precedence;
		}
	}
}
