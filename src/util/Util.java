package util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Util {

    public static String getRunPath() throws Exception {
        return System.getProperty("user.dir");
    }

    public static Settings getSettings() throws Exception{

        Settings setting = new Settings();

        File inputFile = new File("../resource/settings.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(inputFile);

        for(int i = 0; i < document.getElementsByTagName("settings").getLength(); i++){
            setting.port = Integer.valueOf(document.getElementsByTagName("port").item(i).getFirstChild().getNodeValue());
            setting.securePort = Integer.valueOf(document.getElementsByTagName("secureport").item(i).getFirstChild().getNodeValue());
            setting.websiteRoot = document.getElementsByTagName("htmlroot").item(i).getFirstChild().getNodeValue();

        }

        return setting;
    }
}
