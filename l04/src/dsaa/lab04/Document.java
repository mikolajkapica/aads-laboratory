package dsaa.lab04;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Document{
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }
    public void load(Scanner scan) {
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
                    while(i < line.length() && !Character.isWhitespace(line.charAt(i)) && line.charAt(i) != '(') {
                        sb.append(line.charAt(i));
                        i++;
                    }
                    int weight = -1;
                    if (i < line.length() && line.charAt(i) == '(') {
                        StringBuilder weightSB = new StringBuilder();
                        i++;

                        while (i < line.length() && Character.isDigit(line.charAt(i))) {
                            weightSB.append(line.charAt(i));
                            i++;
                        }
                        if (line.charAt(i) != ')') {
                            weight = -2;
                        }
                        if (line.charAt(i) == ')') {
                            String weightString = weightSB.toString();
                            if (weightString.length() == 0) {
                                weight = -2;
                            }
                            else if (weightString.length() > 0 && isOnlyDigits(weightString)) {
                                weight = Integer.parseInt(weightString);
                            }
                        }
                    }
                    // check if link is correct and print it
                    String linkString = sb.toString();
                    if (correctLink(linkString)) {
                        linkString = linkString.toLowerCase();
                        if (weight == -1) {
                            link.add(createLink(linkString));
                        } else if (weight >= 0)
                            link.add(createLink(linkString, weight));
                        }
                    }
                }
            }
        }
    private boolean isOnlyDigits(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCorrectId(String id) {
        if (!Character.isLetter(id.charAt(0))) return false;
        return true;
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    private static boolean correctLink(String link) {
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
    private static Link createLink(String link) {
        return new Link(link);
    }
    private static Link createLink(String link, int weight) {
        return new Link(link, weight);
    }

    @Override
    public String toString() {
        String retStr="Document: "+name;
        if (link.size == 0) return retStr;
        StringBuilder sb = new StringBuilder(retStr);
        ListIterator<Link> iter = link.listIterator();
        int i = 0;
        while(iter.hasNext()) {
            if (i % 10 == 0) {
                sb.append("\n").append(iter.next()).append(" ");
            } else {
                sb.append(iter.next()).append(" ");
            }
            i++;
        }
        return sb.substring(0, sb.length()-1);
    }

    public String toStringReverse() {
        String retStr="Document: "+name;
        if (link.size == 0) return retStr;
        ListIterator<Link> iter=link.listIterator();
        while(iter.hasNext())
            iter.next();
        StringBuilder sb = new StringBuilder(retStr);
        int i = 0;
        while(iter.hasPrevious()){
            if (i % 10 == 0) {
                sb.append("\n").append(iter.previous()).append(" ");
            } else {
                sb.append(iter.previous()).append(" ");
            }
            i++;
        }
        return sb.substring(0, sb.length()-1);
    }
}
