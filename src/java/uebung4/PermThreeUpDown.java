package uebung4;

/**
 * @author Nico Vogel 215998, Julia Keck 215944
 */
public class PermThreeUpDown {
  private static int permThreeUpDown(int length) {
    int[] elements = new int[length];
    int count = 0;
    int index = 0;
    while (index >= 0) {
      if (index == length - 1) {
        int next = findNextFree(1, length, elements, index);
        elements[index] = next;
        if (checkOrder(elements)) {
          for (int element : elements) {
            System.out.print(element + " ");
          }
          System.out.println();
          count++;
        }
        elements[index] = 0;
        index--;
      }
      else {
        if (elements[index] == 0) {
          elements[index] = 1;
        }
        else {
          if (elements[index] == length) {
            elements[index] = 0;
            index--;
            continue;
          }
          elements[index]++;
        }
        int next = findNextFree(elements[index], length, elements, index);
        if (next == 0) {
          elements[index] = 0;
          index--;
        }
        else {
          elements[index] = next;
          index++;
        }
      }
    }
    return count;
  }

  private static int findNextFree(int start, int max, int[] val, int last) {
    outer:
    for (int i = start; i <= max; i++) {
      for (int j = 0; j < last; j++) {
        if (val[j] == i)
          continue outer;
      }
      return i;
    }
    return 0;
  }

  private static boolean checkOrder(int[] arr) {
    boolean leftSmaller = true;
    for (int i = 0; i < 3; i++) {
      for (int j = 3 + i; j < arr.length; j = j + 3) {
        if (leftSmaller) {
          if (arr[j] < arr[j - 3])
            return false;
        } else if (arr[j] > arr[j - 3])
          return false;
        leftSmaller = !leftSmaller;
      }
    }

    return true;
  }

  public static void main(String[] args) {
    System.out.println(permThreeUpDown(6));
    checkOrder(new int[]{1, 0, 0, 4, 0, 0, 5});
  }
}
