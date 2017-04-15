package graph;

import java.util.ArrayList;

import graph.formula.And;
import graph.formula.Implies;
import graph.formula.Not;
import graph.formula.Or;
import graph.formula.comparator.EqualTo;
import graph.formula.comparator.GreaterThan;
import graph.formula.comparator.GreaterThanOrEqualTo;
import graph.formula.comparator.LessThan;
import graph.formula.comparator.LessThanOrEqualTo;
import graph.formula.comparator.NotEqualTo;
import graph.formula.quantifier.Exists;
import graph.formula.quantifier.Forall;
import graph.term.Addition;
import graph.term.Multiply;
import graph.term.Subtraction;

public class SymbolBinding {

	private int precendenceCounter = 100;
	private int nextPrecendence() { return --precendenceCounter; }
	private int samePrecendence() { return precendenceCounter; }
	private ArrayList<Binding> list = new ArrayList<>();
	public String getSymbol(Class<? extends Node> clazz) {
		for (Binding b : list)
			if (b.clazz.equals(clazz))
				return b.symbol;
		throw new RuntimeException("could not find class "+clazz);
	}
	public int getPrecedence(String symbol) {
		for (Binding b : list)
			if (symbol.equals(b.symbol))
				return b.precedence;
		throw new RuntimeException("could not find symbol "+symbol);
	}

	public SymbolBinding() {
		list.add(new Binding(new Multiply(), nextPrecendence()));
		list.add(new Binding(new Addition(), nextPrecendence()));
		list.add(new Binding(new Subtraction(), samePrecendence()));
		list.add(new Binding(new EqualTo(), nextPrecendence()));
		list.add(new Binding(new NotEqualTo(), nextPrecendence()));
		list.add(new Binding(new GreaterThan(), nextPrecendence()));
		list.add(new Binding(new LessThan(), nextPrecendence()));
		list.add(new Binding(new GreaterThanOrEqualTo(), nextPrecendence()));
		list.add(new Binding(new LessThanOrEqualTo(), nextPrecendence()));
		list.add(new Binding(new Not(), nextPrecendence()));
		list.add(new Binding(new And(), nextPrecendence()));
		list.add(new Binding(new Or(), samePrecendence()));
		list.add(new Binding(new Implies(), nextPrecendence()));
		list.add(new Binding(new Forall(null), nextPrecendence()));
		list.add(new Binding(new Exists(null), samePrecendence()));
	}

	class Binding {
		public final String symbol;
		public final Class<? extends Node> clazz;
		public final int precedence;
		public Binding(Node n, int precedence) {
			symbol = n.getSymbol();
			clazz = n.getClass();
			this.precedence = precedence;
		}
	}
}
