/**
 * Created by Søren on 29-04-2016.
 */

import java.io.*;
import java.util.ArrayList;

public class WriterClass {
        //setup filewrite
        public File outputFile;
        public FileWriter writer;
    HeaderSetup headerSetup;

    public WriterClass()throws IOException{
        headerSetup = new HeaderSetup();
        outputFile = new File("output.java");
        outputFile.createNewFile();
        writer = new FileWriter(outputFile);
    }

    public void WriteThis()throws IOException{
        ArrayList<String> result = new ArrayList<String>();
        result= headerSetup.BuildHeader();
        for (String s:result) {
            writer.write(s + "\n");
        }

        writer.write("}");
        writer.flush();
        writer.close();
    }
}
