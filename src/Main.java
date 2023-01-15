import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class Main {

    private final static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        log.debug("Trying to get data from ParsingData class");

        List<Object[]> list = ParsingData.parseData();

        log.debug("Data has been successfully got");
        log.debug("Creating graphic interface");

        javax.swing.SwingUtilities.invokeLater(() -> GraphicInterfaceTable.createAndShowGUI(list));
    }
}