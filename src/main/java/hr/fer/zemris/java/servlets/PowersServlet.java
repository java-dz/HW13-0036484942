package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet creates an XLS file containing numbers and their n-th power.
 * <p>
 * The numbers and power are specified through <tt>URL parameters</tt>, where
 * the range of the number is [-100, 100] and range of the power is [1, 5].
 * <p>
 * If any of the range is invalid, the user is redirected to an error page.
 *
 * @author Mario Bobic
 */
@WebServlet(name="powers", urlPatterns={"/powers"})
public class PowersServlet extends HttpServlet {
	/** Serialization UID. */
	private static final long serialVersionUID = 1L;
	
	/** Message about proper parameter format and range. */
	private static final String PARAMS_MESSAGE =
			"Parameters a and b should be in range [-100, 100] and n in range [1, 5]";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = 0;
		int b = 0;
		int n = 0;
		
		String strA = req.getParameter("a");
		String strB = req.getParameter("b");
		String strN = req.getParameter("n");
		
		/* Initialize and search for errors. */
		boolean error = false;
		String message = "";
		
		try {
			a = Integer.parseInt(strA);
			b = Integer.parseInt(strB);
			n = Integer.parseInt(strN);
		} catch (NumberFormatException e) {
			error = true;
			message = "One or more parameters can not be parsed as integer. " + PARAMS_MESSAGE;
		}
		
		if (!error) {
			if ((a < -100 || a > 100) || (b < -100 || b > 100) || (n < 1 || n > 5)) {
				error = true;
				message = "Invalid parameters. " + PARAMS_MESSAGE;
			} else if (a > b) {
				error = true;
				message = "Parameter a must be smaller than parameter b.";
			}
		}
		
		if (error) {
			req.setAttribute("error", message);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		/* Start doing work. */
		resp.setContentType("application/octet-stream"); // application/vnd.ms-excel
		resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
		
		HSSFWorkbook xls = createXLSFile(a, b, n);
		xls.write(resp.getOutputStream());
		xls.close();
	}
	
	/**
	 * Creates an instance of {@code HSSFWorkbook} with an amount of pages
	 * specified by the <tt>pages</tt> parameter, <tt>end-start+1</tt> rows and
	 * <tt>2</tt> columns on each page. Each page represents a power to which
	 * numbers will be raised to on that specific page.
	 * 
	 * @param start the starting number
	 * @param end the ending number
	 * @param pages number of pages to be created
	 * @return a <tt>HSSFWorkbook</tt> object
	 */
	public HSSFWorkbook createXLSFile(int start, int end, int pages) {
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 0; i < pages; i++) {
			HSSFSheet page = hwb.createSheet("page " + (i + 1));

			int rowNumber = 0;
			for (int number = start; number <= end; number++, rowNumber++) {
				HSSFRow row = page.createRow(rowNumber);
				row.createCell(0).setCellValue(number);
				row.createCell(1).setCellValue(Math.pow(number, i + 1));
			}
		}

		return hwb;
	}
}