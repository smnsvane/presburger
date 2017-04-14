package old;

import java.util.Map;

public class MathComparison implements BooleanEval
{
	// =, <, >, <=, >=, !=, |
	public String operator;
	public MathEval var1, var2;
	@Override
	public boolean eval(Map<String, Integer> map)
	{
		if (operator.equals("="))
			return var1.eval(map) == var2.eval(map);
		if (operator.equals("<"))
			return var1.eval(map) < var2.eval(map);
		if (operator.equals(">"))
			return var1.eval(map) > var2.eval(map);
		if (operator.equals("<="))
			return var1.eval(map) <= var2.eval(map);
		if (operator.equals(">="))
			return var1.eval(map) >= var2.eval(map);
		if (operator.equals("!="))
			return var1.eval(map) != var2.eval(map);
		if (operator.equals("|"))
			return var2.eval(map) % var1.eval(map) == 0;
		throw new RuntimeException("unknown operator");
	}
	@Override
	public String toString()
	{
		if (operator.equals("|"))
			return var1+operator+var2;
		return var1+" "+operator+" "+var2;
	}
	public BooleanEval toLessThan()
	{
		if (operator.equals("="))
		{
			MathComparison lt = new MathComparison();
			lt.var1 = var1;
			lt.var2 = var2;
			
			MathComparison lt2 = new MathComparison();
			lt2.var1 = var2;
			lt2.var2 = var1;
			
			Not n = new Not();
			n.var = lt;
			
			Not n2 = new Not();
			n2.var = lt2;
			
			BinaryLogicOp and = new BinaryLogicOp();
			and.operator = "and";
			and.var1 = n;
			and.var2 = n2;
			
			return and;
		}	
		if (operator.equals("<"))
			return this;
		if (operator.equals(">"))
		{
			MathComparison lt = new MathComparison();
			lt.var1 = var2;
			lt.var2 = var1;
			
			return lt;
		}
		if (operator.equals("<="))
		{
			MathComparison lt = new MathComparison();
			lt.var1 = var2;
			lt.var2 = var1;
			
			Not n = new Not();
			n.var = lt;
			
			return n;
		}
		if (operator.equals(">="))
		{
			MathComparison lt = new MathComparison();
			lt.var1 = var1;
			lt.var2 = var2;
			
			Not n = new Not();
			n.var = lt;
			
			return n;
		}
		if (operator.equals("!="))
		{
			MathComparison lt = new MathComparison();
			lt.var1 = var1;
			lt.var2 = var2;
			
			MathComparison lt2 = new MathComparison();
			lt2.var1 = var2;
			lt2.var2 = var1;
			
			BinaryLogicOp or = new BinaryLogicOp();
			or.operator = "or";
			or.var1 = lt;
			or.var2 = lt2;
			
			return or;
		}
		// this operator should not be converted to <
		if (operator.equals("|"))
			return this;
		throw new RuntimeException("no hit");
	}
	public void isolate(String variable)
	{
		if (!operator.equals("<"))
			throw new RuntimeException("not <");
		Sum s1 = (Sum) var1;
		Sum s2 = (Sum) var2;
		
		for (int i = 0; i < s1.numbers.size(); i++)
		{
			Number n = s1.numbers.get(i);
			if (!variable.equals(n.variable))
			{
				s1.numbers.remove(i--);
				s2.add(n);
			}
		}
		for (int i = 0; i < s2.numbers.size(); i++)
		{
			Number n = s2.numbers.get(i);
			if (variable.equals(n.variable))
			{
				s2.numbers.remove(i--);
				s1.add(n);
			}
		}
//		if (
		
	}
}
