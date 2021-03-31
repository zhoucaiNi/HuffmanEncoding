
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class FrequencyTable {

    private Map<Character, Integer> fTable;

    public FrequencyTable(){
        fTable = new HashMap<Character, Integer>();

    }

    // returns the frequency table of a file
    public void charFrequency(String newFile) throws Exception{

        BufferedReader input = new BufferedReader(new FileReader(newFile));
        int character;

        while((character = input.read()) != -1){
            Character c = (char) character;

            if(fTable.containsKey(c)){
                fTable.put(c,fTable.get(c)+1);
            }
            else{
                fTable.put(c,1);
            }

        }
        input.close();

    }

    public Map<Character, Integer> getfTable() {
        return fTable;
    }
}
