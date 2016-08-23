package rmi;

import rmi.interfaces.PeerInterface;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.MessageInterface;
import rmi.PeerInfo;
import rmi.Message;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import core.Main;

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
        Main.instance.onReceiveMsg((PeerInfo) from, (Message) msg);
        return true;
    }

    public boolean isAlive() throws RemoteException {
        return true;
    }

}
