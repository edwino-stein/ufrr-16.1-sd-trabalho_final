package ui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.Component;

public abstract class Button extends Component implements ActionListener {

    protected String content;
    protected String tooltip;

    protected void init(){

        this.compObj = new JButton();
        this.getJComponent().setVerticalTextPosition(JLabel.BOTTOM);
        this.getJComponent().setHorizontalTextPosition(JLabel.CENTER);
        this.getJComponent().addActionListener(this);

        this.setContent(this.content);
        this.setTootip(tooltip);

        this.updatePos();
    }

    public abstract void onClick(ActionEvent e);

    public String getContent(){
        return this.content;
    }

    public Button setContent(String content){
        this.getJComponent().setText(content);
        this.content = content;
        return this;
    }

    public String getTooltip(){
        return this.tooltip;
    }

    public Button setTootip(String tooltip){
        this.getJComponent().setToolTipText(tooltip);
        this.tooltip = tooltip;
        return this;
    }

    public void actionPerformed(ActionEvent e) {
        this.onClick(e);
    }

    public JButton getJComponent(){
        return (JButton) this.compObj;
    }
}
