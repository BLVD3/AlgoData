package uebung6;

import java.util.ArrayList;
import java.util.BitSet;

public class DLXSolver {
  private class DLXNode {
    public DLXNode head;
    public DLXNode up, down, left, right;

    public DLXNode() {
      head = up = down = left = right = this;
    }
  }

  private DLXNode anchor;

  public DLXSolver(BitSet[] matrix) {
    //Init variables
    anchor = new DLXNode();
    DLXNode[] heads = new DLXNode[matrix[0].length()];
    ArrayList<DLXNode>[] columns = new ArrayList[matrix[0].length()];

    //Init link helper
    for (int i = 0; i < columns.length; i++) {
      columns[i] = new ArrayList<>();
    }

    //Create Header
    heads[0] = new DLXNode();
    heads[0].left = anchor;
    anchor.right = heads[0];
    for (int i = 1; i < heads.length; i++) {
      heads[i] = new DLXNode();
      heads[i - 1].right = heads[i];
      heads[i].left = heads[i - 1];
    }
    (anchor.left = heads[heads.length - 1]).right = anchor;

    // Create Table and link horizontal
    for (BitSet subSet : matrix) {
      int index = 0;
      while ((!subSet.get(index)) && (index < subSet.length()))
        index++;
      if (index == subSet.length())
        continue;

    }
  }
}
