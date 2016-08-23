package core.view;
import ui.HtmlView;
import java.text.SimpleDateFormat;

import core.Main;
import core.ChatHistory;
import core.view.PeerUserLabel;
import rmi.Message;
import rmi.PeerInfo;

public class ChatView extends HtmlView {

    private ChatView(){
        this.x = 198;
        this.y = 40;
        this.width = 292;
        this.height = 345;
        this.init();
    }

    public void showChatOf(int index){

        ChatHistory history = Main.history.get(index);
        PeerInfo self = Main.selfInfo;
        PeerInfo remote = history.getUser();
        PeerUserLabel.getInstance().setContent(history.getUser().getUserName());

        this.resetDocument();
        for(int i = 0; i < history.getTotal(); i++){

            Message msg = history.get(i);

            if(history.isSelf(i)){
                this.addSelfMsg(msg);
            }
            else{
                this.addRemoteMsg(msg);
            }
        }

    }

    public void addSelfMsg(Message msg){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String snippet = "<p align=\"left\">"+
                            "&nbsp;&nbsp;&nbsp;"+
                            "<font size=\"5\" color=\"#555555\">"+msg.getBody()+"</font>"+
                            "<br/>&nbsp;&nbsp;&nbsp;"+
                            "<font size=\"3\" color=\"#777777\">"+sdf.format(msg.getDate())+"</font>"+
                         "</p>";

        this.append(this.getBody(), snippet);
    }

    public void addRemoteMsg(Message msg){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String snippet = "<p align=\"right\">"+
                            "<font size=\"5\" color=\"#2B91AF\">"+msg.getBody()+"</font>"+
                            "&nbsp;&nbsp;&nbsp;<br/>"+
                            "<font size=\"3\" color=\"#4fa7c1\">"+sdf.format(msg.getDate())+"</font>"+
                            "&nbsp;&nbsp;&nbsp;"+
                         "</p>";

        this.append(this.getBody(), snippet);
    }

    private static final ChatView INSTANCE = new ChatView();
    public static ChatView getInstance(){
        return ChatView.INSTANCE;
    }
}
