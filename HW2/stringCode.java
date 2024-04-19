import java.util.Scanner;

public class stringCode {
    public static void main (String[] args) {
        Scanner scanner = new Scanner (System.in);
        StringBuilder s = new StringBuilder (scanner.nextLine ());
        System.out.println (blowUp (s));
        StringBuilder s1 = new StringBuilder (scanner.nextLine ());
        System.out.println (maxRun (s1));
        StringBuilder s2 = new StringBuilder (scanner.nextLine ());
        StringBuilder s3 = new StringBuilder (scanner.nextLine ());
        int len = scanner.nextInt();
        System.out.println (stringIntersect (s2, s3, len));
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

    public static int maxRun (StringBuilder s) {

        int res = 0;
        Map<Character, Boolean> map = new HashMap<Character, Boolean> ();
        int i;
        for (i = 0; i < s.length (); i++) {
            if (map.get (s.charAt (i)) == null) {
                res++;
                map.put (s.charAt (i), true);
            }
        }
        return res;
    }


}