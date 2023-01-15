import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class ParsingData {

    private final static String file = "names.properties";

    /**
     * A logger created to verify programme correctness
     */
    private final static Logger log = LogManager.getLogger(ParsingData.class);
    public static List<Object[]> parseData(String address) {
        Connection.Response r;
        Document doc = null;
        URL url;
        try {
            url = new URL(address);
            log.info("Trying to connect to " + url);
            r = Jsoup.connect(url.toString()).header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .header("Content-Type", "text/html; charset=ISO-8859-1").referrer("https://www.google.com")
                    .ignoreContentType(true).maxBodySize(0).timeout(600000).execute();
            log.info("Successfully connected to" + url);
            log.debug("Parsing the content");
            doc = r.parse();
        }
        catch (IOException e) {
            System.out.println("Failed to connect to site");
            log.fatal("Failed to connect to site. Error: " + e.getLocalizedMessage());
            System.exit(1);
        }

        log.debug("Content has been successfully parsed");
        Elements objects = doc.select("table[cellspacing=0][cellpadding=0]");
        log.info("Found " + objects.size() + " tables");
        if (objects.size() != 1) {
            log.fatal("It must equal 1.");
            System.exit(1);
        }
        objects = Objects.requireNonNull(objects.first()).select("tr");
        log.info("Found " + objects.size() + " elements");

        List<Object[]> list = new ArrayList<>();
        log.debug("Created list of objects");
        Properties nameProps = new Properties();
        log.debug("Created properties object");

        nameProps.setProperty("date", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));

        for (int i = 1; i < objects.size(); i++) {
            Object[] temp = {"", "", "", ""};
            for (int j = 0; j < 4; j++) {
                temp[j] = objects.get(i).select("td").get(j).text();
            }
            list.add(temp);
        }

        nameProps.setProperty("data", "" + list.size());

        log.info("Added date and number of rows in table to properties file");
        log.debug("Added data to List");

        for (int i=0; i<list.size(); i++) {
            nameProps.setProperty("entry" + i, Arrays.toString(list.get(i)));
        }

        log.info("Added entries to properties");

        try {
            OutputStream outputStream = new FileOutputStream(file);
            nameProps.store(outputStream, address);
        }
        catch (IOException e) {
            System.out.println("Error creating file !");
            log.error("Exception: " + e.getLocalizedMessage(), e);
            System.exit(1);
        }
        log.info("Successfully saved properties file");
        return(list);
    }
}
