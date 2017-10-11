package util;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Util {

    public static String getRunPath() throws Exception {
        return System.getProperty("user.dir");
    }

    public static Settings getSettings() throws Exception{

        Settings setting = new Settings(null, null, null);


        File inputFile = new File(getRunPath() + "settings.xml");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        


        return setting;
    }
}
