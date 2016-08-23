package core;

import rmi.interfaces.PeerInterface;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.MessageInterface;
import rmi.PeerInfo;
import rmi.Message;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;

public class Client {

    protected PeerInterface stub;
    protected Exception lastException;

    public Client(PeerInterface stub){
        this.stub = stub;
        this.lastException = null;
    }

    public String getUUID(){

        try {

            if(this.stub.isAlive()){
                String uuid = this.stub.getUUID();
                return uuid;
            }

        } catch (Exception e) {
            this.lastException = e;
            return null;
        }

        return null;
    }

    public PeerInfo getInfo(){

        try {

            if(this.stub.isAlive()){
                PeerInfoInterface info = this.stub.getInfo();
                return (PeerInfo) info;
            }

        } catch (Exception e) {
            this.lastException = e;
            return null;
        }

        return null;
    }

    public boolean send(PeerInfoInterface from, MessageInterface msg){

        try {

            if(this.stub.isAlive()){
                return this.stub.send(from, msg);
            }

        } catch (Exception e) {
            this.lastException = e;
            return false;
        }

        return false;
    }

    public boolean send(PeerInfoInterface from, String msg){
        return this.send(from, new Message(msg));
    }


    public boolean isAlive(){

        try {
            return this.stub.isAlive();
        } catch (Exception e) {
            this.lastException = e;
            return false;
        }
    }

    public Exception getLastException(){
        return this.lastException;
    }

    public static Client fetchPeer(String server, int port){

        PeerInterface stub = null;

        try {
            Registry registry = LocateRegistry.getRegistry(server, port);
            stub = (PeerInterface) registry.lookup("Peer");
            if(stub.isAlive()) return new Client(stub);

        } catch (Exception e) {
            System.out.println("Erro ao buscar o Peer "+server+":"+String.valueOf(port));
            return null;
        }

        return null;
    }
}
