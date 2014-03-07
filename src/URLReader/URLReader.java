package URLReader;

import java.net.*;
import java.io.*;

public class URLReader {
    public URLReader(){
    }

    public String getHTML(URL target) throws Exception {
        String result = "";


        BufferedReader in = new BufferedReader(
        new InputStreamReader(target.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            result += inputLine;
        in.close();

        return result;
    }
}
