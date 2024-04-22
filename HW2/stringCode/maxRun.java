import java.util.Scanner;

public class stringCode {
    public static void main (String[] args) {
        Scanner scanner = new Scanner (System.in);
        StringBuilder s1 = new StringBuilder (scanner.nextLine ());
        System.out.println (maxRun (s1));
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