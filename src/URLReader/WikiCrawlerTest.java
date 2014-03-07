package URLReader;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ThoughtWorker on 3/6/14.
 */
public class WikiCrawlerTest {
    WikiCrawler wikiCrawler;

    @Before
    public void initWikiParser() {
        wikiCrawler = new WikiCrawler();
    }

    @Test
    public void testGetRandomPage() {
        URL randomPage = null;
        try {
            randomPage = wikiCrawler.getRandomWikiPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(randomPage);
        assertEquals("en.wikipedia.org", randomPage.getHost());
    }

    @Test
    public void testGetWikiTitle() throws Exception {
        String title = wikiCrawler.getWikiTitle(new URL("http://en.wikipedia.org/wiki/Java"));
        assertEquals("Java", title);
    }

    @Test
    public void testGetFirstLink() throws Exception {
        URL indonesia = new URL("http://en.wikipedia.org/wiki/Indonesia");
        URL firstLink = wikiCrawler.getFirstLink(indonesia);
        assertEquals("http://en.wikipedia.org/wiki/Indonesian_language", firstLink.toString());
    }

    @Test
    public void testGetToPhilosophy() throws Exception {
        URL java = new URL("http://en.wikipedia.org/wiki/History");
        URL philosophy = new URL("http://en.wikipedia.org/wiki/History");
        int philNumber = wikiCrawler.getWikiNumber(new URL[] {java, philosophy});
        assertEquals(7, philNumber);

    }
}
