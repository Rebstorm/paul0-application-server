package util;

public class Settings {

    int port;
    int securePort;
    String websiteRoot;
    String sslRoot;

    public Settings(){

    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSecurePort(int securePort) {
        this.securePort = securePort;
    }

    public void setWebsiteRoot(String websiteRoot) {
        this.websiteRoot = websiteRoot;
    }

    public void setSslRoot(String sslRoot) {
        this.sslRoot = sslRoot;
    }

    public int getPort() {
        return port;
    }

    public int getSecurePort() {
        return securePort;
    }

    public String getWebsiteRoot() {
        return websiteRoot;
    }

    public String getSslRoot() {
        return sslRoot;
    }

}

