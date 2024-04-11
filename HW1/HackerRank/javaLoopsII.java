import java.util.*;
import java.io.*;

class Solution {
    public static void main(String[] argh) {
        Scanner in = new Scanner (System.in);
        int t = in.nextInt ();
        for (int i = 0; i < t; i++) {
            int a = in.nextInt ();
            int b = in.nextInt ();
            int n = in.nextInt ();
            long res = a, curr = 1;
            for (int j = 0; j < n; j++) {
                res = res + curr * b;
                curr = curr * 2;
                System.out.print (res + " ");
            }
            System.out.println ();
        }
        in.close ();
    }
}

