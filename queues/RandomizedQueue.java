/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 3.5.2020
 *  Description: ramdom queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int end;
    private int capacity = 1;
    private int front;
    // private boolean[] sss;

    // construct an empty randomized queue\
    // @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        this.end = 0;
        this.s = (Item[]) new Object[capacity];
        this.front = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.end - this.front <= 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.end - this.front;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null enqueue");
        }
        if (this.end == this.capacity) {
            resize(2 * this.capacity);
        }
        s[this.end++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("remove first null");
        }
        int j = StdRandom.uniform(this.front, this.end);

        Item item = this.s[j];
        if (j == this.front) {
            this.s[this.front++] = null;
        }
        else if (j == this.end - 1) {
            this.s[--this.end] = null;
        }
        else { // swap the removed item with the beignning element
            this.s[j] = this.s[this.front];
            this.s[this.front++] = null;
        }

        // System.out.println("end" + this.end);
        if ((this.end - this.front) > 0 && (this.end - this.front) == (this.capacity / 4)) {
            resize(this.capacity / 2);
        }
        return item;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("remove first null");
        }
        int j = StdRandom.uniform(this.front, this.end);
        Item item = this.s[j];

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // @SuppressWarnings("unchecked")
    private void resize(int ss) {
        Item[] copy = (Item[]) new Object[ss];
        int j = front;
        for (int i = 0; i < end - front; i++) {
            copy[i] = s[j++];
        }
        s = copy;
        end = end - front;
        front = 0;
        this.capacity = ss;
    }

    private class ArrayIterator implements Iterator<Item> {
        private int e = end;
        private Item[] c = s.clone();
        private int f = front;

        public boolean hasNext() {
            return e - f > 0;
        }

        public Item next() {
            if (e - f == 0) {
                throw new NoSuchElementException("no element");
            }
            int j = StdRandom.uniform(f, e);
            Item item = c[j];
            if (j == f) {
                this.c[f++] = null;
            }
            else if (j == e - 1) {
                this.c[--e] = null;
            }
            else {
                this.c[j] = this.c[f];
                this.c[f++] = null;
            }

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String x;
        // rq.size();
        // rq.isEmpty();
        // rq.isEmpty();
        // rq.isEmpty();
        // rq.isEmpty();
        // rq.isEmpty();
        // rq.enqueue("k");
        // rq.isEmpty();
        // rq.size();
        // rq.enqueue("j");
        // x = rq.dequeue();
        rq.enqueue("k");
        rq.enqueue("j");
        rq.enqueue("l");
        rq.enqueue("l");
        rq.enqueue("l");
        rq.enqueue("l");
        rq.enqueue("l");
        rq.enqueue("l");
        rq.enqueue("l");
        rq.enqueue("l");
        rq.enqueue("l");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        rq.enqueue("j");
        x = rq.sample();     //==> 744
        System.out.println(x);
        rq.size();        //==> 3
        x = rq.dequeue();     //==> 744
        System.out.println(x);
        rq.size();        //==> 2
        rq.size();       // ==> 2
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.sample();      //==>null
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        x = rq.dequeue();    // ==> 17
        System.out.println(x);
        // s.enqueue("j");
        // s.enqueue("j");
        // s.enqueue("l");
        // String x = s.dequeue();
        // s.dequeue();
        System.out.println(x);

    }

}
