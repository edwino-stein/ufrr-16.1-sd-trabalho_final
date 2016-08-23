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
    public static final int BROADCAST_TIMEOUT = 5000;

    public static Vector<Client> users;
    public static Vector<ChatHistory> history;

    public static PeerInfo selfInfo;
    public static Server rmiServer;
    public static BroadcastServer bcServer;
    public static BroadcastClient bcClient;

    public static void main(String[] args) {

        Main.selfInfo = new PeerInfo("192.168.2.4", "Edwino Stein");

        Main.users = new Vector<Client>();
        Main.history = new Vector<ChatHistory>();

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

        //Constroi a GUI
        MainWindow.getInstance().build();

        Main.instance = new Main();
        Main.bcClient = new BroadcastClient(Main.instance, Main.BROADCAST_PORT);
        Main.bcClient.start();

        while(true){

            for(int i = 0; i < Main.users.size(); i++){

                Client p = Main.users.get(i);

                if(!p.isAlive()){
                    UsersList.getInstance().rmItem(i);
                    Main.users.remove(i);
                    Main.history.remove(i);
                }

            }

            Main.bcServer.start(3);
            try{Thread.sleep(Main.BROADCAST_TIMEOUT);} catch (Exception e){}
        }
    }

    public void onReceiveBroadcast(String uuid, String ip){

        boolean founded = false;
        for(int i = 0; i < Main.users.size(); i++){
            Client p = Main.users.get(i);
            if(p.getInfo().getUUID().equals(uuid)){
                founded = true;
                break;
            }
        }

        if(!founded){

            if(Main.selfInfo.getUUID().equals(uuid)){
                Client peer = Client.fetchPeer(ip, Main.RMI_PORT);
                if(peer != null){
                    Main.users.addElement(peer);
                    PeerInfo info = new PeerInfo(ip, "Teste de Echo");
                    UsersList.getInstance().addItem(info.getUserName());
                    Main.history.addElement(new ChatHistory(info));
                    System.out.println("Peer para teste de echo detectado.");
                    peer.send(Main.selfInfo, "Olá, isto é um teste de comunição.");
                    UsersList.getInstance().updateSelection();
                }
            }
            else{
                Client peer = Client.fetchPeer(ip, Main.RMI_PORT);
                if(peer != null){
                    Main.users.addElement(peer);
                    UsersList.getInstance().addItem(peer.getInfo().getUserName());
                    Main.history.addElement(new ChatHistory(peer.getInfo()));
                    System.out.println("Novo peer detectado: " + peer.getInfo().getUserName() + " (" + peer.getInfo().getUUID() + ", "+ ip +")");
                    UsersList.getInstance().updateSelection();
                }
            }
        }
    }

    public void onReceiveMsg(PeerInfo from, Message msg){

        Client p = null;
        int i;

        System.out.println("Recebe: "+from.getUserName()+" ("+from.getUUID()+", "+from.getIp()+"):\n\t"+msg.getBody()+" ("+msg.getDate()+")\n");

        for(i = 0; i < Main.users.size(); i++){
            p = Main.users.get(i);
            if(p.getInfo().getUUID().equals(from.getUUID())) break;
            else p = null;
        }

        if(p != null){
            Main.history.get(i).push(msg, false);
            UsersList.getInstance().notifyNewMsg(i);
        }
        else{

            Client peer = Client.fetchPeer(from.getIp(), Main.RMI_PORT);
            if(peer != null){

                Main.users.addElement(peer);
                UsersList.getInstance().addItem(peer.getInfo().getUserName());

                ChatHistory ch = new ChatHistory(from);
                ch.push(msg, false);

                Main.history.addElement(ch);
                System.out.println("Novo peer detectado: " + from.getUserName() + " (" + from.getUUID() + ", "+ from.getIp() +")");

            }
        }
    }

    public static boolean sendMessage(int userIndex, String msg){

        Client peer = Main.users.get(userIndex);
        Message m = new Message(msg);

        if(peer.send(Main.selfInfo, m)){

            Main.history.get(userIndex).push(m, true).resetUnreadedCounter();
            PeerInfo info = peer.getInfo();

            System.out.println("Envia: "+info.getUserName()+" ("+info.getUUID()+", "+info.getIp()+"):\n\t"+m.getBody()+" ("+m.getDate()+")\n");
            return true;
        }
        else{
            return false;
        }
    }
}
