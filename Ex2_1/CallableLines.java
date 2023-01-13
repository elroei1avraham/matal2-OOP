package Ex2_1;

import java.util.concurrent.Callable;

import static Ex2.Ex2_1.singleFileNumOfLines;

/**
 * Callable class that counts the number of lines in a text file.
 */
public class CallableLines implements Callable<Integer> {

        // Presenting the file name to be processed by this callable
        private String fileName;

    /**
     *  Constructs a new Part_1.CallableLines for the given file name.
     *
     * @param name String of the file name to be processed by this callable.
     */
        public CallableLines(String name){
            this.fileName = name;
        }


    /**
     * Counts the number of lines in the file specified in the constructor
     * by using ths Asst. Method singleFileNumOfLines.
     *
     * @return Number of lines which the specified file contains.
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        int numberOfLines;
            numberOfLines = singleFileNumOfLines(this.fileName);
            return numberOfLines;
    }
}
