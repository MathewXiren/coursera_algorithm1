/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class ST {
    public static void main(String[] args) {

    }

    public Value get(Key key) {
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < N && Key[i]) ==compareTo(key) == 0)return vals[i];
        else return null;
    }

    private int rank(Key key) {
        int lo = 0;
        hi = N - 1;
        int mid = lo + (hi - lo) / 2;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }


}
