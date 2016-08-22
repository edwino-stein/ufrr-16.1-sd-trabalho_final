package core.view;
import ui.HtmlView;

public class ChatView extends HtmlView {

    private ChatView(){
        this.x = 198;
        this.y = 40;
        this.width = 292;
        this.height = 345;
        this.init();
    }

    private static final ChatView INSTANCE = new ChatView();
    public static ChatView getInstance(){
        return ChatView.INSTANCE;
    }
}
