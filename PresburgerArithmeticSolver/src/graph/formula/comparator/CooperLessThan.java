package graph.formula.comparator;


import graph.Formula;
import graph.TwoChildrenBranch;
import graph.VariableAssignment;
import graph.formula.False;
import graph.formula.True;
import graph.term.SumMap;

public class CooperLessThan extends TwoChildrenBranch<SumMap, SumMap> implements Formula {

	public CooperLessThan(SumMap child1, SumMap child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) <
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() { throw new RuntimeException("not implemented"); }
	@Override
	public CooperLessThan copy() { return new CooperLessThan(getFirstChild().copy(), getSecondChild().copy()); }

//	public CooperLessThan isolate(String variableSymbol) {
//		ArrayList<Term> var = new ArrayList<>();
//		ArrayList<Term> nonVar = new ArrayList<>();
//		nonVar.addAll(getFirstChild().getChildren());
//		nonVar.addAll(getSecondChild().getChildren());
//		for (int i = 0; i < nonVar.size(); i++)
//			if (nonVar instanceof Variable) {
//				
//			}
//			
//	}
	@Override
	public Formula simplify() {
		try {
			if (evaluate(null))
				return new True();
			return new False();
		} catch (NullPointerException e) {
			return this;
		}
	}
}
