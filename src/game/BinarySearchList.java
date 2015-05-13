package game;

/**
 * Created by jonathanbrodie on 3/2/15.
 */
import java.util.List;
public class BinarySearchList {
    private List<Object> list;
    public BinarySearchList() {
    }
    public int getIndex(List<Object> sortedList,Object oObject) {

        if (sortedList.isEmpty()) return -1;
        int i=(int)Math.floor(sortedList.size() / 2.0)-1;

        if (sortedList.get(i) == oObject) {
            return i;
        }
        else if (sortedList.get(i).getLayoutX() > oObject.getLayoutX()) {
            sortedList=sortedList.subList(0,i+1);
            return this.getIndex(sortedList,oObject);
        }
        else {
            sortedList=sortedList.subList(i-1,sortedList.size());
            return this.getIndex(sortedList,oObject);
        }
    }
}
