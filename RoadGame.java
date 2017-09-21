import com.shpp.cs.a.console.TextProgram;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RoadGame extends TextProgram {
    public void run() {
        while (true) {
            String s = readLine("Enter set letters: ");
            s = s.toLowerCase();
            find(s);
        }
    }

    /**
     * Find words that contain certain specified letters.
     * @param s a given set of letters.
     */
    private void find(String s) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("en-dictionary.txt"));
            /* Sequentially read words and sent for analysis */
            while (true) {
                String word = br.readLine();
                if (word == null) break;
                int j = 0;
                /* Each letter words verified, with the preset letters */
                for (int i = 0; i<(word.length()); i++){
                    if ( word.charAt(i) == s.charAt(j) ){
                        j++;
                        if ((j)== s.length()) {
                            println(word);
                            break;
                        }
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            println(e);
        }
    }
}

