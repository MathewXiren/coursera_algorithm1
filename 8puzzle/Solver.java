/* *****************************************************************************
 *  Name: Xiren
 *  Date: Ma
 *  Description: Solver for puzzle
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {

    private int move;
    private boolean solveAble;
    private Stack<Board> solve;

    private class Node {
        private Board board;
        private int move;
        private Node pre;

        public Comparator<Node> byManh() {
            return new Bymanh();
        }

        private class Bymanh implements Comparator<Node> {

            public int compare(Node b1, Node b2) {
                int priority1 = b1.board.manhattan() + b1.move;
                int priority2 = b2.board.manhattan() + b2.move;
                if (priority1 > priority2) {
                    return 1;
                }
                else if (priority1 < priority2) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial is null");
        }

        move = 0;
        Node goal = new Node();
        goal.board = initial;
        goal.move = 0;
        MinPQ<Node> pq = new MinPQ<Node>(goal.byManh());
        pq.insert(goal);

        // twin board to check if the solution exists
        Board twin = initial.twin();
        Node twinP = new Node();
        twinP.board = twin;
        twinP.move = 0;
        MinPQ<Node> twinpq = new MinPQ<Node>(twinP.byManh());
        twinpq.insert(twinP);

        while (!goal.board.isGoal() && !twinP.board.isGoal()) {
            goal = aAlgorithm(pq);
            twinP = aAlgorithm(twinpq);
        }

        if (goal.board.isGoal()) {
            solveAble = true;
            move = 0; // count the min move
            solve = new Stack<Board>();
            while (goal.pre != null) {
                solve.push(goal.board); // push the path
                goal = goal.pre;
                move++;
            }
            solve.push(goal.board);
        }
        else {
            solveAble = false;
            move = -1;
        }

    }

    // each time enque the neighbors
    private Node aAlgorithm(MinPQ<Node> pp) {
        Node oldNode = pp.delMin();
        int currentMove = oldNode.move;
        Iterable<Board> loop = oldNode.board.neighbors();
        Iterator<Board> it = loop.iterator();

        while (it.hasNext()) {
            Board ne = it.next();
            if (oldNode.pre == null || !oldNode.pre.board
                    .equals(ne)) { // do not ad the board that is the same with the root board
                Node minNode = new Node();
                minNode.board = ne;
                minNode.move = currentMove + 1;
                minNode.pre = oldNode;
                pp.insert(minNode); // add neighbor to the priority queue
            }
        }
        Node newMin = pp.min();
        return newMin;
    }

    // // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solveAble;
    }

    // min number of moves to solve initial board
    public int moves() {
        return move;
    }


    // // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return new SolverBoard();
        }
        else {
            return null;
        }
    }

    private class SolverBoard implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return solve.iterator();
        }
    }
    //

    public static void main(String[] args) {

    }
}
