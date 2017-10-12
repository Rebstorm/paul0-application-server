import com.sun.jndi.toolkit.url.Uri;
import handlers.QuestionHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.webapp.WebAppContext;
import util.Settings;
import util.Util;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.net.URI;

public class HomeServerConfig{

    Server s;
    Settings settings = new Settings();

    public HomeServerConfig() throws Exception{
        try {
            settings = Util.getSettings();
            System.out.println("[OK] Settings loaded.");
        } catch(Exception e){
            System.out.println("[FATAL ERROR] Error reading the settings file, reverting to normal dev settings");
            settings.setPort(8080);
            settings.setSecurePort(443);
            settings.setWebsiteRoot("../resource/new-websiter");
        }

        s = new Server(settings.getPort());
        s.setHandler(getAllServices());

    }

    public void startHomeServer() throws Exception {
        if(s != null && !s.isStarted()){
            s.start();
            s.join();
        } else {
            throw new RuntimeException("Initialize the server before you start it, genious");
        }
    }

    private HandlerList getAllServices() throws Exception{
        // File server & Context Handler for root, also setting the index.html
        // to be the "welcome file", i.e, autolink on root addresses.
        ContextHandler rootContext = new ContextHandler();
        rootContext.setContextPath("/*");
        rootContext.setHandler(getResourceHandlers());

        // Possible servlet lists, for all servlets or custom services you want to access later.
        // Warning, it might become a little bit nested if you add to many classes.
        ServletHandler questionHandler = new ServletHandler();
        questionHandler.addServletWithMapping(QuestionHandler.class, "/question");

        // Add the ResourceHandler to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {
                rootContext ,
                questionHandler,
        });

        return handlers;
    }

    private ResourceHandler getResourceHandlers(){
        ResourceHandler rHandler = new ResourceHandler();
        rHandler.setDirectoriesListed(false);
        rHandler.setWelcomeFiles(new String[]{ "index.html" });
        rHandler.setResourceBase(settings.getWebsiteRoot());
        rHandler.setDirAllowed(false);

        return rHandler;
    }

}
