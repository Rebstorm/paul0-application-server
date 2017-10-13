package util;

import org.w3c.dom.Element;

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

    public static Settings getSettings(){

        Settings setting = new Settings();
        String appendageDev = "";
        try {

            File inputFile = new File("../resource/settings.xml");

            if(!inputFile.exists()){
                inputFile = new File("../paul0/resource/settings.xml");
                appendageDev = "../paul0/";
            }

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(inputFile);


            for (int i = 0; i < document.getElementsByTagName("settings").getLength(); i++) {
                // integers, careful to load string.
                setting.setPort(Integer.valueOf(document.getElementsByTagName("port").item(i).getFirstChild().getNodeValue()));

                // Secure settings stuff if you dont want to run a server above it.
                Element secureSettings = (Element)document.getElementsByTagName("secureport").item(i);
                setting.setSecurePort(Integer.valueOf(document.getElementsByTagName("secureport").item(i).getFirstChild().getNodeValue()));
                setting.setSecurePortEnabled(Boolean.valueOf(secureSettings.getAttribute("enabled")));

                // Website & String vals
                setting.setWebsiteRoot(appendageDev + document.getElementsByTagName("htmlroot").item(i).getFirstChild().getNodeValue());
                setting.setSslRoot(appendageDev + document.getElementsByTagName("sslcert").item(i).getFirstChild().getNodeValue());
            }

            return setting;

        } catch(Exception e){
            log.log(Level.SEVERE, "Cannot load the system settings. " + e );
            setting.setPort(9000);
            setting.setSecurePort(9001);
            setting.setWebsiteRoot("/new-websiter");
            setting.setSslRoot("butts");
            setting.setSecurePortEnabled(false);

            return setting;
        }


    }
}
