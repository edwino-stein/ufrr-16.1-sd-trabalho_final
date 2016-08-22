package ui;

import javax.swing.*;

public abstract class Component {

    protected JComponent compObj;

    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected abstract void init();

    public int getX(){
        return this.x;
    }

    public Component setX(int x){
        return this.setX(x, true);
    }

    public Component setX(int x, boolean updatePos){
        this.x = x;
        if(updatePos) this.updatePos();
        return this;
    }

    public int getY(){
        return this.y;
    }

    public Component setY(int y){
        return this.setY(y, true);
    }

    public Component setY(int y, boolean updatePos){
        this.y = y;
        if(updatePos) this.updatePos();
        return this;
    }

    public int getWidth(){
        return this.width;
    }

    public Component setWidth(int width, boolean updatePos){
        this.width = width;
        if(updatePos) this.updatePos();
        return this;
    }

    public Component setWidth(int width){
        return this.setWidth(width, true);
    }

    public int getHeight(){
        return this.height;
    }

    public Component setHeight(int height, boolean updatePos){
        this.height = height;
        if(updatePos) this.updatePos();
        return this;
    }

    public Component setHeight(int height){
        return this.setHeight(height, true);
    }

    protected void updatePos(){
        this.compObj.setBounds(
            this.x,
            this.y,
            this.width,
            this.height
        );
    }

    public JComponent getJComponent(){
        return this.compObj;
    }
}
