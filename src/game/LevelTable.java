package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import game.*;

/**
 * Created by jonathanbrodie on 4/10/15.
 */
public class LevelTable implements Serializable {
    private Hashtable<String, Double> xMap = new Hashtable<String, Double>();
    private Hashtable<String, Double> yMap = new Hashtable<String, Double>();
    //ID is the immediate class of the object we are storing (EG: koopa, goomba, etc.)
    private Hashtable<String, Class> idMap=new Hashtable<String, Class>();
    //Type is the parent class of the object we are storing (EG: block, object, enemy);
    private Hashtable<String, Class> typeMap=new Hashtable<String, Class>();

    private Hashtable<String, Object> objectMap=new Hashtable<String, Object>();
    private List<String> keyList=new ArrayList<String>();
    private String backgroundString="";
    private String levelName="";

    public LevelTable() {

    }
    //directly store a node in the list of tables
    public void store(ObjectNode myNode) {
        double xPos=myNode.getXPosition();
        double yPos=myNode.getYPosition();
        Class typeClass=myNode.getObjectSuper();
        Class idClass=myNode.getObjectID();
        String myKey=idClass.toString();
        if (this.keyList.contains(myKey)) {
            int nCount=1;
            while (this.keyList.contains(myKey)) {
                myKey=idClass.toString()+String.valueOf(nCount);
                nCount++;
            }
        }
        this.keyList.add(myKey);
        this.xMap.put(myKey, xPos);
        this.yMap.put(myKey, yPos);
        this.idMap.put(myKey, idClass);
        this.typeMap.put(myKey, typeClass);
    }
    //This allows a bit more flexibility, but overwrites the key regardless of whether it is being used.
    public void storeAs(String myKey, double xPos, double yPos, Class type, Class id) {
        if (this.keyList.contains(myKey)) {
            this.keyList.remove(myKey);
        }
        this.keyList.add(myKey);
        this.xMap.put(myKey,xPos);
        this.yMap.put(myKey,yPos);
        this.idMap.put(myKey,id);
        this.typeMap.put(myKey,type);

    }
    public void storeObjectAs(String myKey, Object myObject) {
        if (this.keyList.contains(myKey)) {
            this.keyList.remove(myKey);
        }
        this.keyList.add(myKey);
        this.objectMap.put(myKey,myObject);
    }

    public void delete(String myKey) {
        this.xMap.remove(myKey);
        this.yMap.remove(myKey);
        this.idMap.remove(myKey);
        this.typeMap.remove(myKey);
        this.keyList.remove(myKey);
        this.objectMap.remove(myKey);
    }
    public double getX(String myKey) {
        return this.xMap.get(myKey);
    }
    public double getY(String myKey) {
        return this.yMap.get(myKey);
    }
    public Class getID(String myKey) {
        return this.idMap.get(myKey);
    }
    public Class getType(String myKey) {
        return this.typeMap.get(myKey);
    }
    public List<String> getKeyList() {
        return this.keyList;
    }

    public String getBackgroundString() {
        return this.backgroundString;
    }
    public void setBackgroundString(String myString) {
        this.backgroundString=myString;
    }
    public String getLevelName() {
        return this.levelName;
    }
    public void setLevelName(String myString) {
        this.levelName=myString;
    }
    public Object getObject(String myKey) {
        return this.objectMap.get(myKey);
    }
}
