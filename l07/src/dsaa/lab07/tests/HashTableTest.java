package dsaa.lab07.tests;


import dsaa.lab07.Document;
import dsaa.lab07.HashTable;
import org.junit.Test;

import javax.print.Doc;

import static org.junit.Assert.assertEquals;

public class HashTableTest {

    @Test
    public void hashFunctionDocPlaceTest() {
        HashTable hashTable = new HashTable(10);
        Document secon = new Document("secon");
        assertEquals(2, hashTable.hashFunctionDocPlace(secon.hashCode()));
    }
}