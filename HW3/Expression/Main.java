public class Main {
    public static void main(String[] args) {
        Numeral two = new Numeral (2);
        System.out.println(two);
        Numeral three = new Numeral (3);
        Square two_square = new Square (two);
        System.out.println(two_square.evaluate ());
        Expression res = new Addition (two_square, three);
        System.out.println(two_square.toString ());
        System.out.println(res.toString () + " = " + res.evaluate ());
    }
}
