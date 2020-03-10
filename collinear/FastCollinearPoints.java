/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 3.7.2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;
    private int count;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] pointss) {
        if (pointss == null) {
            throw new IllegalArgumentException("points is null");
        }
        for (Point p : pointss) {
            if (p == null) {
                throw new IllegalArgumentException("point is null");
            }
        }
        Point[] points = pointss.clone();
        MergeX.sort(points); // sort array to remove duplicate points
        int n = points.length;
        for (int i = 0; i < n - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("repeat point found");
            }
        }

        this.count = 0;
        ArrayList<LineSegment> temp = new ArrayList<LineSegment>();
        Point[] pp = points.clone();
        for (Point po : points) {
            MergeX.sort(pp, po.slopeOrder());

            Point p = pp[0];
            Point q;
            Point r;
            boolean flag = false;
            int m = 1;
            int count2 = 3;
            while (m < n - 1) {
                q = pp[m];
                r = pp[m + 1];
                if (p.slopeTo(q) == p.slopeTo(r)) {
                    double slope = p.slopeTo(q);
                    ArrayList<Point> temp2 = new ArrayList<Point>();
                    int c = 2;
                    temp2.add(p);
                    temp2.add(q);
                    temp2.add(r);
                    count2 = 3;
                    while (m + c < n) { // try to find the forth point
                        if (slope == p.slopeTo(pp[m + c])) {
                            temp2.add(pp[m + c]);// as long as slop equal, add all points
                            count2++;
                            flag = true;
                        }
                        else {
                            // once slope is not equal, stop loop
                            break;
                        }
                        c++;
                    }
                    if (count2 > 3) {
                        Point[] temp3 = new Point[count2];
                        temp2.toArray(temp3);

                        MergeX.sort(temp3);
                        if (p.compareTo(temp3[0]) == 0) {
                            temp.add(new LineSegment(p, temp3[count2 - 1]));
                            count++;
                            flag = true;
                            // break;
                        }
                    }
                }
                if (flag) {
                    m = m + count2 - 1; // System.out.println("b");
                    flag = false;
                    // System.out.println("b");// break;
                }
                else {
                    m++;
                }

            }
        }

        lineSegments = new LineSegment[count];
        temp.toArray(lineSegments);

    }

    // the number of line segments
    public int numberOfSegments() {

        return count;
    }

    // the line segments
    public LineSegment[] segments() {

        return lineSegments.clone();
    }

    //
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        // System.out.println(collinear.numberOfSegments());
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
