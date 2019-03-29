package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.glasanje.GlasanjeUtil.BandInfo;

/**
 * This servlet represents a voting start-page which obtains the band list from
 * a file, sets the <tt>bandList</tt> attribute to that list and forwards the
 * request to <tt>/WEB-INF/pages/glasanjeIndex.jsp</tt>.
 *
 * @author Mario Bobic
 */
@WebServlet(name="glasanje", urlPatterns={"/glasanje"})
public class GlasanjeServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BandInfo> bandList = GlasanjeUtil.getBandList(req, resp);

        req.setAttribute("bandList", bandList);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }

}
