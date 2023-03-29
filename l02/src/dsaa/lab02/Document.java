package dsaa.lab02;

import java.util.Scanner;

public class Document{
    public String name;
    public OneWayLinkedList<Link> links;
    public Document(String name, Scanner scan) {
        this.links = new OneWayLinkedList<>();
        this.name = name;
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
                    while(i < line.length() && !Character.isWhitespace(line.charAt(i))) {
                        sb.append(line.charAt(i));
                        i++;
                    }
                    // check if link is correct and print it
                    String link = sb.toString();
                    if (correctLink(link)) {
                        links.add(new Link(link));
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Document: ").append(name);
        for (int i = 0; i < links.size(); i++) {
            sb.append("\n").append(links.get(i).ref);
        }
        return sb.toString();
    }

}
