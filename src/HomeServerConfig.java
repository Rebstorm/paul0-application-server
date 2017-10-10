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
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

public class HomeServerConfig{

    Server s;

    public HomeServerConfig() throws Exception{
        s = new Server(8080);
        s.setHandler(getAllServices());
    }

    public void startHomeServer() throws Exception {

        if(s != null){
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
        rootContext.setContextPath(".");
        rootContext.setHandler(getResourceHandlers());

        // Get error handlers & set it!
        rootContext.setErrorHandler(getErrorHandlers());

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

    private ErrorHandler getErrorHandlers() throws Exception {
        ErrorHandler e = new ErrorHandler();

        // This be the error handler
        ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
        errorHandler.addErrorPage(404, "../resource/new-website/404.html");
        e.addBean(errorHandler);
        e.setServer(s);
        return e;
    }

    private ResourceHandler getResourceHandlers(){
        ResourceHandler rHandler = new ResourceHandler();
        rHandler.setDirectoriesListed(false);
        rHandler.setWelcomeFiles(new String[]{ "index.html" });
        rHandler.setResourceBase("../resource/new-websiter");
        rHandler.setDirAllowed(false);

        return rHandler;
    }

}
