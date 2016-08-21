package rmi;

import rmi.interfaces.MessageInterface;
import java.util.Date;

public class Message implements MessageInterface {

    private static final long serialVersionUID = 1L;

    protected String body;
    protected Date date;

    public Message(String body){
        this.body = body;
        this.date = new Date();
    }

    public String getBody(){
        return this.body;
    }

    public Date getDate(){
        return this.date;
    }
}
