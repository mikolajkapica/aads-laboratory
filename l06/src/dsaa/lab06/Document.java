package dsaa.lab06;

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

    public void iterativeMergeSort(int[] arr) {
		showArray(arr);

        int n = arr.length;

        // Merge subarrays in bottom-up manner.
        // First merge subarrays of size 1 to create
        // sorted subarrays of size 2, then merge
        // subarrays of size 2 to create sorted
        // subarrays of size 4, and so on.
        for (int curr_size = 1; curr_size <= n-1; curr_size = 2*curr_size) {
            // Pick starting point of different subarrays of current size
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

    public static void merge(int arr[], int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
    
        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];
    
        // Copy data to temp arrays L[] and R[]
        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];
    
        // Merge the temp arrays back into arr[left..right]
        int i = 0; // Initial index of left subarray
        int j = 0; // Initial index of right subarray
        int k = left; // Initial index of merged subarray
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
    
        // Copy the remaining elements of L[], if there are any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
    
        // Copy the remaining elements of R[], if there are any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    

	public void radixSort(int[] arr) {
		showArray(arr);
		int n = arr.length;
        int max = 999;

        // Do counting sort for each digit place
        for (int digitPlace = 1; max/digitPlace > 0; digitPlace *= 10) {
            countingSort(arr, n, digitPlace);
            showArray(arr);
        }
	}
    
    public static void countingSort(int arr[], int n, int digitPlace) {
        int output[] = new int[n];
        int count[] = new int[10];
    
        // Store count of occurrences in count[]
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / digitPlace) % 10;
            count[digit]++;
        }
    
        // Modify count[] so that it contains actual position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i-1];
        }
    
        // Build the output array
        for (int i = n-1; i >= 0; i--) {
            int digit = (arr[i] / digitPlace) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
    
        // Copy the output array to arr[], so that arr[] now contains sorted numbers according to current digit place
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

}
