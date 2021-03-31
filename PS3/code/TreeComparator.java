import java.util.Comparator;

public class TreeComparator implements Comparator<BinaryTree<FrequencyNode>> {

    @Override
    /**
     * returns -1 if less than, 0 if equal and 1 if greater than
     */

    public int compare(BinaryTree<FrequencyNode> t1, BinaryTree<FrequencyNode> t2) {
        if( t1.getData().getFrequency() < t2.getData().getFrequency()) return -1;
        else if( t1.getData().getFrequency() == t2.getData().getFrequency()) return 0;
        return 1;
    }
}
