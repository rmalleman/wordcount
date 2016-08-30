/**
 * Created by matt on 8/29/16.
 */

import junit.framework.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordCountTest extends TestCase {
    ConcurrentHashMap<String, Integer> map;
    ConcurrentHashMap<String, Integer> expected_map;
    String input;
    WordCount count;
    ExecutorService executor;

    protected void setUp(){
        input = "This is only a test. A *very* short test!";
        map = new ConcurrentHashMap<String, Integer>();
        expected_map = new ConcurrentHashMap<String, Integer>();
        expected_map.put("a", 2);
        expected_map.put("test", 2);
        expected_map.put("only", 1);
        expected_map.put("short", 1);
        expected_map.put("this", 1);
        expected_map.put("very", 1);
        expected_map.put("is", 1);
        executor = Executors.newFixedThreadPool(1);
        count = new WordCount(input, map);
        executor.execute(count);
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        assertEquals(map, expected_map);
    }

    public void testCount() {

    }

}
