package core.view;
import ui.Button;
import java.awt.event.ActionEvent;

public class ListUpdateBtn extends Button {

    private ListUpdateBtn(){
        this.content = "Atualizar Lista";
        this.tooltip = "Atualizar lista de usuários";
        this.x = 10;
        this.y = 395;
        this.width = 165;
        this.height = 20;
        this.init();
    }

    public void onClick(ActionEvent e){
        System.out.println("Atualizar lista de usuários!");
    }

    private static final ListUpdateBtn INSTANCE = new ListUpdateBtn();
    public static ListUpdateBtn getInstance(){
        return ListUpdateBtn.INSTANCE;
    }
}
