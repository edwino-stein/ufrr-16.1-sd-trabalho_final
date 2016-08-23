package core.services;

import rmi.PeerInfo;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.Thread;
import java.lang.Runnable;
import java.net.SocketException;
import java.net.UnknownHostException;

public class BroadcastServer implements Runnable {

    protected Thread th;

    protected PeerInfo info;
    protected int port;
    protected DatagramSocket socket;
    protected String targetIp = "255.255.255.255";
    protected int bufferSize = 36;
    protected int timeout = 1000;
    protected int packetCount;
    protected int packetLimit;
    protected boolean runnig = false;

    public BroadcastServer(PeerInfo info, int port){
        this.info = info;
        this.port = port;
    }

    public void run(){

        String uuid = this.info.getUUID();
        byte[] data = new byte[this.bufferSize];
        data = uuid.getBytes();

        while(this.runnig){

            this.broadcast(data);

            if(this.packetLimit > 0 && this.packetCount >= this.packetLimit){
                this.forceStop();
                return;
            }

            try {
                Thread.sleep(this.timeout);
            } catch (Exception e) {}
        }

    }

    public boolean start(int packetLimit){

        this.runnig = true;
        this.packetCount = 0;
        this.packetLimit = packetLimit;

        try{
            this.socket = new DatagramSocket();
            this.socket.setBroadcast(true);
        }catch(Exception e){
            return false;
        }

        this.th = new Thread(this);
        this.th.start();
        return true;
    }

    public void start(){
        this.start(0);
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

    protected void forceStop(){
        this.runnig = false;
        this.socket = null;
        Thread.currentThread().interrupt();
        this.th = null;
    }

    public boolean isRunnig(){
        return this.runnig;
    }

    public int getPacketCount(){
        return this.packetCount;
    }

    public void broadcast(byte[] data){
        try{
            DatagramPacket packet = this.createPacket(data);
            this.socket.send(packet);
            this.packetCount++;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    protected DatagramPacket createPacket(byte[] data) throws UnknownHostException {
        return new DatagramPacket(data, data.length, InetAddress.getByName(this.targetIp), this.port);
    }
}
