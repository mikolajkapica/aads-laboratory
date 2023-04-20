package dsaa.lab06;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.Assert.assertTrue;

class DocumentTest {

    @Test
    void iterativeMergeSort() {
        // get 100 random numbers
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 1000);
        }
        Document doc = new Document("test", new Scanner(""));
        doc.iterativeMergeSort(arr);
        // check if the array is sorted
        for (int i = 0; i < arr.length - 1; i++) {
            assertTrue(arr[i] <= arr[i + 1]);
        }
    }

    @Test
    void radixSort() {
        // get 100 random numbers
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 1000);
        }
        Document doc = new Document("test", new Scanner(""));
        doc.radixSort(arr);
        // check if the array is sorted
        for (int i = 0; i < arr.length - 1; i++) {
            assertTrue(arr[i] <= arr[i + 1]);
        }
    }
}