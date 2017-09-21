import com.shpp.cs.a.console.TextProgram;

public class SyllableCounting extends TextProgram {
    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.  
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesIn(word));
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.  
     *
     * @param word A string containing a single word.  
     * @return An estimate of the number of syllables in that word.  
     */
    private int syllablesIn(String word) {
        int r =0; // counter of symbols

        for (int i = 0; i < word.length(); i++ ){
            if ( vowelLetter(word, i) ){
                if (previousConsonant(word, i)){
                    if (symbolE(word,i)) { r++; }
                }
            }
        }
        if (r == 0) { r=1; }
        return r;
    }

    /**
     * Checks there is a letter "e" at the end of the word.
     * @param word the checked word.
     * @param i the address of the checked symbol.
     * @return yes - if the letter "e" not at the end of the word.
     */
    private boolean symbolE(String word, int i) {
        if (word.charAt(i) == 'e'){
        if( i == word.length()-1 || word.charAt(i+1) == ' ') { return false;  }
        }
        return true;
    }

    /**
     * Checks vowel or consonant previous letter.
     * @param word the checked word.
     * @param i the address of the checked symbol.
     * @return yes - if the previous letter is the consonant.
     */
    private boolean previousConsonant(String word, int i) {
        if ( i>0 ){
            if (vowelLetter(word, i-1)){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks vowel or consonant current letter.
     * @param word the checked word.
     * @param i the address of the checked symbol.
     * @return yes - if the letter is the vowel.
     */
    private boolean vowelLetter(String word, int i) {
        char c = word.charAt(i);
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y'){ return true; }
        if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'Y'){ return true; }
        return false;
    }
}