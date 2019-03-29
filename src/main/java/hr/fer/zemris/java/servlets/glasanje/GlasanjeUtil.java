package hr.fer.zemris.java.servlets.glasanje;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is a utility class used by most of the <tt>Glasanje</tt> Servlets.
 * It defines methods for handling files and has classes commonly used for
 * gathering information on bands and vote results.
 *
 * @author Mario Bobic
 */
public class GlasanjeUtil {

    /**
     * Disables instantiation.
     */
    private GlasanjeUtil() {
    }

    /**
     * Creates a new vote results file with the specified <tt>path</tt>. Vote
     * results are all set to <tt>0</tt>, where the IDs are separated from
     * results with a tab. Attribute names are fetched from the <tt>req</tt>.
     *
     * @param path path of the file to be created
     * @param req request from which attribute names are fetched
     * @throws IOException if an I/O exception occurs
     */
    public static synchronized void createFile(Path path, HttpServletRequest req) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            Enumeration<String> en = req.getAttributeNames();
            while (en.hasMoreElements()) {
                String id = en.nextElement();
                writer.write(id + "\t" + "0\n");
            }
        }
    }

    /**
     * Returns a <tt>List</tt> of {@linkplain BandInfo} objects loaded from the
     * <tt>/WEB-INF/glasanje-definicija.txt</tt> file. Lines are parsed and a
     * new <tt>BandInfo</tt> object is created for each line.
     * <p>
     * This method also loads votes from
     * <tt>/WEB-INF/glasanje-rezultati.txt</tt> file.
     * <p>
     * Throws {@linkplain IllegalArgumentException} if any line in the file
     * contains not exactly three attributes separated by a tab symbol.
     *
     * @param req a HTTP servlet request for getting file path
     * @param resp a HTTP servlet response for forwarding in case of error
     * @return a list containing band info
     * @throws IllegalArgumentException if any line in the file is invalid
     * @throws IOException if an I/O exception occurs
     * @throws ServletException if a servlet exception occurs
     */
    public static List<BandInfo> getBandList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            req.setAttribute("error", "Server error. File glasanje-definicija.txt does not exist.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return null;
        }

        Map<Integer, Integer> voteResults = getVoteResults(req);

        List<BandInfo> bandList = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                String[] attributes = line.split("\\t");
                if (attributes.length != 3) {
                    throw new IllegalArgumentException("Line [" + line + "] does not contain 3 attributes.");
                }

                int id = Integer.parseInt(attributes[0]);
                String name = attributes[1];
                String songLink = attributes[2];
                int votes = voteResults.get(id);
                bandList.add(new BandInfo(id, name, songLink, votes));
            });
        }

        return bandList;
    }

    /**
     * Returns a <tt>Map</tt> of vote results loaded from the
     * <tt>/WEB-INF/glasanje-rezultati.txt</tt> file. Lines are parsed
     * attributes <tt>id</tt> and <tt>votes</tt> are added to the map for each
     * line.
     *
     * @param req a HTTP servlet request for getting file path
     * @return a map containing vote results
     * @throws IOException if an I/O exception occurs
     */
    private static Map<Integer, Integer> getVoteResults(HttpServletRequest req) throws IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            createFile(path, req);
        }

        Map<Integer, Integer> voteResults = new HashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                String[] attrs = line.split("\\t");

                int id = Integer.parseInt(attrs[0]);
                int votes = Integer.parseInt(attrs[1]);
                voteResults.put(id, votes);
            });
        }

        return voteResults;
    }

    /**
     * This class represents info of one band, holding information on the band's
     * unique ID number, band name, a link to one representative song of that
     * band and the number of votes the band has gained.
     *
     * @author Mario Bobic
     */
    public static class BandInfo {
        /** Comparator by unique ID numbers. */
        public static final Comparator<BandInfo> BY_ID =
                (band1, band2) -> Integer.compare(band1.id, band2.id);
        /** Comparator by number of votes, <strong>descending</strong>. */
        public static final Comparator<BandInfo> BY_VOTES =
                (band1, band2) -> -Integer.compare(band1.votes, band2.votes);

        /** Unique ID of the band. */
        public final int id;
        /** Name of the band. */
        public final String name;
        /** Link to one representative song of the band. */
        public final String songLink;

        /** Number of votes the band gained. */
        private int votes;

        /**
         * Constructs an instance of {@code BandInfo} with the specified
         * arguments.
         *
         * @param id unique ID of the band
         * @param name name of the band
         * @param songLink link to one representative song of the band
         */
        public BandInfo(int id, String name, String songLink) {
            this(id, name, songLink, 0);
        }

        /**
         * Constructs an instance of {@code BandInfo} with the specified
         * arguments.
         *
         * @param id unique ID of the band
         * @param name name of the band
         * @param songLink link to one representative song of the band
         * @param votes number of votes the band gained
         */
        public BandInfo(int id, String name, String songLink, int votes) {
            this.id = id;
            this.name = name;
            this.songLink = songLink;
            this.votes = votes;
        }

        /**
         * Returns the unique ID of the band.
         *
         * @return the unique ID of the band
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the name of the band.
         *
         * @return the name of the band
         */
        public String getName() {
            return name;
        }

        /**
         * Returns a link to one representative song of the band.
         *
         * @return a link to one representative song of the band
         */
        public String getSongLink() {
            return songLink;
        }

        /**
         * Returns the number of votes this band has gained.
         *
         * @return the number of votes this band has gained
         */
        public int getVotes() {
            return votes;
        }

        /**
         * Increases the number of votes for this band by one.
         */
        public void vote() {
            votes++;
        }
    }

}
