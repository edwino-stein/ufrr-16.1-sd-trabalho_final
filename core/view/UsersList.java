package core.view;
import ui.List;
import javax.swing.event.ListSelectionEvent;

public class UsersList extends List {

    private UsersList(){
        this.x = 10;
        this.y = 65;
        this.width = 165;
        this.height = 320;
        this.init();
    }

    public void onChange(int selectedItem, int lastSelectedItem, boolean isAdjusting, ListSelectionEvent e){
        System.out.println("Selecionado item: " + String.valueOf(selectedItem));
    }

    private static final UsersList INSTANCE = new UsersList();
    public static UsersList getInstance(){
        return UsersList.INSTANCE;
    }
}
