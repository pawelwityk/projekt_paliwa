import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphicInterface extends JFrame {
    public static void createAndShowGUI(ArrayList<List<String>> data) {
        ActionListener myActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
            }
        };
        Object[] column = {"Data","Cena hurtowa","Opłata paliwowa","Akcyza"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        JFrame jf = new JFrame("Hurtowe ceny paliw");
        jf.setVisible(true);
        JPanel jp = new JPanel();
        jf.getContentPane().add(jp);
        JTable jt = new JTable(tableModel);
        jp.add(jt);


        //System.out.println(temp[0]);
        List<Object[]> list = new ArrayList<>();
        tableModel.addColumn(column);
        for (List<String> datum : data) {
            Object[] tempObj = datum.toArray();
            list.add(tempObj);
            tableModel.addRow(tempObj);
        }

        Dimension dm = new Dimension(1200, 900);
        jf.pack();
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setPreferredSize(dm);
        jp.setMaximumSize(dm);
        jp.setMinimumSize(dm);
        jf.setMaximumSize(dm);
        jf.setMinimumSize(dm);
        jp.setPreferredSize(dm);

        jp.setLayout(new GridLayout(5, 0, 5, 15));
        jp.setBorder(new EmptyBorder(10, 10, 10, 10));

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}