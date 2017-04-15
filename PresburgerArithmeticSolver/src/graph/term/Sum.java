package graph.term;

import java.util.HashMap;

import graph.MultipleChildrenBranch;
import graph.VariableAssignment;

public class Sum extends MultipleChildrenBranch<Term> implements Term {

	@Override
	public String getSymbol() { return "SUM"; }
	public static Sum sumFromChildren(Term...children) {
		Sum sum = new Sum();
		for (Term t : children)
			if (!t.equals(new Constant(0)))
				sum.addChild(t);
		return sum;
	}
	public static Sum isolationSum(Sum positiveSum, Sum negativeSum) {
		Sum sum = new Sum();
		sum.editChildren().addAll(positiveSum.viewChildren());
		for (Term t : negativeSum.viewChildren())
			sum.addChild(t.multiply(-1));
		return sum;
	}
	public boolean isConstant() {
		for (Term t : viewChildren())
			if (!(t instanceof Constant))
				return false;
		return true;
	}
	@Override
	public String toString() {
		if (viewChildren().isEmpty())
			return "SUM[0]";
		return "SUM"+viewChildren();
	}
	@Override
	public int evaluate(VariableAssignment varAss) {
		int value = 0;
		for (Term t : viewChildren())
			value += t.evaluate(varAss);
		return value;
	}
	@Override
	public Sum multiply(int factor) {
		for (int i = 0; i < viewChildren().size(); i++)
			editChildren().set(i, viewChildren().get(i).multiply(factor));
		return this;
	}
	public Sum flatten() {
		for (int i = 0; i < viewChildren().size(); i++) {
			if (viewChildren().get(i) instanceof Sum) {
				Sum child = (Sum) editChildren().remove(i--);
				editChildren().addAll(child.viewChildren());
			}
		}
		return this;
	}
	@Override
	public Sum toSum() { return this; }
	@Override
	public Sum simplify() {
		boolean haveSumChildren = false;
		for (Term t : viewChildren())
			if (t instanceof Sum)
				haveSumChildren = true;
		if (haveSumChildren)
			return flatten();
		return compact();
	}
	public Sum compact() {
		HashMap<String, Integer> varToFactor = new HashMap<>();
		int constantValue = 0;
		for (int i = 0; i < viewChildren().size(); i++) {
			if (viewChildren().get(i) instanceof Constant) {
				Constant c = (Constant) editChildren().remove(i);
				constantValue += c.value;
			} else if (viewChildren().get(i) instanceof Variable) {
				Variable v = (Variable) editChildren().remove(i);
				if (varToFactor.containsKey(v.getSymbol()))
					varToFactor.put(v.getSymbol(), varToFactor.get(v.getSymbol()) + v.factor);
				else
					varToFactor.put(v.getSymbol(), v.factor);
			}
		}
		for (String symbol : varToFactor.keySet()) {
			int factor = varToFactor.get(symbol);
			if (factor != 0) {
				Variable v = new Variable(factor, symbol);
				addChild(v);
			}
		}
		if (constantValue != 0)
			addChild(new Constant(constantValue));
		return this;
	}
}
