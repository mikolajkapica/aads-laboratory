package dsaa.lab12;

import java.util.LinkedList;

public class KMP implements IStringMatcher {

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		if (pattern.length() == 0 || text.length() == 0) {
			return new LinkedList<Integer>();
		}

		// calculate prefix function
		int[] prefixFunction = computePrefixFunction(pattern);

		// search
		LinkedList<Integer> result = new LinkedList<Integer>();
		int q = 0;
		for (int i = 0; i < text.length(); i++) {
			while (q > 0 && pattern.charAt(q) != text.charAt(i)) {
				q = prefixFunction[q - 1];
			}
			if (pattern.charAt(q) == text.charAt(i)) {
				q += 1;
			}
			if (q == pattern.length()) {
				result.add(i - pattern.length() + 1);
				q = prefixFunction[q - 1];
			}
		}
		return result;
	}

	static int[] computePrefixFunction(String pattern) {
		int[] pi = new int[pattern.length()];
		pi[0] = 0;
		int k = 0;
		for (int q = 1; q < pattern.length(); q++) {
			while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
				k = pi[k - 1];
			}
			if (pattern.charAt(k) == pattern.charAt(q)) {
				k += 1;
			}
			pi[q] = k;
		}
		return pi;
	}
}
