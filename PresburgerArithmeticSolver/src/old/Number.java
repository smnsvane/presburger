package old;

import java.util.Map;

public class Number implements MathEval
{
	public int constant = 1;
	public String variable = null;
	@Override
	public double eval(Map<String, Integer> map)
	{
		if (variable == null)
			return constant;
		if (map.containsKey(variable))
			return constant * map.get(variable);
		throw new RuntimeException("unknown operator");
	}
	@Override
	public String toString() { return constant+variable; }
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Number && ((Number) o).variable.equals(variable);
	}
}
