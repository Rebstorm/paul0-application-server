package util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    static Logger log = Logger.getLogger(Util.class.getName());

    public static String getRunPath() throws Exception {
        return System.getProperty("user.dir");
    }

    public static Settings getSettings() throws Exception{

        Settings setting = new Settings();
        try {

            File inputFile = new File("../resource/settings.xml");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(inputFile);


            for (int i = 0; i < document.getElementsByTagName("settings").getLength(); i++) {

                // integers, careful to load string.
                setting.setPort(Integer.valueOf(document.getElementsByTagName("port").item(i).getFirstChild().getNodeValue()));
                setting.setSecurePort(Integer.valueOf(document.getElementsByTagName("secureport").item(i).getFirstChild().getNodeValue()));
                // Website & String vals
                setting.setWebsiteRoot(document.getElementsByTagName("htmlroot").item(i).getFirstChild().getNodeValue());
                setting.setSslRoot(document.getElementsByTagName("sslcert").item(i).getFirstChild().getNodeValue());
            }

            return setting;

        } catch(Exception e){
            log.log(Level.SEVERE, "Cannot load the system settings. " + e );
            setting.setPort(8080);
            setting.setSecurePort(8081);
            setting.setWebsiteRoot("../resource/new-websiter");
            setting.setSslRoot("butts");

            return setting;
        }


    }
}
