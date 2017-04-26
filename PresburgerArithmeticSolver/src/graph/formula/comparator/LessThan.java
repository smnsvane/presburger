package graph.formula.comparator;

import java.util.ArrayList;

import graph.Branch;
import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.term.Sum;
import graph.term.Variable;

public class LessThan extends Comparator {

	public LessThan(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) <
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		GreaterThanOrEqualTo greaterOrEqual = new GreaterThanOrEqualTo(getFirstChild(), getSecondChild());
		return greaterOrEqual;
	}
	@Override
	public LessThan toLessThan() { return this; }
	@Override
	public LessThan isolate() {
		LessThan less = new LessThan(new Sum(), getSecondChild().toSum().addToSum(getFirstChild().multiply(-1)));
		return less;
	}
	@Override
	public LessThan copy() { return new LessThan(getFirstChild().copy(), getSecondChild().copy()); }
	public LessThan isolate(String variableSymbol) {
		if (getFirstChild() instanceof Branch<?> && ((Branch<?>) getFirstChild()).hasBranchChildren())
			throw new RuntimeException("to call isolate it is expected that both children are flattened, first child is not");
		if (getSecondChild() instanceof Branch<?> && ((Branch<?>) getSecondChild()).hasBranchChildren())
			throw new RuntimeException("to call isolate it is expected that both children are flattened, second child is not");
		
		ArrayList<Term> var = new ArrayList<>();
		ArrayList<Term> nonVar = new ArrayList<>();
		//calling .toSum() in order to be able to iterate over the child's children
		for (Term t1 : getFirstChild().toSum())
			if (t1 instanceof Variable) {
				Variable v = (Variable) t1;
				if (v.getVariableSymbol().equals(variableSymbol))
					var.add(v);
				else
					nonVar.add(v.multiply(-1));
			} else
				nonVar.add(t1.multiply(-1));
		for (Term t2 : getSecondChild().toSum())
			if (t2 instanceof Variable) {
				Variable v = (Variable) t2;
				if (v.getVariableSymbol().equals(variableSymbol))
					var.add(v.multiply(-1));
				else
					nonVar.add(v);
			} else
				nonVar.add(t2);
		Sum varSum = new Sum(var).compact();
		Sum nonVarSum = new Sum(nonVar);
		LessThan less;
		if (varSum.getChildren().size() == 1) {
			Variable v = (Variable) varSum.getChildren().get(0);
			if (v.getFactor() < 0)
				less = new LessThan(nonVarSum.multiply(-1), varSum.multiply(-1));
			else
				less = new LessThan(varSum, nonVarSum);
		} else
			less = new LessThan(varSum, nonVarSum);
		return less;
	}
}
