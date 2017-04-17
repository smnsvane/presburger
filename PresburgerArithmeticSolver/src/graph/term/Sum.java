package graph.term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import graph.MultipleChildrenBranch;
import graph.VariableAssignment;

public class Sum extends MultipleChildrenBranch<Term> implements Term {

	@Override
	public String getSymbol() { return "SUM"; }
	public Sum(Collection<Term> children) { super(children); }
	public Sum(Term...children) { super(Arrays.asList(children)); }
	public static Sum isolationSum(Sum addedSum, Sum subtractedSum) {
		ArrayList<Term> sumChildren = new ArrayList<>();
		for (Term t : addedSum)
			if (!t.equals(0))
				sumChildren.add(t);
		for (Term t : subtractedSum)
			if (!t.equals(0))
				sumChildren.add(t.multiply(-1));
		Sum sum = new Sum(sumChildren);
		return sum;
	}
	public boolean onlyConstantChildren() {
		for (Term t : this)
			if (!(t instanceof Constant))
				return false;
		return true;
	}
	@Override
	public int evaluate(VariableAssignment assignment) {
		int value = 0;
		for (Term t : this)
			value += t.evaluate(assignment);
		return value;
	}
	@Override
	public Sum multiply(int factor) {
		ArrayList<Term> sumChildren = new ArrayList<>();
		for (Term child : this)
			sumChildren.add(child.multiply(factor));
		Sum sum = new Sum(sumChildren);
		return sum;
	}
	public Sum flatten() {
		ArrayList<Term> children = new ArrayList<>();
		for (Term child : this) {
			if (child instanceof Sum) {
				Sum sumChild = (Sum) child;
				for (Term grandChild : sumChild)
					children.add(grandChild);
			} else if (child instanceof Addition) {
				Addition additionChild = (Addition) child;
				children.add(additionChild.getFirstChild());
				children.add(additionChild.getSecondChild());
			} else if (child instanceof Subtraction) {
				Subtraction subtractionChild = (Subtraction) child;
				children.add(subtractionChild.getFirstChild());
				children.add(subtractionChild.getSecondChild().multiply(-1));
			} else
				children.add(child);
		}
		Sum sum = new Sum(children);
		return sum;
	}
	@Override
	public Sum toSum() { return this; }
	@Override
	public Term simplify() {
		if (numberOfChildren() == 0)
			return new Constant(0);
		if (numberOfChildren() == 1)
			return iterator().next();
		return this;
	}
	public Sum compact() {
		HashMap<String, Integer> varToFactor = new HashMap<>();
		int constantValue = 0;
		for (Term t : this) {
			if (t instanceof Constant) {
				Constant c = (Constant) t;
				constantValue += c.getValue();
			} else if (t instanceof Variable) {
				Variable v = (Variable) t;
				if (varToFactor.containsKey(v.getSymbol()))
					varToFactor.put(v.getSymbol(), varToFactor.get(v.getSymbol()) + v.getFactor());
				else
					varToFactor.put(v.getSymbol(), v.getFactor());
			}
		}
		ArrayList<Term> children = new ArrayList<>();
		for (String symbol : varToFactor.keySet()) {
			int factor = varToFactor.get(symbol);
			if (factor != 0) {
				Variable v = new Variable(factor, symbol);
				children.add(v);
			}
		}
		if (constantValue != 0)
			children.add(new Constant(constantValue));
		Sum sum = new Sum(children);
		return sum;
	}
}
