package game;

import java.util.ArrayList;

/**
 * Created by jonathanbrodie on 3/24/15.
 */
public class DistinctList<E> extends ArrayList<E> {
    @Override
    public boolean add(E e) {
        boolean mybool;

        if (this.contains(e)) {
            mybool= false;
        }
        else {
            mybool=super.add(e);
        }
        return mybool;
    }
}
