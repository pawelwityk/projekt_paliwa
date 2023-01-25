import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class Main {

    /**
     * A logger created to verify programme correctness
     */

    private final static Logger log = LogManager.getLogger(Main.class);

    /**
     * An address from which data would be collected
     */

    private final static String address = "https://www.lotos.pl/145/type,oil_95/dla_biznesu/hurtowe_ceny_paliw/archiwum_cen_paliw";

    public static void main(String[] args) {

        log.debug("Trying to get data from ParsingData class");
        List<Object[]> list = ParsingData.parseData(address);
        log.debug("Data has been successfully transferred");
        //GraphicInterfaceMain.createAndShowGUI();

        log.debug("Creating graphic interface");
        javax.swing.SwingUtilities.invokeLater(() -> GraphicInterfaceTable.createAndShowGUI(list));
    }
}