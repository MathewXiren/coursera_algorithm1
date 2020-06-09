/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 21.3.2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node<Point2D> root;
    private int count;
    // private static int num; //for debug, num can be delete

    private class Node<Key> {
        private Key key;
        private Node<Key> left;
        private Node<Key> right;
        private RectHV rec;
        private char sec; //ab: x axis, cd: y axis; a:left, b:right, c:down(left) d:up(right);
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        count = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // number of points in the set
    public int size() {
        return count;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("contains point is null");
        }
        if (!contains(p)) {
            count++;
            root = insert(root, p, 'a', null);
        }
    }

    private Node<Point2D> insert(Node<Point2D> h, Point2D p, char sec, Node<Point2D> old) {
        if (h == null) {
            Node<Point2D> t = new Node<Point2D>();
            t.key = p;
            t.sec = sec;
            // System.out.println("p: " + p);
            if (old == null) {
                t.rec = new RectHV(0, 0, 1, 1);
            }
            else if (sec == 'c') {
                double x0 = old.rec.xmin();
                // double x1 = old.rec.xmax();
                double y0 = old.rec.ymin();
                double y1 = old.rec.ymax();
                double x1 = old.key.x();

                t.rec = new RectHV(x0, y0, x1, y1);
                // System.out.println("c: " + t.rec);


            }
            else if (sec == 'd') {
                // double x0 = old.rec.xmin();
                double x1 = old.rec.xmax();
                double y0 = old.rec.ymin();
                double y1 = old.rec.ymax();
                double x0 = old.key.x();

                t.rec = new RectHV(x0, y0, x1, y1);
                // System.out.println("d: " + t.rec);

            }
            else if (sec == 'a') {
                double x0 = old.rec.xmin();
                double x1 = old.rec.xmax();
                double y0 = old.rec.ymin();
                // double y1 = old.rec.ymax();
                double y1 = old.key.y();

                t.rec = new RectHV(x0, y0, x1, y1);
                // System.out.println("a: " + t.rec);

            }
            else if (sec == 'b') {
                double x0 = old.rec.xmin();
                double x1 = old.rec.xmax();
                // double y0 = old.rec.ymin();
                double y1 = old.rec.ymax();
                double y0 = old.key.y();

                t.rec = new RectHV(x0, y0, x1, y1);
                // System.out.println("b: " + t.rec);

            }
            h = t;
            return h;
        }
        if (h.sec == 'a' || h.sec == 'b') {
            if (p.x() < h.key.x()) {
                h.left = insert(h.left, p, 'c', h);
            }
            else {
                h.right = insert(h.right, p, 'd', h);
            }
        }
        else {
            if (p.y() < h.key.y()) {
                h.left = insert(h.left, p, 'a', h);
            }
            else {
                h.right = insert(h.right, p, 'b', h);
            }
        }
        return h;

    }

    // // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("contains point is null");
        }
        return contains(root, p);
    }

    private boolean contains(Node<Point2D> h, Point2D p) {
        if (h == null) {
            return false;
        }
        if (h.key.equals(p)) {
            return true;
        }
        // if (h.key.x() == p.x() && h.key.y() == p.y()) {
        //     return true;
        // }
        if (h.sec == 'a' || h.sec == 'b') {
            if (p.x() < h.key.x()) {
                return contains(h.left, p);
            }
            else {
                return contains(h.right, p);
            }
        }
        else {
            if (p.y() < h.key.y()) {
                return contains(h.left, p);
            }
            else {
                return contains(h.right, p);
            }

        }
    }

    // draw all points to standard draw
    public void draw() {
        // num = 65; //only for debug
        scan(root, null);

    }

    private void scan(Node<Point2D> t, Node<Point2D> last) {
        if (t == null) return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        t.key.draw();
        // StdDraw.text(t.key.x(), t.key.y(), Character.toString((char) num++)); //for debug
        StdDraw.setPenRadius(0.001);
        if (t.sec == 'a' || t.sec == 'b') {
            StdDraw.setPenColor(StdDraw.RED);
            if (last == null) {
                Point2D a = new Point2D(t.key.x(), 0);
                Point2D b = new Point2D(t.key.x(), 1);
                a.drawTo(b);
            }
            else {
                Point2D a = new Point2D(t.key.x(), t.rec.ymin());
                Point2D b = new Point2D(t.key.x(), t.rec.ymax());
                a.drawTo(b);
            }
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D a = new Point2D(t.rec.xmin(), t.key.y());
            Point2D b = new Point2D(t.rec.xmax(), t.key.y());
            a.drawTo(b);
        }
        scan(t.left, t);
        scan(t.right, t);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        inorder(root, q, rect);
        return q;
    }

    private void inorder(Node<Point2D> t, Queue<Point2D> q, RectHV rect) {
        if (t == null) return;
        if (rect.contains(t.key)) {
            q.enqueue(t.key);
            inorder(t.left, q, rect);
            inorder(t.right, q, rect);
        }
        else if (t.rec.intersects(rect)) {
            inorder(t.right, q, rect);
            inorder(t.left, q, rect);
        }
        //     if (t.sec == 'a' || t.sec == 'b') {
        //         if (rect.xmin() >= t.key.x()) {
        //             inorder(t.right, q, rect);
        //         }
        //         else if (rect.xmax() < t.key.x()) {
        //             inorder(t.left, q, rect);
        //         }
        //         else {
        //             inorder(t.right, q, rect);
        //             inorder(t.left, q, rect);
        //         }
        //     }
        //     else {
        //         if (rect.ymin() >= t.key.y()) {
        //             inorder(t.right, q, rect);
        //         }
        //         else if (rect.ymax() < t.key.x()) {
        //             inorder(t.left, q, rect);
        //         }
        //         else {
        //             inorder(t.right, q, rect);
        //             inorder(t.left, q, rect);
        //         }
        //     }
        //
        // }
        // else {
        //     if (t.sec == 'a' || t.sec == 'b') {
        //         if (rect.xmin() >= t.key.x()) {
        //             inorder(t.right, q, rect);
        //         }
        //         else if (rect.xmax() < t.key.x()) {
        //             inorder(t.left, q, rect);
        //         }
        //         else {
        //             inorder(t.right, q, rect);
        //             inorder(t.left, q, rect);
        //         }
        //     }
        //     else {
        //         if (rect.ymin() >= t.key.y()) {
        //             inorder(t.right, q, rect);
        //         }
        //         else if (rect.ymax() < t.key.x()) {
        //             inorder(t.left, q, rect);
        //         }
        //         else {
        //             inorder(t.right, q, rect);
        //             inorder(t.left, q, rect);
        //         }
        //
        //     }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("contains point is null");
        }
        if (root == null) {
            return null;
        }
        Point2D minPoint = root.key;
        double minDis = root.key.distanceSquaredTo(p);
        minPoint = near(root, p, minPoint, minDis);
        return minPoint;
    }

    private Point2D near(Node<Point2D> h, Point2D query, Point2D minpoint, double minDis) {
        if (h == null) return minpoint;
        double disrec = h.rec.distanceSquaredTo(query);
        if (minDis < disrec) {
            return minpoint;
        }
        double dis = query.distanceSquaredTo(h.key);
        if (dis < minDis) {
            minpoint = h.key;
            // System.out.println("minpoint: " + minpoint);
            minDis = dis;
        }
        Point2D minpoint1 = near(h.right, query, minpoint, minDis);
        Point2D minpoint2 = near(h.left, query, minpoint, minDis);
        if (query.distanceSquaredTo(minpoint1) < query.distanceSquaredTo(minpoint2)) {
            minpoint = minpoint1;
        }
        else {
            minpoint = minpoint2;
        }
        return minpoint;
        //==============================
        // double x0 = h.key.x();
        // double y0 = h.key.y();
        // double x1 = minpoint.x();
        // double y1 = minpoint.y();
        // RectHV rec = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
        //                         Math.max(x0, x1), Math.max(y0, y1));
        //
        // double disrec = rec.distanceSquaredTo(query);
        // double dis = query.distanceSquaredTo(h.key);
        // if (dis < disrec) {
        //     return minpoint;
        // }
        // else if (dis < minDis) {
        //     minpoint = h.key;
        //     // System.out.println("minpoint: " + minpoint);
        //     minDis = dis;
        // }
        //
        // Point2D minpoint1 = near(h.right, query, minpoint, minDis);
        // Point2D minpoint2 = near(h.left, query, minpoint, minDis);
        // if (query.distanceSquaredTo(minpoint1) < query.distanceSquaredTo(minpoint2)) {
        //     minpoint = minpoint1;
        // }
        // else {
        //     minpoint = minpoint2;
        // }
        // return minpoint;


    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        double x0 = 0.34, y0 = 0.84;      // initial endpoint of rectangle
        double x1 = 0.06, y1 = 0.48;      // current location of mouse
        RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                 Math.max(x0, x1), Math.max(y0, y1));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        rect.draw();

        // StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kdtree.draw();

        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Point2D p : kdtree.range(rect))
            p.draw();

        StdDraw.show();
        // StdDraw.setPenRadius(0.02);
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D query = new Point2D(x, y);

                StdDraw.clear();
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                kdtree.draw();
                StdDraw.setPenRadius(0.02);
                StdDraw.setPenColor(StdDraw.BLUE);
                kdtree.nearest(query).draw();
                StdDraw.show();
                StdDraw.pause(40);

            }
        }
    }
}
