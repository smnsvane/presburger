package graph.term;

import graph.Node;
import graph.VariableAssignment;

public class Constant implements Term {

	@Override
	public String getSymbol() { return null; }

	public final int value;
	public Constant(int value) { this.value = value; }
	@Override
	public String toString() { return Integer.toString(value); }
	@Override
	public int evaluate(VariableAssignment varAss) { return value; }
	@Override
	public Sum toSum() {
		return Sum.sumFromChildren(this);
	}
	@Override
	public Constant multiply(int factor) {
		int value = this.value * factor;
		Constant constant = new Constant(value);
		return constant;
	}
	@Override
	public Constant simplify() { return this; }
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Constant)
			return value == ((Constant) obj).value;
		if (obj instanceof Integer)
			return value == (int) obj;
		return false;
	}
	@Override
	public Node copy() { return new Constant(value); }
}
