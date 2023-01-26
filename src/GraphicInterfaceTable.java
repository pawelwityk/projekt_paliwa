import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class used to display table containing specific data
 */

public class GraphicInterfaceTable extends JFrame {

    /**
     * A logger created to verify programme correctness
     */

    private final static Logger log = LogManager.getLogger(GraphicInterfaceTable.class);

    /**
     * A list containing filtered months
     */

    private static final List<Object[]> filteredMonths = new ArrayList<>();

    /**
     * A list containing filtered years
     */

    private static final List<Object[]> filteredYears = new ArrayList<>();

    /**
     * A list containing data filtered both by month and year
     */

    private static final List<Object[]> dataToShow = new ArrayList<>();

    /**
     * A boolean field containing information about filtering by month
     */

    private static boolean isMonthFiltered = false;

    /**
     * A boolean field containing information about filtering by year
     */

    private static boolean isYearFiltered = false;

    /**
     * This method displays GUI containing table and scroller
     * @param data Data transferred from other class expected to show in the table
     */

    public static void createAndShowGUI(List<Object[]> data) {

        //creating strings
        String[] months = { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER",
                "OCTOBER", "NOVEMBER", "DECEMBER" };

        String[] years = {"2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016",
                "2017", "2018", "2019", "2020", "2021", "2022", "2023"};

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
        JPanel jp2 = new JPanel(new FlowLayout());
        JTable jt = new JTable(tableModel);
        JComboBox<String> jcMonths = new JComboBox<>(months);
        JComboBox<String> jcYears = new JComboBox<>(years);
        JButton clearButton = new JButton("Wyczyść");
        JButton confirmButton = new JButton("Wyświetl");
        JButton chartButton = new JButton("Wykres");

        //parameters of the table
        log.debug("Setting up JTable");
        jt.setDragEnabled(false);
        jt.setDefaultEditor(Object.class, null);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //parameters of the panel
        log.debug("Setting up JPanel");
        jp.setOpaque(true);
        jp.setFocusable(false);
        jp.add(jt);
        jp.add(new JScrollPane(jt));

        //creating action listener
        ActionListener mouseClickedMonth = e -> {
            String selected = (String) jcMonths.getSelectedItem();
            if(isMonthFiltered) {
                log.error("Dane zostały już przefiltrowane !");
                JOptionPane.showMessageDialog(null, "Dane zostały już przefiltrowane !", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            else if(!filteredYears.isEmpty()){
                filteredMonths.clear();
                for (Object[] datum: filteredYears) {
                    if (Objects.equals(LocalDate.parse(datum[0].toString()).getMonth().toString(), selected)) {
                        filteredMonths.add(datum);
                        dataToShow.add(datum);
                    }
                }
                isMonthFiltered = true;
            }
            else {
                filteredMonths.clear();
                dataToShow.clear();
                for (Object[] datum: data) {
                    if (Objects.equals(LocalDate.parse(datum[0].toString()).getMonth().toString(), selected)) {
                        filteredMonths.add(datum);
                    }
                }
                isMonthFiltered = true;
            }
        };
        ActionListener mouseClickedYear = e -> {
            String selected = (String) jcYears.getSelectedItem();
            if(isYearFiltered) {
                log.error("Dane zostały już przefiltrowane !");
                JOptionPane.showMessageDialog(null, "Dane zostały już przefiltrowane !", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            else if(!filteredMonths.isEmpty()){
                filteredYears.clear();
                dataToShow.clear();
                for (Object[] datum: filteredMonths) {
                    if (String.valueOf(LocalDate.parse(datum[0].toString()).getYear()).equals(selected)) {
                        filteredYears.add(datum);
                        dataToShow.add(datum);
                    }
                }
                isYearFiltered = true;
            }
            else {
                filteredYears.clear();
                dataToShow.clear();
                for (Object[] datum: data) {
                    if (String.valueOf(LocalDate.parse(datum[0].toString()).getYear()).equals(selected)) {
                        filteredYears.add(datum);
                    }
                }
                isYearFiltered = true;
            }
        };
        ActionListener clearingButton = e -> {
            isMonthFiltered = false;
            isYearFiltered = false;
            filteredMonths.clear();
            filteredYears.clear();
            tableModel.getDataVector().removeAllElements();
            for (Object[] datum: data) {
                tableModel.addRow(datum);
            }
        };
        ActionListener displayingFilteredData = e -> {
            if (isMonthFiltered && !isYearFiltered) {
                tableModel.getDataVector().removeAllElements();
                for (Object[] datum: filteredMonths) {
                    tableModel.addRow(datum);
                }
            }
            else if (!isMonthFiltered && isYearFiltered) {
                tableModel.getDataVector().removeAllElements();
                for (Object[] datum: filteredYears) {
                    tableModel.addRow(datum);
                }
            }
            else {
                tableModel.getDataVector().removeAllElements();
                for (Object[] datum: dataToShow) {
                    tableModel.addRow(datum);
                }
            }
        };
        ActionListener displayChart = e -> {
            if (isMonthFiltered && !isYearFiltered) {
                ChartCreator chart = new ChartCreator("Hurtowe ceny paliwa", filteredMonths);
                chart.pack( );
                RefineryUtilities.centerFrameOnScreen( chart );
                chart.setVisible( true );
            }
            else if (!isMonthFiltered && isYearFiltered) {
                ChartCreator chart = new ChartCreator("Hurtowe ceny paliwa", filteredYears);
                chart.pack( );
                RefineryUtilities.centerFrameOnScreen( chart );
                chart.setVisible( true );
            }
            else if(filteredYears.isEmpty() && filteredMonths.isEmpty()) {
                ChartCreator chart = new ChartCreator("Hurtowe ceny paliwa", data);
                chart.pack( );
                RefineryUtilities.centerFrameOnScreen( chart );
                chart.setVisible( true );
            }
            else {
                ChartCreator chart = new ChartCreator("Hurtowe ceny paliwa", dataToShow);
                chart.pack( );
                RefineryUtilities.centerFrameOnScreen( chart );
                chart.setVisible( true );
            }
        };

        //parameters of the second panel
        log.debug("Adding parameters of jp2");
        jp2.add(clearButton);
        jp2.add(jcMonths);
        jp2.add(jcYears);
        jp2.add(confirmButton);
        jp2.add(chartButton);
        jp2.setOpaque(true);

        //adding action listeners to JComboBoxes
        log.debug("Adding action listeners");
        jcMonths.addActionListener(mouseClickedMonth);
        jcYears.addActionListener(mouseClickedYear);
        clearButton.addActionListener(clearingButton);
        confirmButton.addActionListener(displayingFilteredData);
        chartButton.addActionListener(displayChart);

        //parameters of the frame
        log.debug("Setting up JFrame");
        jf.setLayout(new BorderLayout());
        jf.setResizable(true);
        jf.setLocationRelativeTo(null);
        jf.getContentPane().add(BorderLayout.CENTER, jp);
        jf.getContentPane().add(BorderLayout.SOUTH, jp2);

        //preapring JFrame to be visible
        log.debug("Setting up window to visible");
        jf.setVisible(true);
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}