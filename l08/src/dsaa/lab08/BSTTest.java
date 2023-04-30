package dsaa.lab08;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BSTTest {

    private void addMultiple (BST myBST, Integer ... values) {
        for (Integer value : values) {
            myBST.add(value);
        }
    }
    @Test
    public void BSTInOrderTest() {
        BST<Integer> myBST = new BST<>();
        addMultiple(myBST, 15, 8, 17, 2, 11, 4, 10, 13, 18, 25, 22);
        String stringInOrder = myBST.toStringInOrder();
        assertEquals("2,4,8,10,11,13,15,17,18,22,25", stringInOrder);
    }

    @Test
    public void BSTPreOrderTest() {
        BST<Integer> myBST = new BST<>();
        addMultiple(myBST, 15, 8, 17, 2, 11, 4, 10, 13, 18, 25, 22);
        String stringPreOrder = myBST.toStringPreOrder();
        assertEquals("15,8,2,4,11,10,13,17,18,25,22", stringPreOrder);
    }

    @Test
    public void BSTPostOrderTest() {
        BST<Integer> myBST = new BST<>();
        addMultiple(myBST, 15, 8, 17, 2, 11, 4, 10, 13, 18, 25, 22);
        String stringPostOrder = myBST.toStringPostOrder();
        assertEquals("4,2,10,13,11,8,22,25,18,17,15", stringPostOrder);
    }

    @Test
    public void BSTClear() {
        BST<Integer> myBST = new BST<>();
        addMultiple(myBST, 15, 8, 17, 2, 11, 4, 10, 13, 18, 25, 22);
        myBST.clear();
        assertEquals("", myBST.toStringInOrder());
    }

    @Test
    public void BSTSuccessor() {
        BST<Integer> myBST = new BST<>();
        addMultiple(myBST, 15, 8, 17, 2, 11, 4, 10, 13, 18, 25, 22);
        assertEquals(17, myBST.successor(15).intValue());
        assertEquals(11, myBST.successor(10).intValue());
        assertEquals(13, myBST.successor(11).intValue());
        assertEquals(22, myBST.successor(18).intValue());
        assertEquals(25, myBST.successor(22).intValue());
        assertEquals(null, myBST.successor(25));
    }
    @Test
    public void BSTSize() {
        BST<Integer> myBST = new BST<>();
        addMultiple(myBST, 15, 8, 17, 2, 11, 4, 10, 13, 18, 25, 22);
        assertEquals(11, myBST.size());
        addMultiple(myBST, 1, 3, 5, 6, 7, 9, 12, 14, 16, 19, 20, 21, 23, 24);
        assertEquals(25, myBST.size());
    }



}