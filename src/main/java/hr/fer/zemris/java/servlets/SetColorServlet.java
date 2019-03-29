package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet sets the background color (<tt>bgcolor</tt> attribute) of the
 * session a color that the user has selected. Forwards the request to
 * <tt>/colors.jsp</tt> afterwards.
 *
 * @author Mario Bobic
 */
@WebServlet(name="setcolor", urlPatterns={"/setcolor"})
public class SetColorServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String color = req.getParameter("color");
        req.getSession().setAttribute("bgcolor", color);

        req.getRequestDispatcher("/colors.jsp").forward(req, resp);
    }
}
