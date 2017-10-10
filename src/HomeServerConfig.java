import handlers.QuestionHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

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
            throw new RuntimeException("Initialize the server before you start it");
        }
    }

    private HandlerList getAllServices(){

        // File server & Context Handler for root, also setting the index.html
        // to be the "welcome file", i.e, autolink on root addresses.
        ContextHandler resourceBase = new ContextHandler();
        resourceBase.setContextPath(".");
        ResourceHandler rHandler = new ResourceHandler();
        rHandler.setDirectoriesListed(false);
        rHandler.setWelcomeFiles(new String[]{ "index.html" });
        rHandler.setResourceBase("../resource/");
        resourceBase.setHandler(rHandler);

        // Possible servlet lists, for all servlets or custom services you want to access later.
        // Warning, it might become a little bit nested if you add to many classes.
        ServletHandler questionHandler = new ServletHandler();
        questionHandler.addServletWithMapping(QuestionHandler.class, "/question");

        // Add the ResourceHandler to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {
                resourceBase ,
                questionHandler});

        return handlers;
    }

}
