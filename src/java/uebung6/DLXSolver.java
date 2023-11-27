package uebung6;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Stack;

public class DLXSolver {
    private class DLXNode {
        public DLXNode head;
        public DLXNode up, down, left, right;

        public DLXNode() {
            head = up = down = left = right = this;
        }
    }

    private final DLXNode anchor;

    int count;

    public DLXSolver(BitSet[] matrix, int width) {
        //Init variables
        anchor = new DLXNode();
        DLXNode[] heads = new DLXNode[width];
        ArrayList<DLXNode>[] columns = new ArrayList[width];

        //Init link helper
        for (int i = 0; i < columns.length; i++) {
            columns[i] = new ArrayList<>();
        }

        //Create Header
        heads[0] = new DLXNode();
        heads[0].left = anchor;
        anchor.right = heads[0];
        for (int i = 1; i < width; i++) {
            heads[i] = new DLXNode();
            heads[i - 1].right = heads[i];
            heads[i].left = heads[i - 1];
        }
        (anchor.left = heads[width - 1]).right = anchor;
        // Create Table and link horizontal
        for (BitSet subSet : matrix) {
            int index = 0;
            while ((!subSet.get(index)) && (index < subSet.length())) index++;
            if (index == subSet.length()) continue;
            DLXNode first = new DLXNode();
            columns[index].add(first);
            DLXNode cur = first;
            for (index++; index < subSet.length(); index++) {
                if (subSet.get(index)) {
                    DLXNode newNode = new DLXNode();
                    cur.right = newNode;
                    newNode.left = cur;
                    cur = newNode;
                    columns[index].add(newNode);
                }
            }
            cur.right = first;
            first.left = cur;
        }

        // Link Vertically
        for (int i = 0; i < columns.length; i++) {
            DLXNode cur = heads[i];
            for (DLXNode node : columns[i]) {
                node.head = heads[i];
                cur.down = node;
                node.up = cur;
                cur = node;
            }
            cur.down = heads[i];
            heads[i].up = cur;
        }
    }

    public int countSolutions() {
        count = 0;
        searchAndCount();
        return count;
    }

    private void searchAndCount() {
        if (anchor.right == anchor) {
            count++;
            return;
        }
        DLXNode current = anchor.right;
        cover(current);
        for (DLXNode downSearch = current.down; downSearch != current; downSearch = downSearch.down) {
            for (DLXNode rightIter = downSearch.right; rightIter != downSearch; rightIter = rightIter.right)
                cover(rightIter.head);
            searchAndCount();
            for (DLXNode leftIter = downSearch.left; downSearch != leftIter; leftIter = leftIter.left)
                uncover(leftIter.head);
        }
        uncover(current);
    }

    private void cover(DLXNode node) {
        node.right.left = node.left;
        node.left.right = node.right;
        for (DLXNode row = node.down; row != node; row = row.down)
            for (DLXNode collumn = row.right; collumn != row; collumn = collumn.right) {
                collumn.down.up = collumn.up;
                collumn.up.down = collumn.down;
            }
    }

    private void uncover(DLXNode node) {
        for (DLXNode row = node.up; row != node; row = row.up)
            for (DLXNode collumn = row.left; collumn != row; collumn = collumn.left) {
                collumn.down.up = collumn;
                collumn.up.down = collumn;
            }
        node.right.left = node;
        node.left.right = node;
    }

    public static void main(String[] args) {
        BitSet bitSet1 = new BitSet(2);
        bitSet1.set(0, true);
        bitSet1.set(1, false);
        BitSet bitSet2 = new BitSet(2);
        bitSet2.set(0, false);
        bitSet2.set(1, true);
        DLXSolver solver = new DLXSolver(new BitSet[]{bitSet1, bitSet2, bitSet1, bitSet2}, 2);
        for (int i = 0; i < 5; i++) {
            System.out.println(solver.countSolutions());
        }
        System.out.println(solver.countSolutions());
    }
}
