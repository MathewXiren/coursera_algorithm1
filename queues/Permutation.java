/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 3.3.2020
 *  Description: test
 **************************************************************************** */

// import edu.princeton.cs.algs4.In;

// import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());

        }
        Iterator<String> ii = q.iterator();
        while (ii.hasNext() && count < k) {
            String c = ii.next();
            count++;
            System.out.println(c);
        }
    }
}
