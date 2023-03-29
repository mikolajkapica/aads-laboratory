package dsaa.lab01;

import java.util.Scanner;

public class Document {
    public static void loadDocument(String name, Scanner scan) {
        String line;
        while (!(line = scan.nextLine()).equals("eod")) {
            char[] fiveLast = new char[5];
            for (int i = 0; i < line.length(); i++) {
                // shift array
                fiveLast[0] = fiveLast[1];
                fiveLast[1] = fiveLast[2];
                fiveLast[2] = fiveLast[3];
                fiveLast[3] = fiveLast[4];
                // add new char
                fiveLast[4] = line.charAt(i);
                String fiveLastString = new String(fiveLast);
                // check if link is found
                if (fiveLastString.equalsIgnoreCase("link=")) {
                    StringBuilder sb = new StringBuilder();
                    i += 1;
                    // add all chars until whitespace
                    while(i < line.length() && !Character.isWhitespace(line.charAt(i))) {
                        sb.append(line.charAt(i));
                        i++;
                    }
                    // check if link is correct and print it
                    String link = sb.toString();
                    if (correctLink(link)) {
                        System.out.println(link.toLowerCase());
                    }
                }
            }
        }
    }



    // accepted only small letters, capitalic letter, digits nad '_' (but not on the beginning)
    public static boolean correctLink(String link) {
        // link is empty or starts with not a letter
        if (link.length() == 0 || !Character.isLetter(link.charAt(0))) {
            return false;
        }
        // check if link contains only letters, digits and '_'
        for (int i = 0; i < link.length(); i++) {
            boolean validCondition = Character.isLetterOrDigit(link.charAt(i)) || link.charAt(i) == '_';
            if (!validCondition) {
                return false;
            }
        }
        return true;
    }

}
