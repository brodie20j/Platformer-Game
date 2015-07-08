package LevelEdit;

/**
 * Created by jonathanbrodie on 6/14/15.
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectConstructor {

    public ObjectConstructor() {

    }


    /**
     * Returns the array of classes needed to satisfy the best constructor
     * @param myClass
     * @return
     */
    public static Class[] getBestParameters(Class myClass) {
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

    public static String getConstructorName(Class myClass) {
        Constructor constructor=getBestConstructor(myClass);
        return constructor.toString();
    }

    /**
     * Returns the most well-defined constructor
     * @param myClass
     * @return
     */
    public static Constructor getBestConstructor(Class myClass) {
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
    public static game.Object constructObject(Constructor constructor, java.lang.Object[] params) {
        game.Object myObject=null;

        try {
            constructor.setAccessible(true);
            myObject=(game.Object) constructor.newInstance(params);
        }
        catch (InstantiationException e) {

        }
        catch (IllegalAccessException e) {

        }
        catch (InvocationTargetException e) {

        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return myObject;

    }


    public static Constructor getPositionConstructor(Class myClass) {
        try {
            return myClass.getConstructor(Double.class, Double.class);

        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();

        }
        return null;
    }
    public static Class[] getPositionParameters(Class myClass) {
        try {
            return myClass.getConstructor(Double.class, Double.class).getParameterTypes();

        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();

        }
        return null;
    }
    //need to do some more planning here with regards to what constructors are allowed for
    public static game.Object constructFromArgs(Constructor constructor,String[] param) {
        List<Object> tempArray=new ArrayList<Object>();
        Class[] args=constructor.getParameterTypes();
        for (int i=0; i< args.length; i++) {
            Class current=args[i];
            if (current instanceof Double)

        }
    }
}
