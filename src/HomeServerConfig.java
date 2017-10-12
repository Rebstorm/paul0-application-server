import handlers.QuestionHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import util.Settings;
import util.Util;

import javax.net.ssl.SSLContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeServerConfig{

    Logger log = Logger.getLogger(HomeServerConfig.class.getName());

    Server s;
    Settings settings = new Settings();

    public HomeServerConfig() throws Exception{
        try {
            settings = Util.getSettings();


            log.log(Level.INFO, "Server Started and loaded from system settings.");
        } catch(Exception e){
            log.log(Level.SEVERE, "Cannot load the system settings. " + e );
            settings.setPort(8080);
            settings.setSecurePort(443);
            settings.setWebsiteRoot("../resource/new-websiter");
        } finally {
            s = new Server();
            s.setConnectors(getAllConnectors());
            s.setHandler(getAllServices());
        }



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


    private Connector[] getAllConnectors(){

        ServerConnector httpsConnector = new ServerConnector(s);
        httpsConnector.setPort(settings.getSecurePort());

        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("path/to/keychain");


        Connector[] connectors = new Connector[]{
                httpsConnector,

        };

        return connectors;

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
