package uebung2;

public class Test {
  public static void main(String[] args) {
    int n = 0;
    int max = 4;
    for (int i = 1; i <= max; i++) {
      n += (1<<(max-i)) * i;
    }
    System.out.println(4 + ": " + n);
  }
}
