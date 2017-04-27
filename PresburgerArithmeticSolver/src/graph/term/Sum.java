package graph.term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import graph.Branch;
import graph.MultipleChildrenBranch;
import graph.Term;
import graph.VariableAssignment;
import parser.NodeSorter;
import parser.Parser;

public class Sum extends MultipleChildrenBranch<Term> implements Term {

	public Sum(Collection<Term> children) { super(children); }
	public Sum(Term...children) { super(Arrays.asList(children)); }
	public Sum add(Collection<Term> adds) {
		ArrayList<Term> sumChildren = new ArrayList<>();
		sumChildren.addAll(getChildren());
		for (Term t : adds)
			sumChildren.add(t);
		Sum sum = new Sum(sumChildren);
		return sum;
	}
	public Sum add(Term...adds) { return add(Arrays.asList(adds)); }
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
		ArrayList<Term> children = new ArrayList<>();
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
			} else
				children.add(t);
		}
		for (String symbol : varToFactor.keySet()) {
			int factor = varToFactor.get(symbol);
			if (factor != 0) {
				Variable v = new Variable(factor, symbol);
				children.add(v);
			}
		}
		if (constantValue != 0)
			children.add(new Constant(constantValue));
		Collections.sort(children, new NodeSorter());
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
		for (int i = 0; i < getChildren().size(); i++)
			sb.append(sumChildToString(getChildren().get(i), i == 0));
		return sb.toString();
	}
	private String sumChildToString(Term t, boolean firstChild) {
		if (t instanceof Branch<?>)
			return (firstChild?"":" + ")+"("+t+")";
		if (firstChild)
			return t.toString();
		if (t instanceof Constant) {
			Constant c = (Constant) t;
			return (c.getValue() < 0?" - "+(-1*c.getValue()):" + "+c.getValue());
		}
		if (t instanceof Variable) {
			Variable v = (Variable) t;
			String factorString = Math.abs(v.getFactor())==1?"":""+Math.abs(v.getFactor());
			if (v.getFactor() < 0)
				return " - "+factorString+v.getVariableSymbol();
			return " + "+factorString+v.getVariableSymbol();
		}
		return t.toString();
	}
}
