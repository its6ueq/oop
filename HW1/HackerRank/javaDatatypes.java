package HW1.HackerRank;

import java.util.*;


class javaDatatypes {
    public static void main (String[] argh) {

        Scanner sc = new Scanner (System.in);
        int t = sc.nextInt ();

        for (int i = 0; i < t; i++) {

            try {
                long x = sc.nextLong ();
                System.out.println (x + " can be fitted in:");
                if (x == (byte) x)
                    System.out.println ("* byte");
                if (x == (short) x)
                    System.out.println ("* short");
                if (x == (int) x)
                    System.out.println ("* int");
                System.out.println ("* long");
                //Complete the code
            } catch (InputMismatchException exception) {
                System.out.println (sc.next () + " can't be fitted anywhere.");
            }
        }
    }
}




