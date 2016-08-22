package core.view;
import ui.Button;
import java.awt.event.ActionEvent;

public class SendBtn extends Button {

    private SendBtn(){
        this.content = "Enviar";
        this.tooltip = "Enviar menssagem";
        this.x = 440;
        this.y = 395;
        this.width = 50;
        this.height = 20;
        this.init();
    }

    public void onClick(ActionEvent e){
        System.out.println("Enviar menssagem!");
    }

    private static final SendBtn INSTANCE = new SendBtn();
    public static SendBtn getInstance(){
        return SendBtn.INSTANCE;
    }
}
