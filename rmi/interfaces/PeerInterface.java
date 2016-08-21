package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.MessageInterface;

public interface PeerInterface extends Remote {
    public String getUUID() throws RemoteException;
    public PeerInfoInterface getInfo() throws RemoteException;
    public boolean send(PeerInfoInterface from, MessageInterface msg) throws RemoteException;
    public boolean isAlive() throws RemoteException;
}
