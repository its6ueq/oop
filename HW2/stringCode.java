import java.util.Scanner;

public class stringCode {
    public static void main (String[] args) {
        Scanner scanner = new Scanner (System.in);
        StringBuilder s = new StringBuilder (scanner.nextLine ());
        System.out.println (blowUp (s));
    }

    public static StringBuilder blowUp (StringBuilder s) {
        StringBuilder res = new StringBuilder ();
        for (int i = 0; i < s.length (); i++) {
            if (i == s.length () - 1) {
                res.append (s.charAt (i));
                break;
            }
            if ((int) s.charAt (i) >= (int) '0' && (int) s.charAt (i) <= (int) '9' && ((int) s.charAt (i + 1) < (int) '0' || (int) s.charAt (i + 1) > (int) '9') && i != s.length () - 1) {
                res.append (String.valueOf (s.charAt (i + 1)).repeat (Math.max (0, s.charAt (i) - '0')));
                i++;
            } else if ((int) s.charAt (i) < (int) '0' || (int) s.charAt (i) > (int) '9')
                res.append (s.charAt (i));
        }
        return res;
    }
}