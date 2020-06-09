/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 21.3.2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;

public class PointSET {
    private SET<Point2D> pset;

    // construct an empty set of points
    public PointSET() {
        pset = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pset.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pset.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("contains point is null");
        }
        pset.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("contains point is null");
        }
        return pset.contains((p));
    }

    // draw all points to standard draw
    public void draw() {
        // System.out.println("brute draw: ");
        for (Point2D p : pset) {
            p.draw();
            // System.out.print(" point: " + p);

        }
        // System.out.println();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("contains range rect is null");
        }
        Queue<Point2D> recset;
        Iterator<Point2D> it = pset.iterator();
        recset = new Queue<Point2D>();
        while (it.hasNext()) {
            Point2D point = it.next();
            if (rect.contains(point)) {
                recset.enqueue(point);
            }
        }
        return recset;
        // return new RecPoint(rect);
    }

    // private class RecPoint implements Iterable<Point2D> {
    //
    //
    //
    //     public RecPoint(RectHV rect) {
    //
    //     }
    //
    //     public Iterator<Point2D> iterator() {
    //
    //     }
    // }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("contains point is null");
        }
        if (pset.isEmpty()) {
            return null;
        }

        Iterator<Point2D> pit = pset.iterator();
        double minDis = Double.POSITIVE_INFINITY;
        Point2D minpoint = null;
        while (pit.hasNext()) {
            Point2D tpoint = pit.next();
            double dis = p.distanceSquaredTo(tpoint);
            if (dis < minDis) {
                minDis = dis;
                minpoint = tpoint;
            }
        }
        return minpoint;
    }

    public static void main(String[] args) {

    }
}
