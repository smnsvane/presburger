package old;

import java.util.Map;

public class Quantifier implements BooleanEval {

	// A, E
	public String operator;
	public String var;
	public BooleanEval expression;

	public BooleanEval toExists() {

		if (operator.equals("E"))
			return this;

		if (operator.equals("A")) {

			Not n = new Not();
			n.var = expression;
			
			Quantifier q = new Quantifier();
			q.operator = "E";
			q.var = var;
			q.expression = n;
			
			Not n2 = new Not();
			n2.var = q;
			
			return n2;
		}
		throw new RuntimeException("no hit");
	}

	@Override
	public boolean eval(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return false;
	}
}
