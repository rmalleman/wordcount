import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by matt on 8/29/16.
 */
public class WordCountImpl {

    public static void main(String args[]) {
        Integer numberOfThreads = null;
        String inputFile = null;

        Options options = new Options();
        options.addOption("h", false, "show help.");
        options.addOption("t", true, "Set the number of thread to use.");
        options.addOption("f", true, "Path to the file with list of filenames");
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse( options, args);
            if (cmd.hasOption('t') && cmd.hasOption('f')){
                numberOfThreads = Integer.parseInt(cmd.getOptionValue('t'));
                inputFile = cmd.getOptionValue('f');
            }
            else{
                help(options);
            }
        } catch (ParseException e) {
            help(options);
        }

        //create a concurrent hashmap to hold the values
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
        try {
            //create a threadpool with the given number of threads
            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String foo = readFileToString(line);
                //create new wordcount thread and pass the file thats been converted to a string and the map
                Runnable wordcount = new WordCount(foo, map);
                executor.execute(wordcount);
            }

            //shutdown the thread pool and wait until all the threads have finished
            executor.shutdown();
            while (!executor.isTerminated()) {

            }

            //iterate over the hashmap and print the key value pairs
            Iterator it = map.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }

        } catch (FileNotFoundException e) {
            System.out.println("given file doesn't exist");
        } catch (IOException e) {
            System.out.println("given file doesn't exist");
        }
    }

    private static void help(Options options) {
        //print help if you screw up the command line parsing
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Main", options);
        System.exit(0);
    }

    static String readFileToString(String path) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }


}
