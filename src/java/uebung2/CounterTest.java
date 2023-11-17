package uebung2;

public class CounterTest {
    static boolean increment(int[] nums, int max) {
        int pos = 0;
        while (pos < nums.length && nums[pos] == (max))
            pos++;
        if (pos == nums.length)
            return false;
        int newNum = ++nums[pos];
        while (pos > 0) {
            nums[--pos] = newNum;
        }
        return true;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 8; i++){
            int[] nums = new int[i];
            for (int j = 1; j <= 8; j++) {
                for (int k = 0; k < i; k++) {
                    nums[k] = 0;
                }
                int count = 0;
                do {
                    count++;
                } while (increment(nums, j));
                System.out.print(count + "   \t");
            }
            System.out.println();
        }
    }
}
