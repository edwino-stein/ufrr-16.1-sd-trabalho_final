package core.view;
import ui.List;
import javax.swing.event.ListSelectionEvent;

import core.Main;
import core.Client;
import core.view.ChatView;

public class UsersList extends List {

    private UsersList(){
        this.x = 10;
        this.y = 65;
        this.width = 165;
        this.height = 320;
        this.init();
        this.updateList();
    }

    public void onChange(int selectedItem, int lastSelectedItem, boolean isAdjusting, ListSelectionEvent e){
        ChatView.getInstance().showChatOf(selectedItem);
    }

    public void notifyNewMsg(int index){
        if(index == this.getSelectedIndex()){
            ChatView.getInstance().showChatOf(index);
        }
    }

    public void updateList(){

        for(int i = 0; i < Main.users.size(); i++){
            Client p = Main.users.get(i);
            this.addItem(i, p.getInfo().getUserName());
        }

        this.updateSelection();
    }

    public void updateSelection(){
        if(this.count() > 0){
            if(this.getSelectedIndex() >= 0 && this.getSelectedIndex() < this.count()){
                this.selectItem(this.getSelectedIndex());
            }
            else {
                this.selectItem(0);
            }
        }
    }

    private static final UsersList INSTANCE = new UsersList();
    public static UsersList getInstance(){
        return UsersList.INSTANCE;
    }
}
