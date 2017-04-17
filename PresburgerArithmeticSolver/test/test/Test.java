package test;

import static org.junit.Assert.*;

import engine.Compacter;
import engine.Flattener;
import engine.Isolater;
import engine.Simplifier;
import engine.ToLessThan;
import engine.VariableReplacer;
import graph.Formula;
import graph.Node;
import graph.VariableAssignment;
import parser.Parser;

public class Test {

	private Parser p = new Parser();

	@org.junit.Test
	public void testFormula01() {
		String input = "~(0 = x + 1)";
		Formula root = p.parseLogic(input);
		assertEquals("~0=x+1", root.toString());
		Formula simplifiedRoot = root.simplify();
		assertEquals("0!=x+1", simplifiedRoot.toString());
	}

	@org.junit.Test
	public void testFormula02() {
		String input = "x + 1 = y + 1 -> x = y";
		Formula root = p.parseLogic(input);
		assertEquals("x+1=y+1->x=y", root.toString());
		Formula simplifiedRoot = root.simplify();
		assertEquals("~x+1=y+1/x=y", simplifiedRoot.toString());
		Node simplifiedGraphRoot = new Simplifier(root).go();
		assertEquals("x+1!=y+1/x=y", simplifiedGraphRoot.toString());
	}

	@org.junit.Test
	public void testFormula04() {
		String input = "x + 0 = x";
		Formula root = p.parseLogic(input);
		assertEquals("x+0=x", root.toString());
		Node root2 = new Isolater(root).go();
		assertEquals("x+0=x", root2.toString());
	}

	@org.junit.Test
	public void testFormula05() {
		String input = "x + y + 1 = x + y + 1";
		Formula root = p.parseLogic(input);
		assertEquals("x+(y+1)=x+(y+1)", root.toString());
	}

	@org.junit.Test
	public void testFormula055() {
		String input = "x + (y + 1) = (x + y) + 1";
		Formula root = p.parseLogic(input);
		assertEquals("x+(y+1)=(x+y)+1", root.toString());
	}

	@org.junit.Test
	public void testFormula06() {
		String input = "(y + y = x) / (y + y + 1 = x)";
		Formula root = p.parseLogic(input);
		assertEquals("y+y=x/y+(y+1)=x", root.toString());
		Formula isolatedRoot = new Isolater(root).go();
		assertEquals("0=SUM[x, -y, -y]/0=SUM[x, -y, -y+-1]", isolatedRoot.toString());
		Formula flattenedRoot = new Flattener(root).go();
		assertEquals("0=SUM[x, -y, -y]/0=SUM[x, -y, -y, -1]", flattenedRoot.toString());
		Formula compactedRoot = new Compacter(root).go();
		assertEquals("0=SUM[x, -2y]/0=SUM[x, -2y, -1]", compactedRoot.toString());
		Formula toLessThanRoot = new ToLessThan(root).go();
		assertEquals("(0<SUM[x, -2y]+1&SUM[x, -2y]<0+1)/(0<SUM[x, -2y, -1]+1&SUM[x, -2y, -1]<0+1)", toLessThanRoot.toString());
		Formula flattenedRoot2 = new Flattener(root).go();
		assertEquals("(0<SUM[x, -2y, 1]&SUM[x, -2y]<1)/(0<SUM[x, -2y, -1, 1]&SUM[x, -2y, -1]<1)", flattenedRoot2.toString());
	}

	@org.junit.Test
	public void testFormula07() {
		String input = "Ax. Ey. x + 1 = 2y & x > 0 / y < 2";
		Formula root = p.parseLogic(input);
		assertEquals("Ax.Ey.x+1=2y&(x>0/y<2)", root.toString());
	}

	@org.junit.Test
	public void testFormula08() {
		String input = "10 > 11 / 3 + 4 < 15 - 6";
		Formula root = p.parseLogic(input);
		String parsing = root.toString();
		assertEquals("10>11/3+4<15-6", parsing);
	}

	@org.junit.Test
	public void testFormula09() {
		String input = "Ax. Ey. x + 1 = 2y & x > 0 / y < 2";
		Formula root = p.parseLogic(input);
		String parsing = root.toString();
		assertEquals("Ax.Ey.x+1=2y&(x>0/y<2)", parsing);
	}

	@org.junit.Test
	public void testFormula10() {
		String input = "10 > 11 / 3 + 4 < 5 * 3 - 6";
		Formula root = p.parseLogic(input);
		String parsing = root.toString();
		assertEquals("10>11/3+4<5*3-6", parsing);
	}

	@org.junit.Test
	public void testFormula11() {
		String input = "Ex. (3 < x & x + 2y <= 6 & y < 0)";
		Formula root = p.parseLogic(input);
		String parsing = root.toString();
		assertEquals("Ex.3<x&(x+2y<=6&y<0)", parsing);
	}

	@org.junit.Test
	public void testFormula12() {
		String input = "3 < x & x +2y <= 6 & y < 0";
		Formula root = p.parseLogic(input);
		String parsing = root.toString();
		assertEquals("3<x&(x+2y<=6&y<0)", parsing);
	}

	@org.junit.Test
	public void testLauncher() {
		String input = "x + 1 = y + 1 -> x = y";
		Formula root = p.parseLogic(input);
		String parsing = root.toString();
		assertEquals("x+1=y+1->x=y", parsing);

		Formula simplifiedGraphRoot = new Simplifier(root).go();
		parsing = simplifiedGraphRoot.toString();
		assertEquals("x+1!=y+1/x=y", parsing);

		VariableAssignment assignment = new VariableAssignment().put("x", 1).put("y", 1);
		Formula assignedRoot = new VariableReplacer(simplifiedGraphRoot, assignment).go();
		parsing = assignedRoot.toString();
		assertEquals("1+1!=1+1/1=1", parsing);

		Formula simplifiedAssignedGraphRoot = new Simplifier(simplifiedGraphRoot).go();
		parsing = simplifiedAssignedGraphRoot.toString();
		assertEquals("false/true", parsing);

		simplifiedAssignedGraphRoot = new Simplifier(simplifiedAssignedGraphRoot).go();
		parsing = simplifiedAssignedGraphRoot.toString();
		assertEquals("true", parsing);
	}
}
