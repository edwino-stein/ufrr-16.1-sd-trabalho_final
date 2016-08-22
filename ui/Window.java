package ui;
import javax.swing.*;
import ui.Component;

public abstract class Window {

    protected JFrame frame;
    protected JPanel mainPanel;

    protected String title = "Titulo";
    protected int width = 100;
    protected int height = 100;
    protected boolean visible = false;

    protected void init(){
        this.frame = new JFrame(this.title);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);
        this.frame.add(this.mainPanel);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(this.width, this.height);
        this.frame.setResizable(false);
    }

    public void setVisible(boolean visibility){
        this.visible = visibility;
        this.frame.setVisible(visibility);
    }

    public boolean isVisible(){
        return this.visible;
    }

    public String getTitle(){
        return this.title;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public Window add(Component component){
        this.mainPanel.add(component.getJComponent());
        return this;
    }

    public Window add(JComponent component){
        this.mainPanel.add(component);
        return this;
    }
}
