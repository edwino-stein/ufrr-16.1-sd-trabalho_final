package core.view;
import ui.Button;
import java.awt.event.ActionEvent;

public class UserEditBtn extends Button {

    private UserEditBtn(){
        this.content = "Editar";
        this.tooltip = "Editar nome do usuário";
        this.x = 125;
        this.y = 10;
        this.width = 50;
        this.height = 20;
        this.init();
    }

    public void onClick(ActionEvent e){
        System.out.println("Editar nome do usuário!");
    }

    private static final UserEditBtn INSTANCE = new UserEditBtn();
    public static UserEditBtn getInstance(){
        return UserEditBtn.INSTANCE;
    }
}
