package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import graph.VariableAssignment;
import graph.logic.Formula;

public class Launcher {

	public static void main(String[] args) {

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("formulas.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int lineNumber = 1; scanner.hasNextLine(); lineNumber++) {
			String line = scanner.nextLine();
			if (line.charAt(0) == '#')
				continue;
			System.out.println("\nformula "+lineNumber+": "+line);

			line = line.replaceAll("[\\s()]", "");
			System.out.println("shortened to \""+line+"\"");

			Parser p = new Parser();
			Formula root = p.parseLogic(line, null);
			System.out.println("parsed as: "+root);
			
			VariableAssignment varAss = new VariableAssignment();
			varAss.addAssignment("x", 1);
			varAss.addAssignment("y", 1);
			
			Engine e = new Engine();
			boolean success = e.applyAssignment(root, varAss);
			System.out.println("Evaluated with the assignment "+varAss+" result was: "+success);
			
			varAss.addAssignment("y", null);
			root.replaceVariables(varAss);
			System.out.println("Variables replaced with the assignment "+varAss+" result: "+root);
		}
	}
}
