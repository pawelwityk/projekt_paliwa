import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.time.LocalDate;
import java.util.List;

public class ChartCreator extends ApplicationFrame {

    public ChartCreator(String applicationTitle, List<Object[]> data) {
        super(applicationTitle);
        JFreeChart chart = ChartFactory.createLineChart("Zmienność ceny hurtowej benzyny w zadanym czasie",
                "Data", "Cena hurtowa paliwa", createDataSet(data), PlotOrientation.VERTICAL,
                true, true, false);
        CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataSet(List<Object[]> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] datum: data) {
            dataset.addValue(Integer.valueOf(datum[1].toString().replaceAll("\\s+","").replaceFirst(",00", "")),
                    "Cena hurtowa", LocalDate.parse(datum[0].toString()));
        }
        return dataset;
    }
}