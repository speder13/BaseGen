/**
 * Created by Søren on 28-04-2016.
 */

import java.io.*;
import java.util.ArrayList;

public class HeaderSetup {
//setup filewriter
File outputFile = new File("output.java");
    // creates the file
    outputFile.createNewFile();
    // creates a FileWriter Object
    FileWriter writer = new FileWriter(outputFile);
    // Writes the content to the file
    writer.write("This\n is\n an\n example\n");
    writer.flush();
    writer.close();
}

