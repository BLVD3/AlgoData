package Uebung1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class ICantName {
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

    static int countPossibleSolutions(int n) {
        int count = 16;
        int targetNumber = n * n;
        for (int i = 2; i <= 8; i++) {

        }
        return count;
    }

    public static void main(String[] args) {
        possibleSolution_BruteForce(8);
    }
}
