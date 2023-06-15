package dsaa.lab12;

import java.util.LinkedList;

public class Automaton implements IStringMatcher {

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		if (pattern.length() == 0 || text.length() == 0) {
			return new LinkedList<>();
		} 


		// alphabet range
		String patternAndText = pattern + text;
		int biggerCode = 0;
		int lowestCode = 256;
		for (int i = 0; i < patternAndText.length(); i++) {
			if (patternAndText.charAt(i) > biggerCode) {
				biggerCode = patternAndText.charAt(i);
			}
			if (patternAndText.charAt(i) < lowestCode) {
				lowestCode = patternAndText.charAt(i);
			}
		}
		int alphabetRange = biggerCode - lowestCode + 1;

		// build automaton

		int[][] automaton = new int[pattern.length() + 1][alphabetRange];
		// int[] prefixFunction = new int[pattern.length()]; // prefix function
		//
		// // Compute the prefix function
		// prefixFunction[0] = 0;
		// for (int i = 1; i < pattern.length(); i++) {
		// 	int j = prefixFunction[i - 1];
		// 	while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
		// 		j = prefixFunction[j - 1];
		// 	}
		// 	if (pattern.charAt(i) == pattern.charAt(j)) {
		// 		j++;
		// 	}
		// 	prefixFunction[i] = j;
		// }
		//
		// // print prefix function
		// for (int i = 0; i < prefixFunction.length; i++) {
		// 	System.out.print(prefixFunction[i] + " ");
		// }
		// System.out.println();
		//
		// // Build the automaton using the prefix function
		// for (int state = 0; state <= pattern.length(); state++) {
		// 	for (int c = 0; c < alphabetRange; c++) {
		// 		if (state > 0 && pattern.charAt(state) != (char)(c + lowestCode)) {
		// 			automaton[state][c] = automaton[prefixFunction[state - 1]][c];
		// 		} else {
		// 			automaton[state][c] = state + 1;
		// 		}
		// 	}
		// }

		for (int state = 0; state <= pattern.length(); state++) {
			for (int c = 0; c < alphabetRange; c++) {
				int nextState = Math.min(pattern.length() + 1, state + 2);
				do {
					nextState--;
				} while (!isSuffix(pattern.substring(0, nextState), pattern.substring(0, state) + (char) (c + lowestCode)));
				automaton[state][c] = nextState;
			}
		}

		// search
		LinkedList<Integer> result = new LinkedList<>();
		int state = 0;
		for (int i = 0; i < text.length(); i++) {
			state = automaton[state][text.charAt(i) - lowestCode];
			if (state == pattern.length()) {
				result.add(i - pattern.length() + 1);
			}
		}
		return result;
	}

	boolean isSuffix(String suffix, String word) {
		return word.endsWith(suffix);
	}

}
