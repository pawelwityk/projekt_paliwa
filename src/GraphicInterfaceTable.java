import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.util.List;

public class GraphicInterfaceTable extends JFrame {

    private final static Logger log = LogManager.getLogger(GraphicInterfaceTable.class);

    public static void createAndShowGUI(List<Object[]> data) {
        //creating column descriptions and table model
        log.debug("Creating descriptions of the columns");
        Object[] column = {"Data","Cena hurtowa","Opłata paliwowa","Akcyza"};
        log.debug("Creating table model");
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        //adding data to the table
        log.debug("Trying to add data to the table");
        for (Object[] datum : data) {
            tableModel.addRow(datum);
        }
        log.debug("Successfully added data to the table");
        //creating swing objects
        log.debug("Creating Swing objects");
        JFrame jf = new JFrame("Hurtowe ceny paliw");
        JPanel jp = new JPanel(new GridLayout(0, 1));
        JTable jt = new JTable(tableModel);
        //parameters of the table
        log.debug("Setting up JTable");
        jt.setDragEnabled(false);
        jt.setAutoCreateRowSorter(true);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //parameters of the panel
        log.debug("Setting up JPanel");
        jp.setOpaque(true);
        jp.setFocusable(false);
        jp.add(jt);
        jp.add(new JScrollPane(jt));
        //parameters of the frame
        log.debug("Setting up JFrame");
        jf.setResizable(true);
        jf.setLocationRelativeTo(null);
        jf.getContentPane().add(jp);
        log.debug("Setting up window to visible");
        jf.setVisible(true);
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}