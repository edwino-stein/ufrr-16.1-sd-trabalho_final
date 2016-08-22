package ui;
import javax.swing.*;
import ui.Component;

public abstract class InputText extends Component {

    protected int columns = 10;
    protected String initialValue;

    protected void init(){
        this.compObj = new JTextField(this.initialValue, this.columns);
        this.updatePos();
    }

    public String getValue(){
        return this.getJComponent().getText();
    }

    public InputText setValue(String value){
        this.getJComponent().setText(value);
        return this;
    }

    public int getColumns(){
        return this.columns;
    }

    public InputText setColumns(int columns){
        this.getJComponent().setColumns(columns);
        this.columns = columns;
        return this;
    }

    public void clear(){
        this.setValue("");
    }

    public JTextField getJComponent(){
        return (JTextField) this.compObj;
    }
}
