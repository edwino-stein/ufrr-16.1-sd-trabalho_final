package core.view;
import ui.Label;
import core.Main;

public class UserLabel extends Label{

    private UserLabel(){
        this.content = "Nome do Usuário";
        this.x = 10;
        this.y = 10;
        this.width = 165;
        this.height = 20;
        this.init();
    }

    protected void init(){
        super.init();
        this.setContent(Main.selfInfo.getUserName());
    }

    private static final UserLabel INSTANCE = new UserLabel();
    public static UserLabel getInstance(){
        return UserLabel.INSTANCE;
    }
}
