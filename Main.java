import rmi.PeerInfo;
import java.rmi.RemoteException;
import core.Server;

public class Main {

    public static void main(String[] args) throws Exception {

        System.setProperty("java.rmi.server.hostname", "192.168.2.6");

        PeerInfo info = new PeerInfo("192.168.2.6", "Edwino Stein");
        Server svr;

        try{
            svr = new Server(info, 1099);
            svr.run();
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
