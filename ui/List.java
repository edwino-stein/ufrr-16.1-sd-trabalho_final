package ui;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import ui.Component;

public abstract class List extends Component implements ListSelectionListener {

    protected DefaultListModel<String> data;
    protected JList<String> listView;
    protected int lastSelectedItem;

    protected void init(){

        this.lastSelectedItem = -1;

        this.data = new DefaultListModel<String>();
        this.listView = new JList<String>(this.data);

        this.listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listView.setLayoutOrientation(JList.VERTICAL);
        this.listView.setVisibleRowCount(-1);
        this.listView.addListSelectionListener(this);
        this.listView.setBounds(this.x, this.y, this.width, this.height);

        this.compObj = new JScrollPane(this.listView);
        this.updatePos();
    }

    public abstract void onChange(int selectedItem, int lastSelectedItem, boolean isAdjusting, ListSelectionEvent e);

    public void valueChanged(ListSelectionEvent e){

        if(this.lastSelectedItem == this.listView.getSelectedIndex()) return;

        this.onChange(
            this.listView.getSelectedIndex(),
            this.lastSelectedItem,
            !e.getValueIsAdjusting(),
            e
        );

        this.lastSelectedItem = this.listView.getSelectedIndex();
    }

    public int getSelectedIndex(){
        return this.lastSelectedItem;
    }

    public List selectItem(int index){
        this.listView.setSelectedIndex(index);
        return this;
    }

    public List clearSelection(){
        this.listView.clearSelection();
        return this;
    }

    public boolean hasSelectedItem(){
        return !this.listView.isSelectionEmpty();
    }

    public List addItem(int index, String label){
        this.data.add(index, label);
        return this;
    }

    public List addItem(String label){
        this.data.add(this.data.getSize(), label);
        return this;
    }

    public List rmItem(int index){
        if(index < 0 || index >= this.data.getSize()) return this;
        this.data.remove(index);
        return this;
    }


    public String getItem(int index){
        if(index < 0 || index >= this.data.getSize()) return null;
        return this.data.get(index);
    }

    public List setItem(int index, String label){
        if(index < 0 || index >= this.data.getSize()) return null;
        this.data.set(index, label);
        return this;
    }

    public List clear(){
        this.data.clear();
        return this;
    }

    public boolean isEmpty(){
        return this.data.isEmpty();
    }

    public int count(){
        return this.data.getSize();
    }

    public JList<String> getJList(){
        return this.listView;
    }

    public JScrollPane getJComponent(){
        return (JScrollPane) this.compObj;
    }
}
