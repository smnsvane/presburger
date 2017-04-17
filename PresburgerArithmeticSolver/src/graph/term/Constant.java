package graph.term;

import graph.VariableAssignment;

public class Constant implements Term {

	@Override
	public String getSymbol() { return null; }
	private int value;
	public int getValue() { return value; }
	public Constant(int value) { this.value = value; }
	@Override
	public String toString() { return Integer.toString(value); }
	@Override
	public int evaluate(VariableAssignment varAss) { return value; }
	@Override
	public Sum toSum() { return new Sum(this); }
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
	public Constant copy() { return new Constant(getValue()); }
}
