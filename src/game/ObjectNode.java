package game;

import java.io.Serializable;

/**
 * Created by jonathanbrodie on 3/26/15.
 */
public class ObjectNode implements Serializable{
    private Class type;
    private Class id;
    private double startX;
    private double startY;
    public ObjectNode(Class type, Class id, double startx, double starty) {
        this.type=type;
        this.id=id;
        this.startX=startx;
        this.startY=starty;
    }

    public double getXPosition() {return this.startX;}
    public double getYPosition() {return this.startY;}
    public Class getObjectSuper() {return this.type;}
    public Class getObjectID() {return this.id;}

}
