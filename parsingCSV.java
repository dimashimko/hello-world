import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class parsingCSV extends TextProgram {
    private static final String file = "file.csv";
    public void run() {
        println(extractColumn(file, readInt("Enter number of a column: ")));
    }

    /**
     * Reading the file on one line
     * @param filename Name of the file.
     * @param columnIndex Number of a column which needs to be transferred to a ArrayList.
     * @return Output ArrayList.
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            ArrayList<String> outList = new ArrayList<>();
            String line;
            while ((line = br.readLine())!= null) {
                outList.add(findColumn(line,columnIndex));
            }
                br.close();
            return outList;
        } catch (IOException e) {
            println(e);
            return null;
        }
    }

    /**
     * Processes the received line, and returns value of the set column.
     * @param line Line of the table, you need to extract from it the value of the specified column.
     * @param columnIndex Number of a column which needs to be transferred to a ArrayList.
     * @return Value of the specified column.
     */
    private String findColumn(String line, int columnIndex) {
        String outString = "";
        boolean noKavichki = true;
       int i = 0;
        for (int j = 0; j<line.length(); j++) {
            if(line.charAt(j) == '"') {
                if (noKavichki ? (noKavichki = false) : (noKavichki = true));
            }

            if(line.charAt(j)==',' && noKavichki ){ i++; }

            if (i == columnIndex ) {
                boolean b = true;
                if ( line.charAt(j) == '"' ){ b = false; }
                if (line.charAt(j) == ',' && noKavichki){ b = false; }
               if (b) { outString += line.charAt(j);}
            }
        }
        if (outString == ""){ return null; }
        return outString;
    }

}
