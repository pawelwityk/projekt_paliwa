import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GraphicInterfaceMain extends JFrame {

    private final static Logger log = LogManager.getLogger(GraphicInterfaceTable.class);

    public static void createAndShowGUI() {
        log.info("Created ActionListener");
        ActionListener myActionListener = e -> {
            String buttonPressed = e.getActionCommand();
        };

    }
}
