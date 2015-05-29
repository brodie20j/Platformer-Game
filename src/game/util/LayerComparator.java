package game.util;

import game.*;
import game.Object;

import java.util.Comparator;

/**
 * Created by jonathanbrodie on 5/13/15.
 */
public class LayerComparator implements Comparator<game.Object> {


        @Override
        public int compare(Object o1, Object o2) {

            // descending order (ascending order would be:
            // o1.getGrade()-o2.getGrade())
            int layoutX1=o1.getLayer();
            int layoutX2=o2.getLayer();

            return layoutX1-layoutX2;

            //return
        }
    }

