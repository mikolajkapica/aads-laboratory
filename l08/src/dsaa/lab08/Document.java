package dsaa.lab08;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Document implements IWithName {
    public String name;
    public BST<Link> link;
    public Document(String name) {
        this.name=name.toLowerCase();
        link=new BST<Link>();
    }
    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new BST<Link>();
        load(scan);
    }
    public void load(Scanner scan) {
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.length() == 0) continue;
            if (line.equalsIgnoreCase("eod")) break;
            String[] splittedLine = line.split("\\s+");
            for (String s : splittedLine) {
                if (s.toLowerCase().startsWith("link=")) {
                    Link linkTyped = createLink(s.substring(5).toLowerCase());
                    if (linkTyped != null) {
                        this.link.add(linkTyped);
                    }
                }
            }
        }
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    private static boolean correctLink(String link) {
        Pattern pattern = Pattern.compile("[a-zA-Z]\\w*(\\(\\d+\\))?");
        return pattern.matcher(link).matches();
    }

    public static Link createLink(String linkTyped) {
        if (!correctLink(linkTyped)) return null;
        if (linkTyped.charAt(linkTyped.length()-1) == ')') {
            String[] split = linkTyped.split("\\(");
            String linkName = split[0];
            int weight = Integer.parseInt(split[1].substring(0, split[1].length()-1));
            return new Link(linkName, weight);
        } else {
            return new Link(linkTyped);
        }
    }

    public static boolean isCorrectId(String id) {
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isLowerCase(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String retStr="Document: "+name+"\n";
        retStr+=link.toStringInOrder();
        return retStr;
    }

    public String toStringPreOrder() {
        String retStr="Document: "+name+"\n";
        retStr+=link.toStringPreOrder();
        return retStr;
    }

    public String toStringPostOrder() {
        String retStr="Document: "+name+"\n";
        retStr+=link.toStringPostOrder();
        return retStr;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }
}
