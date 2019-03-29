package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet validates parameters <tt>a</tt> and <tt>b</tt> specified by the
 * user in the following way:
 * <ul>
 * <li>If parameter <strong>a</strong> is not parsable as an integer or missing,
 * it is set to <strong>0</strong>.
 * <li>If parameter <strong>b</strong> is not parsable as an integer or missing,
 * it is set to <strong>360</strong>.
 * <li>Else if parameter <strong>a</strong> is greater than parameter
 * <strong>b</strong>, the values are swapped.
 * <li>Finally, if parameter <strong>b</strong> is greater than <strong>a +
 * 720</strong>, <strong>b</strong> is set to <strong>a + 720</strong>.
 * </ul>
 *
 * @author Mario Bobic
 */
@WebServlet(name="trigonometric", urlPatterns={"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a = 0;
        int b = 360;

        String strA = req.getParameter("a");
        String strB = req.getParameter("b");

        try {
            a = Integer.parseInt(strA);
        } catch (NumberFormatException ignorable) {}

        try {
            b = Integer.parseInt(strB);
        } catch (NumberFormatException ignorable) {}

        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }

        if (b > a + 720) {
            b = a + 720;
        }

        // Calculate and serve in lists as Strings
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.##########", otherSymbols);

        List<String[]> trigList = new ArrayList<>();

        for (int i = a; i <= b; i++) {
            double rads = Math.toRadians(i);

            String n = Integer.toString(i);
            String sin = df.format(Math.sin(rads));
            String cos = df.format(Math.cos(rads));

            // separate results with '&'
            trigList.add(new String[]{n, sin, cos});
        }

        req.setAttribute("trigList", trigList);

        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }

}
