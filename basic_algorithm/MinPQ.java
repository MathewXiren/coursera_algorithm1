/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class MinPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N = 0;

    public MinPQ(int capacity) {
        pq = (Key[]) new Object[capacity + 1];

    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key item) {
        pq[++N] = item;
        swim(N);
    }

    public void swim(int k) {
        while (k > 1 && less(pq[k], pq[k / 2])) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    public Key delMin() {
        Key min = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null;
        return min;
    }

    public void sink(int k) {
        while (2 * k < N) {
            int j = 2 * k;
            if (less(pq[j + 1], pq[j])) j++;
            if (!less(pq[j], pq[k])) break;

            exch(j, k);
            k = j;

        }
    }

    public void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public boolean less(Key x, Key y) {
        return x.compareTo(y) < 0;
    }

    public static void main(String[] args) {

    }
}
