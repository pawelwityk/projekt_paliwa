import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphicInterfaceTable extends JFrame {
    private final static Logger log = LogManager.getLogger(GraphicInterfaceTable.class);
    public static void createAndShowGUI(ArrayList<List<String>> data) {
        log.info("Created AcionListener");
        ActionListener myActionListener = e -> {
            String buttonPressed = e.getActionCommand();
        };

        Object[] column = {"Data","Cena hurtowa","Opłata paliwowa","Akcyza"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        List<Object[]> list = new ArrayList<>();

        for (List<String> datum : data) {
            Object[] tempObj = datum.toArray();
            list.add(tempObj);
            tableModel.addRow(tempObj);
        }

        JFrame jf = new JFrame("Hurtowe ceny paliw");
        JPanel jp = new JPanel(new GridLayout(0, 1));
        JTable jt = new JTable(tableModel);

        jt.setDragEnabled(false);
        jt.setAutoCreateRowSorter(true);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        jp.setOpaque(true);
        jp.setFocusable(false);
        jp.add(jt);
        jp.add(new JScrollPane(jt));

        jf.setResizable(true);
        jf.setLocationRelativeTo(null);
        jf.getContentPane().add(jp);
        jf.setVisible(true);
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}