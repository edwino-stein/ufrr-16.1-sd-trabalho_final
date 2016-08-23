package core.view;
import ui.Button;
import java.awt.event.ActionEvent;
import core.Main;
import core.view.UsersList;
import core.view.ChatView;
import core.view.MessageInput;

public class SendBtn extends Button {

    private SendBtn(){
        this.content = "Enviar";
        this.tooltip = "Enviar menssagem";
        this.x = 400;
        this.y = 395;
        this.width = 90;
        this.height = 20;
        this.init();
    }

    public void onClick(ActionEvent e){

        int index = UsersList.getInstance().getSelectedIndex();
        MessageInput input = MessageInput.getInstance();

        if(Main.sendMessage(index, input.getValue())){
            input.clear();
            ChatView.getInstance().showChatOf(index);
        }
    }

    private static final SendBtn INSTANCE = new SendBtn();
    public static SendBtn getInstance(){
        return SendBtn.INSTANCE;
    }
}
