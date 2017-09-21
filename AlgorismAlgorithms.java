import com.shpp.cs.a.console.TextProgram;

public class AlgorismAlgorithms extends TextProgram {
    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.  
     *
     * @param n1 The first number.  
     * @param n2 The second number.  
     * @return A String representation of n1 + n2
     */

    private String addNumericStrings(String n1, String n2) {
        // TODO: Replace this comment with your implementation of this method!
        int i = 1; // discharge of the number.
        int perenos = 0; // remainder.
        String A = "";
        /* the maximum discharge in numbers */
        int length = (n1.length()>n2.length()) ? n1.length():n2.length();
        while (i<=length) {
            /* read discharge numbers and add numbers */
            int n = operation(transfer(getNumber(n1, i)),transfer(getNumber(n2,i)))+perenos;
            char ch = transfer(n % 10);
            perenos = n/10;
            A = ch + A;
            i++;
        }
        if (perenos>0){ A = perenos + A;}
        return A;
    }

    /**
     * returns number of the set discharge, the set number.
     * @param n the number.
     * @param i the discharge.
     * @return number of the set discharge, the set number.
     */
    private char getNumber(String n, int i) {
        if (n.length()>=i){ return n.charAt(n.length()-i); }
        return '0';
    }

    /**
     * Transfers values from int to the char.
     * @param c value in a numerical form (int).
     * @return value in a symbol form (char).
     */
    private int transfer(char c) {
        return (int) c - '0';
    }

    /**
     * Transfers values from char to the int.
     * @param i value in a symbol form (char).
     * @return value in a numerical form (int).
     */
    private char transfer(int i) {
        if (i == 10) { return 48; }
        return (char) (i + '0');
    }

    /**
     * Summation of numbers.
     * @param c1 first numbers.
     * @param c2 second numbers.
     * @return summa.
     */
    private int operation(int c1, int c2) {
        return c1 + c2;
    }
}