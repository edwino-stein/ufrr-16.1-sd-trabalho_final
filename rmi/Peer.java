package rmi;

import rmi.interfaces.PeerInterface;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.MessageInterface;
import rmi.PeerInfo;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Peer extends UnicastRemoteObject implements PeerInterface {

    private static final long serialVersionUID = 1L;

    protected PeerInfo info;

    public Peer(PeerInfo info) throws RemoteException {
        super();
        this.info = info;
    }

    public String getUUID() throws RemoteException {
        return this.info.getUUID();
    }

    public PeerInfoInterface getInfo() throws RemoteException{
        return (PeerInfoInterface) this.info;
    }

    public boolean send(PeerInfoInterface from, MessageInterface msg) throws RemoteException{
        System.out.println(from.getUserName()+": "+msg.getBody()+" ("+msg.getDate()+")");
        return true;
    }

    public boolean isAlive() throws RemoteException {
        return true;
    }

}
