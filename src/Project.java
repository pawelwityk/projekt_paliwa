import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class Project {
    private final static Logger log = LogManager.getLogger(Project.class);
    private final static String file = "names.properties";
    private final static String address = "https://www.lotos.pl/145/type,oil_95/dla_biznesu/hurtowe_ceny_paliw/archiwum_cen_paliw";
    public static void main(String[] args) {
        Response r;
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
            log.info("Parsing the content");
            doc = r.parse();
        }
        catch (IOException e) {
            System.out.println("Failed to connect to site");
            log.fatal("Failed to connect to site. Error: " + e.getLocalizedMessage());
            System.exit(1);
        }

        log.info("Content has been successfully parsed");
        Elements tmp = doc.select("table[cellspacing=0][cellpadding=0]");
        log.info("Found " + tmp.size() + " tables");
        if (tmp.size() != 1) {
            log.fatal("It must equal 1.");
            System.exit(1);
        }
        tmp = Objects.requireNonNull(tmp.first()).select("td");
        log.info("Found " + tmp.size() / 4 + " elements");

        ArrayList<List<String>> al = new ArrayList<>();
        log.info("Created ArrayList");
        Properties nameProps = new Properties();
        log.info("Created properties object");

        int x = 0;

        nameProps.setProperty("date", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));

        for (int i = tmp.size() / 4; i > 0; i--) {
            String[] temp = {"", "", "", ""};
            for (int j = 0; j < 4; j++) {
                temp[j] = tmp.get(x).text();
                System.out.println(temp[j]);
                x++;
            }
            al.add(Arrays.stream(temp).toList());
        }
        nameProps.setProperty("data", "" + al.size());

        log.info("Added date and number of rows in table to properties file");
        log.info("Added data to ArrayList");

        for (int i=0; i<al.size(); i++) {
            nameProps.setProperty("entry" + i, al.get(i).toString());
        }

        log.info("Added entries to properties");

//        al.forEach(line -> {
//            for (Object object : line)
//                System.out.println(object);
//        });
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
    }
}