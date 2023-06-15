package dsaa.lab12;

import java.util.LinkedList;

import static dsaa.lab12.KMP.computePrefixFunction;

public class Automaton implements IStringMatcher {

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		if (pattern.length() == 0 || text.length() == 0) {
			return new LinkedList<>();
		}

		// Alphabet ascii range [lowestCode, biggerCode]
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


		// Automaton
		int[][] automaton = new int[pattern.length() + 1][alphabetRange];

		/* O(m^3 * |alphabet|) Solution
		for (int state = 0; state <= pattern.length(); state++) {
			for (int c = 0; c < alphabetrange; c++) {
				int nextstate = math.min(pattern.length() + 1, state + 2);
				do {
					nextstate--;
					// if the pattern is a suffix of the pattern + character
				} while (!(pattern.substring(0, nextstate).endswith(pattern.substring(0, state) + (char) (c + lowestcode))));
				automaton[state][c] = nextstate;
			}
		}
		*/

		// Using a prefixFunction concept from KMP
		// we can achieve time complexity of O(m * |alphabet|)

		// Theta(m) time complexity, O(m) <= Theta(m)
		int[] prefixArray = computePrefixFunction(pattern);

		// O(m * |alphabet|)
		// automata starts in state 0 for all characters
		for (int state = 0; state <= pattern.length(); state++) {
			for (int c = 0; c < alphabetRange; c++) {
				// if T[c] is different from P[state]
				if (state == pattern.length() || pattern.charAt(state) != (char)(c + lowestCode)) {
					if (state == 0) {
						automaton[state][c] = 0;
					} else {
						// go to the state with the longest prefix that is also a suffix of P[0..state-1]c
						automaton[state][c] = automaton[prefixArray[state - 1]][c];
					}
				} else {
					// If next character matches the pattern, go to the next state
					automaton[state][c] = state + 1;
				}
			}
		}
		// O(m) + O(m * |alphabet|) + O(m * |alphabet|) = O(m * |alphabet|)


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

}
