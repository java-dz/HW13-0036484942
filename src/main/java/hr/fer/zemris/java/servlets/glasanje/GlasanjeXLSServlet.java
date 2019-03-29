package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servlets.glasanje.GlasanjeUtil.BandInfo;

/**
 * This servlet creates an XLS file with voting results. Since the voting
 * results are generated dynamically, this file keeps track of the current
 * results and is generated just in time it is requested.
 *
 * @author Mario Bobic
 */
@WebServlet(name="glasanje-xls", urlPatterns={"/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/octet-stream"); // application/vnd.ms-excel
        resp.setHeader("Content-Disposition", "attachment; filename=\"vote_results.xls\"");

        List<BandInfo> bandList = GlasanjeUtil.getBandList(req, resp);
        bandList.sort(BandInfo.BY_VOTES);

        HSSFWorkbook xls = createXLSFile(bandList);
        xls.write(resp.getOutputStream());
        xls.close();
    }

    /**
     * Creates an instance of {@code HSSFWorkbook} with one sheet, having
     * <tt>4</tt> columns and <tt>bandList.size()</tt> rows.
     * <p>
     * The columns are generated in the following way:
     * <ol>
     * <li>ID (unique ID of the band)
     * <li>Name (name of the band)
     * <li>Votes (number of votes the band gained)
     * <li>Representative song (link to one representative song of the band)
     * </ol>
     *
     * @param bandList band list
     * @return a <tt>HSSFWorkbook</tt> object
     */
    public HSSFWorkbook createXLSFile(List<BandInfo> bandList) {
        HSSFWorkbook hwb = new HSSFWorkbook();

        HSSFSheet page = hwb.createSheet("results");

        HSSFRow header = page.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Votes");
        header.createCell(3).setCellValue("Representative song");

        for (int i = 0, n = bandList.size(); i < n; i++) {
            HSSFRow row = page.createRow(i+1);

            BandInfo bandInfo = bandList.get(i);

            row.createCell(0).setCellValue(bandInfo.id);
            row.createCell(1).setCellValue(bandInfo.name);
            row.createCell(2).setCellValue(bandInfo.getVotes());
            row.createCell(3).setCellValue(bandInfo.songLink);
        }

        return hwb;
    }
}
