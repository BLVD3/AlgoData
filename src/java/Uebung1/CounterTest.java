package Uebung1;

public class CounterTest {
    static boolean increment(int[] nums, int max) {
        int pos = 0;
        while (pos < nums.length && nums[pos] == (max - 1))
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
        for (int i = 1; i <= 100; i++){
            int[] nums = {0, 0, 0, 0, 0, 0, 0, 0};
            int count = 0;
            do {
                count++;
            } while (increment(nums, i));
            System.out.println(i + ": " + count);
        }
    }
}
