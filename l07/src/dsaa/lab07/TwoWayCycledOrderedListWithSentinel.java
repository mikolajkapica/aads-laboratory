package dsaa.lab07;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E> {

    private class Element{

        E object;
        Element next=null;
        Element prev=null;

        public Element(E e) {
            this.object = e;
        }
        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
        }
        // add element e after this
        public void addAfter(Element elem) {
            this.next.prev = elem;
            elem.next = this.next;
            this.next = elem;
            elem.prev = this;
        }

        // assert it is NOT a sentinel
        public void remove() {
            assert this != sentinel;
            this.prev.next = this.next;
            this.next.prev = this.prev;
        }
        @SuppressWarnings("unchecked")
        public int compareTo(Element element) {
            return ((Comparable<E>)this.object).compareTo(element.object);
        }
    }

    private class InnerIterator implements Iterator<E>{
        Element pos;

        public InnerIterator() {
            pos = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return pos != sentinel;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E object = pos.object;
            pos = pos.next;
            return object;
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        Element prev;
        Element next;
        int index;

        public InnerListIterator() {
            prev = sentinel;
            next = sentinel.next;
            this.index = 0;
        }
        @Override
        public boolean hasNext() {
            return next != sentinel;
        }
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E object = next.object;
            prev = next;
            next = next.next;
            index++;
            return object;
        }
        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }
        @Override
        public boolean hasPrevious() {
            return prev != sentinel;
        }
        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            E object = prev.object;
            next = prev;
            prev = prev.prev;
            index--;
            return object;
        }
        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void set(E arg0) {
            throw new UnsupportedOperationException();
        }
    }

    Element sentinel;
    int size;

    public TwoWayCycledOrderedListWithSentinel() {
        sentinel = new Element(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    //@SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        // start with the first element
        Element actElem = sentinel.next;

        // go through the list until the end or until the element is found
        while (actElem != sentinel) {
            if (actElem.compareTo(new Element(e)) > 0) {
                break;
            }
            actElem = actElem.next;

        }
        // if the element is found, add it before it
        Element newElem = new Element(e, actElem, actElem.prev);
        actElem.prev.next = newElem;
        actElem.prev = newElem;
        size++;
        return true;
    }

    private Element getElement(int index) {
        // if the index is out of bounds, throw an exception
        if (index < 0 || index > size - 1) throw new NoSuchElementException();


        // if the index is closer to the tail, start from the tail
        if (index > size / 2) {
            Element actElem = sentinel.prev;
            for (int i = size - 1; i > index; i--) {
                actElem = actElem.prev;
            }
            return actElem;
        }

        // otherwise, start from the head
        Element actElem = sentinel.next;
        for (int i = 0; i < index; i++) {
            actElem = actElem.next;
        }
        return actElem;
    }

    private Element getElement(E obj) {
        // if the index is out of bounds, throw an exception
        Element actElem = sentinel.next;

        while (actElem != sentinel) {
            if (actElem.object.equals(obj))
                return actElem;
            actElem = actElem.next;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E get(int index) {
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        // start with the first element
        int pos = 0;
        Element actElem = sentinel.next;

        // go through the list
        while (actElem != sentinel) {
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
        return sentinel.next == sentinel;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size - 1) throw new NoSuchElementException();
        Element elementToDelete = getElement(index);
        elementToDelete.prev.next = elementToDelete.next;
        elementToDelete.next.prev = elementToDelete.prev;
        size--;
        return elementToDelete.object;
    }

    @Override
    public boolean remove(E e) {
        // if list is empty, return false
        if (isEmpty()) return false;

        // start with first element
        Element actElem = sentinel.next;

        // go through the list until the element is found or the end of the list is reached
        while (actElem != sentinel && !actElem.object.equals(e)) {
            actElem = actElem.next;
        }

        // if the element is not found, return false
        if (actElem == sentinel) return false;

        // otherwise just remove the element
        actElem.prev.next = actElem.next;
        actElem.next.prev = actElem.prev;

        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

//    @SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
        if (other == null) return;
        if (other == this) return;
        if (other.isEmpty()) return;


        Element thisActElem = sentinel.next;
        Element otherActElem = other.sentinel.next;

        while (otherActElem != other.sentinel && thisActElem != sentinel) {
            Element otherActElemNext = otherActElem.next;
            if (otherActElem.compareTo(thisActElem) < 0) {
                otherActElem.prev.next = otherActElem.next;
                otherActElem.next.prev = otherActElem.prev;
                thisActElem.prev.next = otherActElem;
                otherActElem.prev = thisActElem.prev;
                otherActElem.next = thisActElem;
                thisActElem.prev = otherActElem;
                this.size++;
                other.size--;
                otherActElem = otherActElemNext;
                continue;
            }
            thisActElem = thisActElem.next;
        }
        if (thisActElem == sentinel) {
            thisActElem = thisActElem.prev;
            thisActElem.next = other.sentinel.next;
            other.sentinel.next.prev = thisActElem;
            other.sentinel.prev.next = sentinel;
            sentinel.prev = other.sentinel.prev;
            this.size += other.size;
            other.clear();
        }
    }

    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
        if (e == null) return;
        Element actElem = sentinel.next;
        while (actElem != sentinel) {
            if (actElem.object.equals(e)) {
                actElem.prev.next = actElem.next;
                actElem.next.prev = actElem.prev;
                size--;
            }
            actElem = actElem.next;
        }
    }

}

