package dsaa.lab05;

import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Document{
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TwoWayCycledOrderedListWithSentinel<Link>();
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
        return Character.isLetter(id.charAt(0));
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
    public int[] getWeights() {
        int[] retArr=new int[link.size];
        ListIterator<Link> iter = link.listIterator();
        int i=0;
        while(iter.hasNext()) {
            retArr[i]=iter.next().weight;
            i++;
        }
        return retArr;
    }

    public static void showArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    void bubbleSort(int[] arr) {
        showArray(arr);
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = arr.length-1; j > i; j--) {
                if (arr[j-1] > arr[j]) {
                    swap(arr, j - 1, j);
                }
            }
            showArray(arr);
        }
    }

    public void insertSort(int[] arr) {
        showArray(arr);
        for (int i = arr.length-2; i >= 0; i--) {
            int j = i;
            while (j < arr.length-1 && arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
                j++;
            }
            showArray(arr);
        }
    }

    private void swap(int[] arr, int pos1, int pos2) {
        int temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }

    public void selectSort(int[] arr) {
        showArray(arr);
        for (int i = arr.length-1; i > 0; i--) {
            int maxValuePosition = i;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[maxValuePosition]) {
                    maxValuePosition = j;
                }
            }
            swap(arr, i, maxValuePosition);
            showArray(arr);
        }
    }

}
