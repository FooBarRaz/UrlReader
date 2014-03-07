package URLReader;

import URLReader.URLReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ThoughtWorker on 3/6/14.
 */
public class UrlReaderTest {

    String website;
    URLReader urlReader;

    @Before
    public void setUp(){
        website = "http://www.xkcd.com/";
        urlReader = new URLReader();
    }


    @Test
    public void testGetHTMLFromWebsite() throws Exception {
        //String html = urlReader.getHTML(website);

    }
}
