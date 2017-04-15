package graph.term;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import graph.Branch;
import graph.VariableAssignment;

public class Sum implements Term, Branch {

	private ArrayList<Term> children = new ArrayList<>();
	public void addChild(Term child) { children.add(child); }
	public List<Term> getAllChildren() { return Collections.unmodifiableList(children); }
	public Sum() {}
	public Sum(List<Term> children1, List<Term> children2) {
		children.addAll(children1);
		children.addAll(children2);
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
			Variable v = new Variable(varToFactor.get(symbol), symbol);
			children.add(v);
		}
		if (constantValue != 0)
			children.add(new Constant(constantValue));
		return this;
	}
}
