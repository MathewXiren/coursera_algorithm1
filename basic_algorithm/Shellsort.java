/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class Shellsort {
    // upgrade of insertion sort
    // instead of switch 1 position each time, go h back.
    // increment use 3x +1
    // worst O(N^{3/2})
    // better for small size of array
    // not stable
    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;  // 1 4 13 40

        while (h >= 1) {
            //h-sort the array
            for (int i = h; h < N; i++) {
                for (int j = i; j > h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;

        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swp = a[i];
        a[i] = a[j];
        a[j] = swp;
    }

    public static void main(String[] args) {

    }
}
