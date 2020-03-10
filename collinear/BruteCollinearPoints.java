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

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    private int count = -1;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointss) {
        if (pointss == null) {
            throw new IllegalArgumentException("points is null");
        }
        for (Point p : pointss) {
            if (p == null) {
                throw new IllegalArgumentException("point is null");
            }
        }
        Point[] points = pointss.clone();
        MergeX.sort(points); // sort the points to find the duplicate points.
        int n = points.length;
        for (int i = 0; i < n - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("repeat point found");
            }
        }
        // find the segments
        this.count = 0;
        ArrayList<LineSegment> temp = new ArrayList<LineSegment>();
        for (int i = 0; i < n - 3; i++) {
            Point p = points[i];
            boolean flag = false;
            for (int j = i + 1; j < n - 2; j++) {
                Point q = points[j];
                double slope = p.slopeTo(q);
                for (int k = j + 1; k < n - 1; k++) {
                    Point r = points[k];
                    if (slope == p.slopeTo(r)) {
                        for (int l = k + 1; l < n; l++) {
                            Point s = points[l];
                            if (slope == p.slopeTo(s)) {
                                temp.add(new LineSegment(p, s));
                                count++;
                            }
                        }
                    }
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

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }// the line segments
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
