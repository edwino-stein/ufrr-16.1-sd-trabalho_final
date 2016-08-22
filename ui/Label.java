package ui;
import javax.swing.*;
import ui.Component;

public abstract class Label extends Component {

    protected String content;

    protected void init(){

        this.compObj = new JLabel();
        this.getJComponent().setVerticalTextPosition(JLabel.BOTTOM);
        this.getJComponent().setHorizontalTextPosition(JLabel.CENTER);

        this.setContent(this.content);
        this.updatePos();
    }

    public String getContent(){
        return this.content;
    }

    public Label setContent(String content){
        this.getJComponent().setText(content);
        this.content = content;
        return this;
    }

    public JLabel getJComponent(){
        return (JLabel) this.compObj;
    }
}
