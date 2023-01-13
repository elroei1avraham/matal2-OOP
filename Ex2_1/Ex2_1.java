package Ex2_1;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @authors Elroei Avraham, Talor Langnas
 * @Version 2.1
 * This class provides methods for counting the number of lines in multiple text files.
 *
 */
public class Ex2_1 {

    public static void main(String[] args){
        int n = 1000;
        int seed = 4;
        int bound = 100;


        String[] strings = createTextFiles(n,seed,bound);
        System.out.println(Arrays.toString(strings));

        // Calculate the time that take to getNumOfLines()
        Instant startA = Instant.now();
        int lines = getNumOfLines(strings);
        Instant endA = Instant.now();
        Duration timeElapsed = Duration.between(startA, endA);

        // Print the details
        System.out.println("time for getNumOfLines: "+timeElapsed.toMillis() +" Millis");
        System.out.println("getNumOfLines = "+lines);

        // Calculate the time that take to getNumOfLinesThreads()
        Instant startB = Instant.now();
        int lines2 = getNumOfLinesThreads(strings);
        Instant endB = Instant.now();
        timeElapsed = Duration.between(startB, endB);

        // Print the details
        System.out.println("time for getNumOfLinesThreads: "+timeElapsed.toMillis() +" Millis");
        System.out.println("getNumOfLinesThreadPool = "+ lines2);

        // Calculate the time that take to getNumOfLinesThreadPool()
        Instant startC = Instant.now();
        int lines3 = getNumOfLinesThreadPool(strings);
        Instant endC = Instant.now();
        timeElapsed = Duration.between(startC, endC);

        // Print the details
        System.out.println("time for getNumOfLinesThreadPool: "+timeElapsed.toMillis() +" Millis");
        System.out.println("getNumOfLinesThreadPool = "+ lines3);
    }

    /**
     * This method creates n number of files
     * @param n the number of files to create
     * @param seed the seed for the random number generator
     * @param bound the upper bound for the number of lines in each file
     * @return Array of the names of the created files
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String filesName[] = new String[n];
        Random random = new Random(seed);

        // Create the files
        for (int i = 0; i < n; i++) {
            String fileName = "file" + i + ".txt";
            filesName[i] = fileName;
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                for (int j = 0; j < random.nextInt(bound); j++) {
                    bufferedWriter.write("Hello World\n");
                }
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return filesName;
    }

    /**
     * Counting Total number of lines in all the txt files given the names of the files,
     * using the naive method that iterate all the files
     * @param fileNames Array of file names
     * @return Total number of lines of all given txt files
     */
    public static int getNumOfLines(String[] fileNames){
        int numberOfLines = 0;
        for(int i = 0; i < fileNames.length; i++){
            numberOfLines = numberOfLines + singleFileNumOfLines(fileNames[i]);
        }
        return numberOfLines;
    }

    /**
     * Counting Total number of lines in all the txt files given the names of the files,
     * using a separate thread for each file.
     * @param fileNames Array of the files names
     * @return Total number of lines of all given txt files
     */
    public static int getNumOfLinesThreads(String[] fileNames){
        int numOfLines = 0;
        ComputeableThread computeThreadArr[] = new ComputeableThread[fileNames.length];

        for(int i=0; i<fileNames.length; i++){
            computeThreadArr[i] = new ComputeableThread(fileNames[i]);
            computeThreadArr[i].start();
            try {
                computeThreadArr[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            numOfLines = numOfLines +  computeThreadArr[i].getNumberOfLines();
        }

        return numOfLines;
    }

    /**
     * Counting Total number of lines in all the txt files given the names of the files,
     * using a thread pool of the size of fileNames.length.
     * @param fileNames Array of the files names
     * @return Total number of lines of all given txt files
     */
    public static int getNumOfLinesThreadPool(String[] fileNames){
        int totalNumber =0;
        Future<Integer> futersTask[] = new Future[fileNames.length];
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
        for (int i = 0; i < fileNames.length; i++) {
            futersTask[i] = threadPool.submit(new CallableLines(fileNames[i]));
        }

        for(int i=0; i<fileNames.length; i++) {
            try {
                totalNumber = totalNumber + futersTask[i].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        return totalNumber;
    }

    /**
     * Asst. method
     * This method count number of lines in one single file.
     * @param fileName String of file name.
     * @return Number of line.
     */
    public static int singleFileNumOfLines(String fileName){
        int numberOfLines = 0;
        int counter = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while(reader.readLine()!=null){
                counter++;
            }
            numberOfLines = numberOfLines + counter;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return numberOfLines;
        }
    }