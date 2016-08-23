package core;

import rmi.Peer;
import rmi.PeerInfo;
import rmi.Message;
import core.Server;
import core.Client;
import core.ChatHistory;
import core.view.MainWindow;
import core.view.UsersList;
import core.services.BroadcastServer;
import core.services.BroadcastClient;
import core.services.BroadcastClientCallback;
import java.util.Vector;

public class Main implements BroadcastClientCallback{

    public static Main instance;

    public static final int RMI_PORT = 1099;
    public static final int BROADCAST_PORT = 1098;

    public static Vector<Client> users;
    public static Vector<ChatHistory> history;

    public static PeerInfo selfInfo;
    public static Server rmiServer;
    public static BroadcastServer bcServer;
    public static BroadcastClient bcClient;

    public static void main(String[] args) {

        Main.users = new Vector<Client>();
        Main.history = new Vector<ChatHistory>();

        Main.selfInfo = new PeerInfo("192.168.2.4", "Edwino Stein");

        System.out.println("IP deste Peer: " + Main.selfInfo.getIp());
        System.out.println("UUID deste Peer: " + Main.selfInfo.getUUID());

        try{
            Main.rmiServer = new Server(Main.selfInfo, Main.RMI_PORT);
            Main.rmiServer.run();
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return;
        }

        try{
            Main.bcServer = new BroadcastServer(Main.selfInfo, Main.BROADCAST_PORT);
            Main.bcServer.start(3);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        Main.instance = new Main();
        Main.bcClient = new BroadcastClient(Main.instance, Main.BROADCAST_PORT);
        Main.bcClient.start();

        //Constroi a GUI
        MainWindow.getInstance().build();
    }

    public void onReceiveBroadcast(String uuid, String ip){

        // if(Main.selfInfo.getUUID().equals(uuid)) return;

        boolean founded = false;
        for(int i = 0; i < Main.users.size(); i++){
            Client p = Main.users.get(i);
            if(p.getInfo().getUUID().equals(uuid)){
                founded = true;
                break;
            }
        }

        if(!founded){
            Client peer = Client.fetchPeer(ip, Main.RMI_PORT);
            if(peer != null){
                Main.users.addElement(peer);
                Main.history.addElement(new ChatHistory(peer.getInfo()));
                System.out.println("Novo peer detectado: " + peer.getInfo().getUserName() + " (" + peer.getInfo().getUUID() + ", "+ ip +")");
                peer.send(Main.selfInfo, "teste");
            }

        }
    }

    public void onReceiveMsg(PeerInfo from, Message msg){

        Client p = null;
        int i;

        for(i = 0; i < Main.users.size(); i++){
            p = Main.users.get(i);
            if(p.getInfo().getUUID().equals(from.getUUID())) break;
        }

        if(p != null){
            Main.history.get(i).push(msg, false);
            UsersList.getInstance().notifyNewMsg(i);
        }
        else{
            Client peer = Client.fetchPeer(from.getIp(), Main.RMI_PORT);
            if(peer != null){
                Main.users.addElement(peer);
                Main.history.addElement(new ChatHistory(from));
                System.out.println("Novo peer detectado: " + from.getUserName() + " (" + from.getUUID() + ", "+ from.getIp() +")");
            }
        }
    }

    public static boolean sendMessage(int userIndex, String msg){

        Client peer = Main.users.get(userIndex);
        Message m = new Message(msg);

        if(peer.send(Main.selfInfo, m)){
            Main.history.get(userIndex).push(m, true);
            return true;
        }
        else{
            return false;
        }
    }
}
