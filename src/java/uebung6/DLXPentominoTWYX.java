package uebung6;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Schreiben Sie ein Programm DLXPentominoTWYX, das auf dem DLX Verfahren beruht und das
 * Folgendes leistet: Es wird eine nat¨urliche Zahl n ≥ 0 eingelesen. Anschließend wird a(n) berechnet
 * und ausgegeben. Dabei gibt a(n) an, auf wie viele verschiedene Arten ein rechteckiges 5 × n-Feld
 * mit T-, W-, Y- und X-Pentominos vollst¨andig gekachelt werden kann. Alle Pentominos haben die
 * Fl¨ache 5. Alle Figuren k¨onnen nat¨urlich gedreht und gewendet werden.
 *
 * @author Nico Vogel, Henri Staudenrausch, Julia Keck
 */
public class DLXPentominoTWYX {
    private static Pentomino[] pentominos = new Pentomino[17];
    private static List<BitSet> initialProblem = new ArrayList<>();

    public static void main(String[] args) {
        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Usage: java DLXPentominoTWYX <n>");
            return;
        }

        pentominos[0] = new Pentomino(new int[]{0, 1, 2, 6, 11}); // T's
        pentominos[1] = new Pentomino(new int[]{0, 5, 6, 7, 10});
        pentominos[2] = new Pentomino(new int[]{1, 6, 10, 11, 12});
        pentominos[3] = new Pentomino(new int[]{2, 5, 6, 7, 12});
        pentominos[4] = new Pentomino(new int[]{0, 1, 6, 7, 12}); // W's
        pentominos[5] = new Pentomino(new int[]{0, 1, 6, 11, 12});
        pentominos[6] = new Pentomino(new int[]{1, 2, 5, 6, 10});
        pentominos[7] = new Pentomino(new int[]{2, 6, 7, 10, 11});
        pentominos[8] = new Pentomino(new int[]{1, 5, 6, 7, 11}); // X
        pentominos[9] = new Pentomino(new int[]{1, 5, 6, 11, 16}); // Y's
        pentominos[10] = new Pentomino(new int[]{1, 6, 10, 11, 16});
        pentominos[11] = new Pentomino(new int[]{0, 5, 6, 10, 15});
        pentominos[12] = new Pentomino(new int[]{0, 5, 10, 11, 15});
        pentominos[13] = new Pentomino(new int[]{0, 1, 2, 3, 6});
        pentominos[14] = new Pentomino(new int[]{0, 1, 2, 3, 7});
        pentominos[15] = new Pentomino(new int[]{1, 5, 6, 7, 8});
        pentominos[16] = new Pentomino(new int[]{2, 5, 6, 7, 8});

        for (Pentomino pentomino : pentominos) {
            for (int x = 0; x < 5 - pentomino.getWidth(); x++) {
                for (int y = 0; y < n - pentomino.getHeight(); y++) {
                    initialProblem.add(pentomino.getBitSet(x, y));
                }
            }
        }

        DLXSolver solver = new DLXSolver(initialProblem.toArray(new BitSet[initialProblem.size()]), 5 * n);
        System.out.println(solver.countSolutions());
    }

    private static class Pentomino {
        private final int[] shape;
        private int width;
        private int height;

        public Pentomino(int[] shape) {
            this.shape = shape;
            for (int i : shape) {
                width = Math.max(width, i % 5);
                height = Math.max(height, i / 5);
            }
        }

        public BitSet getBitSet(int xShift, int yShift) {
            BitSet bitSet = new BitSet(shape[shape.length - 1] + xShift + yShift * 5 + 1);
            for (int i : shape) {
                bitSet.set(i + xShift + yShift * 5);
            }
            return bitSet;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
