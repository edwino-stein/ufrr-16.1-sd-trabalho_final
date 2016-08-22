package core.services;

import core.services.BroadcastClientCallback;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.Thread;
import java.lang.Runnable;
import java.net.SocketException;
import java.net.UnknownHostException;

public class BroadcastClient implements Runnable {

    protected Thread th;

    protected int port;
    protected BroadcastClientCallback target;
    protected DatagramSocket socket;
    protected String targetIp = "0.0.0.0";
    protected int bufferSize = 36;
    protected boolean runnig = false;

    public BroadcastClient(BroadcastClientCallback target, int port){
        this.target = target;
        this.port = port;
    }

    public void run(){

        while(this.runnig){

            byte[] buffer = new byte[this.bufferSize];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try{
                this.socket.receive(packet);
                this.target.onReceiveBroadcast(new String(packet.getData()), packet.getAddress().getHostAddress());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean start(){

        this.runnig = true;

        try{

            this.socket = new DatagramSocket(this.port, InetAddress.getByName(this.targetIp));
            this.socket.setBroadcast(true);

        }catch(Exception e){
            return false;
        }

        this.th = new Thread(this);
        this.th.start();
        return true;
    }

    public void stop(){

        this.runnig = false;
        this.socket = null;

        try{
            this.th.join();
        } catch(Exception e){
            e.printStackTrace();
        }

        this.th = null;
    }
}
