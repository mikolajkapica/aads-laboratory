package dsaa.lab07.tests;

import dsaa.lab07.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DocumentTest {

    @Test
    public void testHashCode() {
        String str = "xxx";
        assertEquals(10680, new Document(str).hashCode());
        str = "abcd";
        assertEquals(112498, new Document(str).hashCode());
    }
}