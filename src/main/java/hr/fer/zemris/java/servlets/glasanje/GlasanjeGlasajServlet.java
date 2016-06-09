package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet represents a <strong>synchronized</strong> vote action. If the
 * voting file does not exist, it will be created with all votes set to zero.
 * Vote for a band with the <tt>id</tt> specified by a parameter from the user
 * will be increased by one.
 *
 * @author Mario Bobic
 */
@WebServlet(name="glasanje-glasaj", urlPatterns={"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
	/** Serialization UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		Path path = Paths.get(fileName);
		if (!Files.exists(path)) {
			GlasanjeUtil.createFile(path, req);
		}
		
		String voteId = req.getParameter("id");
		vote(path, voteId);
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
	/**
	 * <strong>Synchronized.</strong> Creates a vote to a vote file with the
	 * specified <tt>path</tt> by updating the vote value of the specified
	 * <tt>id</tt> to plus one.
	 * 
	 * @param path path of the vote file
	 * @param id id of the band whose vote is to be increased
	 * @throws IOException if an I/O exception occurs
	 */
	// Note: Since there is only one object of every Servlet this method may also be non-static and be synchronized.
	private static synchronized void vote(Path path, String id) throws IOException {
		List<String> newLines = new ArrayList<>();
		
		try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
			lines.forEach(line -> {
				String[] attrs = line.split("\\t");
				
				if (!attrs[0].equals(id)) {
					newLines.add(line);
				} else {
					int votes = Integer.parseInt(attrs[1]);
					newLines.add(id + "\t" + (votes+1));
				}
			});
		}
		
		Files.write(path, newLines, StandardCharsets.UTF_8);
	}
	
}
