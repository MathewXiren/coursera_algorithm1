/* *****************************************************************************
 *  Name:              Xiren Ma
 *  Last modified:     3.10.2020
 **************************************************************************** */

public class Heap {
    // linear extra space
    // worst case NlogN
    // bad side:
    // make poor use of cache memory
    // looper longer than quicksort
    public static void sort(Comparable[] pq) {
        int N = pq.length;
        for (int k = N / 2; k > 1; k--) { // construct the heap
            sink(pq, k, N);
        }
        while (N > 1) { // sort by keep "removing" the max
            exch(pq, 1, N);
            sink(pq, 1, --N);
        }
    }

    private static void sink(Comparable[] pq, int k, int N) {
        while (2 * k < N) {
            int j = 2 * k;
            if (j < N && less(pq, j, j + 1)) {
                j++;
            }
            if (!less(pq, k, j)) {
                break;
            }
            exch(pq, k, j);
            k = j;
        }
    }

    private static void swim(Comparable[] pq, int k, int N) {
        while (k / 2 > 0) {
            if less(pq[k / 2] < pq[k]) {
                exch(pq, k, k / 2)
            }
            k = k / 2;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private static void exch(Comparable[] pq, int i, int j) {
        Comparable temp = pq[i];
        pq[i] = pq[j];
        pq[j] = pq[i];
    }

    public static void main(String[] args) {

    }
}
