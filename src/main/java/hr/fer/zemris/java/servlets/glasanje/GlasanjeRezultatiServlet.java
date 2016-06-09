package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.glasanje.GlasanjeUtil.BandInfo;

/**
 * This servlet represents a voting results page which obtains the vote results
 * and a band list from a file. The obtained band list is then processed to
 * leave out only the winners that will be used by the
 * <tt>/WEB-INF/pages/glasanjeRez.jsp</tt> file.
 *
 * @author Mario Bobic
 */
@WebServlet(name="glasanje-rezultati", urlPatterns={"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
	/** Serialization UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BandInfo> bandList = GlasanjeUtil.getBandList(req, resp);
		bandList.sort(BandInfo.BY_VOTES);
		
		List<BandInfo> winners = getWinners(bandList);
		
		req.setAttribute("bandList", bandList);
		req.setAttribute("winners", winners);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	/**
	 * Returns a <tt>List</tt> of {@linkplain BandInfo} objects containing only
	 * winners of the pole. The maximum number of votes a band has is calculated
	 * based on the votes in <tt>bandList</tt> list, which <strong>must</strong>
	 * be sorted before passing it to this method.
	 * 
	 * @param bandList band list, <strong>must</strong> be sorted
	 * @return a list containing winners of the pole
	 */
	private static List<BandInfo> getWinners(List<BandInfo> bandList) {
		int maxVotes = bandList.isEmpty() ? 0 : bandList.get(0).getVotes();
		
		List<BandInfo> winners = new ArrayList<>();
		winners.addAll(bandList);
		winners.removeIf(bandInfo -> bandInfo.getVotes() < maxVotes);
		
		return winners;
	}
	
}
