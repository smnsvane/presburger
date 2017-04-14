package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import graph.logic.binary.And;
import graph.logic.binary.Implies;
import graph.logic.binary.Or;
import graph.math.comparator.EqualTo;

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
			System.out.println("formula "+lineNumber+": "+line);

//			line = line.replaceAll("\\s", "");
//			System.out.println(line);

			Parser p = new Parser();
			p.parse(line);
		}
	}
}
