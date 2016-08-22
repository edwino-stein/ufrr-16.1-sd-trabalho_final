package core.view;

import ui.Window;
import core.view.UserLabel;
import core.view.PeerUserLabel;
import core.view.UsersListLabel;
import core.view.UserEditBtn;
import core.view.ListUpdateBtn;
import core.view.ChatView;
import core.view.UsersList;
import core.view.MessageInput;
import core.view.SendBtn;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MainWindow extends Window{

    private MainWindow(){
        this.title = "Chat SD P2P";
        this.width = 500;
        this.height = 450;
        this.init();
    }

    public void build(){

        System.out.println("Inicializando a GUI...");

        this.add(UserLabel.getInstance());
        this.add(UserEditBtn.getInstance());
        this.add(UsersListLabel.getInstance());
        this.add(UsersList.getInstance());
        this.add(ListUpdateBtn.getInstance());
        this.add(PeerUserLabel.getInstance());
        this.add(ChatView.getInstance());
        this.add(MessageInput.getInstance());
        this.add(SendBtn.getInstance());

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBounds(180, 5, 10, 417);
        this.add(separator);

        this.setVisible(true);
    }

    private static final MainWindow INSTANCE = new MainWindow();
    public static MainWindow getInstance(){
        return MainWindow.INSTANCE;
    }
}
