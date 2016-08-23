package core;

import rmi.interfaces.PeerInterface;
import rmi.Peer;
import rmi.PeerInfo;
import rmi.Message;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;

public class Server {

    protected int port;
    protected Peer peer;
    protected PeerInfo info;
    protected Registry registry;

    public Server(PeerInfo info, int port){

        this.info = info;
        this.port = port;
        this.registry = null;

        try {
            this.peer = new Peer(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(){

        try {

            UnicastRemoteObject.unexportObject(this.peer, true);
            PeerInterface stub = (PeerInterface) UnicastRemoteObject.exportObject(this.peer, this.port);
            this.registry = LocateRegistry.createRegistry(this.port);
            this.registry.bind("Peer", stub);

            System.out.println("O servidor RMI está em execução!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
