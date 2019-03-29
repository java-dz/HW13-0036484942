package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * This servlet creates a PNG image of a pie chart using the
 * {@linkplain JFreeChart}. The chart is predefined with OS usages of three
 * operating systems:
 * <ul>
 * <li>Linux
 * <li>Mac
 * <li>Windows
 * </ul>
 *
 * @author Mario Bobic
 */
@WebServlet(name="reportImage", urlPatterns={"/reportImage"})
public class ReportImageServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");

        // Create dataset
        PieDataset dataset = createDataset();
        // Create a chart based on the dataset
        JFreeChart chart = createChart(dataset, "OS Usage");

        // Convert to an image and encode it
        byte[] image = ChartUtilities.encodeAsPNG(chart.createBufferedImage(320, 240));
        resp.getOutputStream().write(image);
    }

    /**
     * Creates a simple dataset.
     *
     * @return pie dataset
     */
    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        return result;
    }

    /**
     * Creates an instance of {@code JFreeChart} whose dataset is specified by
     * the {@code PieDataset}.
     *
     * @param dataset pie dataset
     * @param title chart title
     * @return an instance of JFreeChart
     */
    private JFreeChart createChart(PieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart3D(
                title,    // chart title
                dataset,// data
                true,    // include legend
                true,    // include tooltips
                false    // exclude urls
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }

}
