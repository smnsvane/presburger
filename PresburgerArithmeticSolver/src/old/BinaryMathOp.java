package old;

import java.util.Map;

public class BinaryMathOp implements MathEval
{
	// +, -, *
	public String operator;
	// during parse validate that var1 is a constant if operator is a *
	public MathEval var1, var2;
	@Override
	public double eval(Map<String, Integer> map)
	{
		if (operator.equals("+"))
			return var1.eval(map) + var2.eval(map);
		if (operator.equals("-"))
			return var1.eval(map) - var2.eval(map);
		if (operator.equals("*"))
			return var1.eval(map) * var2.eval(map);
		throw new RuntimeException("unknown math binary operator");
	}
	@Override
	public String toString() { return var1+" "+operator+" "+var2; }
}
