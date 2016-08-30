# Overview

Because the program takes in multiple files, it seemed like to me that
parallelizing would yield some performance benefits.  I didn't have time
to sort the resulting hashmap, so thats the only requirement I missed.

To run the unittests:

<code>$ mvn clean test </code>

To create a commandline executable

<code>$ mvn assembly:assembly</code>

From there you can see the help menu by running:

<code>$ java -jar target/wordcount-1.0-SNAPSHOT-jar-with-dependencies.jar 
 -f <arg>   Path to the file with list of filenames
 -h         show help.
 -t <arg>   Set the number of thread to use.
</code>

