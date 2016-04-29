
/*Offers functionality to build a Java file header*/

import com.sun.org.apache.xerces.internal.xs.StringList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HeaderSetup {

    //Builds, then returns, a header. One line per string in the list.
    List<String> BuildHeader()
    {
        ArrayList<String> Header = new ArrayList<String>();
        Header.add("import java.util.ArrayList;");
        Header.add("import java.util.List;");
        Header.add("");
        Header.add("public class Main{");


        System.out.println("The following has been written:");
        for(Iterator<String> i = Header.iterator(); i.hasNext(); ) {
            String item = i.next();
            System.out.println("* " + item);
        }

        return Header;
    }
}