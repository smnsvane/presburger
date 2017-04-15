package graph.term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import graph.Branch;
import graph.Node;
import graph.VariableAssignment;

public class Sum implements Term, Branch {

	private final ArrayList<Term> children = new ArrayList<>();
	@Override
	public Iterator<Node> iterator() {
		return new Iterator<Node>() {
			Iterator<Term> i = children.iterator();
			@Override
			public Node next() { return i.next(); }
			@Override
			public boolean hasNext() { return i.hasNext(); }
		};
	}
	public Sum() {}
	public static Sum sumFromChildren(Term...children) {
		Sum sum = new Sum();
		for (Term t : children)
			sum.children.add(t);
		return sum;
	}
	public static Sum isolationSum(Sum positiveSum, Sum negativeSum) {
		Sum sum = new Sum();
		sum.children.addAll(positiveSum.children);
		for (Term t : negativeSum.children)
			sum.children.add(t.multiply(-1));
		return sum;
	}
	public boolean isConstant() {
		for (Term t : children)
			if (t instanceof Variable)
				return false;
			else if (!(t instanceof Constant))
				throw new RuntimeException(getClass()+
						" contains non-constant, non-variable children. Run toSum and flattern first");
		return true;
	}
	@Override
	public String toString() {
		if (children.isEmpty())
			return "SUM[0]";
		return "SUM"+children;
	}
	@Override
	public Sum replaceVariables(VariableAssignment assignment) {
		for (int i = 0; i < children.size(); i++)
			children.set(i, (Term) children.get(i).replaceVariables(assignment));
		return this;
	}
	@Override
	public int evaluate(VariableAssignment varAss) {
		int value = 0;
		for (Term t : children)
			value += t.evaluate(varAss);
		return value;
	}
	@Override
	public Sum toSum() { return this; }
	@Override
	public Sum multiply(int factor) {
		for (int i = 0; i < children.size(); i++)
			children.set(i, children.get(i).multiply(factor));
		return this;
	}
	public void flatten() {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof Sum) {
				Sum child = (Sum) children.remove(i--);
				children.addAll(child.children);
			}
		}
	}
	@Override
	public Sum simplify() {
		HashMap<String, Integer> varToFactor = new HashMap<>();
		int constantValue = 0;
		for (Term t : children) {
			t = t.simplify();
			if (t instanceof Constant) {
				Constant c = (Constant) t;
				constantValue += c.getValue();
			} else if (t instanceof Variable) {
				Variable v = (Variable) t;
				if (varToFactor.containsKey(v.getSymbol()))
					varToFactor.put(v.getSymbol(), varToFactor.get(v.getSymbol()) + v.getFactor());
				else
					varToFactor.put(v.getSymbol(), v.getFactor());
			} else
				throw new RuntimeException(getClass()+
						" contains non-constant, non-variable children. Run toSum and flattern first");
		}
		children.clear();
		for (String symbol : varToFactor.keySet()) {
			int factor = varToFactor.get(symbol);
			if (factor != 0) {
				Variable v = new Variable(factor, symbol);
				children.add(v);
			}
		}
		if (constantValue != 0)
			children.add(new Constant(constantValue));
		return this;
	}
}
