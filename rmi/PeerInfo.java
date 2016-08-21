package rmi;

import java.util.UUID;
import rmi.interfaces.PeerInfoInterface;

public class PeerInfo implements PeerInfoInterface {

    private static final long serialVersionUID = 1L;

    protected String uuid;
    protected String ip;
    protected String userName;

    public PeerInfo(String ip, String userName){
        this.uuid = PeerInfo.generateUUID();
        this.ip = ip;
        this.userName = userName;
    }

    public String getUUID(){
        return this.uuid;
    }

    public String getIp(){
        return this.ip;
    }

    public String getUserName(){
        return this.userName;
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
