public class HomeServer {
    public static void main(String[] args) throws Exception{
        HomeServerConfig serverConfig = new HomeServerConfig(args[0]);
        serverConfig.startHomeServer();
    }
}
