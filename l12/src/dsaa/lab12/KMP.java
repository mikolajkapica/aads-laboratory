package dsaa.lab12;

import java.util.LinkedList;

public class KMP implements IStringMatcher {

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		if (pattern.length() == 0 || text.length() == 0) {
			return new LinkedList<Integer>();
		}

		// calculate prefix function
		int[] prefixFunction = new int[pattern.length()];
		prefixFunction[0] = 0;
		for (int i = 1; i < pattern.length(); i++) {
			int j = prefixFunction[i - 1];
			while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
				j = prefixFunction[j - 1];
			}
			if (pattern.charAt(i) == pattern.charAt(j)) {
				j++;
			}
			prefixFunction[i] = j;
		}

		// search
		LinkedList<Integer> result = new LinkedList<Integer>();
		int j = 0;
		for (int i = 0; i < text.length(); i++) {
			while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
				j = prefixFunction[j - 1];
			}
			if (text.charAt(i) == pattern.charAt(j)) {
				j++;
			}
			if (j == pattern.length()) {
				result.add(i - pattern.length() + 1);
				j = prefixFunction[j - 1];
			}
		}
		return result;
	}

}
