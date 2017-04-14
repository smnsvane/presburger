package old;

import java.util.Map;

public class Logic implements BooleanEval {

	public boolean constant;
	@Override
	public boolean eval(Map<String, Integer> map) { return constant; }
	@Override
	public String toString() { return ""+constant; }
}
