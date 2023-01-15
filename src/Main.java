import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;


public class Main {

    private final static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        log.debug("Trying to get data from ParsingData class");

        ArrayList<List<String>> al = ParsingData.parseData();

        log.debug("Data has been sucessfully got");
        log.debug("Creating graphic interface");

        javax.swing.SwingUtilities.invokeLater(() -> GraphicInterfaceTable.createAndShowGUI(al));
    }
}