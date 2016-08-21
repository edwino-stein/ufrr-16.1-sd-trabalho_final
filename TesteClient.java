import rmi.interfaces.PeerInterface;
import rmi.PeerInfo;
import rmi.Message;

import core.Client;

public class TesteClient {

    public static void main(String[] args) throws Exception {

        Client peer = Client.fetchPeer("192.168.2.6", 1099);

        PeerInfo peerInfo = peer.getInfo();
        if(peerInfo == null){
            peer.getLastException().printStackTrace();
            return;
        }

        System.out.println("Conectado com: " + peerInfo.getUserName() + " ("+peerInfo.getIp()+": "+peerInfo.getUUID()+")");
        PeerInfo info = new PeerInfo("192.168.2.6", "Edwino");

        peer.send(info, new Message("Olá marilene"));
        peer.send(info, "Olá marilene 2");
    }
}
