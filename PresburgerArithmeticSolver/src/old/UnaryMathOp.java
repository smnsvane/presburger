package old;

import java.util.Map;

public class UnaryMathOp implements MathEval
{
	// -
	public String operator = "-";
	public MathEval var;
	@Override
	public double eval(Map<String, Integer> map)
	{
		return -1 * var.eval(map);
	}
	@Override
	public String toString() { return operator+var; }
}
