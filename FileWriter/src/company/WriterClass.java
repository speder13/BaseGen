/**
 * Created by Søren on 29-04-2016.
 */

import java.io.*;
import java.util.ArrayList;

public class WriterClass {
        //setup FileWriter
        public File outputFile;
        public FileWriter writer;
        ArrayList<String> strings=new ArrayList<String>();
    HeaderSetup headerSetup;

    public WriterClass()throws IOException{
        headerSetup = new HeaderSetup();
        outputFile = new File("output.java");
        outputFile.createNewFile();
        writer = new FileWriter(outputFile);
    }

    public void WriteThis()throws IOException{
        ArrayList<String> result;
        result= headerSetup.BuildHeader();
        for (String s:result) {
            writer.write(s + "\n");
        }


        writer.write("}");
        writer.flush();
        writer.close();
    }
    public void GetGeneratedCode(){
        // strings= returmetode fra AST builder

    }
}
