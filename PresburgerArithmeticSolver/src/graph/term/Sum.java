package graph.term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import graph.Branch;
import graph.MultipleChildrenBranch;
import graph.Term;
import graph.VariableAssignment;
import parser.Parser;

public class Sum extends MultipleChildrenBranch<Term> implements Term {

	public Sum(Collection<Term> children) { super(children); }
	public Sum(Term...children) { super(Arrays.asList(children)); }
	public Sum addToSum(Term add) {
		ArrayList<Term> sumChildren = new ArrayList<>();
		sumChildren.addAll(getChildren());
		sumChildren.add(add);
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
	@SuppressWarnings("unchecked")
	public Sum flatten() {
		ArrayList<Term> children = new ArrayList<>();
		for (Term child : this) {
			if (child instanceof Sum || child instanceof Addition) {
				for (Term grandChild : (Branch<Term>) child)
					children.add(grandChild);
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
	public Sum compact() {
		HashMap<String, Integer> varToFactor = new HashMap<>();
		int constantValue = 0;
		for (Term t : this) {
			if (t instanceof Constant) {
				Constant c = (Constant) t;
				constantValue += c.getValue();
			} else if (t instanceof Variable) {
				Variable v = (Variable) t;
				if (varToFactor.containsKey(v.getVariableSymbol()))
					varToFactor.put(v.getVariableSymbol(),
							varToFactor.get(v.getVariableSymbol()) + v.getFactor());
				else
					varToFactor.put(v.getVariableSymbol(), v.getFactor());
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
	@Override
	public Sum copy() {
		ArrayList<Term> children = new ArrayList<>();
		for (Term child : this)
			children.add(child.copy());
		return new Sum(children);
	}
	@Override
	public String toString() {
		if (!Parser.prettyPrint)
			return super.toString();
		if (getChildren().isEmpty())
			return "0";
		if (getChildren().size() == 1)
			return getChildren().get(0).toString();
		StringBuilder sb = new StringBuilder();
		sb.append(getChildren().get(0).toString());
		for (int i = 1; i < getChildren().size(); i++)
			sb.append(" + "+getChildren().get(i));
		return sb.toString();
	}
}
