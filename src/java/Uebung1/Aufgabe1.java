package Uebung1;

import java.util.Arrays;

public class Aufgabe1 {
    static void possibleSolution_BruteForce(int n) {
        int targetNumber = n * n;
        int count = 0;
        for (int i = -n; i <= n; i++) {
            for (int j = -n; j <= n; j++) {
                for (int k = -n; k <= n; k++) {
                    for (int l = -n; l <= n; l++) {
                        for (int m = -n; m <= n; m++) {
                            for (int o = -n; o <= n; o++) {
                                for (int p = -n; p <= n; p++) {
                                    for (int q = -n; q <= n; q++) {
                                        if (i*i+j*j+k*k+l*l+m*m+o*o+p*p+q*q == targetNumber) {
                                            count++;
                                            System.out.println(count + ": "
                                                    + i + " " + j + " "
                                                    + k + " " + l + " "
                                                    + m + " " + o + " "
                                                    + p + " " + q);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(count);
    }

    static long countPossibleSolutions(int n) {
        long count = 16;
        int targetNumber = n * n;

        for (int i = 2; i <= 8; i++) {
            long nullPositionVariation = countPermutationsOfElementCount(8-i, i);
            int[] nums = new int[i];
            boolean hit = false;
            while (!incrementNums(nums, n, hit)) {
                int sum = squareSum(nums);
                if (sum >= targetNumber){
                    hit = true;
                    if (sum == targetNumber) {
                        count += countPermutationsOfArray(nums) * nullPositionVariation * (1L << i);
                    }
                }
                else
                    hit = false;
            }
        }
        return count;
    }

    /**
     * Increments to the next non-duplicate solution candidate
     * @param nums the solution candidate that will be mutated.
     *             When the first value is 0, the method will initialize with 1
     * @param hit was the last solution candidate bigger or equal to the target number
     * @return true if all candidates were exhausted
     */
    static boolean incrementNums(int[] nums, int max, boolean hit) {
        int currentIndex = nums.length - 1;
        if (!hit) {
            //Initialize if the array starts with a zero (0, 0, ... 0)
            if (nums[0] == 0)
                currentIndex = 0;
        }
        else {
            // skip candidates that will be too big
            while (nums[currentIndex] == nums[currentIndex - 1]) {
                currentIndex--;
                // when incrementation of the first number led to a hit, then all solutions have been exhausted
                if (currentIndex == 0) {
                    return true;
                }
            }
        }
        while (nums[currentIndex] == max - 1) {
            currentIndex--;
            // when all numbers are max, then there are no more possible solutions
            if (currentIndex == -1)
                return true;
        }
        //increments the next significant number
        int newBase = ++nums[currentIndex];
        // sets all the following numbers to the same number
        while (++currentIndex < nums.length) {
            nums[currentIndex] = newBase;
        }
        return false;
    }

    /**
     * @return the sum of every number squared
     */
    static int squareSum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num * num;
        }
        return sum;
    }

    /**
     * counts all Permutations of that array.
     */
    static int countPermutationsOfArray(int[] arr) {
        int[] sortedArray = Arrays.stream(arr).sorted().toArray();
        int lastElement = sortedArray[0];
        int uniqueCount = 1;
        int[] duplicateCount = new int[sortedArray.length];
        duplicateCount[0] = 1;
        for (int i = 1; i < sortedArray.length; i++) {
            if (sortedArray[i] != lastElement) {
                lastElement = sortedArray[i];
                uniqueCount++;
            }
            duplicateCount[uniqueCount - 1]++;
        }

        return countPermutationsOfElementCount(Arrays.copyOf(duplicateCount, uniqueCount));
    }

    static int countPermutationsOfElementCount(int... counts) {
        int correctionValue = 1;
        int elements = 0;
        for (int count : counts) {
            correctionValue *= factorial(count);
            elements += count;
        }
        int perIfUnique = factorial(elements);
        return perIfUnique / correctionValue;
    }

    static int factorial(int n) {
        int val = 1;
        for (int i = 2; i <= n; i++) {
            val *= i;
        }
        return val;
    }

    public static void main(String[] args) {
        System.out.println(countPossibleSolutions(4));
    }
}
