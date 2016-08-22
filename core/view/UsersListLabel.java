package core.view;
import ui.Label;

public class UsersListLabel extends Label{

    private UsersListLabel(){
        this.content = "Usuários disponives:";
        this.x = 10;
        this.y = 40;
        this.width = 165;
        this.height = 20;
        this.init();
    }

    private static final UsersListLabel INSTANCE = new UsersListLabel();
    public static UsersListLabel getInstance(){
        return UsersListLabel.INSTANCE;
    }
}
