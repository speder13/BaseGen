/**
 * Created by Søren on 29-04-2016.
 */
import java.io.*;
public class WriterClass {
        //setup filewrite
        File outputFile;
        FileWriter writer;

    public void setupOutputFile() {
        outputFile = new File("output.java");
        // creates the file
    }

    public void WriteThis(){
        writer = new java.io.FileWriter(outputFile);
        // Writes the content to the file
        writer.write("test");
        writer.flush();
        writer.close();
    }
    
}
