import java.util.Scanner;

public class charArea {
    static char[][] grids = new char[][] {{'c', 'a', 'x'}, {'b', ' ', 'b'}, {' ', ' ', 'a'}};

    public static void main (String[] args) {
        Scanner scanner = new Scanner (System.in);
        char ch = scanner.nextLine ().charAt (0);
        System.out.println (charArea (ch));
    }

    public static int charArea (char ch) {
        int maxX = 0, maxY = 0, minX = 3, minY = 3;
        boolean res = false;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grids[i][j] == ch) {
                    res = true;
                    maxX = Math.max (maxX, i);
                    maxY = Math.max (maxY, j);
                    minX = Math.min (minX, i);
                    minY = Math.min (minY, j);
                }
        return res ? (maxX - minX + 1) * (maxY - minY + 1) : 0;
    }
}