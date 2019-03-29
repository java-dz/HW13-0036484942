package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The {@code AppInfoListener} class implements the
 * {@linkplain ServletContextListener} by defining non-empty body only for the
 * {@linkplain #contextInitialized(ServletContextEvent)} method.
 *
 * @author Mario Bobic
 */
@WebListener
public class AppInfoListener implements ServletContextListener {

    /**
     * Registers, or sets the start time attribute of the server.
     *
     * @param sce a servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
