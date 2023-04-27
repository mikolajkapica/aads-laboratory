package dsaa.lab07;

import java.util.ListIterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Document implements IWithName {
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;

    public Document(String name) {
        this.name=name.toLowerCase();
        link=new TwoWayCycledOrderedListWithSentinel<Link>();
    }
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
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isLowerCase(id.charAt(i))) {
                return false;
            }
        }
        return true;
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

    public void iterativeMergeSort(int[] arr) {
        showArray(arr);
        int n = arr.length;

        // creating arrays of current size, 1, 2, 4...
        for (int curr_size = 1; curr_size <= n-1; curr_size = 2*curr_size) {
            // starting point of left subarray
            for (int left_start = 0; left_start < n-1; left_start += 2*curr_size) {
                // Find ending point of left subarray
                int mid = Math.min(left_start + curr_size - 1, n-1);
                // Find ending point of right subarray
                int right_end = Math.min(left_start + 2*curr_size - 1, n-1);
                // Merge left and right subarrays
                merge(arr, left_start, mid, right_end);
            }
            showArray(arr);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int sizeLeft = mid - left + 1;
        int[] L = new int[sizeLeft];
        System.arraycopy(arr, left, L, 0, sizeLeft);

        int sizeRight = right - mid;
        int[] R = new int[sizeRight];
        System.arraycopy(arr, mid + 1, R, 0, sizeRight);

        // Merge the temp arrays back into arr[left..right]

        // Initial index of left subarray
        int i = 0;
        // Initial index of right subarray
        int j = 0;
        // Initial index of merged subarray
        int k = left;

        while (i < sizeLeft && j < sizeRight) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy if there are any left in L
        while (i < sizeLeft) {
            arr[k++] = L[i++];
        }

        // Copy if there are any left in R
        while (j < sizeRight) {
            arr[k++] = R[j++];
        }
    }

    public void radixSort(int[] arr) {
        showArray(arr);
        int max = 999;
        // Do counting sort for each digit place
        for (int digitPlace = 1; digitPlace <= max; digitPlace *= 10) {
            countingSort(arr, digitPlace);
            showArray(arr);
        }
    }

    public static void countingSort(int[] arr, int digitPlace) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        // Check what are the digits at the current digit place
        // (124/1) % 10 = 4, (124 / 10) % 10 = 2, (124 / 100) % 10 = 1
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / digitPlace) % 10;
            count[digit]++;
        }

        // Set count to be the index of the last element in the output array
        // if count looks like
        // 0 4 2 5 1
        // then it will be
        // -1 3 5 10 11
        count[0]--;
        for (int i = 1; i < 10; i++) {
            count[i] += count[i-1];
        }

        // Build the output array
        // from the last element to the first element because we want to keep the order of the elements with the same digit
        // to be stable,
        // for example: 724, 824, 924
        // we put it back: 724, 824, 924
        // and not: 924, 824, 724
        for (int i = n-1; i >= 0; i--) {
            int digit = (arr[i] / digitPlace) % 10;
            output[count[digit]] = arr[i];
            count[digit]--;
        }

        // copy the output array to the input array
        System.arraycopy(output, 0, arr, 0, n);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int MODVALUE=100000000;
        int[] numbers = {7,11,13,17,19};
        int numbersLength = numbers.length;
        int nameLength = name.length();
        int hash = name.charAt(0);
        for (int i = 0; i < nameLength - 1; i++) {
            hash *= numbers[i % numbersLength] % MODVALUE;
            hash += name.charAt(i + 1) % MODVALUE;
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(name, document.name);
    }
}
