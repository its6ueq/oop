public class RandomWalk {
    public static void main (String[] args) {
        int n = Integer.parseInt (args[0]);
        begin (n);
        drawLine (n);
    }

    public static void begin (int n) {
        StdDraw.setScale (-n - 0.5, n + 0.5);
        StdDraw.clear (StdDraw.GRAY);
        StdDraw.enableDoubleBuffering ();
    }

    public static void drawLine (int n) {
        int x = 0, y = 0, r = 0;
        for (int i = 0; i <= n; i++) {
            StdDraw.setPenColor (StdDraw.WHITE);
            StdDraw.filledSquare (0, i - 1, 0.45);
            round1 (0, i, i);
            round2 (i, 0, i);
            round3 (0, -i, i);
            round4 (-i, 0, i);
        }
    }

    //Khong dung na~o nen moi code ntn XD
    private static void round1 (int x, int y, int r) {
        int a = x, b = y;
        while (y > 0) {
            a = x;
            b = y;
            boolean isDraw = false;
            a++;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
            a--;
            b--;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
            a++;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
        }
    }

    private static void round2 (int x, int y, int r) {
        int a = x, b = y;
        while (x > 0) {
            a = x;
            b = y;
            boolean isDraw = false;
            a--;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
            a++;
            b--;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
            a--;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
        }
    }

    private static void round3 (int x, int y, int r) {
        int a = x, b = y;
        while (y < 0) {
            a = x;
            b = y;
            boolean isDraw = false;
            a--;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
            a++;
            b++;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
            a--;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
        }
    }

    private static void round4 (int x, int y, int r) {
        int a = x, b = y;
        while (x < 0) {
            a = x;
            b = y;
            boolean isDraw = false;
            a++;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
            a--;
            b++;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
            a++;
            if (check (a, b, r)) {
                draw (x, y, a, b);
                x = a;
                y = b;
            }
        }
    }

    private static boolean check (int x, int y, int r) {
        double temp = Math.sqrt (x * x + y * y);
        long temp1 = Math.round (temp);
        return temp1 == r;
    }

    private static void draw (int x, int y, int a, int b) {
        StdDraw.setPenColor (StdDraw.WHITE);
        StdDraw.filledSquare (x, y, 0.45);
        StdDraw.setPenColor (StdDraw.BLUE);
        StdDraw.filledSquare (a, b, 0.45);
        StdDraw.show ();
        StdDraw.pause (1);
    }
}