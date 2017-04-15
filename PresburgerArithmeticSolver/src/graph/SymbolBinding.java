package graph;

import java.util.ArrayList;

import graph.logic.Not;
import graph.logic.binary.And;
import graph.logic.binary.Implies;
import graph.logic.binary.Or;
import graph.math.quantifier.Exists;
import graph.math.quantifier.Forall;

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
		public final Class<? extends NodeBranch> clazz;
		public final int precedence;
		public Binding(String symbol, NodeBranch instance, int precedence) {
			this.symbol = symbol;
			this.clazz = instance.getClass();
			this.precedence = precedence;
		}
	}
}
