package core.view;
import ui.Label;

public class PeerUserLabel extends Label{

    private PeerUserLabel(){
        this.content = "Nome do Usuário do peer";
        this.x = 198;
        this.y = 10;
        this.width = 292;
        this.height = 20;
        this.init();
    }

    private static final PeerUserLabel INSTANCE = new PeerUserLabel();
    public static PeerUserLabel getInstance(){
        return PeerUserLabel.INSTANCE;
    }
}
