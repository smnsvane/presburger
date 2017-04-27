package test;

import static org.junit.Assert.*;

import org.junit.Before;

import engine.Compacter;
import engine.DeMorgan;
import engine.Flattener;
import engine.ForallToExists;
import engine.ImplicationRemover;
import engine.ProductRemover;
import engine.Reducer;
import engine.ToLessThan;
import engine.VariableIsolater;
import engine.VariableReplacer;
import graph.Formula;
import graph.Node;
import graph.VariableAssignment;
import parser.Parser;

public class Test {

	private Parser p = new Parser();

	@Before
	public void before() {
		Parser.prettyPrint = false;
	}


	@org.junit.Test
	public void testRootSimplify() {
		String input = "~(0 = x + 1)";
		Formula root = p.parse(input);
		assertEquals("~0=x+1", root.toString());
		Formula simplifiedRoot = root.reduce();
		assertEquals("0!=x+1", simplifiedRoot.toString());
	}

	@org.junit.Test
	public void testSimpleSimplify() {
		String input = "x + 1 = y + 1 -> x = y";
		Formula root = p.parse(input);
		assertEquals("x+1=y+1->x=y", root.toString());
		Formula simplifiedRoot = root.reduce();
		assertEquals("~x+1=y+1/x=y", simplifiedRoot.toString());
		Node simplifiedGraphRoot = new Reducer(root).go();
		assertEquals("x+1!=y+1/x=y", simplifiedGraphRoot.toString());
	}

	@org.junit.Test
	public void testParser() {
		String input = "x + y + 1 = x + y + 1";
		Formula root = p.parse(input);
		assertEquals("x+(y+1)=x+(y+1)", root.toString());
	}

	@org.junit.Test
	public void testParserBrackets() {
		String input = "x + (y + 1) = (x + y) + 1";
		Formula root = p.parse(input);
		assertEquals("x+(y+1)=(x+y)+1", root.toString());
	}

	@org.junit.Test
	public void testEngines() {
		String input = "(y + y = x) / (y + y + 1 = x)";
		Formula root = p.parse(input);
		assertEquals("y+y=x/y+(y+1)=x", root.toString());
		root = new Flattener(root).go();
		assertEquals("SUM[]=SUM[x, -y, -y]/SUM[]=SUM[-y+-1, x, -y]", root.toString());
		root = new Compacter(root).go();
		assertEquals("SUM[]=SUM[x, -2y]/SUM[]=SUM[-y+-1, x, -y]", root.toString());
		root = new ToLessThan(root).go();
		assertEquals("(SUM[]<SUM[x, -2y, 1]&SUM[x, -2y]<SUM[1])/(SUM[]<SUM[-y+-1, x, -y, 1]&SUM[-y+-1, x, -y]<SUM[1])", root.toString());
		root = new Flattener(root).go();
		assertEquals("(SUM[]<SUM[x, -2y, 1]&SUM[x, -2y]<SUM[1])/(SUM[]<SUM[x, -y, -y, 1, -1]&SUM[x, -y, -y, -1]<SUM[1])", root.toString());
		root = new Compacter(root).go();
		assertEquals("(SUM[]<SUM[x, -2y, 1]&SUM[x, -2y]<SUM[1])/(SUM[]<SUM[x, -2y]&SUM[x, -2y, -1]<SUM[1])", root.toString());
		VariableAssignment assignment = new VariableAssignment().put("x", 41).put("y", -23);
		root = new VariableReplacer(root, assignment).go();
		assertEquals("(SUM[]<SUM[41, 46, 1]&SUM[41, 46]<SUM[1])/(SUM[]<SUM[41, 46]&SUM[41, 46, -1]<SUM[1])", root.toString());
		root = new Compacter(root).go();
		assertEquals("(SUM[]<SUM[88]&SUM[87]<SUM[1])/(SUM[]<SUM[87]&SUM[86]<SUM[1])", root.toString());
		root = new Flattener(root).go();
		assertEquals("(SUM[]<SUM[88]&SUM[87]<SUM[1])/(SUM[]<SUM[87]&SUM[86]<SUM[1])", root.toString());
		root = new Reducer(root).go();
		assertEquals("(true&false)/(true&false)", root.toString());
		root = new Reducer(root).go();
		assertEquals("false/false", root.toString());
		root = new Reducer(root).go();
		assertEquals("false", root.toString());
	}

	@org.junit.Test
	public void testPrettyPrint() {
		Parser.prettyPrint = true;
		String input = "(y + y = x) / (y + y + 1 = x)";
		Formula root = p.parse(input);
		assertEquals("y + y = x / y + (y + 1) = x", root.toString());
		root = new Flattener(root).go();
		assertEquals("0 = x + -y + -y / 0 = (-y + -1) + x + -y", root.toString());
		root = new Compacter(root).go();
		assertEquals("0 = x + -2y / 0 = (-y + -1) + x + -y", root.toString());
		root = new ToLessThan(root).go();
		assertEquals("(0 < x + -2y + 1 & x + -2y < 1) / (0 < (-y + -1) + x + -y + 1 & (-y + -1) + x + -y < 1)", root.toString());
		root = new Flattener(root).go();
		assertEquals("(0 < x + -2y + 1 & x + -2y < 1) / (0 < x + -y + -y + 1 + -1 & x + -y + -y + -1 < 1)", root.toString());
		root = new Compacter(root).go();
		assertEquals("(0 < x + -2y + 1 & x + -2y < 1) / (0 < x + -2y & x + -2y + -1 < 1)", root.toString());
		VariableAssignment assignment = new VariableAssignment().put("x", 41).put("y", -23);
		root = new VariableReplacer(root, assignment).go();
		assertEquals("(0 < 41 + 46 + 1 & 41 + 46 < 1) / (0 < 41 + 46 & 41 + 46 + -1 < 1)", root.toString());
		root = new Compacter(root).go();
		assertEquals("(0 < 88 & 87 < 1) / (0 < 87 & 86 < 1)", root.toString());
		root = new Flattener(root).go();
		assertEquals("(0 < 88 & 87 < 1) / (0 < 87 & 86 < 1)", root.toString());
		root = new Reducer(root).go();
		assertEquals("(true & false) / (true & false)", root.toString());
		root = new Reducer(root).go();
		assertEquals("false / false", root.toString());
		root = new Reducer(root).go();
		assertEquals("false", root.toString());
	}

	@org.junit.Test
	public void testParseQuantifiers() {
		String input = "Ax. Ey. x + 1 = 2y & x > 0 / y < 2";
		Formula root = p.parse(input);
		assertEquals("Ax.Ey.x+1=2y&(x>0/y<2)", root.toString());
	}

	@org.junit.Test
	public void testSolveMath() {
		String input = "10 > 11 / 3 + 4 < 15 - 6";
		Formula root = p.parse(input);
		String parsing = root.toString();
		assertEquals("10>11/3+4<15-6", parsing);
		root = new Reducer(root).go();
		assertEquals("false/true", root.toString());
		root = new Reducer(root).go();
		assertEquals("true", root.toString());
	}

	@org.junit.Test
	public void testFormula09() {
		String input = "Ax. Ey. x + 1 = 2y & x > 0 / y < 2";
		Formula root = p.parse(input);
		String parsing = root.toString();
		assertEquals("Ax.Ey.x+1=2y&(x>0/y<2)", parsing);
	}

	@org.junit.Test
	public void testFormula10() {
		String input = "10 > 11 / 3 + 4 < 5 * 3 - 6";
		Formula root = p.parse(input);
		String parsing = root.toString();
		assertEquals("10>11/3+4<5*3-6", parsing);
	}

	@org.junit.Test
	public void testFormula11() {
		String input = "Ex. (3 < x & x + 2y <= 6 & y < 0)";
		Formula root = p.parse(input);
		String parsing = root.toString();
		assertEquals("Ex.3<x&(x+2y<=6&y<0)", parsing);
	}

	@org.junit.Test
	public void testFormula12() {
		String input = "3 < x & x +2y <= 6 & y < 0";
		Formula root = p.parse(input);
		String parsing = root.toString();
		assertEquals("3<x&(x+2y<=6&y<0)", parsing);
	}

	@org.junit.Test
	public void testLauncher() {
		String input = "x + 1 = y + 1 -> x = y";
		Formula root = p.parse(input);
		String parsing = root.toString();
		assertEquals("x+1=y+1->x=y", parsing);

		Formula simplifiedGraphRoot = new Reducer(root).go();
		parsing = simplifiedGraphRoot.toString();
		assertEquals("x+1!=y+1/x=y", parsing);

		VariableAssignment assignment = new VariableAssignment().put("x", 1).put("y", 1);
		Formula assignedRoot = new VariableReplacer(simplifiedGraphRoot, assignment).go();
		parsing = assignedRoot.toString();
		assertEquals("1+1!=1+1/1=1", parsing);

		Formula simplifiedAssignedGraphRoot = new Reducer(assignedRoot).go();
		parsing = simplifiedAssignedGraphRoot.toString();
		assertEquals("false/true", parsing);

		simplifiedAssignedGraphRoot = new Reducer(simplifiedAssignedGraphRoot).go();
		parsing = simplifiedAssignedGraphRoot.toString();
		assertEquals("true", parsing);
	}

	@org.junit.Test
	public void testCooperExample() {
		String input = "Ex.(3x+1<10/7x-6>7)&2|x";
		Formula root = p.parse(input);
		String parsing = root.toString();
		assertEquals("Ex.(3x+1<10/7x-6>7)&2|x", parsing);
		root = new ToLessThan(root).go();
		assertEquals("Ex.(3x+1<10/7<7x-6)&2|x", root.toString());
		root = new VariableIsolater(root).go();
		assertEquals("Ex.(SUM[3x]<SUM[10, -1]/SUM[7, 6]<SUM[7x])&2|x", root.toString());
	}

	@org.junit.Test
	public void testCooperExample2() {
		String input = "Ex.Ay.(3x+1<10/7x-6>7+y)&2|x";
		Formula root = p.parse(input);
		String parsing = root.toString();
		assertEquals("Ex.Ay.(3x+1<10/7x-6>7+y)&2|x", parsing);
		root = new ForallToExists(root).go();
		assertEquals("Ex.~Ey.~((3x+1<10/7x-6>7+y)&2|x)", root.toString());
		root = new Reducer(root).go();
		assertEquals("Ex.~Ey.(3x+1>=10&7x-6<=7+y)/~2|x", root.toString());
	}

	@org.junit.Test
	public void testCooperExample22() {
		String input = "Ex.~Ey.(3x+1>=10&7x-6<=7+y)/~2|x";
		Formula root = p.parse(input);
		root = new ToLessThan(root).go();
		assertEquals("Ex.~Ey.(10<SUM[3x, 1, 1]&7x-6<SUM[y, 7, 1])/~2|x", root.toString());
		root = new VariableIsolater(root).go();
		assertEquals("Ex.~Ey.(SUM[]<SUM[3x, 1, 1, -10]&SUM[7x, -1, -6, -7]<SUM[y])/~2|x", root.toString());
	}

	@org.junit.Test
	public void testPrecedenceParsing() {
		String input = "~Ex.x=3/x<3/x>=4";
		Formula root = p.parse(input);
		assertEquals("~Ex.x=3/(x<3/x>=4)", root.toString());
	}

	@org.junit.Test
	public void testPrecedenceParsing2() {
		String input = "~Ay.(x=3/x<3)&(x>=4/y=1)";
		Formula root = p.parse(input);
		assertEquals("~Ay.(x=3/x<3)&(x>=4/y=1)", root.toString());
	}

	@org.junit.Test
	public void testCooper1() {
		String input = "Ex.~(Ey.(3x+1>=10&7x-6<=7+y)/~2|x)";
		Formula root = p.parse(input);
		assertEquals("Ex.~Ey.(3x+1>=10&7x-6<=7+y)/~2|x", root.toString());
		root = new ToLessThan(root).go();
		assertEquals("Ex.~Ey.(SUM[10]<SUM[3x, 1, 1]&SUM[7x, -6]<SUM[y, 7, 1])/~2|x", root.toString());
		root = new VariableIsolater(root).go();
		assertEquals("Ex.~Ey.(SUM[]<SUM[3x, 1, 1, -10]&SUM[7x, -1, -6, -7]<SUM[y])/~2|x", root.toString());
	}

	@org.junit.Test
	public void testDeMorgan1() {
		Parser.prettyPrint = true;
		String input = "~~(~x<2&(true/y>=4))";
		Formula root = p.parse(input);
		assertEquals("~~(~x < 2 & (true / y >= 4))", root.toString());
		root = new DeMorgan(root).go();
		assertEquals("x >= 2 & (true / y >= 4)", root.toString());
	}

	@org.junit.Test
	public void testDeMorgan2() {
		Parser.prettyPrint = true;
		String input = "~~~(~x<2&(true/y>=4))";
		Formula root = p.parse(input);
		assertEquals("~~~(~x < 2 & (true / y >= 4))", root.toString());
		root = new DeMorgan(root).go();
		assertEquals("x < 2 / (false & y < 4)", root.toString());
		root = new Reducer(root).go();
		assertEquals("x < 2 / false", root.toString());
		root = new Reducer(root).go();
		assertEquals("x < 2", root.toString());
	}

	@org.junit.Test
	public void testCooper2() {
		Parser.prettyPrint = true;
		String input = "Ax. 20+x <= 0 -> Ey. 3y +x <= 10 & 20 <= y - x";
		Formula root = p.parse(input);
		assertEquals("Ax.20 + x <= 0 -> (Ey.3y + x <= 10 & 20 <= y - x)", root.toString());
		// remove products
		root = new ProductRemover(root).go();
		assertEquals("Ax.20 + x <= 0 -> (Ey.3y + x <= 10 & 20 <= y - x)", root.toString());
		// subtraction to addition
		// addition to sum (flatten)
		// remove implications
		root = new ImplicationRemover(root).go();
		assertEquals("Ax.~20 + x <= 0 / (Ey.3y + x <= 10 & 20 <= y - x)", root.toString());
		// foralls to exists'
		root = new ForallToExists(root).go();
		assertEquals("~Ex.~(~20 + x <= 0 / (Ey.3y + x <= 10 & 20 <= y - x))", root.toString());
		// press non-exists negations in (de morgan)
		root = new DeMorgan(root).go();
		assertEquals("~Ex.20 + x > 0 & ~(Ey.3y + x <= 10 & 20 <= y - x)", root.toString());
		// press non-exists negations in (de morgan)
		assertEquals("~Ex.20 + x > 0 & ~(Ey.3y + x <= 10 & 20 <= y - x)", root.toString());
		// convert all comparators to less-than
		assertEquals("~Ex.20 + x > 0 & ~(Ey.3y + x < 10 + 1 & 20 < y - x + 1)", root.toString());
		// isolate y under Ey.
		assertEquals("~Ex.20 + x > 0 & ~(Ey.3y < 10 + 1 - x & 20 + x - 1 < y)", root.toString());
		// compact comparator children under Ey.
		assertEquals("~Ex.20 + x > 0 & ~(Ey.3y < 11 - x & 19 + x < y)", root.toString());
		// gcd on y factor under Ey.
		assertEquals("~Ex.20 + x > 0 & ~(Ey.3y < 11 - x & 57 + 3x < 3y)", root.toString());
		// replace 3y with y1 (and add divisor constraint)
		assertEquals("~Ex.20 + x > 0 & ~(Ey1.y1 < 11 - x & 57 + 3x < y1 & 3|y1)", root.toString());
		// cooper case 1 under Ey1.
		assertEquals("true & false & 3|y1", root.toString());
		// reduction of cooper case 1 under Ey1.
		assertEquals("false", root.toString());
		// cooper case 2 under Ey1.
		assertEquals("Vee.j=1..3.b={57+3x}.y1 < 11 - x & 57 + 3x < y1 & 3|y1", root.toString());
		// Vee b+j set
		assertEquals("Vee.y1={58 + 3x, 59 + 3x, 60 + 3x}.y1 < 11 - x & 57 + 3x < y1 & 3|y1", root.toString());
		// 58+3x and 59+3x is not dividable with 3
		assertEquals("Vee.y1={60+3x}.y1 < 11 - x & 57 + 3x < y1 & 3|y1", root.toString());
		// Vee expansion
		assertEquals("60 + 3x < 11 - x & 57 + 3x < 60 + 3x & 3|60 + 3x", root.toString());
		// insertion in original formula "case 1 or case 2"
		assertEquals("~Ex.20 + x > 0 & ~(false / (60 + 3x < 11 - x & 57 + 3x < 60 + 3x & 3|60 + 3x))", root.toString());
		// reduce
		assertEquals("~Ex.20 + x > 0 & ~(60 + 3x < 11 - x & 57 + 3x < 60 + 3x & 3|60 + 3x)", root.toString());
		// de morgan
		assertEquals("~Ex.20 + x > 0 & (~60 + 3x < 11 - x / ~57 + 3x < 60 + 3x / ~3|60 + 3x)", root.toString());
		// de morgan
		assertEquals("~Ex.20 + x > 0 & (60 + 3x >= 11 - x / 57 + 3x >= 60 + 3x / ~3|60 + 3x)", root.toString());
		// convert to less-than
		assertEquals("~Ex.0 < 20 + x & (11 - x < 60 + 3x + 1 / 60 + 3x < 57 + 3x + 1 / ~3|60 + 3x)", root.toString());
		// isolate x under Ex.
		assertEquals("~Ex.0 - 20 < x & (11 - 60 - 1 < 2x / 0x < 57 + 1 - 60 / ~3|60 + 3x)", root.toString());
		// simplify
		assertEquals("~Ex.0 - 20 < x & (11 - 60 - 1 < 2x / 0 < 57 + 1 - 60 / ~3|60 + 3x)", root.toString());
		// eval(null)
		assertEquals("~Ex.0 - 20 < x & (11 - 60 - 1 < 2x / false / ~3|60 + 3x)", root.toString());
		// reduce
		assertEquals("~Ex.0 - 20 < x & (11 - 60 - 1 < 2x / ~3|60 + 3x)", root.toString());
		// compact
		assertEquals("~Ex.-20 < x & (-48 < 2x / ~3|60 + 3x)", root.toString());
		// gcd on x factor under Ex.
		assertEquals("~Ex.-120 < 6x & (-144 < 6x / ~6|120 + 6x)", root.toString());
		// replace x with x1 under Ex.
		assertEquals("~Ex1.-120 < x1 & (-144 < x1 / ~6|120 + x1) & 6|x1", root.toString());
		// cooper case 1 under Ex.
		assertEquals("false & (false / ~6|120 + x1) & 6x1", root.toString());
		// reduce
		assertEquals("false", root.toString());
		// cooper case 2 under Ex.
		assertEquals("Vee.j=1..6.b={}.y1 < 11 - x & 57 + 3x < y1 & 3|y1", root.toString());
		
		
//		this formula is true
	}
}
