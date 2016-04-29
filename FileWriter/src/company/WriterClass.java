/**
 * Created by Søren on 29-04-2016.
 */

import java.io.*;
import java.util.ArrayList;

public class WriterClass {
        //setup filewrite
        public File outputFile;
        public FileWriter writer;

    public WriterClass()throws IOException{
        outputFile = new File("output.java");
        outputFile.createNewFile();
        writer = new FileWriter(outputFile);
    }

    public void WriteThis()throws IOException{
        ArrayList<String> result = new ArrayList<String>();
        result= //funktionskald til setup
                for
        writer.write("test");
        writer.flush();
        writer.close();
    }
}
