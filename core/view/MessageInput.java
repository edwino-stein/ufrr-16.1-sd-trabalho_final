package core.view;
import ui.InputText;

public class MessageInput extends InputText {

    private MessageInput(){
        this.x = 195;
        this.y = 395;
        this.width = 240;
        this.height = 20;
        this.init();
    }

    private static final MessageInput INSTANCE = new MessageInput();
    public static MessageInput getInstance(){
        return MessageInput.INSTANCE;
    }
}
