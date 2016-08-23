package core;

import rmi.Message;
import rmi.PeerInfo;
import java.util.Vector;

public class ChatHistory {

    protected Vector<Message> history;
    protected Vector<Boolean> historyOwner;
    protected int unreadedCounter;
    protected PeerInfo user;

    public ChatHistory(PeerInfo user){
        this.user = user;
        this.history = new Vector<Message>();
        this.historyOwner = new Vector<Boolean>();
        this.unreadedCounter = 0;
    }

    public int getTotal(){
        return this.history.size();
    }

    public Message get(int index){
        return this.history.get(index);
    }

    public boolean isSelf(int index){
        return this.historyOwner.get(index).booleanValue();
    }

    public ChatHistory push(Message msg, boolean self){
        this.history.addElement(msg);
        this.historyOwner.addElement(new Boolean(self));
        this.unreadedCounter++;
        return this;
    }

    public int getUnreadedCounter(){
        return this.unreadedCounter;
    }

    public void resetUnreadedCounter(){
        this.unreadedCounter = 0;
    }

    public PeerInfo getUser(){
        return this.user;
    }
}
