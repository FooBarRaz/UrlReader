package URLReader;

import java.net.URL;
import java.util.Scanner;

/**
 * Created by ThoughtWorker on 3/6/14.
 */
public class WikiNumber {

    public static void main(String args[]) throws Exception {
        String[] endpoints = promptForEndpoints();
        WikiCrawler wikiCrawler = new WikiCrawler();
        URL[] endpointURLs = wikiCrawler.convertStringsToURLs(endpoints);
        wikNumber(wikiCrawler, endpointURLs);
    }

    private static void wikNumber(WikiCrawler wikiCrawler, URL[] endpointURLs) throws Exception {
        int wikiNumber = wikiCrawler.getWikiNumber(endpointURLs);
        System.out.println(wikiNumber + " clicks from " + endpointURLs[0].getPath() + " to " + endpointURLs[1].getPath());
    }


    private static String[] promptForEndpoints() {
        String[] endpoints = new String[2];
        System.out.print("Wiki to start from: ");
        Scanner scanner = new Scanner(System.in);
        endpoints[0] = scanner.nextLine();
        System.out.print("Wiki to end at: ");
        endpoints[1] = scanner.nextLine();
        return endpoints;
    }
}
