/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 3.3.2020
 *  Description: queue implementation
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item value;
        private Node next;
        private Node pre;
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        this.first = new Node();
        this.last = new Node();
        this.first.next = this.last;
        this.last.pre = this.first;
        this.size = 0;
    }

    // // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // // return the number of items on the deque
    public int size() {

        return this.size;
    }

    //
    // // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null addfirst item");
        }

        Node newfirst = new Node();
        newfirst.value = item;
        newfirst.next = this.first;
        this.first.pre = newfirst;
        this.first = newfirst;
        if (this.size == 0) {
            this.last = newfirst;
        }
        this.size++;

    }

    //
    // // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null addlast item");
        }
        Node newlast = new Node();
        newlast.value = item;
        this.last.next = newlast;
        newlast.pre = this.last;
        this.last = newlast;
        if (this.size == 0) {
            this.first = newlast;
        }
        this.size++;

    }

    // // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("remove first null");
        }
        Item item = first.value;
        if (size == 1) {
            first = new Node();
        }
        else {
            first = first.next;
            first.pre = null;
        }
        this.size--;
        return item;
    }

    // // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("remove last null");
        }

        Item item = last.value;
        if (size > 1) {
            last = last.pre;
            last.next = null;
        }
        else {
            last = new Node();
        }

        this.size--;
        return item;
    }

    // // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkIterator();
    }

    private class LinkIterator implements Iterator<Item> {
        private Node current = first;
        private int n = size;

        public boolean hasNext() {
            return n != 0;
        }

        public Item next() {
            if (n == 0) {
                throw new NoSuchElementException("next is null");
            }
            Item item = current.value;
            current = current.next;
            n--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("k");
        deque.addFirst("j");
        deque.addLast("m");
        deque.addLast("c");
        deque.removeFirst();
        deque.removeLast();
        deque.addFirst("k");
        deque.removeLast();
        deque.removeLast();
        deque.removeFirst();
        deque.addFirst("k");
        deque.addFirst("j");
        Iterator<String> it = deque.iterator();
        while (it.hasNext()) {
            // System.out.println(",");
            System.out.println(it.next());
        }

    }
}
