package dsaa.lab02;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E> {
    private int size;

    private class Element {
        E object;
        Element next = null;

        public Element(E e) {
            this.object = e;
        }
    }

    private class InnerIterator implements Iterator<E> {
        Element actElem;

        public InnerIterator() {
            // start with the first element
            actElem = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            // if there is a next element, return true
            return actElem != null;
        }

        @Override
        public E next() {
            // if there is no next element, throw an exception
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // return the next element and move the pointer to the next element
            E object = actElem.object;
            actElem = actElem.next;
            return object;
        }
    }

    Element sentinel;
    public OneWayLinkedList() {
        // make a sentinel and set the size to 0
        sentinel = new Element(null);
        sentinel.next = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        // create a new element with the given object
        Element newElem = new Element(e);

        // start with the sentinel and go to the last element
        Element actElem = sentinel;
        while (actElem.next != null) {
            actElem = actElem.next;
        }

        // add the new element to the end of the list
        actElem.next = newElem;

        // increase the size
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) throws NoSuchElementException {
        // if the index is out of bounds, throw an exception
        if (index < 0 || index > size)
            throw new NoSuchElementException();

        // create a new element with the given object
        Element newElem = new Element(element);

        if (index == 0) {
            newElem.next = sentinel.next;
            sentinel.next = newElem;
            size++;
            return;
        }

        // start with the first element and go to the element before the given index
        Element actElem = sentinel;
        for (int i = 0; i < index; i++) {
            actElem = actElem.next;
        }

        newElem.next = actElem.next;
        actElem.next = newElem;

        // increase the size
        size++;
    }

    @Override
    public void clear() {
        sentinel.next = null;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        return (indexOf(element) != -1);
    }

    @Override
    public E get(int index) throws NoSuchElementException {
        // if the index is out of bounds, throw an exception
        if (index < 0 || index >= size())
            throw new NoSuchElementException();

        // start with the first element and go to the element at the given index
        Element currElem = sentinel.next;
        for (int i = 0; i < index; i++) {
            currElem = currElem.next;
        }

        // return the object of the element
        return currElem.object;
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException {
        // if the index is out of bounds, throw an exception
        if (index < 0 || index >= size())
            throw new NoSuchElementException();

        // start with the first element and go to the element at the given index
        Element currElem = sentinel.next;
        for (int i = 0; i < index; i++) {
            currElem = currElem.next;
        }

        // set the new object and return the old object
        E oldElem = currElem.object;
        currElem.object = element;
        return oldElem;
    }

    @Override
    public int indexOf(E element) {
        // start with the first element and go through the list
        int pos = 0;
        Element actElem = sentinel.next;
        while (actElem != null) {
            // if the object of the element is equal to the given object, return the position
            if (actElem.object.equals(element))
                return pos;
            pos++;
            actElem = actElem.next;
        }
        // if the object is not found, return -1
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == null;
    }

    @Override
    public E remove(int index) throws NoSuchElementException {
        // if the index is out of bounds, throw an exception
        if (index < 0 || index >= size())
            throw new NoSuchElementException();

        // start with the sentinel and go to the element before the element to be removed
        Element currElem = sentinel;
        for (int i = 0; i < index; i++) {
            currElem = currElem.next;
        }

        // remove the element
        Element ePrevious = currElem;
        Element e = ePrevious.next;
        ePrevious.next = e.next;

        // decrease the size
        size--;

        // return the object of the removed element
        return e.object;
    }

    @Override
    public boolean remove(E e) {
        // start with the sentinel and go through the list
        Element currElem = sentinel;
        while (currElem.next != null) {
            // if the object of the next element is equal to the given object, remove the element, decrease the size and return true
            if (currElem.next.object.equals(e)) {
                currElem.next = currElem.next.next;
                size--;
                return true;
            }
            currElem = currElem.next;
        }

        // if the object is not found, return false
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    public void deleteEven() {
        Element currElem = sentinel;
        boolean even = true;
        while (currElem.next != null) {
            if (even) {
                currElem.next = currElem.next.next;
                size--;
                even = false;
                continue;
            }
            even = true;
            currElem = currElem.next;
        }
    }
}