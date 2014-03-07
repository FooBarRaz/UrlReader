package URLReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by ThoughtWorker on 3/6/14.
 */
public class WikiCrawler {
    final String randomWikiPageUrl = "http://en.wikipedia.org/wiki/Special:Random";
    ArrayList<String> visitedPages;


    public URL getRandomWikiPage() throws IOException {
        URLConnection con = new URL(randomWikiPageUrl).openConnection();
        con.connect();
        InputStream is = con.getInputStream();
        is.close();
        return con.getURL();
    }

    public String getWikiTitle(URL url) throws Exception {
        Document doc = getWikiDocument(url);
        return doc.getElementById("firstHeading").text();
    }

    private Document getWikiDocument(URL url) throws Exception {
        String html = new URLReader().getHTML(url);
        return Jsoup.connect(url.toString()).get();
    }


    public URL getFirstLink(URL wiki) throws Exception {
        Document html = getWikiDocument(wiki);
        Element articleBody = getArticleBody(html);
        Elements links = articleBody.getElementsByTag("a");
        for (Element link: links) {
            if (isWikiArticle(link) && notVisited(link))
            {
                return new URL("http://" + wiki.getHost() + link.attr("href"));
            }
        }
        return null;
    }

    private ArrayList<URL> getAllWikiLinks (URL page) throws Exception {
        ArrayList<URL> allWikiLinks = new ArrayList<URL>();
        Element articleBody = getArticleBody(getWikiDocument(page));
        for (Element link : articleBody.getElementsByTag("a")) {
            URL linkUrl = buildURL(link);
            if (isWikiArticle(link) && !allWikiLinks.contains(linkUrl)) {
                allWikiLinks.add(linkUrl);
            }
        }
        return allWikiLinks;
    }

    private URL buildURL(Element link) throws Exception {
        URL url =  new URL("http://" + "en.wikipedia.org" + link.attr("href"));
        return url;
    }

    private boolean notVisited(Element link) {
        return visitedPages == null || !visitedPages.contains(link.attr("href"));
    }

    private Element getArticleBody(Document html) {
        String title = html.getElementById("firstHeading").text();
        Element result = html.getElementById("mw-content-text");
        return result;
    }



    private boolean areSimilar(String a, String b) {
        return (a.contains(b) || b.contains(a) || a.startsWith(b) || b.startsWith(a));
    }

    private boolean isWikiArticle(Element link) {
        String path = link.attr("href");
        return !(!path.startsWith("/wiki/") || path.startsWith("/wiki/Help") || path.startsWith("/wiki/File")
                || path.startsWith("/wiki/Wikipedia"));
    }

    public int getWikiNumber(URL[] endpoints) throws Exception {
        int count = 0;
        visitedPages = new ArrayList<String>();
        visitedPages.add(endpoints[0].getPath());
        URL currentURL = endpoints[0];
        URL target = endpoints[1];

        System.out.println(currentURL.getPath());

        while (!currentURL.equals(target) && count < 200) {
            count++;
            currentURL = getFirstUnvisitedLink(currentURL);
            visitedPages.add(currentURL.getPath());
            for (int i = 0; i < count; i++) System.out.print(" ");
            System.out.print(currentURL.getPath() + "\n");
        }

        return count;
    }

    private URL getFirstUnvisitedLink(URL url) throws Exception {
        Document wikidoc = getWikiDocument(url);
        Element articleBody = getArticleBody(wikidoc);
        for (Element child: articleBody.children())
        {
            if (child.tag().toString().equals("p")) {
                for (Element link : child.getElementsByTag("a")) {
                    if (isWikiArticle(link) && notVisited(link)) return buildURL(link);
                }
            }
        }
        return null;
    }

    public URL[] convertStringsToURLs(String[] endpoints) throws IOException {
        URL[] urlList = new URL[endpoints.length];
        for(int i = 0; i < endpoints.length; i++)
        {
            if (endpoints[i].toLowerCase().equals("r")){
                urlList[i] = getRandomWikiPage();
            }
            else if (!endpoints[i].toLowerCase().contains("/")) {
                urlList[i] = new URL("http://en.wikipedia.org/wiki/" + endpoints[i]);
            }
            else {
                urlList[i] = new URL(endpoints[i]);
            }
        }
        return  urlList;
    }
}
