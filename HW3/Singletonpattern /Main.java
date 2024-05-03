class TextGraphics {
    private static TextGraphics instance;
    int width, height;
    char[][] graph;

    private TextGraphics () {
    }

    public static TextGraphics getInstance () {
        if (instance == null) {
            instance = new TextGraphics ();
        }

        return instance;
    }

    void init (int width, int height) {
        this.width = width;
        this.height = height;
        this.graph = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.graph[i][j] = ' ';
            }
        }
    }

    void drawPoint (char c, int x, int y) {
        this.graph[x][y] = c;
    }

    void drawLine (char c, int x1, int y1, int x2, int y2) {
        System.out.println (x1 + " " + y1 + " " + x2 + " " + y2);
        this.graph[x1][y1] = c;
        this.graph[x2][y2] = c;
        if (Math.abs (x1 - x2) <= 1 && Math.abs (y1 - y2) <= 1) {
            return;
        }
        drawLine (c, x1, y1, (x1 + x2) / 2, (y1 + y2) / 2);
        drawLine (c, x2, y2, (x2 + x1) / 2, (y2 + y1) / 2);
    }

    void fillRectPoint (char c, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j > y - height; j--) {
                graph[i][j] = c;
            }
        }
    }

    void render () {
        System.out.println ("Graph:");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print (this.graph[i][j] + " ");
            }
            System.out.println (" ");
        }

    }
}

public class Main {
    public static void main (String[] args) {
        TextGraphics graphics = TextGraphics.getInstance ();
        graphics.init (15, 10);
        graphics.drawPoint ('*', 1, 1);
        graphics.fillRectPoint ('o', 5, 5, 3, 2);
        graphics.render ();
    }
}