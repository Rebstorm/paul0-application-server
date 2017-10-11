package util;

public class Settings {

    String port;
    String securePort;
    String websiteRoot;

    public Settings(String port, String securePort, String websiteRoot){

        this.port = port;
        this.securePort = securePort;
        this.websiteRoot = websiteRoot;

    }
}
