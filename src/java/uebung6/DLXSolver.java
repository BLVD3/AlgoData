package uebung6;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Stack;

public class DLXSolver {
    private class DLXNode {
        public DLXNode head;
        public DLXNode up, down, left, right;
        public final int x, y;



        public DLXNode(int x, int y) {
            head = up = down = left = right = this;
            this.x = x;
            this.y = y;
        }

        public void coverHorizontal() {
            left.right = right;
            right.left = left;
        }

        public void uncoverHorizontal() {
            left.right = this;
            right.left = this;
        }

        public void coverVertical() {
            up.down = down;
            down.up = up;
        }

        public void uncoverVertical() {
            up.down = this;
            down.up = this;
        }

        public void coverRow() {
            DLXNode current = this.right;
            while (current != this) {
                current.coverVertical();
                current = current.right;
            }
        }
        public void uncoverRow() {
            DLXNode current = this.right;
            while (current != this) {
                current.uncoverVertical();
                current = current.right;
            }
        }
    }

    private final DLXNode anchor;

    int count;

    public DLXSolver(BitSet[] matrix, int width) {
        //Init variables
        anchor = new DLXNode(-1, -1);
        DLXNode[] heads = new DLXNode[width];
        ArrayList<DLXNode>[] columns = new ArrayList[width];

        //Init link helper
        for (int i = 0; i < columns.length; i++) {
            columns[i] = new ArrayList<>();
        }

        //Create Header
        heads[0] = new DLXNode(0, -1);
        heads[0].left = anchor;
        anchor.right = heads[0];
        for (int i = 1; i < width; i++) {
            heads[i] = new DLXNode(i, -1);
            heads[i - 1].right = heads[i];
            heads[i].left = heads[i - 1];
        }
        (anchor.left = heads[width - 1]).right = anchor;
        // Create Table and link horizontal
        for (int i = 0; i < matrix.length; i++) {
            BitSet subSet = matrix[i];
            int index = 0;
            while ((!subSet.get(index)) && (index < subSet.length())) index++;
            if (index == subSet.length()) continue;
            DLXNode first = new DLXNode(index, i);
            columns[index].add(first);
            DLXNode cur = first;
            for (index++; index < subSet.length(); index++) {
                if (subSet.get(index)) {
                    DLXNode newNode = new DLXNode(index, i);
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
        while ((current = current.down) != current.head) {
            current.head.coverHorizontal();
            current.coverRow();
            DLXNode hCover = current.right;
            while (hCover != current) {
                hCover.head.coverHorizontal();
                DLXNode vCover = hCover.down;
                while (vCover != hCover) {
                    vCover.coverRow();
                    vCover = vCover.down;
                }
                hCover = hCover.right;
            }
            searchAndCount();
            current.head.uncoverVertical();
            current.uncoverRow();
            hCover = current.right;
            while (hCover != current) {
                hCover.head.uncoverHorizontal();
                DLXNode vCover = hCover.down;
                while (vCover != hCover) {
                    vCover.uncoverRow();
                    vCover = vCover.down;
                }
                hCover = hCover.right;
            }
        }
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
