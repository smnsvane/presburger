package old;

import java.util.Map;

public class Not implements BooleanEval {

	// ~
	public final String operator = "~";
	public BooleanEval var;
	@Override
	public boolean eval(Map<String, Integer> map) {
		return !var.eval(map);
	}
	@Override
	public String toString() { return operator+var; }
}

