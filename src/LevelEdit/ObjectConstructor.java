package LevelEdit;

/**
 * Created by jonathanbrodie on 6/14/15.
 */

import java.lang.reflect.Constructor;
import java.util.ArrayList;
public class ObjectConstructor {

    public ObjectConstructor() {

    }

    public Class[] getBestParameters(Class myClass) {
        Constructor[] constructors = myClass.getConstructors();
        int biggest=-1;
        Class[] bestParameter=null;
        Constructor bestConstructor;
        for (int i=0; i<constructors.length; i++) {
            Constructor current=constructors[i];
            Class[] parameterTypes = current.getParameterTypes();
            if (parameterTypes.length > biggest) {
                bestConstructor=current;
                bestParameter=parameterTypes;
                biggest=parameterTypes.length;
            }
        }
        return bestParameter;
    }
    public Constructor getBestConstructor(Class myClass) {
        Constructor[] constructors = myClass.getConstructors();
        int biggest=-1;
        Class[] bestParameter=null;
        Constructor bestConstructor=null;
        for (int i=0; i<constructors.length; i++) {
            Constructor current=constructors[i];
            Class[] parameterTypes = current.getParameterTypes();
            if (parameterTypes.length > biggest) {
                bestConstructor=current;
                bestParameter=parameterTypes;
                biggest=parameterTypes.length;
            }
        }
        return bestConstructor;
    }
    public Object constructObject(Constructor constructor, Class[] params) {
        Object myObject=null;

        return myObject;

    }
}
