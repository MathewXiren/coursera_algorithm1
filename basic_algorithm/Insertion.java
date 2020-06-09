/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.Comparator;

public class Insertion {
    // assume the left part is sorted, then start from the right
    // if the found one is smaller, than left one ,switch the element.
    // best already sort n-1
    // worst reverse n^2/2
    // partically sorted n
    // stable
    public static sort(Object[] a, Comparator comparator) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1]) {
                    exch(a, j, j - 1);
                }else break;
            }
        }
    }

    private static boolean less(Comparator c, Object v, Object w) {
        return c.compare(v, w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swp = a[i];
        a[i] = a[j];
        a[j] = swp;
    }

    private static boolean isSorted(Object[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
