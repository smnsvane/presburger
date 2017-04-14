package old;

import java.util.Map;

public class BinaryLogicOp implements BooleanEval
{
	// and(&), or(/)
	public String operator;
	public BooleanEval var1, var2;
	@Override
	public boolean eval(Map<String, Integer> map)
	{
		if (operator.equals("and"))
			return var1.eval(map) && var2.eval(map);
		if (operator.equals("or"))
			return var1.eval(map) || var2.eval(map);
		throw new RuntimeException("unknown logic operator");
	}
	@Override
	public String toString() { return var1+" "+operator+" "+var2; }
}

