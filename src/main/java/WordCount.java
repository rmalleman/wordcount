
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentMap;
/**
 * Created by matt on 8/29/16.
 */
public class WordCount implements Runnable {

    private String words;
    private ConcurrentMap<String, Integer> map;

    public WordCount(String words, ConcurrentMap<String, Integer> map) {
        this.map = map;
        this.words = words;
    }

    private void incrementCount(String token) {
        //remove trailing punctuation and convert to lowercase
        String cleanToken = token.replaceAll("^[^a-zA-Z0-9\\s]+|[^a-zA-Z0-9\\s]+$", "").toLowerCase();

        //if the value is already in there increment the count, otherwise make it 1
        Integer value = map.get(cleanToken);
        if(value != null) {
            map.put(cleanToken, value+1);
        }
        else{
            map.put(cleanToken, 1);
        }
    }

    public void run() {
        //Tokenize the string by white space and pass tokens to incrementCount
        StringTokenizer st = new StringTokenizer(this.words);
        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            incrementCount(token);
        }

    }

}
