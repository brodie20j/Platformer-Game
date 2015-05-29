package game.util;

/**
 * Created by jonathanbrodie on 3/1/15.
 */
import game.*;
import game.Object;

import java.util.Comparator;
public class ObjectComparator implements Comparator<game.Object> {
    int xBase;

    public ObjectComparator() {
        this.xBase=0;
    }

    @Override
    public int compare(Object o1, Object o2) {

        // descending order (ascending order would be:
        // o1.getGrade()-o2.getGrade())
        int layoutX1=(int) o1.getLayoutX();
        int layoutX2=(int) o2.getLayoutX();

        return layoutX1-layoutX2;

        //return
    }
}
