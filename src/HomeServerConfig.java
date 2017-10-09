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

        ContextHandler resourceBase = new ContextHandler();
        resourceBase.setContextPath("/");
        ResourceHandler rHandler = new ResourceHandler();
        rHandler.setDirectoriesListed(false);
        rHandler.setWelcomeFiles(new String[]{ "index.html" });
        rHandler.setResourceBase("resource/");
        resourceBase.setHandler(rHandler);


        ServletHandler questionHandler = new ServletHandler();
        questionHandler.addServletWithMapping(QuestionHandler.class, "/*");


        // Add the ResourceHandler to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resourceBase , questionHandler});
        s.setHandler(handlers);
    }

    public void startHomeServer() throws Exception {

        if(s != null){
            s.start();
            s.join();
        } else {
            throw new RuntimeException("Initialize the server before you start it");
        }
    }
}
