import handlers.QuestionHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import util.Settings;
import util.Util;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeServerConfig{

    Logger log = Logger.getLogger(HomeServerConfig.class.getName());

    Server s;
    Settings settings = new Settings();

    public HomeServerConfig(String configPath) throws Exception{
            settings = Util.getSettings(configPath);
            s = new Server();
            s.setConnectors(getAllConnectors());
            s.setHandler(getAllServices());
    }

    public void startHomeServer() throws Exception {
        if(s != null && !s.isStarted()){
            s.start();
            s.join();
            log.log(Level.INFO, "Server Started successfully");
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

        List<ServerConnector> availableConnectors = new ArrayList<>();

        ServerConnector httpConnector = new ServerConnector(s);
        httpConnector.setPort(settings.getPort());

        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        availableConnectors.add(httpConnector);

        if(settings.getSecurePortEnabled()){
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStorePath(settings.getSslRoot());
            // The keys used (just from my simple example), TODO: Remove hardcoding of the pw. This is disgusting Paul!
            sslContextFactory.setKeyStorePassword("123456");
            sslContextFactory.setKeyManagerPassword("123456");

            ServerConnector httpsConnector = new ServerConnector(s,
                    new SslConnectionFactory(sslContextFactory, "http/1.1"),
                    new HttpConnectionFactory(https));

            httpsConnector.setPort(settings.getSecurePort());

            availableConnectors.add(httpsConnector);

        }

        return availableConnectors.toArray(new Connector[availableConnectors.size()]);

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
