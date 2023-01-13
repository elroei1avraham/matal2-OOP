package Ex2_1;

import static Ex2.Ex2_1.singleFileNumOfLines;

/**
 * Thread class that counts the number of lines in a text file.
 */
public class ComputeableThread extends Thread{

    //presenting the file name to be processed by this thread
    private String myFileName;

    // presentingThe number of lines counted by this thread
    private int numberOfLines;



    /**
     * Constructs a new Part_1.ComputeableThread for the given file name.
     * @param name The file name to be processed by this thread.
     */
    public ComputeableThread(String name){
        this.numberOfLines = 0;
        this.myFileName = name;

    }

    /**
     * Getter for numberOfLines data member.
     * @return the number of lines counted by this thread.
     */
    public int getNumberOfLines(){
        return this.numberOfLines;
    }

    /**
     * Counts the number of lines in the file specified in the constructor.
     * Only processes files that end with the ".txt" extension.
     */
    @Override
    public void run(){
        this.numberOfLines = this.numberOfLines + singleFileNumOfLines(this.myFileName);

    }
}

