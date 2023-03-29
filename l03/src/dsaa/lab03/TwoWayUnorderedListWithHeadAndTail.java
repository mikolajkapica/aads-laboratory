package dsaa.lab03;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E> {

    Element head;
    Element tail;
    // can be realization with the field size or without
    int size;

    public TwoWayUnorderedListWithHeadAndTail() {
        // make a head and a tail
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(E e) {
        // if the list is empty, create a new element and set it as head and tail
        if (head == null) {
            Element newElem = new Element(e);
            head = newElem;
            tail = newElem;
            size++;
            return true;
        }

        // if the list is not empty, create a new element and set it as the next element of the tail
        tail.next = new Element(e, null, tail);
        tail = tail.next;

        size++;
        return true;
    }

    @Override
    public void add(int index, E element) throws NoSuchElementException {
        // if the index is out of bounds, throw an exception
        if (index < 0 || index > size) throw new NoSuchElementException();

        // if the index is 0
        if (index == 0) {
            // if the list is empty, create a new element and set it as head and tail
            if (head == null) {
                Element newElem = new Element(element);
                head = newElem;
                tail = newElem;
                size++;
                return;
            }

            // otherwise, create a new element and set it as the new head
            Element newElem = new Element(element, head, null);
            head.prev = newElem;
            head = newElem;
            size++;
            return;
        }

        // otherwise, get the element before the index
        // and set the new element as the next element of the previous element
        Element actElem = getElement(index - 1);
        Element newElem = new Element(element, actElem.next, actElem);

        if (actElem == tail) {
            tail = newElem;
        } else {
            actElem.next.prev = newElem;
        }
        actElem.next = newElem;

        size++;
    }

    public Element getElement(int index) throws NoSuchElementException {
        // if the index is out of bounds, throw an exception
        if (index < 0 || index > size - 1) throw new NoSuchElementException();

        // if the index is closer to the tail, start from the tail
        if (index > size / 2) {
            Element actElem = tail;
            for (int i = size - 1; i > index; i--) {
                actElem = actElem.prev;
            }
            return actElem;
        }

        // otherwise, start from the head
        Element actElem = head;
        for (int i = 0; i < index; i++) {
            actElem = actElem.next;
        }
        return actElem;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E get(int index) throws NoSuchElementException {
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException {
        Element oldElem = getElement(index);
        E oldElemObject = oldElem.object;
        oldElem.object = element;
        return oldElemObject;
    }

    @Override
    public int indexOf(E element) {
        // start with the first element
        int pos = 0;
        Element actElem = head;

        // go through the list
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
        return head != null;
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
    public E remove(int index) throws NoSuchElementException {
        // if index is 0, remove the head
        if (index == 0) {
            Element toBeRemoved = head;
            if (toBeRemoved == null) throw new NoSuchElementException();
            head = head.next;
            head.prev = null;
            size--;
            return toBeRemoved.object;
        }

        // otherwise, get the element before the index
        Element beforeToBeRemoved = getElement(index - 1);

        // the next element will be removed
        Element toBeRemoved = beforeToBeRemoved.next;

        // toBeRemoved is null, throw an exception
        if (toBeRemoved == null) throw new NoSuchElementException();

        // toBeRemoved is not the last element
        if (toBeRemoved != tail) {
            Element afterToBeRemoved = toBeRemoved.next;
            beforeToBeRemoved.next = afterToBeRemoved;
            afterToBeRemoved.prev = beforeToBeRemoved;
//            tail = afterToBeRemoved;
            size--;
            return toBeRemoved.object;
        }

        // toBeRemoved is the last element
        beforeToBeRemoved.next = null;
        tail = beforeToBeRemoved;
        size--;
        return toBeRemoved.object;
    }

    @Override
    public boolean remove(E e) {
        // if list is empty, return false
        if (head == null) return false;

        // start with head
        Element actElem = head;

        // if the head is the element to be removed, set the new head and return true
        if (actElem.object.equals(e)) {
            head = head.next;
            size--;
            return true;
        }

        // go through the list until the element is found or the end of the list is reached
        while (actElem != null && !actElem.object.equals(e)) {
            actElem = actElem.next;
        }

        // if the element is not found, return false
        if (actElem == null) return false;

        // if the element is the tail, set the new tail and return true
        if (actElem == tail) {
            tail = tail.prev;
            tail.next = null;
            size--;
            return true;
        }

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

    public String toStringReverse() {
        ListIterator<E> iter = new InnerListIterator();
        while (iter.hasNext())
            iter.next();
        StringBuilder retStr = new StringBuilder();
        while (iter.hasPrevious()) {
            retStr.append("\n").append(iter.previous());
        }
        return retStr.toString();
    }

    public String toString() {
        ListIterator<E> iter = new InnerListIterator();
        StringBuilder retStr = new StringBuilder();
        while (iter.hasNext())
            retStr.append("\n").append(iter.next());
        return retStr.toString();
    }

    public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
        if (other == null) return;
        if (other.head == null) return;
        if (head == null) {
            head = other.head;
            tail = other.tail;
            size = other.size;
            other.head = null;
            other.tail = null;
            return;
        }
        tail.next = other.head;
        other.head.prev = tail;
        tail = other.tail;
        size += other.size;
        other.head = null;
        other.tail = null;
    }

    public void removeEven() {
        // if list empty return
        if (head == null) {
            return;
        }

        // if head.next empty, empty the list
        if (head.next == null) {
            head = null;
            tail = null;
            return;
        }


        // make head the next element
        Element actElem = head.next;
        actElem.prev = null;
        head = actElem;

        // go through the list
        while (actElem.next != null) {
            // if there's next even number
            if (actElem.next.next != null) {
                // remove the next element
                actElem.next = actElem.next.next;
                actElem.next.prev = actElem;

                actElem = actElem.next;
            }

            // if there's no next even number
            else {
                actElem.next = null;
            }
        }
        // set the tail to the last element
        tail = actElem;
    }

    private class Element {
        E object;
        Element next = null;
        Element prev = null;

        public Element(E e) {
            this.object = e;
        }

        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.prev = prev;
            this.next = next;
        }
    }

    private class InnerIterator implements Iterator<E> {
        Element pos;
        // TODO maybe more fields....

        public InnerIterator() {
            pos = head;
        }

        @Override
        public boolean hasNext() {
            return pos != null;
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

    private class InnerListIterator implements ListIterator<E> {
        Element prev;
        Element next;
        int index;

        public InnerListIterator() {
            prev = null;
            next = head;
            this.index = 0;
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public boolean hasPrevious() {
            return prev != null;
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
        public void set(E e) {
            TwoWayUnorderedListWithHeadAndTail.this.set(index, e);
        }
    }

}

